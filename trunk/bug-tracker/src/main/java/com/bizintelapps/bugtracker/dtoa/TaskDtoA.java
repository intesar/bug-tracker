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
package com.bizintelapps.bugtracker.dtoa;

import com.bizintelapps.bugtracker.entity.Task;
import com.bizintelapps.bugtracker.dto.TaskDto;
import com.bizintelapps.bugtracker.entity.Users;
import com.bizintelapps.bugtracker.service.TaskService;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author intesar
 */
@Component
public class TaskDtoA {

    public TaskDto copyForDisplay(Task task, TaskDto taskDto, boolean isAdmin, boolean pm, Integer requestedUserId) {
        taskDto.setId(task.getId());
        if (task.getAssignedTo() != null) {
            if (task.getAssignedTo().getId().equals(requestedUserId)) {
                //taskDto.setAssignedToUsername("me");
                taskDto.setAssignedToName("me");
            } else {
                //taskDto.setAssignedToUsername(task.getAssignedTo().getUsername());
                taskDto.setAssignedToName(task.getAssignedTo().getFirstname());// + " " +task.getAssignedTo().getLastname());
            }
            taskDto.setAssignedToUsername(task.getAssignedTo().getUsername());
            taskDto.setAssignedToId(task.getAssignedTo().getId());
            taskDto.setAssignedById(task.getAssignedBy().getId());
            taskDto.setAssignedDate(task.getAssignedDate());
        }
        taskDto.setCompletedDate(task.getCompletedDate());
        taskDto.setCreateDate(task.getCreateDate());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setEstimatedHours(task.getEstimatedHours());
        taskDto.setSpendHours(task.getSpendHours());
        taskDto.setDescription(task.getDescription());
        taskDto.setNotificationEmails(task.getNotificationEmails());
        taskDto.setDescription(task.getDescription());
        taskDto.setId(task.getId());
        taskDto.setLastStatusChangedDate(task.getLastStatusChangedDate());
        if (task.getOwner().getId().equals(requestedUserId)) {
            taskDto.setOwnerUsername("me");
        } else {
            taskDto.setOwnerUsername(task.getOwner().getUsername());
        }
        taskDto.setOwnerId(task.getOwner().getId());
        taskDto.setPriority(task.getPriority());
        if (task.getProject() != null) {
            taskDto.setProjectName(task.getProject().getName());
            taskDto.setProjectId(task.getProject().getId());
        }
        taskDto.setStatus(task.getStatus());
        taskDto.setTitle(task.getTitle());
        if (isAdmin || task.getOwner().getId().equals(requestedUserId) || pm) {
            taskDto.setIsOwner(true);
        }
        return taskDto;
    }

    /**
     * copies everything from userDto to users
     * @param usersDto
     * @param users
     * @return
     */
    public Task copyForCreate(TaskDto taskDto, Task task, Users users, Users assignedTo) {
        task.setCreateDate(new Date());
        task.setDeadline(taskDto.getDeadline());
        task.setEstimatedHours(taskDto.getEstimatedHours());
        task.setSpendHours(task.getSpendHours());
        task.setDescription(taskDto.getDescription());
        task.setNotificationEmails(taskDto.getNotificationEmails());
        task.setLastStatusChangedDate(new Date());
        task.setOwner(users);
        task.setPriority(taskDto.getPriority());
        if (assignedTo != null) {
            task.setAssignedTo(assignedTo);
            task.setAssignedBy(users);
            task.setAssignedDate(new Date());
        }
        task.setTitle(taskDto.getTitle());
        task.setStatus(taskDto.getStatus());
        task.setSpendHours(taskDto.getSpendHours());
        if (taskDto.getStatus().equals(TaskService.TASK_STATUS_COMPLETED)) {
            task.setCompletedDate(new Date());
        }
        return task;
    }

    /**
     * only copies fields that can be changed from client side
     * Owner: title, assigned, end date, estimated hours, status, priority,
     * complete date, hours spend, description, add comment, listeners
     * @param usersDto
     * @param users
     * 
     */
    public Task copyForUpdate(TaskDto taskDto, Task task, Users users, Users assignedTo) {
        task.setTitle(taskDto.getTitle());
        task.setDeadline(taskDto.getDeadline());
        task.setEstimatedHours(taskDto.getEstimatedHours());
        task.setPriority(taskDto.getPriority());
        if (!task.getStatus().equals(TaskService.TASK_STATUS_COMPLETED) && taskDto.getStatus().equals(TaskService.TASK_STATUS_COMPLETED)) {
            task.setCompletedDate(new Date());
        }
        task.setStatus(taskDto.getStatus());
        task.setSpendHours(taskDto.getSpendHours());
        task.setDescription(taskDto.getDescription());
        task.setNotificationEmails(taskDto.getNotificationEmails());
        task.setLastStatusChangedDate(new Date());
        if (assignedTo != null) {
            task.setAssignedTo(assignedTo);
            task.setAssignedBy(users);
            task.setAssignedDate(new Date());
        }
        return task;
    }

    /**
     * Assigned: status, complete date, hours spend, add comment
     * @param taskDto
     * @param task
     * @return
     */
    public Task copyForUpdateForAssignee(TaskDto taskDto, Task task) {
        task.setPriority(taskDto.getPriority());
        if (!task.getStatus().equals(TaskService.TASK_STATUS_COMPLETED) && taskDto.getStatus().equals(TaskService.TASK_STATUS_COMPLETED)) {
            task.setCompletedDate(taskDto.getCompletedDate());
        }
        task.setStatus(taskDto.getStatus());
        task.setSpendHours(taskDto.getSpendHours());
        task.setDescription(taskDto.getDescription());
        task.setLastStatusChangedDate(new Date());
        return task;
    }
}
