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

import com.bizintelapps.bugtracker.dao.UsersDao;
import com.bizintelapps.bugtracker.entity.Users;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author intesar
 */
@Repository
public class UsersDaoImpl extends GenericDaoImpl<Users, Integer> implements UsersDao {

    public UsersDaoImpl() {
        super(Users.class);
    }

    @Override
    public Users findByUsername(String username) {
        return executeNamedQuerySingleResult("Users.findByUsername", username);
    }

    @Override
    public Users findByEmail(String email) {
        return executeNamedQuerySingleResult("Users.findByEmail", email);
    }
    private final Log log = LogFactory.getLog(getClass());

    @Override
    public List<Users> findByOrganizationId(Integer organizationId) {
        return executeNamedQueryList("Users.findByOrganizationId", null, organizationId);
    }

    @Override
    public List<Users> findEnabledUsersByOrganizationId(Integer organizationId) {
        return executeNamedQueryList("Users.findEnabledUsersByOrganizationId", null, organizationId);
    }
}
