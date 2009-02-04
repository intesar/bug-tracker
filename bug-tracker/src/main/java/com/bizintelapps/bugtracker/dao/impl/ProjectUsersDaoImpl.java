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
package com.bizintelapps.bugtracker.dao.impl;

import com.bizintelapps.bugtracker.dao.ProjectUsersDao;
import com.bizintelapps.bugtracker.entity.ProjectUsers;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author intesar
 */
@Repository
public class ProjectUsersDaoImpl extends GenericDaoImpl<ProjectUsers, Integer> implements ProjectUsersDao {

    private final Log log = LogFactory.getLog(getClass());
    public ProjectUsersDaoImpl() {
        super(ProjectUsers.class);
    }

    @Override
    public ProjectUsers findByProjectAdministratorByProjectNameAndUserId(String projectName, Integer userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<ProjectUsers> findByUserId(Integer userId) {
        return executeNamedQueryList("ProjectUsers.findByUserId", null, userId);
    }

    @Override
    public List<ProjectUsers> findManagedProjectsByUserId(Integer userId) {
        return executeNamedQueryList("ProjectUsers.findManagedProjectsByUserId", null, userId);
    }

    @Override
    public ProjectUsers findByProjectIdAndUserId(Integer projectId, Integer ownerId) {
        return executeNamedQuerySingleResult("ProjectUsers.findByProjectIdAndUserId", projectId, ownerId);
    }
    
    @Override
    public List<ProjectUsers> findByProjectStatusAndUserId ( String status, Integer userId) {
        return executeNamedQueryList("ProjectUsers.findByProjectStatusAndUserId", null, status, userId);
    }
}
