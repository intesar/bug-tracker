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
package com.bizintelapps.bugtracker.dao;

import java.io.Serializable;

/**
 *
 * @author intesar
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * creates a new instance in the database
     * @param newInstance
     * @return
     */
    void create(T newInstance);

    /**
     * merges the new state to the database
     * @param transientObject
     */
    void update(T transientObject);

    /**
     * removes the instance from the database
     * @param persistentObject
     */
    void delete(T persistentObject);

    /**
     * finds the instance by primary key id
     * @param id
     * @return
     */
    T read(PK id);

    /**
     * finds all records 
     * @param pagingParams defines the limit, if null then returns 0 to 100 objects
     * @return PagedResult object which holds returned list of objects along with 
     * maxObjects in database for this table
     */
    PagingParams<T> findAll(PagingParams<T> pagingParams);

    /**
     * 
     * @param propertyName entity property name
     * example: 
     *  
     * Class Users {
     *   private String username;
     *   private int age;
     *   ..
     * }
     * 
     *  findByProperty ( "username", "intesar.mohammed");
     *  findByProperty ( "age", 25);
     * 
     * @param value
     * @param pagingParams
     * @return PagedResult object which holds returned list of objects along with 
     * maxObjects in database for this query
     * 
     */
    PagingParams<T> findByProperty(String propertyName, Object value, PagingParams<T> pagingParams);


}
