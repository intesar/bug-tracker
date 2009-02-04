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
package com.bizintelapps.bugtracker.dto;

import java.util.List;

/**
 * used for populating drop downs on task creation and update screeens
 * @author intesar
 */
public class ProjectUserDto {

    private Integer projectId;
    private String projectName;
    private List<UsersMinDto> users;

    public ProjectUserDto(Integer projectId, String projectName, List<UsersMinDto> users) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.users = users;
    }

    public ProjectUserDto(Integer projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public ProjectUserDto() {
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<UsersMinDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersMinDto> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectUserDto other = (ProjectUserDto) obj;
        if (this.projectId != other.projectId && (this.projectId == null || !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.projectId != null ? this.projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return projectName;
    }
    
    
}
