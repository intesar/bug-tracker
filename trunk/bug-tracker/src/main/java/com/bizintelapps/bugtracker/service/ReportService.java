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

import com.bizintelapps.bugtracker.dto.ProjectReportDto;
import com.bizintelapps.bugtracker.dto.TaskDto;
import com.bizintelapps.bugtracker.dto.UserReportDto;
import com.bizintelapps.bugtracker.entity.Task;
import java.util.List;

/**
 *
 * @author intesar
 */
public interface ReportService {

    /**
     * 
     * @param task
     * @param dto
     */
    public void processTask(Task task, TaskDto dto);

    /**
     * return list all all user reports
     * @param user if null then get for requestedBy
     * @param requestedBy
     * @return
     */
    public List<UserReportDto> getUserReports(Integer user, Integer maxReports, String requestedBy);

    public List<UserReportDto> getRandomUserReports(Integer maxReports, String requestedBy);

    /**
     * return summary of all user report
     * @param user is null then get for requestedBy
     * @param requestedBy
     * @return
     */
    public UserReportDto getCurrentUserReport(Integer user, String requestedBy);

    /**
     * returns complete summary
     * @param user
     * @param requestedBy
     * @return
     */
    public UserReportDto getUserSummary(Integer user, String requestedBy);

    /**
     *  return list of reports for all users for the time period
     * @param month
     * @param year
     * @param requestedBy
     * @return
     */
    public List<UserReportDto> getAllUserReport(int month, int year, String requestedBy);

    /**
     * return list of reports for given project or todo
     * @param project
     * @param requestedBy
     * @return
     */
    public List<ProjectReportDto> getProjectReports(Integer project, Integer maxReports, String requestedBy);

    /**
     *
     * @param maxReports
     * @param requestedBy
     * @return random project report or todo report beloning to user
     */
    public List<ProjectReportDto> getRandomProjectReports(Integer maxReports, String requestedBy);

    /**
     * 
     * return summary of project or todo 
     * @param project
     * @param requestedBy
     * @return
     */
    public ProjectReportDto getProjectReportSummary(Integer project, String requestedBy);

    /**
     * current project summary
     * @param project
     * @param requestedBy
     * @return
     */
    public ProjectReportDto getCurrentProjectReport(Integer project, String requestedBy);
}
