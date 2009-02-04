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
package com.bizintelapps.bugtracker.service.impl;

import com.bizintelapps.mail.MailSender;
import com.bizintelapps.bugtracker.dao.AuthoritiesDao;
import com.bizintelapps.bugtracker.dao.OrganizationDao;
import com.bizintelapps.bugtracker.dao.PagingParams;
import com.bizintelapps.bugtracker.dao.UsersDao;
import com.bizintelapps.bugtracker.dto.UsersMinDto;
import com.bizintelapps.bugtracker.entity.Users;
import com.bizintelapps.bugtracker.dtoa.UsersDtoA;
import com.bizintelapps.bugtracker.service.UsersService;
import com.bizintelapps.bugtracker.dto.UsersDto;
import com.bizintelapps.bugtracker.entity.Authorities;
import com.bizintelapps.bugtracker.entity.Organization;
import com.bizintelapps.bugtracker.exceptions.ServiceRuntimeException;
import com.bizintelapps.bugtracker.service.validator.UsersValidator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Override
    public void signUp(UsersDto usersDto) {
        // check username is not in use
        Users u1 = usersDao.findByUsername(usersDto.getUsername());
        if (u1 != null && u1.getId() != null) {
            throw new ServiceRuntimeException(usersDto.getUsername() + " is already in use");
        }
        // checking email is not in use
        Users u2 = usersDao.findByEmail(usersDto.getEmail());
        if (u2 != null && u2.getId() != null) {
            throw new ServiceRuntimeException(usersDto.getEmail() + " is already in use");
        }
        // checking organization name is not in use
        Organization org = organizationDao.findByName(usersDto.getOrganization());
        if (org != null && org.getId() != null) {
            throw new ServiceRuntimeException(usersDto.getOrganization() + " is already in use");
        }
        // copy usersDto contents to new Users object and persist
        org = new Organization(null, usersDto.getOrganization(), new Date());
        organizationDao.create(org);
        org = organizationDao.findByName(org.getName());
        Users users = new Users();
        usersConverter.copyForSignUp(usersDto, users);
        users.setPassword(passwordEncryptor.encryptPassword(users.getPassword()));
        users.setIsEncrypted(true);
        users.setOrganization(org);
        usersDao.create(users);
        Authorities authorities1 = new Authorities(null, users.getUsername(), Authorities.ROLE_ADMIN);
        Authorities authorities2 = new Authorities(null, users.getUsername(), Authorities.ROLE_USER);
        authoritiesDao.create(authorities1);
        authoritiesDao.create(authorities2);
        String body = "Hello " + usersDto.getFirstname() + ", \n";
        body += "Your account has been successfully created at Grow-Fast.com. You are all set to use Grow-Fast's professional services! \n"
                + "Grow-Fast is different, and commited towards enhancing work flow within Enterprises and Personal! "
                + " Thanks, Grow-Fast Team";
        mailSender.sendMail(new String[]{usersDto.getEmail()}, "Grow-Fast Commnuity Welcomes you!", body);
    }

    @Override
    public List<UsersDto> saveAndGetUser(UsersDto usersDto, String savedBy) {
        saveUser(usersDto, savedBy);
        return getUsers(savedBy).getCurrentList();
    }

    @Override
    public List<UsersMinDto> getUsersListforDropdown(String requestedBy) {
        Users u = usersDao.findByUsername(requestedBy);
        if (u.isIsAdministrator()) {
            List<Users> list = usersDao.findByOrganizationId(u.getOrganization().getId());
            return usersConverter.copyAllForReportDropdown(list);
        } else {
            return new ArrayList<UsersMinDto>();
        }
    }

    @Override
    public void saveUser(UsersDto usersDto, String savedBy) {
        Users savedByUsers = usersDao.findByUsername(savedBy);
        // if id is null then persist
        if (usersDto.getId() == null) {
            // check user is admin
            if (!savedByUsers.isIsAdministrator()) {
                throw new ServiceRuntimeException("only administrators can create new users");
            }
            // check whether username or email is already in use
            Users u1 = usersDao.findByUsername(usersDto.getUsername());
            if (u1 != null && u1.getId() != null) {
                throw new ServiceRuntimeException(usersDto.getUsername() + " is already in use");
            }
            Users u2 = usersDao.findByEmail(usersDto.getEmail());
            if (u2 != null && u2.getId() != null) {
                throw new ServiceRuntimeException(usersDto.getEmail() + " is already in use");
            }
            // copy usersDto contents to new Users object and persist
            Users users = new Users();
            usersConverter.copyForCreate(usersDto, users);
            users.setCreateDate(new Date());
            users.setCreateUser(savedByUsers.getId());
            users.setLastUpdateDate(new Date());
            users.setLastUpdateUser(savedByUsers.getId());
            users.setOrganization(savedByUsers.getOrganization());
            users.setPassword(passwordEncryptor.encryptPassword(usersDto.getUsername()));
            users.setIsEncrypted(true);
            usersDao.create(users);
            Authorities authorities = new Authorities(null, users.getUsername(), Authorities.ROLE_USER);
            if (usersDto.isAdministrator()) {
                Authorities authorities1 = new Authorities(null, users.getUsername(), Authorities.ROLE_ADMIN);
                authoritiesDao.create(authorities1);
            }
            authoritiesDao.create(authorities);
            mailSender.sendMail(new String[]{users.getUsername()}, "Welcome " + users.getFirstname() + " " + users.getLastname() 
                    + " your registration was Successfull!, your username/password is " + users.getUsername() + "/" + users.getUsername(), "");
        } else { // update state to db
            Users users = usersDao.read(usersDto.getId());
            // admin or self can update there records
            if (savedByUsers.isIsAdministrator() || savedByUsers.equals(users) || savedByUsers.getOrganization().equals(users.getOrganization())) {
                // do nothing
            } else {
                throw new ServiceRuntimeException("only administrators or self can update records");
            }
            // copy usersDto contents to existing Users object and persist
            if (users.isIsSuper() && (!usersDto.isAdministrator() || !usersDto.isEnabled())) {
                throw new ServiceRuntimeException("Super user cannot be demoted from admin or disabled");
            }
            log.debug("---------  usersDto.isAdministrator() " + usersDto.isAdministrator());
            log.debug("---------  users.isIsAdministrator() " + users.isIsAdministrator());
            log.debug("---------  usersDto.enabled() " + usersDto.isEnabled());
            log.debug("---------  users.enabled() " + users.getEnabled());
            if (usersDto.isAdministrator() && !users.isIsAdministrator()) {
                // add him to authority
                log.debug("--------- ADD AUTHORITY ");
                Authorities authority = new Authorities(null, usersDto.getUsername(), Authorities.ROLE_ADMIN);
                authoritiesDao.create(authority);
            } else if (!usersDto.isAdministrator() && users.isIsAdministrator()) {
                // remove him from authority
                log.debug("--------- DELETE AUTHORITY ");
                Authorities authorities = authoritiesDao.findByUsernameAndAuthority(usersDto.getUsername(), Authorities.ROLE_ADMIN);
                authoritiesDao.delete(authorities);
            }
            log.debug("--------- before copy ");
            usersConverter.copyForUpdate(usersDto, users);
            users.setLastUpdateDate(new Date());
            users.setLastUpdateUser(savedByUsers.getId());
            usersDao.update(users);            
            log.debug("--------- after updated ");
        }
    }

    @Override
    public void deleteUser(Integer userId, String deletedBy) {
        Users users = usersDao.findByUsername(deletedBy);
        Users usersToBeDeleted = usersDao.read(userId);
        // super user cannot be deleted & only admin can delete others
        if ((users.getOrganization().equals(usersToBeDeleted.getOrganization()) && users.isIsAdministrator()) && !users.isIsSuper()) {
            usersDao.delete(usersToBeDeleted);
            List<Authorities> authorities = authoritiesDao.findByUsername(usersToBeDeleted.getUsername());
            for (Authorities authority : authorities) {
                authoritiesDao.delete(authority);
            }
        } else {
            throw new ServiceRuntimeException("Only Administrator can delete users!");
        }
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        Users users = usersDao.findByUsername(username);
        //self
        log.debug("----- oldPassword" + oldPassword + "====" + username);
        log.debug("----- oldPassword" + passwordEncryptor.encryptPassword(oldPassword));
        log.debug("----- oldPassword" + users.getPassword());
        log.debug("=====" + passwordEncryptor.checkPassword(oldPassword, users.getPassword()));
        if (users != null && passwordEncryptor.checkPassword(oldPassword, users.getPassword())) {
            users.setPassword(passwordEncryptor.encryptPassword(newPassword));
            usersDao.update(users);
        } else {
            throw new ServiceRuntimeException("Invalid Old Password");
        }
    }

    @Override
    public void requestPasswordKeySendToEmail(String username) {
        Users users = usersDao.findByUsername(username);
        if (users == null) {
            throw new ServiceRuntimeException("We didn't find your email in our system!");
        }
        // key is password - 5 chars
        String password = users.getPassword();
        String key = password.substring(0, password.length() - 5);
        // email this key
        this.mailSender.sendMail(new String[]{username}, "Password Reset Key", "Please ignore this email if you have " +
                "not requested for password reset key, as it won't change your login, However if you want to reset your password please " +
                "use this key :" + key);
    }

    @Override
    public void resetPasswordWithKey(String username, String emailKey, String newPassword) {
        Users users = usersDao.findByUsername(username);
        if (users == null) {
            throw new ServiceRuntimeException("We didn't find your email in our system!");
        }
        // key is password - 3 chars
        String password = users.getPassword();
        String key = password.substring(0, password.length() - 5);
        if (key.equals(emailKey)) {
            users.setPassword(passwordEncryptor.encryptPassword(newPassword));
            usersDao.update(users);
            mailSender.sendMail(new String[]{username}, "Your password has been successfully changed", "");
        } else {
            throw new ServiceRuntimeException("Key mismatch, please remove spaces from begining and end of key and try again!");
        }
    }

    @Override
    public PagingParams<UsersDto> getUsers(String forUser) {
        PagingParams<UsersDto> pagingParams = new PagingParams<UsersDto>();
        Users users = usersDao.findByUsername(forUser);
        if (users.isIsAdministrator()) {
            List<UsersDto> list = usersConverter.copyAllForDisplay(usersDao.findByOrganizationId(users.getOrganization().getId()));
            pagingParams.setCurrentList(list);
        } else {
            List<UsersDto> list = new ArrayList<UsersDto>();
            UsersDto usersDto = new UsersDto();
            usersConverter.copyForDisplay(users, usersDto);
            list.add(usersDto);
        }
        return pagingParams;
    }

    @Override
    public List<UsersDto> getActiveUserList(String requestedBy) {
        Users users = usersDao.findByUsername(requestedBy);
        List<Users> list = usersDao.findEnabledUsersByOrganizationId(users.getOrganization().getId());
        List<UsersDto> dtos = usersConverter.copyAllForDropdown(list);
        return dtos;
    }

    @Override
    public void enableDisableUser(Integer userId, boolean enabled, String changedBy) {
        log.debug("---- executing findByUsername with " + changedBy);
        Users changedByUser = usersDao.findByUsername(changedBy);
        log.debug("---- changedUser " + changedByUser.getUsername());
        Users users = usersDao.read(userId);
        // super user cannot be deleted & only admin can delete others
        if ((users.getOrganization().equals(changedByUser.getOrganization()) && changedByUser.isIsAdministrator()) && !users.isIsSuper()) {
            users.setEnabled(enabled);
            usersDao.update(users);
        } else {
            throw new ServiceRuntimeException(changedBy + " is not an Administrator");
        }
    }

    @Override
    public UsersDto getUserByUsername(String username) {
        UsersDto dto = new UsersDto();
        Users users = usersDao.findByUsername(username);
        usersConverter.copyForDisplay(users, dto);
        return dto;
    }
    // -------------------------------getter & setters---------------------------------
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public void setUsersValidator(UsersValidator usersValidator) {
        this.usersValidator = usersValidator;
    }

    public void setUsersConverter(UsersDtoA usersConverter) {
        this.usersConverter = usersConverter;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    public void setPasswordEncryptor(@Qualifier(value = "pro") PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    public void setAuthoritiesDao(AuthoritiesDao authoritiesDao) {
        this.authoritiesDao = authoritiesDao;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Autowired
    private UsersDtoA usersConverter;
    @Autowired
    private UsersValidator usersValidator;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    @Qualifier(value = "pro")
    private PasswordEncryptor passwordEncryptor;
    @Autowired
    private AuthoritiesDao authoritiesDao;
    @Autowired
    private MailSender mailSender;
    private final Log log = LogFactory.getLog(getClass());
}
