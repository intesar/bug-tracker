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

import com.bizintelapps.bugtracker.entity.Users;
import com.bizintelapps.bugtracker.dto.UsersDto;
import com.bizintelapps.bugtracker.dto.UsersMinDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author intesar
 */
@Service
public class UsersDtoA {
    
    public List<UsersMinDto> copyAllForReportDropdown(List<Users> users) {
        List<UsersMinDto> dtos = new ArrayList<UsersMinDto> ();
        for ( Users u : users) {
            UsersMinDto dto = new UsersMinDto();
            copyForReportDropdown(u, dto);
            dtos.add(dto);
        }
        return dtos;
    }
    public void copyForReportDropdown(Users u, UsersMinDto dto ) {
        dto.setUsername(u.getUsername());
        dto.setEnabled(u.getEnabled());
        dto.setAdministrator(u.isIsAdministrator());
        dto.setFirstname(u.getFirstname());
        dto.setLastname(u.getLastname());
        dto.setId(u.getId());
    }
    
    public List<UsersDto> copyAllForDisplay(Collection<Users> usersCollection) {
        List<UsersDto> list = new ArrayList<UsersDto>();
        for (Users users : usersCollection) {
            UsersDto usersDto = new UsersDto();
            copyForDisplay(users, usersDto);
            list.add(usersDto);
        }
        return list;
    }

    public List<UsersDto> copyAllForDropdown(List<Users> list) {
        List<UsersDto> dtos = new ArrayList<UsersDto>();
        for (Users u : list) {
            UsersDto dto = new UsersDto();
            dto.setId(u.getId());
            dto.setFirstname(u.getFirstname());
            dto.setLastname(u.getLastname());
            dto.setUsername(u.getUsername());
            dtos.add(dto);
        }
        return dtos;
    }

    public void copyForDisplay(Users users, UsersDto usersDto) {
        usersDto.setCity(users.getCity());
        usersDto.setCountry(users.getCountry());
        usersDto.setEmail(users.getEmail());
        usersDto.setEnabled(users.getEnabled());
        usersDto.setExpirationDate(users.getExpirationDate());
        usersDto.setFirstname(users.getFirstname());
        usersDto.setId(users.getId());
        usersDto.setLastname(users.getLastname());
        usersDto.setAdministrator(users.isIsAdministrator());
        usersDto.setUsername(users.getUsername());
    }

    /**
     * copies everything from userDto to users
     * @param usersDto
     * @param users
     * @return
     */
    public void copyForCreate(UsersDto usersDto, Users users) {
        users.setCity(usersDto.getCity());
        users.setCountry(usersDto.getCountry());
        users.setEmail(usersDto.getEmail());
        users.setEnabled(usersDto.isEnabled());
        users.setExpirationDate(usersDto.getExpirationDate());
        users.setFirstname(usersDto.getFirstname());
        users.setLastname(usersDto.getLastname());
        users.setIsAdministrator(usersDto.isAdministrator());
        users.setCreateDate(usersDto.getCreateDate());
        users.setCreateUser(usersDto.getCreateUser());
        users.setPassword(usersDto.getPassword());
        users.setUsername(usersDto.getUsername());
    }

    public void copyForSignUp(UsersDto usersDto, Users users) {
        users.setCity(usersDto.getCity());
        users.setCountry(usersDto.getCountry());
        users.setEmail(usersDto.getEmail());
        users.setEnabled(true);
        users.setExpirationDate(null);
        users.setFirstname(usersDto.getFirstname());
        users.setLastname(usersDto.getLastname());
        users.setIsAdministrator(true);
        users.setIsSuper(true);
        users.setPassword(usersDto.getPassword());
        users.setUsername(usersDto.getUsername());
        users.setCreateDate(new Date());
        users.setLastUpdateDate(new Date());
    }

    /**
     * only copies fields that can be changed from client side
     * @param usersDto
     * @param users
     * 
     */
    public void copyForUpdate(UsersDto usersDto, Users users) {
        //users.setCity(usersDto.getCity());
        //users.setCountry(usersDto.getCountry());
        //users.setEmail(usersDto.getEmail());
        //users.setExpirationDate(usersDto.getExpirationDate());
        users.setEnabled(usersDto.isEnabled());
        users.setFirstname(usersDto.getFirstname());
        users.setLastname(usersDto.getLastname());
        users.setIsAdministrator(usersDto.isAdministrator());
    }
}
