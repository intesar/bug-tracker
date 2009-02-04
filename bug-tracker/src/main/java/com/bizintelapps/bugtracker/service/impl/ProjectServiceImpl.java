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

import com.bizintelapps.bugtracker.dao.ProjectDao;
import com.bizintelapps.bugtracker.dao.ProjectUsersDao;
import com.bizintelapps.bugtracker.dao.UsersDao;
import com.bizintelapps.bugtracker.entity.Project;
import com.bizintelapps.bugtracker.entity.Users;
import com.bizintelapps.bugtracker.service.ProjectService;
import com.bizintelapps.bugtracker.dtoa.ProjectDtoA;
import com.bizintelapps.bugtracker.dto.ProjectDto;
import com.bizintelapps.bugtracker.dto.ProjectUserDto;
import com.bizintelapps.bugtracker.entity.ProjectUsers;
import com.bizintelapps.bugtracker.exceptions.ServiceRuntimeException;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public List<ProjectDto> saveAndGetProjects(ProjectDto projectDto, String savedBy) {
        saveProject(projectDto, savedBy);
        return getProjects(savedBy);
    }

    @Override
    public void saveProject(ProjectDto projectDto, String savedBy) {
        Users savedByUsers = usersDao.findByUsername(savedBy);
        log.debug("----------- project id " + projectDto.getId());
        // create new if id null
        // administrator can create projects
        if (projectDto.getId() == null) {
            if (!savedByUsers.isIsAdministrator()) {
                throw new ServiceRuntimeException("only Administrator can create new Project");
            }
            // project should have unique name
            Project project1 = projectDao.findByNameAndOrganization(projectDto.getName(),
                    savedByUsers.getOrganization().getId());
            if (project1 != null && project1.getId() != null) {
                throw new ServiceRuntimeException(projectDto.getName() + " name is already in use, please provide a different name!");
            }
            // copy usersDto contents to new Users object and persist
            Project project = projectConverter.copyForCreate(projectDto, new Project());
            project.setCreateDate(new Date());
            project.setCreateUser(savedByUsers.getId());
            project.setLastUpdateDate(new Date());
            project.setLastUpdateUser(savedByUsers.getId());
            project.setOrganization(savedByUsers.getOrganization());
            project.setEstimatedCost(projectDto.getEstimatedCost());
            project.setEstimatedHours(projectDto.getEstimatedHours());
            project.setHourlyRate(projectDto.getHourlyRate());
            project.setNotificationEmails(projectDto.getNotificationEmails());
            project.setEnabled(true);
            projectDao.create(project);
        } else {
            // copy projectDto contents 
            // user should be either admin or pm
            if (savedByUsers.isIsAdministrator() || isProjectManager(projectDto.getId(), savedByUsers.getId())) {
                Project project = projectDao.read(projectDto.getId());
                projectConverter.copyForUpdate(projectDto, project);
                project.setLastUpdateDate(new Date());
                project.setLastUpdateUser(savedByUsers.getId());
                project.setEstimatedCost(projectDto.getEstimatedCost());
                project.setEstimatedHours(projectDto.getEstimatedHours());
                project.setHourlyRate(projectDto.getHourlyRate());
                project.setNotificationEmails(projectDto.getNotificationEmails());
                projectDao.update(project);
            } else {
                throw new ServiceRuntimeException(" Only Administrator or ProjectManagers can update project");
            }
        }
    }

    @Override
    public void deleteProject(Integer projectId, String deletedBy) {
        Users users = usersDao.findByUsername(deletedBy);
        Project project = projectDao.read(projectId);
        // find user is admin then only can delete other even they are administrators
        // delete project if no task created
        if (users.isIsAdministrator()) {
            if (project.getTaskCollection() != null && project.getTaskCollection().size() > 0) {
                project.setEnabled(false);
                projectDao.update(project);
            } else {
                projectDao.delete(project);
            }
        } else {
            throw new ServiceRuntimeException("Only Administrator can delete Project!");
        }
    }

    @Override
    public List<ProjectDto> getProjects(String requestedBy) {
        List<ProjectDto> list = new ArrayList<ProjectDto>();
        Users users = usersDao.findByUsername(requestedBy);
        if (users.isIsAdministrator()) {
            list = projectConverter.copyAllForDisplay(projectDao.findByOrganization(users.getOrganization().getId()));
            return list;
        } else { // users will see all managed projectes                
            List<ProjectUsers> projectUsersList = projectUsersDao.findManagedProjectsByUserId(users.getId());
            ProjectDto projectDto = null;
            for (ProjectUsers projectUsers : projectUsersList) {
                projectDto = projectConverter.copyForDisplay(projectUsers.getProject(), new ProjectDto());
                list.add(projectDto);
            }
        }
        return list;
    }

    @Override
    public List<ProjectUserDto> getProjectsForDropdown(String requestedBy) {
        Users users = usersDao.findByUsername(requestedBy);
        List<ProjectUserDto> projectUserDtos = null;
        if (users.isIsAdministrator()) { //return all active projects
            List<Project> projects = projectDao.findByStatusAndOrganization(IN_PROGRESS, users.getOrganization().getId());
            projectUserDtos = projectConverter.copyAllProjectAlongUsers(projects);
        } else { // return user projects
            List<ProjectUsers> projectUsers = projectUsersDao.findByProjectStatusAndUserId(IN_PROGRESS, users.getId());
            projectUserDtos = projectConverter.copyAllProjectAlongUsers2(projectUsers);
        // send to converter
        }
        return projectUserDtos;
    }

    @Override
    public void saveUserToProject(Integer projectId, String userId, boolean isManager, String savedBy) {
        Users savedUsers = usersDao.findByUsername(savedBy);
        ProjectUsers savedByProjectUsers = projectUsersDao.findByProjectIdAndUserId(projectId, savedUsers.getId());
        // user should be either admin or project manager to do this operation
        if (savedUsers.isIsAdministrator() || isProjectManager(savedByProjectUsers)) {
            // user if not assigned to this project then create one else udpate his record
            Users users = usersDao.findByUsername(userId);
            Project project = projectDao.read(projectId);
            ProjectUsers projectUsers = projectUsersDao.findByProjectIdAndUserId(projectId, users.getId());
            if (projectUsers == null || projectUsers.getId() == null) {
                projectUsers = new ProjectUsers(null, true, isManager, new Date(), project, users, savedUsers);
                projectUsersDao.create(projectUsers);
            } else if (projectUsers.getIsManager() != isManager) {
                projectUsers.setIsManager(isManager);
                projectUsersDao.update(projectUsers);
            }
        } else {
            throw new ServiceRuntimeException("Invalid operation!");
        }
    }

    @Override
    public ProjectUserDto getProjectUsers(Integer projectId, String requestedBy) {
        Users savedUsers = usersDao.findByUsername(requestedBy);
        Project project = projectDao.read(projectId);
        ProjectUsers savedByProjectUsers = projectUsersDao.findByProjectIdAndUserId(projectId, savedUsers.getId());
        if (savedUsers.isIsAdministrator() || isProjectManager(savedByProjectUsers)) {
            List<ProjectUsers> list = projectUsersDao.findByProperty("project.id", projectId, null).getCurrentList();
            if (list == null) {
                list = new ArrayList<ProjectUsers>();
            }
            return projectConverter.copyAllProjectAlongUsers1(project.getId(), project.getName(), list);
        } else {
            throw new ServiceRuntimeException("Invalid operation!");
        }
    }

    @Override
    public void deleteUserFromProject(Integer userId, Integer projectId,
            String savedBy) {
        Users savedUsers = usersDao.findByUsername(savedBy);
        ProjectUsers savedByProjectUsers = projectUsersDao.findByProjectIdAndUserId(projectId, savedUsers.getId());
        if (savedUsers.isIsAdministrator() || isProjectManager(savedByProjectUsers)) {
            ProjectUsers pu = projectUsersDao.findByProjectIdAndUserId(projectId, userId);
            projectUsersDao.delete(pu);
        } else {
            throw new ServiceRuntimeException("Invalid operation!");
        }
    }

    private boolean isProjectManager(ProjectUsers pu) {
        if (pu != null && pu.getIsManager()) {
            return true;
        }
        return false;
    }

    private boolean isProjectManager(Integer projectId, Integer usersId) {
        ProjectUsers projectUsers = projectUsersDao.findByProjectIdAndUserId(projectId, usersId);
        if (projectUsers != null) {
            return projectUsers.getIsManager();
        }
        return false;
    }
    // --------------------- daos -----------------------------------------
    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public UsersDao getUsersDao() {
        return usersDao;
    }

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public ProjectDtoA getProjectConverter() {
        return projectConverter;
    }

    public void setProjectConverter(ProjectDtoA projectConverter) {
        this.projectConverter = projectConverter;
    }

    public ProjectUsersDao getProjectUsersDao() {
        return projectUsersDao;
    }

    public void setProjectUsersDao(ProjectUsersDao projectUsersDao) {
        this.projectUsersDao = projectUsersDao;
    }
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectUsersDao projectUsersDao;
    @Autowired
    private ProjectDtoA projectConverter;
    private final Log log = LogFactory.getLog(getClass());
}
