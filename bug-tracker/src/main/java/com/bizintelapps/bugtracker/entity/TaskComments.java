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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author intesar
 */
@Entity
@Table(name = "task_comments")
@NamedQueries({@NamedQuery(name = "TaskComments.findById", query = "SELECT t FROM TaskComments t WHERE t.id = :id"), @NamedQuery(name = "TaskComments.findByTask", query = "SELECT t FROM TaskComments t WHERE t.task = :task"), @NamedQuery(name = "TaskComments.findByText", query = "SELECT t FROM TaskComments t WHERE t.text = :text"), @NamedQuery(name = "TaskComments.findByCreateDate", query = "SELECT t FROM TaskComments t WHERE t.createDate = :createDate")})
public class TaskComments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "task", nullable = false)
    private int task;
    @Column(name = "_text", nullable = false)
    private String text;
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne
    private Task task1;
    @JoinColumn(name = "create_user", referencedColumnName = "id")
    @ManyToOne
    private Users createUser;

    public TaskComments() {
    }

    public TaskComments(Integer id) {
        this.id = id;
    }

    public TaskComments(Integer id, int task, String text, Date createDate) {
        this.id = id;
        this.task = task;
        this.text = text;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
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

    public Task getTask1() {
        return task1;
    }

    public void setTask1(Task task1) {
        this.task1 = task1;
    }

    public Users getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Users createUser) {
        this.createUser = createUser;
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
        if (!(object instanceof TaskComments)) {
            return false;
        }
        TaskComments other = (TaskComments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizintelapps.promanager.entity.TaskComments[id=" + id + "]";
    }

}
