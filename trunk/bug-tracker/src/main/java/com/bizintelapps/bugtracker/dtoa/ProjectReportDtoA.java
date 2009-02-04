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
package com.bizintelapps.bugtracker.dtoa;

import com.bizintelapps.bugtracker.dao.ProjectDao;
import com.bizintelapps.bugtracker.dto.ProjectReportDto;
import com.bizintelapps.bugtracker.entity.Project;
import com.bizintelapps.bugtracker.entity.ProjectReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class ProjectReportDtoA {

    public ProjectReportDto copyForDisplay(ProjectReport pr) {
        ProjectReportDto dto = new ProjectReportDto();
        dto.setEstimatedTime(pr.getEstimatedTime());
        dto.setMonth(pr.getMonth());
        dto.setOrganization(pr.getOrganization());
        dto.setProject(pr.getProject());
        if (pr.getProject() != null) {
            Project p = projectDao.read(pr.getProject());
            dto.setProjectName(p.getName());
        } else {
            dto.setProjectName("Todo");
        }
        dto.setTaskCreated(pr.getTaskCreated());
        dto.setTaskFinished(pr.getTaskFinished());
        dto.setTimeSpend(pr.getTimeSpend());
        dto.setYear(pr.getYear());
        return dto;
    }

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
    @Autowired
    private ProjectDao projectDao;
}
