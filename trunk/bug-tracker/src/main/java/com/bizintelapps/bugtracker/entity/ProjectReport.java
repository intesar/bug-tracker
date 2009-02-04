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
package com.bizintelapps.bugtracker.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author intesar
 */
@Entity
@Table(name = "project_report")
@NamedQueries({
    @NamedQuery(name = "ProjectReport.findById", query = "SELECT p FROM ProjectReport p WHERE p.id = :id"),
    @NamedQuery(name = "ProjectReport.findByTaskCreated", query = "SELECT p FROM ProjectReport p WHERE p.taskCreated = :taskCreated"),
    @NamedQuery(name = "ProjectReport.findByTaskFinished", query = "SELECT p FROM ProjectReport p WHERE p.taskFinished = :taskFinished"),
    @NamedQuery(name = "ProjectReport.findByEstimatedTime", query = "SELECT p FROM ProjectReport p WHERE p.estimatedTime = :estimatedTime"),
    @NamedQuery(name = "ProjectReport.findByTimeSpend", query = "SELECT p FROM ProjectReport p WHERE p.timeSpend = :timeSpend"),
    @NamedQuery(name = "ProjectReport.findByMonth", query = "SELECT p FROM ProjectReport p WHERE p.month = :month"),
    @NamedQuery(name = "ProjectReport.findByYear", query = "SELECT p FROM ProjectReport p WHERE p.year = :year"),
    @NamedQuery(name = "ProjectReport.findByProject", query = "SELECT p FROM ProjectReport p WHERE p.project = ?1  order by p.year desc, p.month desc"),
    @NamedQuery(name = "ProjectReport.findByOrganizationForTodo", query = "SELECT p FROM ProjectReport p WHERE p.organization = ?1 and p.project is null"),
    @NamedQuery(name = "ProjectReport.findByProjectMonthAndYear", query = "SELECT p FROM ProjectReport p WHERE p.project = ?1 and p.month = ?2 and p.year = ?3"),
    @NamedQuery(name = "ProjectReport.findByOrganizationMonthAndYear", query = "SELECT p FROM ProjectReport p WHERE p.organization = ?1 and p.month = ?2 and p.year = ?3 and p.project is null"),
    @NamedQuery(name = "ProjectReport.findProjectSummary", query = "SELECT NEW com.bizintelapps.promanager.dto.ProjectReportDto " +
    "(sum(p.taskCreated), sum(p.taskFinished), sum(p.estimatedTime), sum(p.timeSpend)) FROM ProjectReport p WHERE p.project = ?1 ")
})
public class ProjectReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "task_created", nullable = false)
    private int taskCreated = 0;
    @Column(name = "task_finished")
    private Integer taskFinished = 0;
    @Column(name = "estimated_time")
    private Double estimatedTime = 0.0;
    @Column(name = "time_spend")
    private Double timeSpend = 0.0;
    @Column(name = "month", nullable = false)
    private int month;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "project")
    private Integer project;
    @Column(name = "organization", nullable = false)
    private Integer organization;

    public ProjectReport() {
    }

    public ProjectReport(Integer id) {
        this.id = id;
    }

    public ProjectReport(Integer id, int taskCreated, Double estimatedTime, int month, int year, Integer project) {
        this.id = id;
        this.taskCreated = taskCreated;
        this.estimatedTime = estimatedTime;
        this.month = month;
        this.year = year;
        this.project = project;
    }

    public ProjectReport(Integer id, int taskCreated, int month, int year) {
        this.id = id;
        this.taskCreated = taskCreated;
        this.month = month;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Double getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(Double timeSpend) {
        this.timeSpend = timeSpend;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectReport)) {
            return false;
        }
        ProjectReport other = (ProjectReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.ProjectReport[id=" + id + "]";
    }
}
