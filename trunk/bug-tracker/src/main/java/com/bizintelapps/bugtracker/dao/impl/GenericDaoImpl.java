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

import com.bizintelapps.bugtracker.dao.GenericDao;
import com.bizintelapps.bugtracker.dao.PagingParams;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

/**
 * Hibernate implementation of GenericDao. A typesafe implementation of CRUD and finder methods
 * based on Hibernate and Spring AOP. The finders are implemented through the executeFinder method.
 * Normally called by the FinderIntroductionInterceptor.
 *
 * 
 */
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private final Log log = LogFactory.getLog(getClass());
    // default FinderNamingStrategy can be overriden in Spring config
    private EntityManager entityManager;
    private Class<T> type;
    // ****************************** constructors ******************************
    public GenericDaoImpl(Class<T> type) {
        this.type = type;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    // ****************************** methods ******************************
    @Override
    public void create(T o) {
        entityManager.persist(o);
    }

    @Override
    public T read(PK id) {
        try {
            return entityManager.find(type, id);
        } catch (RuntimeException re) {
            log.debug(re);
            return null;
        }
    }

    @Override
    public void update(T o) {
        entityManager.merge(o);
    }

    @Override
    public void delete(T o) {
        entityManager.merge(o);
        entityManager.remove(o);
    }

    @Override
    public PagingParams<T> findAll(PagingParams pagingParams) {
        // ql = select o from users o;
        // creating findall query
        String ql = " select o from " + type.getSimpleName() + " o ";
        if (pagingParams == null) {
            pagingParams = new PagingParams(0, 50, null);
        }
        if (pagingParams.getSortBy() != null && pagingParams.getSortBy().length() > 0) {
            ql = " select o from " + type.getSimpleName() + " o order by o." + pagingParams.getSortBy();
        }
        // execute the query
        List result = executeQueryReturnList(ql, pagingParams);
        // create query to find total objects in table
        // select count(o) from users o
        String qlCount = " select count(o) from " + type.getSimpleName() + " o ";
        Long total = executeQueryReturnInt(qlCount).longValue();
        pagingParams.setTotalObjects(total);
        pagingParams.setCurrentList(result);
        return pagingParams;
    }

    @Override
    public PagingParams<T> findByProperty(String propertyName, Object value, PagingParams<T> pagingParams) {
        try {
            // ql = select o from users o where o.propertyName = ?1 ;                
            String ql = " select o from " + type.getSimpleName() + " o where o." +
                    propertyName + " = ?1 ";
            System.out.println(ql);
            if (pagingParams == null) {
                pagingParams = new PagingParams(0, 50, null);
            }
            if (pagingParams.getSortBy() != null && pagingParams.getSortBy().length() > 0) {
                ql = " select o from " + type.getSimpleName() + " o where o." +
                        propertyName + " = ?1 order by o." + pagingParams.getSortBy();
            }
            // execute the query
            List result = executeQueryReturnList(ql, pagingParams, value);
            // create query to find total objects in table
            // select count(o) from users o
            String qlCount = " select count(o) from " + type.getSimpleName() + " o  where o." +
                    propertyName + " = ?1 ";
            System.out.println(qlCount);
            Long total = executeQueryReturnInt(qlCount, value).longValue();
            pagingParams.setTotalObjects(total);
            pagingParams.setCurrentList(result);
            System.out.println("finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagingParams;
    }

    protected PagingParams<T> executeNamedQueryPagedResult(String namedQuery, String namedQueryCount, PagingParams<T> pagingParams, Object... params) {
        Long total = 0L;
        if (namedQueryCount != null || namedQueryCount.length() > 0) {
            total = (Long) executeNamedQueryReturnSingleObject(namedQueryCount, params);
        }
        List<T> result = executeNamedQueryReturnList(namedQuery, pagingParams, params);
        if (pagingParams == null) {
            pagingParams = new PagingParams(0, 50, null);
        }
        pagingParams.setTotalObjects(total);
        pagingParams.setCurrentList(result);
        return pagingParams;
    }

    protected List<T> executeNamedQueryList(String namedQuery, PagingParams<T> pagingParams, Object... params) {
        return executeNamedQueryReturnList(namedQuery, pagingParams, params);
    }

    protected T executeNamedQuerySingleResult(String namedQuery, Object... params) {
        return (T) executeNamedQueryReturnSingleObject(namedQuery, params);
    }

    protected Long executeNamedQuerySingleLong(String namedQuery, Object... params) {
        return (Long) executeNamedQueryReturnSingleObject(namedQuery, params);
    }

    protected Object executeNamedQueryReturnSingleObject(final String namedQuery, final Object... params) throws DataAccessException {
        Query query = entityManager.createNamedQuery(namedQuery);
        int i = 1;
        for (Object param : params) {
            query.setParameter(i++, param);
        }
        try {
            return query.getSingleResult();
        } catch (RuntimeException re) {
            log.debug(re);
            return null;
        }
    }

    private List<T> executeNamedQueryReturnList(final String namedQuery, final PagingParams pagingParams, final Object... params) throws DataAccessException {
        Query query = entityManager.createNamedQuery(namedQuery);
        int i = 1;
        for (Object param : params) {
            query.setParameter(i++, param);
        }
        if (pagingParams != null) {
            query.setFirstResult((int) pagingParams.getStart());
            query.setMaxResults(pagingParams.getMaxLimit());
        }
        try {
            List result = query.getResultList();
            return result;
        } catch (RuntimeException re) {
            log.debug(re);
            return null;
        }
    }

    private Integer executeQueryReturnInt(final String jpql, final Object... params) throws DataAccessException {
        Query query = entityManager.createQuery(jpql);
        int i = 1;
        for (Object param : params) {
            query.setParameter(i++, param);
        }
        Long result = (Long) query.getSingleResult();
        try {
            return result.intValue();
        } catch (RuntimeException re) {
            log.debug(re);
            return null;
        }
    }

    private List<T> executeQueryReturnList(final String jpql, final PagingParams pagingParams, final Object... params) throws DataAccessException {
        Query query = entityManager.createQuery(jpql);
        if (pagingParams != null) {
            query.setFirstResult((int) pagingParams.getStart());
            query.setMaxResults(pagingParams.getMaxLimit());
        }
        int i = 1;
        for (Object param : params) {
            query.setParameter(i++, param);
        }
        try {
            List result = query.getResultList();
            return result;
        } catch (RuntimeException re) {
            log.debug(re);
            return null;
        }

    }
}