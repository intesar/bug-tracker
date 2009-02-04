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
package com.bizintelapps.promanager.ajax;

import com.bizintelapps.bugtracker.ajax.AjaxTaskService;
import com.bizintelapps.bugtracker.dto.TaskDto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author intesar
 */
public class AjaxTaskServiceTest extends BaseTest {

    public AjaxTaskServiceTest() {
    }
    private AjaxTaskService ajaxTaskService;

    public void setAjaxTaskService(AjaxTaskService ajaxTaskService) {
        this.ajaxTaskService = ajaxTaskService;
    }

    /**
     * Test of getCurrentTask method, of class AjaxTaskService.
     */
    public void testGetCurrentTask() {
        System.out.println("getCurrentTask");
        TaskDto taskDto = new TaskDto();        
        taskDto.setAssignedToId(7);
        taskDto.setDeadline(new Date());
        taskDto.setDescription("testing!");
        taskDto.setEstimatedHours(4.0);
        taskDto.setNotificationEmails("mdshannan@gmail.com");
        taskDto.setPriority("Medium");
        taskDto.setProjectId(21);
        taskDto.setStatus("New");
        taskDto.setTitle("title");        
        taskDto.setProjectName("demo");
        ajaxTaskService.saveTask(taskDto);    
    }
    
    public void testUpdateTask1() {
        List<TaskDto> list = ajaxTaskService.getCurrentTask("37");
        TaskDto dto = list.get(0);
        dto.setAssignedToId(7);
        ajaxTaskService.saveTask(dto);
        List<TaskDto> list1 = ajaxTaskService.getCurrentTask("37");
        TaskDto dto1 = list1.get(0);
        assertEquals(dto.getAssignedToId(), dto1.getAssignedToId());
        
    }
    
    public void _testUpdateTask2() {
        List<TaskDto> list = ajaxTaskService.getCurrentTask("37");
        TaskDto dto = list.get(0);
        dto.setProjectId(15);
        ajaxTaskService.saveTask(dto);
        List<TaskDto> list1 = ajaxTaskService.getCurrentTask("37");
        TaskDto dto1 = list1.get(0);
        assertEquals(dto.getProjectId(), dto1.getProjectId());
        
    }
    
    public void _testDeleteTask() {
        List<TaskDto> list = ajaxTaskService.getCurrentTask("38");
        TaskDto dto = list.get(0);        
        ajaxTaskService.deleteTask(dto.getId());
    }
    
    public void testDateFormat() {
        String dt = "29 Jan 2009";
        String pattern = "dd MMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            System.out.println(" Date : " + simpleDateFormat.parse(dt));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    public void testChangeStatus() {
        
        ajaxTaskService.changeTaskStatus(43, "Completed");
    }
}