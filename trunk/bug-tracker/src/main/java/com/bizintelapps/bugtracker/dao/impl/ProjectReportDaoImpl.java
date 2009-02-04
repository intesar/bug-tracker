/*
 *  Copyright 2009 intesar.
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

import com.bizintelapps.bugtracker.dao.PagingParams;
import com.bizintelapps.bugtracker.dao.ProjectReportDao;
import com.bizintelapps.bugtracker.entity.ProjectReport;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author intesar
 */
@Repository
public class ProjectReportDaoImpl extends GenericDaoImpl<ProjectReport, Integer> implements ProjectReportDao {

    public ProjectReportDaoImpl() {
        super(ProjectReport.class);
    }

    @Override
    public ProjectReport findByProjectMonthAndYear(Integer project, Integer month, Integer year) {
        return executeNamedQuerySingleResult("ProjectReport.findByProjectMonthAndYear", project, month, year);
    }

    @Override
    public ProjectReport findByOrganizationMonthAndYear(Integer organization, Integer month, Integer year) {
        return executeNamedQuerySingleResult("ProjectReport.findByOrganizationMonthAndYear", organization, month, year);
    }

    @Override
    public List<ProjectReport> findByProject(Integer project, int maxRecords) {
        PagingParams<ProjectReport> pagingParams = new PagingParams<ProjectReport>(0, maxRecords, null);
        return executeNamedQueryList("ProjectReport.findByProject", pagingParams, project);
    }

    @Override
    public List<ProjectReport> findByOrganizationForTodo(Integer organization, Integer maxRecords) {
        PagingParams<ProjectReport> pagingParams = new PagingParams<ProjectReport>(0, maxRecords, null);
        return executeNamedQueryList("ProjectReport.findByOrganizationForTodo", pagingParams, organization);
    }

    @Override
    public Object findProjectSummary(Integer project) {
        return executeNamedQueryReturnSingleObject("ProjectReport.findProjectSummary", project);
    }
}
