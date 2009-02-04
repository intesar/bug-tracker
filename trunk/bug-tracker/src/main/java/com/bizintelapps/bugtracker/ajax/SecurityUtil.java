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

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author intesar
 */
public class SecurityUtil {
    /*
     *  returns logged in user
     */

    public static final String getUsername() {
        String username = null;
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (NullPointerException npe) {
            log.error(npe);
        }
        if (username == null || username.equals("anonymousUser")) {
            return "fatima.shanoor@gmail.com";
        }
        return username;
    }

    public static final boolean isAdmin() {
        GrantedAuthority[] authorities = null;
        try {
            authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        } catch (Exception e) {
            log.error(e);
        }
        if (authorities != null) {
            for (GrantedAuthority a : authorities) {
                if (a.getAuthority().equalsIgnoreCase("role_admin")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final String getUserRole() {
        GrantedAuthority[] authorities = null;
        try {
            authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        } catch (Exception e) {
            // logger            
        }
        boolean admin = false;
        boolean employee = false;
        if (authorities != null) {
            for (GrantedAuthority a : authorities) {
                if (a.getAuthority().equalsIgnoreCase("role_admin")) {
                    admin = true;
                } else if (a.getAuthority().equalsIgnoreCase("role_user")) {
                    employee = true;
                }
            }
        }
        if (admin) {
            return "admin";
        } else if (employee) {
            return "employee";
        } else {
            return "customer";
        }
    }
    private static final Log log = LogFactory.getLog(SecurityUtil.class);
}
