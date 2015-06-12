/*
 * Copyright (c) 2015 Orbis Technologies Inc. All Rights Reserved
 */
package com.masterjedikid.personal.fof.service.main;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author clouddev
 */
public class ServiceTest {
    
    public ServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class Service.
     */
    @Test
    public void testRun() throws Exception {
        System.out.println("run");
        String token = "xoxp-3273869719-3275682192-4150017407-4c2072";
        Service instance = new Service();
        instance.run(token);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSlackMemberList method, of class Service.
     */
    @Test
    public void testGetSlackMemberList() {
        System.out.println("getSlackMemberList");
        String token = "";
        List<String> expResult = null;
        List<String> result = Service.getSlackMemberList(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
