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
package com.bizintelapps.bugtracker.dtoa;

import com.bizintelapps.bugtracker.dto.ProjectUserDto;
import com.bizintelapps.bugtracker.entity.Project;
import com.bizintelapps.bugtracker.dto.ProjectDto;
import com.bizintelapps.bugtracker.dto.UsersMinDto;
import com.bizintelapps.bugtracker.entity.ProjectUsers;
import com.bizintelapps.bugtracker.entity.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class ProjectDtoA {

    public List<ProjectDto> copyAllForDisplay(Collection<Project> projectCollection) {
        List<ProjectDto> list = new ArrayList<ProjectDto>();
        for (Project project : projectCollection) {
            ProjectDto projectDto = copyForDisplay(project, new ProjectDto());
            list.add(projectDto);
        }
        return list;
    }

    public ProjectUserDto copyAllProjectAlongUsers1(Integer projectId, String projectName, List<ProjectUsers> projectUsers) {
        
        List<UsersMinDto> users = new ArrayList<UsersMinDto>();
        for (ProjectUsers pu : projectUsers) {        
            if (pu.getUsers().getEnabled()) {
                Users user = pu.getUsers();
                UsersMinDto usersMinDto = new UsersMinDto(user.getId(),
                        user.getUsername(), user.getFirstname(), user.getLastname(), false,
                        pu.getIsManager());
                users.add(usersMinDto);
            }
        }
        ProjectUserDto dto = new ProjectUserDto(projectId, projectName, users);
        return dto;
    }

    public List<ProjectUserDto> copyAllProjectAlongUsers2(List<ProjectUsers> projectUsers) {
        return null;
    }

    public List<ProjectUserDto> copyAllProjectAlongUsers(List<Project> projects) {
        List<ProjectUserDto> list = new ArrayList<ProjectUserDto>();
        for (Project p : projects) {
            List<UsersMinDto> users = new ArrayList<UsersMinDto>();
            Collection<ProjectUsers> projectUserses = p.getProjectUsersCollection();
            for (ProjectUsers pu : projectUserses) {
                Users user = pu.getUsers();
                if (pu.getUsers().getEnabled()) {
                    UsersMinDto usersDto = new UsersMinDto(user.getId(),
                            user.getUsername(), user.getFirstname(), user.getLastname(), false,
                            pu.getIsManager());
                    users.add(usersDto);
                }
            }
            ProjectUserDto dto = new ProjectUserDto(p.getId(), p.getName(), users);
            list.add(dto);
        }
        return list;
    }

    public ProjectDto copyForDisplay(Project project, ProjectDto projectDto) {
        projectDto.setCreateDate(project.getCreateDate());
        projectDto.setCreateUser(project.getCreateUser());
        projectDto.setDescription(project.getDescription());
        projectDto.setId(project.getId());
        projectDto.setEstimatedCost(project.getEstimatedCost());
        projectDto.setEstimatedHours(project.getEstimatedHours());
        projectDto.setHourlyRate(project.getHourlyRate());
        projectDto.setNotificationEmails(project.getNotificationEmails());
        //projectDto.setLastUpdateUser(project.getLastUpdateUser());
        //String pattern = "yyyy.MM.dd hh:mm";
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        //projectDto.setLastUpdateDate(simpleDateFormat.format(project.getLastUpdateDate()));
        projectDto.setName(project.getName());
        projectDto.setStatus(project.getStatus());
        projectDto.setVisibility(project.getVisibility());
        return projectDto;
    }

    public Project copyForCreate(ProjectDto projectDto, Project project) {
        project.setDescription(projectDto.getDescription());
        project.setName(projectDto.getName());
        project.setStatus(projectDto.getStatus());
        return project;
    }

    public Project copyForUpdate(ProjectDto projectDto, Project project) {
        project.setDescription(projectDto.getDescription());
        project.setStatus(projectDto.getStatus());
        return project;
    }
}
