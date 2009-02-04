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
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author intesar
 */
@Entity
@Table(name = "task")
@NamedQueries({
    @NamedQuery(name = "Task.findById", query = "SELECT t FROM Task t WHERE t.id = :id"),
    @NamedQuery(name = "Task.findByTitle", query = "SELECT t FROM Task t WHERE t.title = :title"),
    @NamedQuery(name = "Task.findByDescription", query = "SELECT t FROM Task t WHERE t.description = :description"),
    @NamedQuery(name = "Task.findByDeadline", query = "SELECT t FROM Task t WHERE t.deadline = :deadline"),
    @NamedQuery(name = "Task.findByCreateDate", query = "SELECT t FROM Task t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "Task.findByVisibility", query = "SELECT t FROM Task t WHERE t.visibility = :visibility"),
    @NamedQuery(name = "Task.findByPercentageCompleted", query = "SELECT t FROM Task t WHERE t.percentageCompleted = :percentageCompleted"),
    @NamedQuery(name = "Task.findByStatus", query = "SELECT t FROM Task t WHERE t.status = :status"),
    @NamedQuery(name = "Task.findByPriority", query = "SELECT t FROM Task t WHERE t.priority = :priority"),
    @NamedQuery(name = "Task.findByParentTask", query = "SELECT t FROM Task t WHERE t.parentTask = :parentTask"),
    @NamedQuery(name = "Task.findBySequenceNumber", query = "SELECT t FROM Task t WHERE t.sequenceNumber = :sequenceNumber"),
    @NamedQuery(name = "Task.findByCompletedDate", query = "SELECT t FROM Task t WHERE t.completedDate = :completedDate"),
    @NamedQuery(name = "Task.findByLastStatusChangedDate", query = "SELECT t FROM Task t WHERE t.lastStatusChangedDate = :lastStatusChangedDate"),
    @NamedQuery(name = "Task.findByNotCompleteAndUser", query = "SELECT t FROM Task t WHERE t.status <> ?1 and ( t.assignedTo.username = ?2 or t.owner.username = ?3) ")
})
public class Task implements Serializable {

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
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "assigned_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignedDate;
    @Column(name = "visibility", nullable = false)
    private String visibility;
    @Column(name = "percentage_completed", nullable = false)
    private int percentageCompleted;
    @Column(name = "_status", nullable = false)
    private String status;
    @Column(name = "priority", nullable = false)
    private String priority;
    @Column(name = "parent_task")
    private Integer parentTask;
    @Column(name = "sequence_number")
    private Double sequenceNumber;
    @Column(name = "context")
    private String context;
    @Column(name = "estimated_hours")
    private double estimatedHours;
    @Column(name = "spend_hours")
    private double spendHours;
    @Column(name = "notification_emails")
    private String notificationEmails;
    @Column(name = "completed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedDate;
    @Column(name = "last_status_changed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastStatusChangedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Collection<TaskImpedance> taskImpedanceCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "task1")
    private TaskComments taskComments;
    @JoinColumn(name = "owner", referencedColumnName = "id")
    @ManyToOne
    private Users owner;
    @JoinColumn(name = "project", referencedColumnName = "id")
    @ManyToOne
    private Project project;
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    @ManyToOne
    private Users assignedTo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Collection<TaskHistory> taskHistoryCollection;
    @Column(name = "vid")
    @Version
    private Integer vid;
    @JoinColumn(name = "assigned_by", referencedColumnName = "id")
    @ManyToOne
    private Users assignedBy;
    

    public Task() {
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Task(Integer id, String title, Date createDate, String visibility, int percentageCompleted, String status, String priority) {
        this.id = id;
        this.title = title;
        this.createDate = createDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Integer getParentTask() {
        return parentTask;
    }

    public void setParentTask(Integer parentTask) {
        this.parentTask = parentTask;
    }

    public Double getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Double sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    
    public Date getLastStatusChangedDate() {
        return lastStatusChangedDate;
    }

    public void setLastStatusChangedDate(Date lastStatusChangedDate) {
        this.lastStatusChangedDate = lastStatusChangedDate;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getNotificationEmails() {
        return notificationEmails;
    }

    public void setNotificationEmails(String notificationEmails) {
        this.notificationEmails = notificationEmails;
    }

    public double getSpendHours() {
        return spendHours;
    }

    public void setSpendHours(double spendHours) {
        this.spendHours = spendHours;
    }

    public Collection<TaskImpedance> getTaskImpedanceCollection() {
        return taskImpedanceCollection;
    }

    public void setTaskImpedanceCollection(Collection<TaskImpedance> taskImpedanceCollection) {
        this.taskImpedanceCollection = taskImpedanceCollection;
    }

    public TaskComments getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(TaskComments taskComments) {
        this.taskComments = taskComments;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Users getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Users assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Collection<TaskHistory> getTaskHistoryCollection() {
        return taskHistoryCollection;
    }

    public void setTaskHistoryCollection(Collection<TaskHistory> taskHistoryCollection) {
        this.taskHistoryCollection = taskHistoryCollection;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Users getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Users assignedBy) {
        this.assignedBy = assignedBy;
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
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.Task[id=" + id + "]";
    }
}
