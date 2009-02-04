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
package com.bizintelapps.bugtracker.ajax;

import com.bizintelapps.bugtracker.dto.ProjectDto;
import com.bizintelapps.bugtracker.dto.ProjectUserDto;
import com.bizintelapps.bugtracker.exceptions.ServiceRuntimeException;
import com.bizintelapps.bugtracker.service.ProjectService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author intesar
 */
public class AjaxProjectService {

    // saveProject should handle create|update
    /**
     *  every method should copy same pattern
     *  msg for success
     *  should catch exceptions exactly the same way
     * @param usersDto
     * @return
     */
    public List<ProjectDto> saveProject(ProjectDto projectDto) {
        try {
            // this should handle create/update
            projectService.saveProject(projectDto, SecurityUtil.getUsername());
            return projectService.getProjects(SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }
    // getProjectList should get all projects visible ot user with brief summary
    /**
     * 
     * @return
     */
    public List<ProjectDto> getProjectList() {
        try {
            return projectService.getProjects(SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * used on task page for project dropdown
     * @return
     */
    public List<ProjectUserDto> getProjectsForDropdown() {
        try {
            return projectService.getProjectsForDropdown(SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * deletes project if possible
     * @param projectId
     */
    public List<ProjectDto> deleteProject(Integer projectId) {
        try {
            projectService.deleteProject(projectId, SecurityUtil.getUsername());
            return getProjectList();
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * creates or updates user to project
     * @param projectId
     * @param userId
     * @param isManager if true user can manage this project
     * 
     */
    public ProjectUserDto saveUserToProject(Integer projectId, String username, boolean isManager) {
        try {            
            projectService.saveUserToProject(projectId, username, isManager, SecurityUtil.getUsername());
            System.out.println ( " ----------------- ");
            return projectService.getProjectUsers(projectId, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * only admin or pm can execute this
     * @param projectId
     * 
     */
    public ProjectUserDto getProjectUsers(Integer projectId) {
        try {
            return projectService.getProjectUsers(projectId, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }

    }

    /**
     * removes user from project 
     * removes user from project
     * @param userId
     * @param projectId
     * 
     */
    public ProjectUserDto deleteUserFromProject(Integer projectId, Integer userId ) {
        try {
            projectService.deleteUserFromProject(userId, projectId, SecurityUtil.getUsername());
            return projectService.getProjectUsers(projectId, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    //----------------------- setters & getters ------------------------------//
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    private ProjectService projectService;
    private final String ERROR_MESSAGE = "Error, Please change input and try again!";
}
