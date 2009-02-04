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
package com.bizintelapps.bugtracker.dao.impl;

import com.bizintelapps.bugtracker.dao.PagingParams;
import com.bizintelapps.bugtracker.dao.UserReportDao;
import com.bizintelapps.bugtracker.entity.UserReport;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author intesar
 */
@Repository
public class UserReportDaoImpl extends GenericDaoImpl<UserReport, Integer> implements UserReportDao {

    public UserReportDaoImpl() {
        super(UserReport.class);
    }

    @Override
    public UserReport findByUserMonthAndYear(Integer user, Integer month, Integer year) {
        return executeNamedQuerySingleResult("UserReport.findByUserMonthAndYear", user, month, year);
    }

    @Override
    public List<UserReport> findByUser(Integer user, Integer maxReports) {
        PagingParams<UserReport> pp = new PagingParams<UserReport>(0,maxReports, null);
        return executeNamedQueryList("UserReport.findByUser", pp, user);
    }

    @Override
    public Object findUserSummary(Integer user) {
        Object obj = executeNamedQueryReturnSingleObject("UserReport.findUserSummary", user);
        return obj;
    }
}
