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

package com.bizintelapps.bugtracker.service;

import com.bizintelapps.bugtracker.dao.PagingParams;
import com.bizintelapps.bugtracker.dto.UsersDto;
import com.bizintelapps.bugtracker.dto.UsersMinDto;
import java.util.List;

/**
 *
 * @author intesar
 */
public interface UsersService {

    /**
     * enabled users with id, firstname, lastname, username
     * @return
     */
    public List<UsersDto> getActiveUserList(String requestedBy);
    
    /**
     * used for reports drop down
     * @param requestedBy
     * @return
     */
    public List<UsersMinDto> getUsersListforDropdown(String requestedBy);
    /**
     * creates
     * @param usersDto
     */
    public void signUp (UsersDto usersDto);
    
    public List<UsersDto> saveAndGetUser(UsersDto usersDto, String savedBy);
    /**
     * 
     * @param usersDto
     * 
     *  persists or sinks user to database
     * 
     *  if username is not in use and email is not in use
     * 
     *  and no validation erorrs
     * 
     *  throws validation exception which should be propagated to client 
     * 
     *  throws Spring JPA exception which should not be exposed to client
     * 
     */
    public void saveUser(UsersDto usersDto, String savedBy);
    
    /**
     * 
     * @param userId to be deleted, possible only when user has no data
     * @param deletedBy deleting user, can only delete if the user is administrator
     * @throws RuntimeException if not deleted or user doesn't have rights or no match for the given id
     * 
     */
    public void deleteUser( Integer userId, String deletedBy );

    /**
     *  administrator or self user could change password
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param changedBy
     */
    //public void changePassword(Integer userId, String oldPassword, String newPassword, String changedBy);
    
    /**
     * 
     * @param username email
     * @param oldPassword 
     * @param newPassword
     */
    public void changePassword ( String username, String oldPassword, String newPassword);
    
    /**
     * 
     * @param username email
     *  
     *  sends a unique key to email address if valid
     */
    public void requestPasswordKeySendToEmail (String username);
    
    /**
     * 
     * @param username email
     * @param emailKey key to send to user email
     * @param newPassword new password
     * @return
     */
    public void resetPasswordWithKey(String username, String emailKey, String newPassword);
    
    
    /**
     * gets users if administrator or else just get self
     * @param forUser
     * @return
     */
    
    public PagingParams<UsersDto> getUsers(String forUser);
    
    /**
     * return user instance for given username
     * @param username
     * @return
     */
    public UsersDto getUserByUsername (String username);
    
    /**
     * 
     * @param userId
     * @param enabled
     * @param changedBy
     */
    public void enableDisableUser ( Integer userId, boolean enabled, String changedBy);
}
