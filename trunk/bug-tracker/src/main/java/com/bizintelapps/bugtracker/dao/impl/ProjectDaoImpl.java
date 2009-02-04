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

import com.bizintelapps.bugtracker.dao.ProjectDao;
import com.bizintelapps.bugtracker.entity.Project;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author intesar
 */
@Repository
public class ProjectDaoImpl extends GenericDaoImpl<Project, Integer> implements ProjectDao {

    public ProjectDaoImpl() {
        super(Project.class);
    }
    
    @Override
    public Project findByNameAndOrganization(String name, Integer organizationId) {
        return executeNamedQuerySingleResult("Project.findByNameAndOrganization", name, organizationId);
    }
    
    @Override
    public List<Project> findByOrganization (Integer organizationId) {
        return executeNamedQueryList("Project.findByOrganization", null, organizationId);
    }
    
    @Override
    public List<Project> findByStatusAndOrganization (String status, Integer organizationId) {
        return executeNamedQueryList("Project.findByStatusAndOrganization", null, status, organizationId);
    }
            
    private final Log log = LogFactory.getLog(getClass());

    
}
