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
@Table(name = "task_history")
@NamedQueries({@NamedQuery(name = "TaskHistory.findById", query = "SELECT t FROM TaskHistory t WHERE t.id = :id"), @NamedQuery(name = "TaskHistory.findByTitle", query = "SELECT t FROM TaskHistory t WHERE t.title = :title"), @NamedQuery(name = "TaskHistory.findByDescription", query = "SELECT t FROM TaskHistory t WHERE t.description = :description"), @NamedQuery(name = "TaskHistory.findByDeadline", query = "SELECT t FROM TaskHistory t WHERE t.deadline = :deadline"), @NamedQuery(name = "TaskHistory.findByVisibility", query = "SELECT t FROM TaskHistory t WHERE t.visibility = :visibility"), @NamedQuery(name = "TaskHistory.findByPercentageCompleted", query = "SELECT t FROM TaskHistory t WHERE t.percentageCompleted = :percentageCompleted"), @NamedQuery(name = "TaskHistory.findByStatus", query = "SELECT t FROM TaskHistory t WHERE t.status = :status"), @NamedQuery(name = "TaskHistory.findByPriority", query = "SELECT t FROM TaskHistory t WHERE t.priority = :priority"), @NamedQuery(name = "TaskHistory.findBySequenceNumber", query = "SELECT t FROM TaskHistory t WHERE t.sequenceNumber = :sequenceNumber"), @NamedQuery(name = "TaskHistory.findByUpdateDate", query = "SELECT t FROM TaskHistory t WHERE t.updateDate = :updateDate")})
public class TaskHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    @Column(name = "visibility", nullable = false)
    private String visibility;
    @Column(name = "percentage_completed", nullable = false)
    private int percentageCompleted;
    @Column(name = "_status", nullable = false)
    private String status;
    @Column(name = "priority", nullable = false)
    private String priority;
    @Column(name = "sequence_number")
    private Double sequenceNumber;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    @ManyToOne
    private Users assignedTo;
    @JoinColumn(name = "update_user", referencedColumnName = "id")
    @ManyToOne
    private Users updateUser;
    @JoinColumn(name = "task", referencedColumnName = "id")
    @ManyToOne
    private Task task;

    public TaskHistory() {
    }

    public TaskHistory(Integer id) {
        this.id = id;
    }

    public TaskHistory(Integer id, String title, String visibility, int percentageCompleted, String status, String priority) {
        this.id = id;
        this.title = title;
        this.visibility = visibility;
        this.percentageCompleted = percentageCompleted;
        this.status = status;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(int percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Double getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Double sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Users getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Users assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Users getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Users updateUser) {
        this.updateUser = updateUser;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
        if (!(object instanceof TaskHistory)) {
            return false;
        }
        TaskHistory other = (TaskHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.TaskHistory[id=" + id + "]";
    }

}
