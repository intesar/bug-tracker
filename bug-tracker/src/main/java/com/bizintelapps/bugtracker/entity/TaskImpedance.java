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

package com.bizintelapps.bugtracker.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author intesar
 */
@Entity
@Table(name = "task_impedance")
@NamedQueries({@NamedQuery(name = "TaskImpedance.findById", query = "SELECT t FROM TaskImpedance t WHERE t.id = :id"), @NamedQuery(name = "TaskImpedance.findByText", query = "SELECT t FROM TaskImpedance t WHERE t.text = :text"), @NamedQuery(name = "TaskImpedance.findByCreateDate", query = "SELECT t FROM TaskImpedance t WHERE t.createDate = :createDate"), @NamedQuery(name = "TaskImpedance.findByIsResolved", query = "SELECT t FROM TaskImpedance t WHERE t.isResolved = :isResolved"), @NamedQuery(name = "TaskImpedance.findByResolvedDate", query = "SELECT t FROM TaskImpedance t WHERE t.resolvedDate = :resolvedDate"), @NamedQuery(name = "TaskImpedance.findByLastUpdatedUser", query = "SELECT t FROM TaskImpedance t WHERE t.lastUpdatedUser = :lastUpdatedUser")})
public class TaskImpedance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "_text", nullable = false)
    private String text;
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "is_resolved", nullable = false)
    private boolean isResolved;
    @Column(name = "resolved_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedDate;
    @Column(name = "last_updated_user", nullable = false)
    private int lastUpdatedUser;
    @JoinColumn(name = "task", referencedColumnName = "id")
    @ManyToOne
    private Task task;
    @JoinColumn(name = "create_user", referencedColumnName = "id")
    @ManyToOne
    private Users createUser;
    @JoinColumn(name = "resolved_user", referencedColumnName = "id")
    @ManyToOne
    private Users resolvedUser;

    public TaskImpedance() {
    }

    public TaskImpedance(Integer id) {
        this.id = id;
    }

    public TaskImpedance(Integer id, String text, Date createDate, boolean isResolved, Date resolvedDate, int lastUpdatedUser) {
        this.id = id;
        this.text = text;
        this.createDate = createDate;
        this.isResolved = isResolved;
        this.resolvedDate = resolvedDate;
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public int getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(int lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Users getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Users createUser) {
        this.createUser = createUser;
    }

    public Users getResolvedUser() {
        return resolvedUser;
    }

    public void setResolvedUser(Users resolvedUser) {
        this.resolvedUser = resolvedUser;
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
        if (!(object instanceof TaskImpedance)) {
            return false;
        }
        TaskImpedance other = (TaskImpedance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.TaskImpedance[id=" + id + "]";
    }

}
