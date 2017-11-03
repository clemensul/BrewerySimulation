/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Caterina
 */
public class CompanyIT {
    
    public CompanyIT() {
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
     * Test of getName method, of class Company.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Company instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPeriod method, of class Company.
     */
    @Test
    public void testAddPeriod_3args() {
        System.out.println("addPeriod");
        float budget = 0.0F;
        float fixCost = 0.0F;
        float costPerHectolitre = 0.0F;
        Company instance = null;
        instance.addPeriod(budget, fixCost, costPerHectolitre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPeriod method, of class Company.
     */
    @Test
    public void testAddPeriod_Period() {
        System.out.println("addPeriod");
        Period previousPeriod = null;
        Company instance = null;
        instance.addPeriod(previousPeriod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentPeriod method, of class Company.
     */
    @Test
    public void testGetCurrentPeriod() {
        System.out.println("getCurrentPeriod");
        int currentPeriod = 0;
        Company instance = null;
        Period expResult = null;
        Period result = instance.getCurrentPeriod(currentPeriod);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
