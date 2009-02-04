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
package com.bizintelapps.bugtracker.dao.impl;

import com.bizintelapps.bugtracker.dao.TaskDao;
import com.bizintelapps.bugtracker.entity.Task;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author intesar
 */
@Repository
public class TaskDaoImpl extends GenericDaoImpl<Task, Integer> implements TaskDao {

    public TaskDaoImpl() {
        super(Task.class);
    }

    @Override
    public List<Task> findByTaskStatusAndUserId(String status, String username) {
        return executeNamedQueryList("Task.findByNotCompleteAndUser", null, status, username, username);
    }

    @Override
    public List<Task> search(String statuses, Integer userId) {
        String sql = "select * from task t " +
                " where t._status in (" + statuses + ") " +
                " and ( t.owner = " + userId + " or t.assigned_to = " + userId + " or (t.assigned_to = null and t.project = null) " +
                " or (t.project in (select project from project_users where users = " + userId + " and is_manager = 1))" +
                " or (t.assigned_to = null and t.project in (select project from project_users where users = " + userId + "))) ";
        log.debug(sql);
        return this.getEntityManager().createNativeQuery(sql, Task.class).getResultList();
    }
    
    @Override
    public List<Task> search(String statuses, Integer userId, int max) {
        String sql = "select * from task t " +
                " where t._status in (" + statuses + ") " +
                " and ( t.owner = " + userId + " or t.assigned_to = " + userId + " or (t.assigned_to = null and t.project = null) " +
                " or (t.project in (select project from project_users where users = " + userId + " and is_manager = 1))" +
                " or (t.assigned_to = null and t.project in (select project from project_users where users = " + userId + "))) order by t.completed_date desc";
        log.debug(sql);
        return this.getEntityManager().createNativeQuery(sql, Task.class).setMaxResults(max).getResultList();
    }
    
    private Logger log = Logger.getLogger(getClass());
}
