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
package com.bizintelapps.bugtracker.dtoa;

import com.bizintelapps.bugtracker.dao.UsersDao;
import com.bizintelapps.bugtracker.dto.UserReportDto;
import com.bizintelapps.bugtracker.entity.UserReport;
import com.bizintelapps.bugtracker.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class UserReportDtoA {

    public UserReportDto copyForDisplay(UserReport ur) {
        UserReportDto dto = new UserReportDto();
        dto.setTotalCreated(ur.getTotalCreated());
        dto.setAssigned(ur.getAssigned());
        dto.setSelfAssigned(ur.getSelfAssigned());
        dto.setCreatedSelfAssigned(ur.getCreatedSelfAssigned());
        dto.setEstimatedHours(ur.getEstimatedHours());
        dto.setHoursSpend(ur.getHoursSpend());
        dto.setTotalAssigned(ur.getTotalAssigned());
        dto.setTotalCompleted(ur.getTotalCompleted());
        dto.setMonth(ur.getMonth());
        dto.setYear(ur.getYear());
        dto.setUser(ur.getUser());
        Users u = usersDao.read(ur.getUser());
        dto.setName(u.getFirstname());
        dto.setUsername(u.getUsername());
        return dto;
    }

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }
    @Autowired
    private UsersDao usersDao;
}
