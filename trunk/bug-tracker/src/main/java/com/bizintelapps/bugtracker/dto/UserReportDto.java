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
public class UserReportDto {

    private int month;
    private int year;
    private Integer totalCreated = 0;
    private Integer assigned = 0;
    private Integer selfAssigned = 0;
    private Integer totalAssigned = 0;
    private Double estimatedHours = 0.0;
    private Double hoursSpend = 0.0;
    private int totalCompleted = 0;
    private int user;
    private String username;
    private String name;
    private int createdSelfAssigned = 0;
    private String reportDate;
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
        "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    public UserReportDto() {
    }
    
    

    public UserReportDto(Long totalCreated, Long assigned, Long selfAssigned, Long totalAssigned, Double estimatedHours, Double hoursSpend, Long totalCompleted, Long createdSelfAssigned) {
        this.totalCreated = totalCreated.intValue();
        this.assigned = assigned.intValue();
        this.selfAssigned = selfAssigned.intValue();
        this.totalAssigned = totalAssigned.intValue();
        this.estimatedHours = estimatedHours;
        this.hoursSpend = hoursSpend;
        this.totalCompleted = totalCompleted.intValue();
        this.createdSelfAssigned = createdSelfAssigned.intValue();
    }

    public String getReportDate() {
        return months[month] + " " + year;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }

    public int getCreatedSelfAssigned() {
        return createdSelfAssigned;
    }

    public void setCreatedSelfAssigned(int createdSelfAssigned) {
        this.createdSelfAssigned = createdSelfAssigned;
    }

    public Double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Double getHoursSpend() {
        return hoursSpend;
    }

    public void setHoursSpend(Double hoursSpend) {
        this.hoursSpend = hoursSpend;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSelfAssigned() {
        return selfAssigned;
    }

    public void setSelfAssigned(Integer selfAssigned) {
        this.selfAssigned = selfAssigned;
    }

    public Integer getTotalAssigned() {
        return totalAssigned;
    }

    public void setTotalAssigned(Integer totalAssigned) {
        this.totalAssigned = totalAssigned;
    }

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(int totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    public Integer getTotalCreated() {
        return totalCreated;
    }

    public void setTotalCreated(Integer totalCreated) {
        this.totalCreated = totalCreated;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "" + totalCreated + assigned + selfAssigned + totalAssigned + estimatedHours + hoursSpend + totalCompleted + createdSelfAssigned;
    }
    
}
