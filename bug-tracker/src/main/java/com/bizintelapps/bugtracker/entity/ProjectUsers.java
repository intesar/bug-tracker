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
import javax.persistence.Version;

/**
 *
 * @author intesar
 */
@Entity
@Table(name = "project_users")
@NamedQueries({
    @NamedQuery(name = "ProjectUsers.findById", query = "SELECT p FROM ProjectUsers p WHERE p.id = :id"),
    @NamedQuery(name = "ProjectUsers.findByCreateDate", query = "SELECT p FROM ProjectUsers p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "ProjectUsers.findByIsEnabled", query = "SELECT p FROM ProjectUsers p WHERE p.isEnabled = :isEnabled"),
    @NamedQuery(name = "ProjectUsers.findByExpirationDate", query = "SELECT p FROM ProjectUsers p WHERE p.expirationDate = :expirationDate"),
    @NamedQuery(name = "ProjectUsers.findManagedProjectsByUserId", query = "SELECT p FROM ProjectUsers p WHERE p.isManager = true and p.users.id = ?1 "),
    @NamedQuery(name = "ProjectUsers.findByProjectStatusAndUserId", query = "SELECT p FROM ProjectUsers p WHERE p.project.status = ?1 and p.users.id = ?2 "),
    @NamedQuery(name = "ProjectUsers.findByProjectIdAndUserId", query = "SELECT p FROM ProjectUsers p WHERE p.project.id = ?1 and p.users.id = ?2 "),
    @NamedQuery(name = "ProjectUsers.findByUserId", query = "SELECT p FROM ProjectUsers p WHERE p.users.id = ?1")
})
public class ProjectUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "is_manager", nullable = false)
    private boolean isManager = false;
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @JoinColumn(name = "project", referencedColumnName = "id")
    @ManyToOne
    private Project project;
    @JoinColumn(name = "users", referencedColumnName = "id")
    @ManyToOne
    private Users users;
    @JoinColumn(name = "create_user", referencedColumnName = "id")
    @ManyToOne
    private Users createUser;
    @Column(name = "vid")
    @Version
    private Integer vid;

    public ProjectUsers() {
    }

    public ProjectUsers(Integer id) {
        this.id = id;
    }

    public ProjectUsers(Integer id, boolean enabled, boolean isManager, Date createDate, Project project, Users users, Users createUsers) {
        this.id = id;
        this.isEnabled = enabled;
        this.createDate = createDate;
        this.isManager = isManager;
        this.createDate = createDate;
        this.project = project;
        this.users = users;
        this.createUser = createUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Users createUser) {
        this.createUser = createUser;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
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
        if (!(object instanceof ProjectUsers)) {
            return false;
        }
        ProjectUsers other = (ProjectUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.ProjectUsers[id=" + id + "]";
    }
}
