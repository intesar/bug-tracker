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
package com.bizintelapps.bugtracker.ajax;

import com.bizintelapps.bugtracker.dto.TaskDto;
import com.bizintelapps.bugtracker.exceptions.ServiceRuntimeException;
import com.bizintelapps.bugtracker.service.TaskService;
import com.bizintelapps.bugtracker.service.UsersService;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author intesar
 */
public class AjaxTaskService {
    
   
    /**
     * creates or updates a task
     * @param taskDto
     * @return
     */
    public void saveTask(TaskDto taskDto) {
        try {
            taskService.saveTask(taskDto, SecurityUtil.getUsername());            
        } catch (ServiceRuntimeException se) {
            log.error(se);
            se.printStackTrace();
            throw se;
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param taskId
     * @param userId
     */
    public void assignTaskUser(Integer taskId, Integer userId) {
        try {
            taskService.assignTaskUser(taskId, userId, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param taskId
     */
    public List<TaskDto> deleteTask(Integer taskId) {
        try {
            taskService.deleteTask(taskId, SecurityUtil.getUsername());
            return getCurrentTask("Current Task");
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param taskId
     * @param status
     */
    public void changeTaskStatus(Integer taskId, String status) {
        try {
            taskService.changeTaskStatus(taskId, status, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param taskId
     * @param status
     */
    public void changeTaskPriority(Integer taskId, String status) {
        try {
            taskService.changeTaskPriority(taskId, status, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param taskId
     * @param comment
     */
    public void addTaskComment(Integer taskId, String comment) {
        try {
            //
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param taskId
     * @param description
     */
    public void udpateTaskDescription(Integer taskId, String description) {
        try {
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }

    }

    public List<TaskDto> getRecentCompletedTask(int max) {
        return taskService.searchTasks("'Completed'", max, SecurityUtil.getUsername());
    }

    /**
     * 
     * @return
     */
    public List<TaskDto> getCurrentTask(String status) {
        try {
            String allStatuses = "'New', 'In Progress', 'On Hold', 'Completed'";
            String completedTask = "'Completed'";
            String currentTask = "'New', 'In Progress', 'On Hold'";
            String statuses = null;
            if (status.equals("Completed Task")) {
                statuses = completedTask;
            } else if (status.equals("All Task")) {
                statuses = allStatuses;
            } else if (status.equals("Current Task")) {
                statuses = currentTask;
            } else {
                List<TaskDto> taskDtos = new ArrayList<TaskDto>(1);
                taskDtos.add(taskService.getTask(Integer.parseInt(status), SecurityUtil.getUsername()));
                return taskDtos;
            }
            return taskService.searchTasks(statuses, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }
    //------------------------ getters & setters -----------------------------//
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    @Autowired
    private TaskService taskService;
    @Autowired
    private UsersService usersService;
    private final String ERROR_MESSAGE = "Error, Please change input and try again!";
    private Logger log = Logger.getLogger(getClass());
}
