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

import java.util.List;

/**
 *
 * @author intesar
 */
/**
 * 
 * This class holds input parameters for service level calls. These params are for pagination
 * efforts needed on the front end.
 * 
 *  Use this class extensively for pagination
 * 
 */
public class PagingParams<T> {

    private int start;
    private int maxLimit;
    private String sortBy;
    private long totalObjects;
    private List<T> currentList;

    /**
     * 
     * @param start database start element to return default is 0
     * @param limit total no of objects to return default is 100
     * @param sortBy entity field name default null
     */
    public PagingParams() {
    }

    /**
     * 
     * @param start database start element to return default is 0
     * @param limit total no of objects to return default is 100
     * @param sortBy entity field name default null
     */
    public PagingParams(int start, int limit, String sortBy) {
        this.start = start;
        this.maxLimit = limit;
        this.sortBy = sortBy;
    }
    
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<T> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(List<T> currentList) {
        this.currentList = currentList;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public long getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(long totalObjects) {
        this.totalObjects = totalObjects;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    /**
     * current position =  current position - limit
     */
    public void previous() {
        int _start = getStart() - getMaxLimit() ;
        if ( start > -1 ) {
            setStart ( _start );
        }
    }
    /**
     * current position =  current position + limit
     */
    public void next() {
        int _start = getStart() + getMaxLimit() ;
        if ( start <= getTotalObjects() ) {
            setStart ( _start );
        }
        
    }
    /**
     * current position =  0
     */
    public void first() {
        setStart(0);
    }
    /**
     * current position =  total objects - limit
     */
    public void last() {
        long _start = getTotalObjects() - getMaxLimit();
        if ( _start > -1 ) {
            setStart ( start );
        }
    }
}

