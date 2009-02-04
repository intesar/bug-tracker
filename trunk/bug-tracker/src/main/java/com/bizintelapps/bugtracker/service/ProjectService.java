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
package com.bizintelapps.bugtracker.service;

import com.bizintelapps.bugtracker.dto.ProjectDto;
import com.bizintelapps.bugtracker.dto.ProjectUserDto;
import java.util.List;

/**
 *
 * @author intesar
 */
public interface ProjectService {

    public List<ProjectDto> saveAndGetProjects(ProjectDto projectDto, String savedBy);

    /**
     *  saves or updates a project to db
     *  only admin can create new project
     *  admin or project manager can change status (update)
     * @param projectDto
     * @param savedBy
     */
    public void saveProject(ProjectDto projectDto, String savedBy);

    /**
     *  tries to delete project
     * @param projectId
     * @param deletedBy
     */
    public void deleteProject(Integer projectId, String deletedBy);

    /**
     * returns projects for client display                   
     * @param requestedBy
     * @return
     */
    public List<ProjectDto> getProjects(String requestedBy);

    /**
     * if admin return all active projects
     * else return all active projects for the users (user should be member of that project)
     * @param requestedBy
     * @return
     */
    public List<ProjectUserDto> getProjectsForDropdown(String requestedBy);

    /**
     * creates or updates user to project
     * @param projectId
     * @param userId
     * @param isManager if true user can manage this project
     * @param savedBy 
     */
    public void saveUserToProject(Integer projectId, String username, boolean isManager, String savedBy);

    /**
     * only admin or pm can execute this
     * @param projectId
     * @param requestedBy
     */
    public ProjectUserDto getProjectUsers(Integer projectId, String requestedBy);

    /**
     * removes user from project
     * @param userId
     * @param projectId
     * @param savedBy
     */
    public void deleteUserFromProject(Integer userId, Integer projectId, String savedBy);
    String IN_PROGRESS = "In Progress";
    String ON_HOLD = "On Hold";
    String COMPLETED = "Completed";
}
