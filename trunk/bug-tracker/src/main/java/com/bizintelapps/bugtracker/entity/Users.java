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
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = ?1 "),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByIsEncrypted", query = "SELECT u FROM Users u WHERE u.isEncrypted = :isEncrypted"),
    @NamedQuery(name = "Users.findByEnabled", query = "SELECT u FROM Users u WHERE u.enabled = :enabled"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT u FROM Users u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "Users.findByLastname", query = "SELECT u FROM Users u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = ?1 "),
    @NamedQuery(name = "Users.findByCity", query = "SELECT u FROM Users u WHERE u.city = :city"),
    @NamedQuery(name = "Users.findByCountry", query = "SELECT u FROM Users u WHERE u.country = :country"),
    @NamedQuery(name = "Users.findByCreateUser", query = "SELECT u FROM Users u WHERE u.createUser = :createUser"),
    @NamedQuery(name = "Users.findByCreateDate", query = "SELECT u FROM Users u WHERE u.createDate = :createDate"),
    @NamedQuery(name = "Users.findByOrganizationId", query = "SELECT u FROM Users u WHERE u.organization.id = ?1"),
    @NamedQuery(name = "Users.findEnabledUsersByOrganizationId", query = "SELECT u FROM Users u WHERE u.enabled = true and u.organization.id = ?1")
})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_encrypted")
    private Boolean isEncrypted;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "create_user")
    private Integer createUser;
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "is_administrator", nullable = false)
    private boolean isAdministrator;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Column(name = "last_update_user")
    private Integer lastUpdateUser;
    @Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Column(name = "vid")
    @Version
    private Integer vid;
    @Column(name = "is_super")
    private boolean isSuper;
    @JoinColumn(name = "organization", referencedColumnName = "id")
    @ManyToOne
    private Organization organization;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createUser")
    private Collection<TaskImpedance> taskImpedanceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resolvedUser")
    private Collection<TaskImpedance> taskImpedanceCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createUser")
    private Collection<TaskComments> taskCommentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Collection<Task> taskCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedTo")
    private Collection<Task> taskCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedTo")
    private Collection<TaskHistory> taskHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updateUser")
    private Collection<TaskHistory> taskHistoryCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<ProjectUsers> projectUsersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createUser")
    private Collection<ProjectUsers> projectUsersCollection1;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String username, String password, boolean enabled, String lastname, Date createDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.lastname = lastname;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(Boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public boolean isIsAdministrator() {
        return isAdministrator;
    }

    public void setIsAdministrator(boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(Integer lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Collection<TaskImpedance> getTaskImpedanceCollection() {
        return taskImpedanceCollection;
    }

    public void setTaskImpedanceCollection(Collection<TaskImpedance> taskImpedanceCollection) {
        this.taskImpedanceCollection = taskImpedanceCollection;
    }

    public Collection<TaskImpedance> getTaskImpedanceCollection1() {
        return taskImpedanceCollection1;
    }

    public void setTaskImpedanceCollection1(Collection<TaskImpedance> taskImpedanceCollection1) {
        this.taskImpedanceCollection1 = taskImpedanceCollection1;
    }

    public Collection<TaskComments> getTaskCommentsCollection() {
        return taskCommentsCollection;
    }

    public void setTaskCommentsCollection(Collection<TaskComments> taskCommentsCollection) {
        this.taskCommentsCollection = taskCommentsCollection;
    }

    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public Collection<Task> getTaskCollection1() {
        return taskCollection1;
    }

    public void setTaskCollection1(Collection<Task> taskCollection1) {
        this.taskCollection1 = taskCollection1;
    }

    public Collection<TaskHistory> getTaskHistoryCollection() {
        return taskHistoryCollection;
    }

    public void setTaskHistoryCollection(Collection<TaskHistory> taskHistoryCollection) {
        this.taskHistoryCollection = taskHistoryCollection;
    }

    public Collection<TaskHistory> getTaskHistoryCollection1() {
        return taskHistoryCollection1;
    }

    public void setTaskHistoryCollection1(Collection<TaskHistory> taskHistoryCollection1) {
        this.taskHistoryCollection1 = taskHistoryCollection1;
    }

    public Collection<ProjectUsers> getProjectUsersCollection() {
        return projectUsersCollection;
    }

    public void setProjectUsersCollection(Collection<ProjectUsers> projectUsersCollection) {
        this.projectUsersCollection = projectUsersCollection;
    }

    public Collection<ProjectUsers> getProjectUsersCollection1() {
        return projectUsersCollection1;
    }

    public void setProjectUsersCollection1(Collection<ProjectUsers> projectUsersCollection1) {
        this.projectUsersCollection1 = projectUsersCollection1;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public boolean isIsSuper() {
        return isSuper;
    }

    public void setIsSuper(boolean isSuper) {
        this.isSuper = isSuper;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.Users[id=" + id + "]";
    }
}
