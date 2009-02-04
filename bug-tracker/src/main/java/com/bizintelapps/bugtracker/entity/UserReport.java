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
@Table(name = "user_report")
@NamedQueries({
    @NamedQuery(name = "UserReport.findById", query = "SELECT u FROM UserReport u WHERE u.id = :id"),
    @NamedQuery(name = "UserReport.findByMonth", query = "SELECT u FROM UserReport u WHERE u.month = :month"),
    @NamedQuery(name = "UserReport.findByYear", query = "SELECT u FROM UserReport u WHERE u.year = :year"),
    @NamedQuery(name = "UserReport.findByTotalCreated", query = "SELECT u FROM UserReport u WHERE u.totalCreated = :totalCreated"),
    @NamedQuery(name = "UserReport.findByAssigned", query = "SELECT u FROM UserReport u WHERE u.assigned = :assigned"),
    @NamedQuery(name = "UserReport.findBySelfAssigned", query = "SELECT u FROM UserReport u WHERE u.selfAssigned = :selfAssigned"),
    @NamedQuery(name = "UserReport.findByTotalAssigned", query = "SELECT u FROM UserReport u WHERE u.totalAssigned = :totalAssigned"),
    @NamedQuery(name = "UserReport.findByEstimatedHours", query = "SELECT u FROM UserReport u WHERE u.estimatedHours = :estimatedHours"),
    @NamedQuery(name = "UserReport.findByHoursSpend", query = "SELECT u FROM UserReport u WHERE u.hoursSpend = :hoursSpend"),
    @NamedQuery(name = "UserReport.findByTotalCompleted", query = "SELECT u FROM UserReport u WHERE u.totalCompleted = :totalCompleted"),
    @NamedQuery(name = "UserReport.findByUserMonthAndYear", query = "SELECT u FROM UserReport u WHERE u.user = ?1 and u.month = ?2 and u.year = ?3"),
    @NamedQuery(name = "UserReport.findByUser", query = "SELECT u FROM UserReport u WHERE u.user = ?1  order by u.year desc, u.month desc"),
    @NamedQuery(name = "UserReport.findUserSummary", query = "SELECT New com.bizintelapps.promanager.dto.UserReportDto (sum(u.totalCreated), sum(u.assigned), sum(u.selfAssigned), sum(u.totalAssigned), " +
    "sum(u.estimatedHours), sum(u.hoursSpend), sum(u.totalCompleted), sum(u.createdSelfAssigned))  FROM UserReport u where u.user = ?1")
})
public class UserReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "month", nullable = false)
    private int month;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "total_created")
    private Integer totalCreated = 0;
    @Column(name = "assigned")
    private Integer assigned = 0;
    @Column(name = "self_assigned")
    private Integer selfAssigned = 0;
    @Column(name = "total_assigned")
    private Integer totalAssigned = 0;
    @Column(name = "estimated_hours")
    private Double estimatedHours = 0.0;
    @Column(name = "hours_spend")
    private Double hoursSpend = 0.0;
    @Column(name = "total_completed", nullable = false)
    private int totalCompleted = 0;
    @Column(name = "user", nullable = false)
    private int user;
    @Column(name = "created_self_assigned")
    private int createdSelfAssigned = 0;

    public UserReport() {
    }

    public UserReport(Integer id) {
        this.id = id;
    }

    public UserReport(Integer id, int month, int year, int totalCompleted) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.totalCompleted = totalCompleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTotalCreated() {
        return totalCreated;
    }

    public void setTotalCreated(Integer totalCreated) {
        this.totalCreated = totalCreated;
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
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

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(int totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getCreatedSelfAssigned() {
        return createdSelfAssigned;
    }

    public void setCreatedSelfAssigned(int createdSelfAssigned) {
        this.createdSelfAssigned = createdSelfAssigned;
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
        if (!(object instanceof UserReport)) {
            return false;
        }
        UserReport other = (UserReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.UserReport[id=" + id + "]";
    }
}
