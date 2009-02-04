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

import com.bizintelapps.mail.MailSender;
import com.bizintelapps.bugtracker.dao.ProjectDao;
import com.bizintelapps.bugtracker.dao.ProjectReportDao;
import com.bizintelapps.bugtracker.dao.ProjectUsersDao;
import com.bizintelapps.bugtracker.dao.TaskDao;
import com.bizintelapps.bugtracker.dao.UserReportDao;
import com.bizintelapps.bugtracker.dao.UsersDao;
import com.bizintelapps.bugtracker.entity.Project;
import com.bizintelapps.bugtracker.entity.ProjectUsers;
import com.bizintelapps.bugtracker.entity.Task;
import com.bizintelapps.bugtracker.entity.Users;
import com.bizintelapps.bugtracker.service.TaskService;
import com.bizintelapps.bugtracker.dtoa.TaskDtoA;
import com.bizintelapps.bugtracker.dto.TaskDto;
import com.bizintelapps.bugtracker.exceptions.ServiceRuntimeException;

import com.bizintelapps.bugtracker.service.ReportService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public void saveTask(TaskDto taskDto, String savedBy) {
        // to create a task user should be part of the project or admin
        // or user can create todo for other users
        Users savedByUser = usersDao.findByUsername(savedBy);
        Users assignedTo = null;
        if (log.isDebugEnabled()) {
            log.debug(taskDto.toString());
        }
        if (taskDto.getId() == null) {
            Integer assign = taskDto.getAssignedToId();
            if (assign != null) {
                assignedTo = usersDao.read(taskDto.getAssignedToId());
                taskDto.setAssignedDate(new Date());
                taskDto.setAssignedById(savedByUser.getId());
            }
            taskDto.setOwnerId(savedByUser.getId());
            taskDto.setOwnerUsername(savedBy);
            Task task = taskConverter.copyForCreate(taskDto, new Task(), savedByUser, assignedTo);
            Project project = projectDao.findByNameAndOrganization(taskDto.getProjectName(), savedByUser.getOrganization().getId());
            if (savedByUser.isIsAdministrator() || isUserProjectMember(savedByUser.getId(), project)) {
                // user should be either admin or project member                
                task.setProject(project);
            }
            taskDao.create(task);
            reportService.processTask(null, taskDto);
            sendTaskAlert(task);
        } else { // only admin, owner, assigned or pm can update task
            Task task = taskDao.read(taskDto.getId());
            if (savedByUser.isIsAdministrator() || task.getOwner().equals(savedByUser) ||
                    isUserProjectManager(savedByUser.getId(), task.getProject())) {
                if ((taskDto.getAssignedToId() != null && task.getAssignedTo() == null) ||
                        (taskDto.getAssignedToId() != null && task.getAssignedTo() != null && !taskDto.getAssignedById().equals(task.getAssignedTo().getId()))) {
                    assignedTo = usersDao.read(taskDto.getAssignedToId());
                    taskDto.setAssignedDate(new Date());
                    taskDto.setAssignedById(savedByUser.getId());
                    taskDto.setAssignedToUsername(assignedTo.getUsername());
                }
                reportService.processTask(task, taskDto);
                Task task1 = taskConverter.copyForUpdate(taskDto, task, savedByUser, assignedTo);
                if (taskDto.getProjectId() != null) {
                    task1.setProject(projectDao.read(taskDto.getProjectId()));
                }
                taskDao.update(task1);
            } else if (task.getAssignedTo() != null && task.getAssignedTo().equals(savedByUser)) {
                reportService.processTask(task, taskDto);
                Task task1 = taskConverter.copyForUpdateForAssignee(taskDto, task);
                taskDao.update(task1);
                sendTaskAlert(task1);
            } else {
                throw new ServiceRuntimeException("Illegal Action");
            }
        }

    }

    @Override
    public void deleteTask(Integer taskId, String deletedBy) {
        Users users = usersDao.findByUsername(deletedBy);
        Task task = taskDao.read(taskId);
        // admins, owner or pm can delete a task
        if (task.getOwner().equals(users) || isUserProjectManager(users.getId(), task.getProject())) {
            taskDao.delete(task);
            TaskDto dto = new TaskDto();
            dto.setAssignedToId(null);
            dto.setOwnerId(task.getOwner().getId());
            dto.setEstimatedHours(task.getEstimatedHours());
            if (task.getProject() != null) {
                //dto.setProjectId(task.getProject().getId());
                }
            //udpateReportForTaskUpdate(task, dto);
            reportService.processTask(task, dto);
        } else {
            throw new ServiceRuntimeException("Only Task Owner can delete task!");
        }
    }

    /**
     * 
     * @param projectId can be null, 0 means todo + projects
     * @param start if null then today - 7 days
     * @param end if null then today + 7 days
     * @param userId if null then requestedBy and if 0 all users
     * @param taskStatus if null then searches for New & In Progress
     * @param requestedBy 
     * @return
     */
    @Override
    public List<TaskDto> searchTasks(String statuses, String requestedBy) {
        Users u = usersDao.findByUsername(requestedBy);
        List<Task> tasks = taskDao.search(statuses, u.getId());
        if (tasks == null) {
            tasks = new ArrayList();
        }
        // requestedBy if admin, pm or owner can udpate lot on task        
        //List<TaskDto> dtos = taskConverter.copyAllForDisplay(tasks, u.isIsAdministrator(), u.getId());
        List<TaskDto> list = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            boolean isAdmin = false;
            if (task.getOwner().equals(u) || (task.getAssignedTo() != null && task.getAssignedBy().equals(u)) || isUserProjectManager(u.getId(), task.getProject())) {
                isAdmin = true;
            }
            TaskDto taskDto = taskConverter.copyForDisplay(task, new TaskDto(), isAdmin, isUserProjectManager(u.getId(), task.getProject()), u.getId());
            list.add(taskDto);
        }
        return list;
    }

    @Override
    public List<TaskDto> searchTasks(String statuses, int max, String requestedBy) {
        Users u = usersDao.findByUsername(requestedBy);
        List<Task> tasks = taskDao.search(statuses, u.getId(), max);
        if (tasks == null) {
            tasks = new ArrayList();
        }
        // requestedBy if admin, pm or owner can udpate lot on task        
        //List<TaskDto> dtos = taskConverter.copyAllForDisplay(tasks, u.isIsAdministrator(), u.getId());
        List<TaskDto> list = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            boolean isAdmin = false;
            if (task.getOwner().equals(u) || (task.getAssignedTo() != null && task.getAssignedBy().equals(u)) || isUserProjectManager(u.getId(), task.getProject())) {
                isAdmin = true;
            }
            TaskDto taskDto = taskConverter.copyForDisplay(task, new TaskDto(), isAdmin, isUserProjectManager(u.getId(), task.getProject()), u.getId());
            list.add(taskDto);
        }
        return list;
    }
    // only admin, owner, pm, assign-to can see task
    @Override
    public TaskDto getTask(Integer taskId, String requestedBy) {
        Users u = usersDao.findByUsername(requestedBy);
        Task task = taskDao.read(taskId);
        if (task.getOwner().equals(u) || (task.getAssignedTo() != null && task.getAssignedTo().equals(u)) || isUserProjectManager(u.getId(), task.getProject())) {
            TaskDto taskDto = taskConverter.copyForDisplay(task, new TaskDto(), u.isIsAdministrator(), isUserProjectManager(u.getId(), task.getProject()), u.getId());
            return taskDto;
        }
        throw new ServiceRuntimeException("Only Admin, Task Owner, Project Manager or Assigned-To can view task");
    }

    @Override
    public void assignTaskUser(Integer taskId, Integer userId, String requestedBy) {
        Users users1 = usersDao.findByUsername(requestedBy);
        Users users = null;
        if (userId == null) {
            users = users1;
        } else {
            users = usersDao.read(userId);
        }

        Task task = taskDao.read(taskId);
        TaskDto dto = new TaskDto();
        taskConverter.copyForDisplay(task, dto, false, false, users1.getId());
        dto.setAssignedToId(users.getId());
        dto.setAssignedDate(new Date());
        dto.setAssignedById(users1.getId());
        dto.setAssignedToUsername(users.getUsername());
        dto.setLastStatusChangedDate(new Date());
        reportService.processTask(task, dto);
        //udpateReportForTaskUpdate(task, dto);
        // * only admin, owner, pm can assign others
        if (users1.isIsAdministrator() || users.equals(users1) || task.getOwner().equals(users1) ||
                isUserProjectManager(users1.getId(), task.getProject())) {
        } else {
            throw new ServiceRuntimeException("Invalid Operation!");
        }
        task.setAssignedTo(users);
        task.setAssignedDate(new Date());
        task.setAssignedBy(users1);
        taskDao.update(task);
        sendTaskAlert(task);
    }

    @Override
    public void changeTaskStatus(Integer taskId, String status, String requestedBy) {
        Users users1 = usersDao.findByUsername(requestedBy);
        Task task = taskDao.read(taskId);
        // admin, owner, assigned, or pm can change
        if ((task.getAssignedTo() != null && task.getAssignedTo().equals(users1)) || task.getOwner().equals(users1) || isUserProjectManager(users1.getId(), task.getProject())) {
            if (status.equals(TASK_STATUS_COMPLETED)) {
                if (task.getSpendHours() <= 0) {
                    task.setSpendHours(task.getEstimatedHours());
                }
                if (!task.getStatus().equals(TASK_STATUS_COMPLETED)) {
                    task.setCompletedDate(new Date());
                }
            }
            //updateReportForTaskComplete(task);
            TaskDto dto = new TaskDto();
            taskConverter.copyForDisplay(task, dto, false, false, users1.getId());
            dto.setStatus(status);
            if ( task.getAssignedTo() == null ) {
                dto.setAssignedById(users1.getId());
                dto.setAssignedDate(new Date());
                dto.setAssignedToId(users1.getId());                
            }
            reportService.processTask(task, dto);
            //}
            if (task.getAssignedTo() == null) {
                task.setAssignedBy(users1);
                task.setAssignedDate(new Date());
                task.setAssignedTo(users1);
            }
            task.setStatus(status);
            task.setLastStatusChangedDate(new Date());            
            taskDao.update(task);
            sendTaskAlert(task);
        } else {
            throw new ServiceRuntimeException("Only Admin, Task Owner, Project Manager or Assigned-To can change task status");
        }
    }

    @Override
    public void changeTaskPriority(Integer taskId, String priority, String requestedBy) {
        Users users1 = usersDao.findByUsername(requestedBy);
        Task task = taskDao.read(taskId);
        // admin, owner, assigned, or pm can change
        if ((task.getAssignedTo() != null && task.getAssignedTo().equals(users1)) || task.getOwner().equals(users1) || isUserProjectManager(users1.getId(), task.getProject())) {
            task.setPriority(priority);
            taskDao.update(task);
            sendTaskAlert(task);
        } else {
            throw new ServiceRuntimeException("Only Admin, Task Owner, Project Manager or Assigned-To can change task priority");
        }
    }

    @Override
    public void addTaskComment(Integer taskId, String comment, String requestedBy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //---------------------- private helper methods -------------------------//
    /**
     *  helper method finds given user is a project manager
     * @param userId
     * @param projectId
     * @return
     */
    private boolean isUserProjectManager(Integer userId, Project project) {
        try {
            if (project == null) {
                return false;
            }
            ProjectUsers pu = projectUsersDao.findByProjectIdAndUserId(project.getId(), userId);
            return pu.getIsManager();
        } catch (RuntimeException re) {
        }
        return false;
    }

    /**
     *  helper method finds given user is a project member
     * @param userId
     * @param projectId
     * @return
     */
    private boolean isUserProjectMember(Integer userId, Project project) {
        try {
            if (project == null) {
                return false;
            }
            ProjectUsers pu = projectUsersDao.findByProjectIdAndUserId(project.getId(), userId);
            if (pu != null && pu.getId() != null) {
                return true;
            }
        } catch (RuntimeException re) {
        }
        return false;
    }

    private void sendTaskAlert(Task task) {
        Set<String> set = new HashSet<String>();
        String subject = "";
        set.add(task.getOwner().getUsername());
        String assignedTo = "N/A";
        if (task.getAssignedTo() != null) {
            assignedTo = task.getAssignedTo().getFirstname() + " " + task.getAssignedTo().getLastname();
            subject += task.getAssignedTo().getFirstname() + ", ";
            set.add(task.getAssignedTo().getUsername());
        }
        String assignedBy = "N/A";
        if (task.getAssignedBy() != null) {
            assignedBy = task.getAssignedBy().getFirstname() + " " + task.getAssignedBy().getLastname();
            set.add(task.getAssignedBy().getUsername());
        }
        String project = "Todo";
        if (task.getProject() != null) {
            project = task.getProject().getName();
        }
        if (task.getNotificationEmails() != null && task.getNotificationEmails().length() > 5) {
            String[] emails = task.getNotificationEmails().split(",");
            for (String email : emails) {
                set.add(email);
            }
        }
        String id = "New";
        if (task.getId() != null) {
            id = task.getId().toString();
        }
        subject += task.getTitle();
        String assignedDate = "N/A";
        if (task.getAssignedDate() != null) {
            assignedDate = task.getAssignedDate().toString();
        }
        String deadline = "N/A";
        if (task.getDeadline() != null) {
            deadline = task.getDeadline().toString();
        }
        String completedDate = "N/A";
        if (task.getCompletedDate() != null) {
            completedDate = task.getCompletedDate().toString();
        }
        String body = "<table>" + "<tr><td>Task #</td><td>" + id + "</td></tr>" + "<tr><td>Title</td><td>" + task.getTitle() + "</td></tr>" + "<tr><td>Assigned To</td><td>" + assignedTo + "</td></tr>" + "<tr><td>Assigned On</td><td>" + assignedDate + "</td></tr>" + "<tr><td>Assigned By</td><td>" + assignedBy + "</td></tr>" + "<tr><td>Created By</td><td>" + task.getOwner().getFirstname() + " " + task.getOwner().getLastname() + "</td></tr>" + "<tr><td>Created On</td><td>" + task.getCreateDate() + "</td></tr>" + "<tr><td>Project</td><td>" + project + "</td></tr>" + "<tr><td>Status</td><td>" + task.getStatus() + "</td></tr>" + "<tr><td>Priority</td><td>" + task.getPriority() + "</td></tr>" + "<tr><td>Estimated Hours</td><td>" + task.getEstimatedHours() + "</td></tr>" + "<tr><td>Hours Spend</td><td>" + task.getSpendHours() + "</td></tr>" + "<tr><td>Finish By</td><td>" + deadline + "</td></tr>" + "<tr><td>Completed Date</td><td>" + completedDate + "</td></tr>" + "<tr><td>Description</td><td>" + task.getDescription() + "</td></tr>" + "</table>";
        try {            
            String[] tos = new String[set.size()];
            int count = 0;
            for (String s : set) {
                tos[count++] = s;
            }
            mailSender.sendMail(tos, subject, body);
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }
    //----------------------- getters & setters--------------------------//
    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public void setProjectUsersDao(ProjectUsersDao projectUsersDao) {
        this.projectUsersDao = projectUsersDao;
    }

    public void setTaskConverter(TaskDtoA taskConverter) {
        this.taskConverter = taskConverter;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setProjectReportDao(ProjectReportDao projectReportDao) {
        this.projectReportDao = projectReportDao;
    }

    public void setUserReportDao(UserReportDao userReportDao) {
        this.userReportDao = userReportDao;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    @Autowired
    private TaskDtoA taskConverter;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectUsersDao projectUsersDao;
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    private MailSender mailSender;
    @Autowired
    private ProjectReportDao projectReportDao;
    @Autowired
    private UserReportDao userReportDao;
    @Autowired
    private ReportService reportService;
}
