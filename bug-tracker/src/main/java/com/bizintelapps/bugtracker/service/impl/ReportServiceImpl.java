/*
 *  Copyright 2008 intesar.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package com.bizintelapps.bugtracker.service.impl;

import com.bizintelapps.bugtracker.dao.ProjectReportDao;
import com.bizintelapps.bugtracker.dao.ProjectUsersDao;
import com.bizintelapps.bugtracker.dao.UserReportDao;
import com.bizintelapps.bugtracker.dao.UsersDao;
import com.bizintelapps.bugtracker.dto.ProjectReportDto;
import com.bizintelapps.bugtracker.dto.TaskDto;
import com.bizintelapps.bugtracker.dto.UserReportDto;
import com.bizintelapps.bugtracker.dtoa.ProjectReportDtoA;
import com.bizintelapps.bugtracker.dtoa.UserReportDtoA;
import com.bizintelapps.bugtracker.entity.Project;
import com.bizintelapps.bugtracker.entity.ProjectReport;
import com.bizintelapps.bugtracker.entity.ProjectUsers;
import com.bizintelapps.bugtracker.entity.Task;
import com.bizintelapps.bugtracker.entity.UserReport;
import com.bizintelapps.bugtracker.entity.Users;
import com.bizintelapps.bugtracker.service.ReportService;
import com.bizintelapps.bugtracker.service.TaskService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public void processTask(Task task, TaskDto dto) {
        processTaskOwner(task, dto);
        processTaskProject(task, dto);
        processTaskAssignee(task, dto);
        processTaskCompletion(task, dto);
    }

    @Override
    public List<UserReportDto> getUserReports(Integer user, Integer maxReports, String requestedBy) {
        if (maxReports == null || maxReports < 1) {
            maxReports = 3;
        }
        Users requestedUser = usersDao.findByUsername(requestedBy);
        List<UserReportDto> dtos = new ArrayList<UserReportDto>();
        if (user == null || user.equals(0)) {
            user = requestedUser.getId();
        }
        // only admin, self can see graph
        if (user.equals(requestedUser.getId()) || requestedUser.isIsAdministrator()) {
            List<UserReport> list = userReportDao.findByUser(user, maxReports);
            if (list == null || list.size() == 0) {
                Calendar c = Calendar.getInstance();
                UserReport up = new UserReport(user, c.get(Calendar.MONTH), c.get(Calendar.YEAR), 0);
                up.setUser(user);
                list.add(up);
            }
            // copy for display
            for (UserReport ur : list) {
                dtos.add(userReportDtoA.copyForDisplay(ur));
            }
        }
        return dtos;
    }

    @Override
    public List<UserReportDto> getRandomUserReports(Integer maxReports, String requestedBy) {
        if (maxReports == null || maxReports < 1) {
            maxReports = 3;
        }
        Users requestedUser = usersDao.findByUsername(requestedBy);
        List<UserReportDto> dtos = new ArrayList<UserReportDto>();
        Integer userId = null;
        // if admin get any user        
        // else get self        
        if (requestedUser.isIsAdministrator()) {
            List<Users> users = usersDao.findByOrganizationId(requestedUser.getOrganization().getId());
            Random r = new Random();
            int randmonUser = r.nextInt(users.size());
            userId = users.get(randmonUser).getId();
        } else {
            userId = requestedUser.getId();
        }
        return getUserReports(userId, maxReports, requestedBy);
    }

    @Override
    public UserReportDto getCurrentUserReport(Integer user, String requestedBy) {
        Users requestedUser = usersDao.findByUsername(requestedBy);
        UserReportDto dto = null;
        if (user == null || user.equals(0)) {
            user = requestedUser.getId();
        }
        // only admin, self can see graph
        if (user.equals(requestedUser.getId()) || requestedUser.isIsAdministrator()) {
            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            UserReport userReport = userReportDao.findByUserMonthAndYear(user, month, year);
            // copy for display
            dto = userReportDtoA.copyForDisplay(userReport);
        }
        return dto;
    }

    @Override
    public UserReportDto getUserSummary(Integer user, String requestedBy) {
        Users requestedUser = usersDao.findByUsername(requestedBy);
        UserReportDto dto = null;
        if (user == null || user.equals(0)) {
            user = requestedUser.getId();
        }
        // only admin, self can see graph
        if (user.equals(requestedUser.getId()) || requestedUser.isIsAdministrator()) {
            dto = (UserReportDto) userReportDao.findUserSummary(user);
        }
        return dto;
    }

    @Override
    public List<UserReportDto> getAllUserReport(int month, int year, String requestedBy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ProjectReportDto> getProjectReports(Integer project, Integer maxReports, String requestedBy) {
        if (maxReports == null || maxReports < 1) {
            maxReports = 3;
        }
        Users requestedUser = usersDao.findByUsername(requestedBy);
        Integer org = null;
        List<ProjectReport> list = null;
        List<ProjectReportDto> dtos = new ArrayList<ProjectReportDto>();
        if (project == null || project.equals(0)) {
            org = requestedUser.getOrganization().getId();
            list = projectReportDao.findByOrganizationForTodo(org, maxReports);
        } else {
            list = projectReportDao.findByProject(project, maxReports);
        }
        // copy for display
        for (ProjectReport pr : list) {
            dtos.add(projectReportDtoA.copyForDisplay(pr));
        }
        return dtos;
    }

    @Override
    public List<ProjectReportDto> getRandomProjectReports(Integer maxReports, String requestedBy) {
        Integer project = null;
        Users requestedUser = usersDao.findByUsername(requestedBy);
        List<ProjectUsers> projectUserses = projectUsersDao.findByUserId(requestedUser.getId());
        Random r = new Random();
        int randmonProject = r.nextInt(projectUserses.size() + 1); // +1 for TODO
        if (randmonProject < projectUserses.size()) { // if randomProject >= size then its TODO
            project = projectUserses.get(randmonProject).getProject().getId();
        }
        return getProjectReports(project, maxReports, requestedBy);
    }

    @Override
    public ProjectReportDto getProjectReportSummary(Integer project, String requestedBy) {
        Users requestedUser = usersDao.findByUsername(requestedBy);
        ProjectReportDto dto = null;
        Integer org = null;
        if (project == null || project.equals(0)) {
            org = requestedUser.getOrganization().getId();
        //list = projectReportDao.findByOrganizationForTodo(org);
        } else {
            dto = (ProjectReportDto) projectReportDao.findProjectSummary(project);
        }
        return dto;
    }

    @Override
    public ProjectReportDto getCurrentProjectReport(Integer project, String requestedBy) {
        Users requestedUser = usersDao.findByUsername(requestedBy);
        Integer org = null;
        ProjectReport list = null;
        ProjectReportDto dto = null;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        if (project == null || project.equals(0)) {
            org = requestedUser.getOrganization().getId();
            list = projectReportDao.findByOrganizationMonthAndYear(org, month, year);
        } else {
            list = projectReportDao.findByProjectMonthAndYear(project, month, year);
        }
        // copy for display
        dto = projectReportDtoA.copyForDisplay(list);
        return dto;
    }
    // ------------------------------- private methods ---------------------//

    /**
     * owner report is effected only when task is added or deleted
     * @param t
     * @param dto
     *
     *  if task is null then its a new task
     *  if dto.id is null then delete task
     */
    private void processTaskOwner(Task t, TaskDto dto) {
        Calendar c = Calendar.getInstance();
        if (t == null && (dto != null && dto.getOwnerId() != null)) { // new task            
            UserReport userReport = userReportDao.findByUserMonthAndYear(dto.getOwnerId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            if (userReport == null) { // create new entity
                userReport = new UserReport(null, c.get(Calendar.MONTH), c.get(Calendar.YEAR), 0);
                userReport.setTotalCreated(1);
                userReport.setUser(dto.getOwnerId());
                userReportDao.create(userReport);
            } else { // update entity
                userReport.setTotalCreated(userReport.getTotalCreated() + 1);
                userReportDao.update(userReport);
            }
        } else if (t != null && t.getId() != null && (dto.getId() == null)) { //  task deleted
            c.setTime(t.getCreateDate());
            UserReport userReport = userReportDao.findByUserMonthAndYear(t.getOwner().getId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            userReport.setTotalCreated(userReport.getTotalCreated() - 1);
            userReportDao.update(userReport);
        }
    }

    /**
     * project report is efected only when task is added, deleted or changed
     * @param t
     * @param dto
     */
    private void processTaskProject(Task t, TaskDto dto) {
        if (t == null && (dto != null && dto.getOwnerId() != null)) { // new task       
            taskCreatedForProjectReport(dto);
        } else if (t != null && t.getId() != null && (dto.getId() == null)) { //  task deleted
            taskDeletedForProjectReport(t);
        } else if (t != null && t.getId() != null && dto != null) { //  project change
            Project p = t.getProject();
            Integer pId = dto.getProjectId();
            // p && Pid should not be same
            //if ((p == null && pId == null) || (p != null && pId != null && p.getId().equals(pId))) {
            //} else {
            taskCreatedForProjectReport(dto);
            taskDeletedForProjectReport(t);
        //}
        }
    }

    /**
     * efects user_report on assineee added, removed or changed
     * @param t
     * @param dto
     */
    private void processTaskAssignee(Task t, TaskDto dto) {
        if ((t != null && t.getAssignedTo() == null && dto != null && dto.getAssignedToId() != null) || (t == null && (dto != null && dto.getAssignedToId() != null))) { // new task  assigned     
            addTaskAssigned(dto.getAssignedToId(), dto.getOwnerId(), dto.getAssignedById(), dto.getSpendHours(), dto.getEstimatedHours(), true, null);
        } else if (t != null && t.getAssignedTo() != null && (dto == null || dto != null && dto.getAssignedToId() == null)) {
            removeTaskAssigned(t.getAssignedTo().getId(), t.getOwner().getId(), t.getAssignedBy().getId(), t.getAssignedDate(), t.getEstimatedHours(), true, 0);
        } else if (t != null && dto != null && t.getAssignedTo() != null && dto.getAssignedToId() != null) {
            removeTaskAssigned(t.getAssignedTo().getId(), t.getOwner().getId(), t.getAssignedBy().getId(), t.getAssignedDate(), t.getEstimatedHours(), false, t.getSpendHours());
            addTaskAssigned(dto.getAssignedToId(), dto.getOwnerId(), dto.getAssignedById(), dto.getSpendHours(), dto.getEstimatedHours(), false, dto.getAssignedDate());
        }
    }

    /**
     * 
     * @param t
     * @param dto
     */
    private void processTaskCompletion(Task t, TaskDto dto) {
        Calendar c = Calendar.getInstance();
        Integer org = usersDao.read(dto.getOwnerId()).getOrganization().getId();
        if ((t == null || !t.getStatus().equals(TaskService.TASK_STATUS_COMPLETED)) &&
                dto.getStatus() != null && dto.getStatus().equals(TaskService.TASK_STATUS_COMPLETED)) {
            UserReport userReport = null;
            if (dto.getAssignedToId() != null) {
                userReport = userReportDao.findByUserMonthAndYear(dto.getAssignedToId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
                if (userReport == null) { // add entity to db
                    userReport = new UserReport(null, c.get(Calendar.MONTH), c.get(Calendar.YEAR), 0);
                    userReport.setUser(dto.getAssignedToId());
                    userReportDao.create(userReport);
                //userReport = userReportDao.findByUserMonthAndYear(dto.getAssignedToId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
                }
            //if (userReport.getEstimatedHours() != null && userReport.getEstimatedHours() > t.getEstimatedHours()) {
            //    userReport.setEstimatedHours(userReport.getEstimatedHours() - t.getEstimatedHours());
            //}
            } else {
                userReport = userReportDao.findByUserMonthAndYear(dto.getOwnerId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
                if (userReport == null) { // add entity to db this will happen on new month startups
                    userReport = new UserReport(null, c.get(Calendar.MONTH), c.get(Calendar.YEAR), 0);
                    userReport.setUser(dto.getOwnerId());
                    userReportDao.create(userReport);
                //userReport = userReportDao.findByUserMonthAndYear(dto.getOwnerId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
                }
                userReport.setCreatedSelfAssigned(userReport.getCreatedSelfAssigned() + 1);
                userReport.setTotalAssigned(userReport.getTotalAssigned() + 1);
            }
            //userReport = getUserReport(userReport, c, dto);
            userReport.setTotalCompleted(userReport.getTotalCompleted() + 1);
            //userReport.setEstimatedHours(userReport.getEstimatedHours() + dto.getEstimatedHours());
            userReport.setHoursSpend(userReport.getHoursSpend() + dto.getSpendHours());
            userReportDao.update(userReport);
            ProjectReport projectReport = null;
            projectReport = getProjectReport(dto, c, projectReport, org);
            projectReport.setTaskFinished(projectReport.getTaskFinished() + 1);
            projectReport.setTimeSpend(projectReport.getTimeSpend() + dto.getSpendHours());
            //projectReport.setEstimatedTime(projectReport.getEstimatedTime() - t.getEstimatedHours());
            //projectReport.setEstimatedTime(projectReport.getEstimatedTime() + dto.getEstimatedHours());
            projectReportDao.update(projectReport);

        } else if ((t != null && t.getStatus().equals(TaskService.TASK_STATUS_COMPLETED)) &&
                !dto.getStatus().equals(TaskService.TASK_STATUS_COMPLETED)) {
            UserReport userReport = userReportDao.findByUserMonthAndYear(dto.getAssignedById(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            ProjectReport projectReport = null;
            userReport = getUserReport(userReport, c, dto);
            projectReport = getProjectReport(dto, c, projectReport, org);
            projectReport.setTaskFinished(projectReport.getTaskFinished() - 1);
            projectReport.setTimeSpend(projectReport.getTimeSpend() - t.getSpendHours());
            //projectReport.setEstimatedTime(projectReport.getEstimatedTime() - dto.getEstimatedHours());
            projectReportDao.update(projectReport);
            userReport.setTotalCompleted(userReport.getTotalCompleted() - 1);
            //userReport.setEstimatedHours(userReport.getEstimatedHours() - t.getEstimatedHours());
            userReport.setHoursSpend(userReport.getHoursSpend() - t.getSpendHours());
            userReportDao.update(userReport);
        }

    }

    private ProjectReport getProjectReport(TaskDto dto, Calendar c, ProjectReport projectReport, Integer org) {
        if (dto.getProjectId() != null) {
            projectReport = projectReportDao.findByProjectMonthAndYear(dto.getProjectId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            if (projectReport == null) {
                // create new entity
                projectReport = new ProjectReport(null, 1, 0.0, c.get(Calendar.MONTH), c.get(Calendar.YEAR), dto.getProjectId());
                projectReportDao.create(projectReport);
            }
        } else {
            projectReport = projectReportDao.findByOrganizationMonthAndYear(org, c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            if (projectReport == null) {
                // create new entity
                projectReport = new ProjectReport(null, 1, 0.0, c.get(Calendar.MONTH), c.get(Calendar.YEAR), null);
                projectReport.setOrganization(org);
                projectReportDao.create(projectReport);
            }
        }
        return projectReport;
    }

    private UserReport getUserReport(UserReport userReport, Calendar c, TaskDto dto) {
        if (userReport == null) {
            // new entity
            userReport = new UserReport(null, c.get(Calendar.MONTH), c.get(Calendar.YEAR), 0);
            userReport.setUser(dto.getAssignedById());
            userReportDao.create(userReport);
        }
        return userReport;
    }

    /**
     *
     * @param userId
     * @param ownerId
     * @param assignedBy
     * @param assignDate
     * @param eh
     */
    private void addTaskAssigned(Integer userId, Integer ownerId, Integer assignedBy, double hoursSpend, double eh, boolean isNew, Date dt) {
        Calendar c = Calendar.getInstance();
        if (!isNew) {
            c.setTime(dt);
        }
        UserReport userReport1 = userReportDao.findByUserMonthAndYear(userId, c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        if (userReport1 == null) { // new entity
            userReport1 = new UserReport(null, c.get(Calendar.MONTH), c.get(Calendar.YEAR), 0);
            userReport1.setUser(userId);
            userReportDao.create(userReport1);
        //userReport1 = userReportDao.findByUserMonthAndYear(userId, c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        }
        if (ownerId.equals(userId) && assignedBy.equals(userId)) {
            userReport1.setCreatedSelfAssigned(userReport1.getCreatedSelfAssigned() + 1);
        } else if (assignedBy.equals(userId)) {
            userReport1.setSelfAssigned(userReport1.getSelfAssigned() + 1);
        } else {
            userReport1.setAssigned(userReport1.getAssigned() + 1);
        }

        userReport1.setTotalAssigned(userReport1.getTotalAssigned() + 1);
        userReport1.setEstimatedHours(userReport1.getEstimatedHours() + eh);
        userReport1.setHoursSpend(userReport1.getHoursSpend() + hoursSpend);
        userReportDao.update(userReport1);
    }

    /**
     * 
     * @param userId
     * @param ownerId
     * @param assignedBy
     * @param assignDate
     * @param eh
     */
    private void removeTaskAssigned(Integer userId, Integer ownerId, Integer assignedBy, Date assignDate, double eh, boolean isDelete, double hoursSpend) {
        Calendar c = Calendar.getInstance();
        c.setTime(assignDate);
        UserReport userReport1 = userReportDao.findByUserMonthAndYear(userId, c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        if (ownerId.equals(userId) && assignedBy.equals(userId)) {
            userReport1.setCreatedSelfAssigned(userReport1.getCreatedSelfAssigned() - 1);
        } else if (assignedBy.equals(userId)) {
            userReport1.setSelfAssigned(userReport1.getSelfAssigned() - 1);
        } else {
            userReport1.setAssigned(userReport1.getAssigned() - 1);
        }
        userReport1.setTotalAssigned(userReport1.getTotalAssigned() - 1);
        userReport1.setEstimatedHours(userReport1.getEstimatedHours() - eh);
        if (!isDelete) {
            userReport1.setHoursSpend(userReport1.getHoursSpend() - hoursSpend);
        }
        userReportDao.update(userReport1);
    }

    /**
     *
     * @param dto
     */
    private void taskCreatedForProjectReport(TaskDto dto) {
        Calendar c = Calendar.getInstance();// new task
        if (dto.getProjectId() != null) {
            ProjectReport projectReport = projectReportDao.findByProjectMonthAndYear(dto.getProjectId(), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            if (projectReport == null) {// create new entity
                projectReport = new ProjectReport(null, 1, dto.getEstimatedHours(), c.get(Calendar.MONTH), c.get(Calendar.YEAR), dto.getProjectId());
                projectReport.setEstimatedTime(dto.getEstimatedHours());
                projectReport.setTimeSpend(dto.getSpendHours());
                projectReportDao.create(projectReport);
            } else {// update entity
                projectReport.setTaskCreated(projectReport.getTaskCreated() + 1);
                projectReport.setEstimatedTime(projectReport.getEstimatedTime() + dto.getEstimatedHours());
                projectReport.setTimeSpend(projectReport.getTimeSpend() + dto.getSpendHours());
                projectReportDao.update(projectReport);
            }
        } else { // Todo
            Integer org = usersDao.read(dto.getOwnerId()).getOrganization().getId();
            ProjectReport projectReport = projectReportDao.findByOrganizationMonthAndYear(org, c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            if (projectReport == null) {// create new entity
                projectReport = new ProjectReport(null, 1, dto.getEstimatedHours(), c.get(Calendar.MONTH), c.get(Calendar.YEAR), null);
                projectReport.setEstimatedTime(dto.getEstimatedHours());
                projectReport.setTimeSpend(dto.getSpendHours());
                projectReport.setOrganization(org);
                projectReportDao.create(projectReport);
            } else {// update entity
                projectReport.setTaskCreated(projectReport.getTaskCreated() + 1);
                projectReport.setEstimatedTime(projectReport.getEstimatedTime() + dto.getEstimatedHours());
                projectReport.setTimeSpend(projectReport.getTimeSpend() + dto.getSpendHours());
                projectReportDao.update(projectReport);
            }
        }
    }

    /**
     *
     * @param t
     */
    private void taskDeletedForProjectReport(Task t) {
        Calendar c = Calendar.getInstance();//  task deleted
        c.setTime(t.getCreateDate());
        ProjectReport projectReport = null;
        if (t.getProject() != null) {
            Integer p = t.getProject().getId();
            projectReport = projectReportDao.findByProjectMonthAndYear(p, c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        } else {
            Integer org = usersDao.findByUsername(t.getOwner().getUsername()).getOrganization().getId();
            projectReport = projectReportDao.findByOrganizationMonthAndYear(org, c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        }
        projectReport.setTaskCreated(projectReport.getTaskCreated() - 1);
        projectReport.setEstimatedTime(projectReport.getEstimatedTime() - t.getEstimatedHours());
        projectReport.setTimeSpend(projectReport.getTimeSpend() - t.getSpendHours());
        projectReportDao.update(projectReport);
    }
    // ------------------------- setters -------------------------------------//

    public void setProjectReportDao(ProjectReportDao projectReportDao) {
        this.projectReportDao = projectReportDao;
    }

    public void setUserReportDao(UserReportDao userReportDao) {
        this.userReportDao = userReportDao;
    }

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public void setProjectReportDtoA(ProjectReportDtoA projectReportDtoA) {
        this.projectReportDtoA = projectReportDtoA;
    }

    public void setUserReportDtoA(UserReportDtoA userReportDtoA) {
        this.userReportDtoA = userReportDtoA;
    }

    public void setProjectUsersDao(ProjectUsersDao projectUsersDao) {
        this.projectUsersDao = projectUsersDao;
    }
    @Autowired
    private ProjectUsersDao projectUsersDao;
    @Autowired
    private ProjectReportDao projectReportDao;
    @Autowired
    private UserReportDao userReportDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UserReportDtoA userReportDtoA;
    @Autowired
    private ProjectReportDtoA projectReportDtoA;
    private Logger log = Logger.getLogger(getClass());
}
