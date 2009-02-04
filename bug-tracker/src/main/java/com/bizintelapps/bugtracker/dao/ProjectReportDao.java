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
package com.bizintelapps.bugtracker.dao;

import com.bizintelapps.bugtracker.entity.ProjectReport;
import java.util.List;

/**
 *
 * @author intesar
 */
public interface ProjectReportDao extends GenericDao<ProjectReport, Integer> {

    ProjectReport findByProjectMonthAndYear(Integer project, Integer month, Integer year);

    ProjectReport findByOrganizationMonthAndYear(Integer organization, Integer month, Integer year);

    List<ProjectReport> findByProject(Integer project, int maxRecords);

    List<ProjectReport> findByOrganizationForTodo(Integer organization, Integer maxRecords);
    
    Object findProjectSummary ( Integer project);
}
