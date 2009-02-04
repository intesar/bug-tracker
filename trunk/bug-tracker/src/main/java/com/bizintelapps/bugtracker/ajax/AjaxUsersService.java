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

import com.bizintelapps.mail.MailSender;
import com.bizintelapps.bugtracker.service.UsersService;
import com.bizintelapps.bugtracker.dto.UsersDto;
import com.bizintelapps.bugtracker.dto.UsersMinDto;
import com.bizintelapps.bugtracker.exceptions.ServiceRuntimeException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author intesar
 */
public class AjaxUsersService {

    /**
     * registration for new enterprise or company
     * 
     * @param usersDto
     * @return
     */
    public String signUp(UsersDto usersDto) {
        String msg = " You have Successfully Signed Up, Please login with your Username/Email & Password! ";
        try {
            // this should handle create/update
            usersService.signUp(usersDto);
        } catch (ServiceRuntimeException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
        return msg;
    }

    /**
     *  
     * saves and returns user list
     * @param usersDto
     * @return
     */
    public List<UsersDto> saveUser(UsersDto usersDto) {
        try {
            // this should handle create/update
            return usersService.saveAndGetUser(usersDto, SecurityUtil.getUsername());
        } catch (ServiceRuntimeException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }

    }

   

    /**
     * excuted by logged in user to change password
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public String changeMyPassword(String oldPassword, String newPassword) {
        try {
            usersService.changePassword(SecurityUtil.getUsername(), oldPassword, newPassword);
            return "Password changed successfully!";
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * first step in forgot password
     * user requests that he forgots password and enters his username/email
     * systems sends a unique code to his email which he uses it to change his password
     * @param username
     * @return
     */
    public String requestPasswordKeySendToEmail(String username) {
        try {
            usersService.requestPasswordKeySendToEmail(username);
            return "A key has been send to your email please use it to change your account password!";
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * second step in forgot password
     * user uses key to change password
     * @param username email
     * @param emailKey key to send to user email
     * @param newPassword new password
     * @return
     */
    public String resetPasswordWithKey(String username, String emailKey, String newPassword) {
        try {
            usersService.resetPasswordWithKey(username, emailKey, newPassword);
            return "Password changed successfully, please login with your new password";
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }
    
    public List<UsersMinDto> getUsersListforDropdown() {
        return usersService.getUsersListforDropdown(SecurityUtil.getUsername());
    }

   /**
     * user object for given id
     * @param userId
     * @return
     */
    public UsersDto getUserDetails() {
        try {
            return usersService.getUserByUsername(SecurityUtil.getUsername());
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }


    }

    /**
     * user list for dropdown
     * @return
     */
    public List<UsersDto> getActiveUserList() {
        try {
            return usersService.getActiveUserList(SecurityUtil.getUsername());
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
     * @return
     */
    public List<UsersDto> getUserList() {
        try {
            return usersService.getUsers(SecurityUtil.getUsername()).getCurrentList();
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
     * @param userId 
     * @param enabled if true user is enabled else user is disabled and cannot access system
     * @return
     */
    public String enableDisalbeUser(Integer userId, boolean enabled) {
        String msg = " User Status Changed Successfully! ";
        try {
            usersService.enableDisableUser(userId, enabled, SecurityUtil.getUsername());
            return msg;
        } catch (ServiceRuntimeException se) {
            log.error(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceRuntimeException(ERROR_MESSAGE);
        }
    }

    /**
     * deletes user if possible
     * @param userId
     * @return
     */
    public List<UsersDto> deleteUser(Integer userId) {
        //String msg = " User Deleted Successfully! ";
        try {
            usersService.deleteUser(userId, SecurityUtil.getUsername());
            return getUserList();
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
     * @param usersService
     */
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Autowired
    private MailSender mailSender;
    @Autowired
    private UsersService usersService;
    private final String ERROR_MESSAGE = "Error, Please change input and try again!";
    private final Log log = LogFactory.getLog(getClass());
}
