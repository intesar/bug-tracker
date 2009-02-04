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
package com.bizintelapps.promanager.ajax;

import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 *
 * @author intesar
 */
public abstract class BaseTest extends AbstractTransactionalSpringContextTests {
    // specifies the Spring configuration to load for this test fixture
    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                    //"classpath:applicationContext-acegi-security.xml",
                    "classpath:applicationContext-dao.xml",
                    "classpath:applicationContext-dao-em.xml",
                    "classpath:applicationContext-service-transactions.xml",
                    "classpath:applicationContext-ajax.xml",
                    "classpath:applicationContext-mail.xml"
                };
    }

    public void testDummy() {
        assertTrue(true);
    }
}
