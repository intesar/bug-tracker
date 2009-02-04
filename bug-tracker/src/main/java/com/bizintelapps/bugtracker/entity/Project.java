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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author intesar
 */
@Entity
@Table(name = "project")
@NamedQueries({@NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id"),
    @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name"),
    @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description"),
    @NamedQuery(name = "Project.findByStatus", query = "SELECT p FROM Project p WHERE p.status = :status"),
    @NamedQuery(name = "Project.findByCreateUser", query = "SELECT p FROM Project p WHERE p.createUser = :createUser"),
    @NamedQuery(name = "Project.findByCreateDate", query = "SELECT p FROM Project p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "Project.findByVisibility", query = "SELECT p FROM Project p WHERE p.visibility = :visibility"),
    @NamedQuery(name = "Project.findByParentProject", query = "SELECT p FROM Project p WHERE p.parentProject = :parentProject"),
    @NamedQuery(name = "Project.findByNameAndOrganization", query = "SELECT p FROM Project p WHERE p.name = ?1 and p.organization.id = ?2"),
    @NamedQuery(name = "Project.findByOrganization", query = "SELECT p FROM Project p WHERE p.organization.id = ?1"),
    @NamedQuery(name = "Project.findByStatusAndOrganization", query = "SELECT p FROM Project p WHERE p.status = ?1 and p.organization.id = ?2")
})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "_status", nullable = false)
    private String status;
    @Column(name = "create_user", nullable = false)
    private Integer createUser;
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "last_update_user", nullable = false)
    private Integer lastUpdateUser;
    @Column(name = "last_update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Column(name = "visibility", nullable = false)
    private String visibility;
    @Column(name = "parent_project")
    private Integer parentProject;
    @Column(name = "estimated_hours")
    private double estimatedHours;
    @Column(name = "hourly_rate")
    private double hourlyRate;
    @Column(name = "estimated_cost")
    private double estimatedCost;
    @Column(name = "notification_emails")
    private double notificationEmails;
    @JoinColumn(name = "organization", referencedColumnName = "id")
    @ManyToOne
    private Organization organization;
    @OneToMany(mappedBy = "project")
    private Collection<Task> taskCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Collection<ProjectUsers> projectUsersCollection;
    @Column(name = "vid")
    @Version
    private Integer vid;
    @Column(name = "enabled")
    private boolean enabled;

    public Project() {
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Project(Integer id, String name, String status, Integer createUser, Date createDate, String visibility) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createUser = createUser;
        this.createDate = createDate;
        this.visibility = visibility;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
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

    public Integer getParentProject() {
        return parentProject;
    }

    public void setParentProject(Integer parentProject) {
        this.parentProject = parentProject;
    }

    public Integer getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(Integer lastUdpateUser) {
        this.lastUpdateUser = lastUdpateUser;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getNotificationEmails() {
        return notificationEmails;
    }

    public void setNotificationEmails(double notificationEmails) {
        this.notificationEmails = notificationEmails;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public Collection<ProjectUsers> getProjectUsersCollection() {
        return projectUsersCollection;
    }

    public void setProjectUsersCollection(Collection<ProjectUsers> projectUsersCollection) {
        this.projectUsersCollection = projectUsersCollection;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.Project[id=" + id + "]";
    }
}
