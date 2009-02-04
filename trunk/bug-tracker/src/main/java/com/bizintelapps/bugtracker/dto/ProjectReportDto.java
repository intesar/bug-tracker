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

/**
 *
 * @author intesar
 */
public class ProjectReportDto {

    private int taskCreated = 0;
    private Integer taskFinished = 0;
    private Double estimatedTime = 0.0;
    private Double timeSpend = 0.0;
    private int month;
    private int year;
    private Integer project;
    private String projectName;
    private Integer organization;
    private String organizationName;
    private String reportDate;
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
        "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    private String title;

    public ProjectReportDto() {
    }

    public ProjectReportDto(Long taskCreated, Long taskFinished, Double estimatedTime, Double timeSpend) {
        this.taskCreated = taskCreated.intValue();
        this.taskFinished = taskFinished.intValue();
        this.estimatedTime = estimatedTime;
        this.timeSpend = timeSpend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    public String getReportDate() {
        return months[month] + " " + year;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
    public Double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTaskCreated() {
        return taskCreated;
    }

    public void setTaskCreated(int taskCreated) {
        this.taskCreated = taskCreated;
    }

    public Integer getTaskFinished() {
        return taskFinished;
    }

    public void setTaskFinished(Integer taskFinished) {
        this.taskFinished = taskFinished;
    }

    public Double getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(Double timeSpend) {
        this.timeSpend = timeSpend;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
