/*
 *  Copyright 2009 intesar.
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
package com.bizintelapps.promanager.service.impl;

import com.bizintelapps.promanager.ajax.BaseTest;
import com.bizintelapps.bugtracker.dto.ProjectReportDto;
import com.bizintelapps.bugtracker.dto.UserReportDto;
import com.bizintelapps.bugtracker.service.ReportService;
import static org.junit.Assert.*;

/**
 *
 * @author intesar
 */
public class ReportServiceImplTest extends BaseTest {

    public ReportServiceImplTest() {
    }
    ReportService instance = null;

    public void setInstance(ReportService instance) {
        this.instance = instance;
    }

    /**
     * Test of getUserSummary method, of class ReportServiceImpl.
     */
    public void testGetUserSummary() {
        Integer user = 7;
        String requestedBy = "fatima.shanoor@gmail.com";
        UserReportDto result = instance.getUserSummary(user, requestedBy);
        assertNotNull(result);

    }
    
    public void testGetProjectReportSummary() {
        Integer project = 14;
        String requestedBy = "fatima.shanoor@gmail.com";
        ProjectReportDto result = instance.getProjectReportSummary(project, requestedBy);
        System.out.println ("Est Time : " + result.getEstimatedTime() );
        assertNotNull(result.getEstimatedTime());
    }
}