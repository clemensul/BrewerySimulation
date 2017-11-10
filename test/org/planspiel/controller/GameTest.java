/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.controller;

import java.util.HashMap;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.planspiel.model.User;

/**
 *
 * @author makra
 */
public class GameTest {

    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of showPlayers method, of class Game.
     */
    @Test
    public void testShowPlayers() {
        System.out.println("showPlayers");
        Game instance = null;
        JsonArray expResult = null;
        JsonArray result = instance.showPlayers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Game.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Game instance = null;
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlayer method, of class Game.
     */
    @Test
    public void testAddPlayer() {
        System.out.println("addPlayer");
        String name = "";
        String cookie = "";
        Boolean admin = null;
        Game instance = null;
        instance.addPlayer(name, cookie, admin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class Game.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        Game instance = null;
        instance.initialize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsers method, of class Game.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        Game instance = null;
        HashMap<String, User> expResult = null;
        HashMap<String, User> result = instance.getUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkClosed method, of class Game.
     */
    @Test
    public void testCheckClosed() {
        System.out.println("checkClosed");
        Game instance = null;
        Boolean expResult = null;
        Boolean result = instance.checkClosed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submitValues method, of class Game.
     */
    @Test
    public void testSubmitValues() {
        System.out.println("submitValues");
        String cookie = "";
        double producedHectolitres = 0.0;
        double pricePerHectolitre = 0.0;
        double optionMarketing1 = 0.0;
        double optionMarketing2 = 0.0;
        double optionMarketing3 = 0.0;
        double optionDevelopment1 = 0.0;
        double optionDevelopment2 = 0.0;
        double optionDevelopment3 = 0.0;
        Game instance = null;
        Boolean expResult = null;
        Boolean result = instance.submitValues(cookie, producedHectolitres, pricePerHectolitre, optionMarketing1, optionMarketing2, optionMarketing3, optionDevelopment1, optionDevelopment2, optionDevelopment3);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextPeriod method, of class Game.
     */
    @Test
    public void testNextPeriod() {
        System.out.println("nextPeriod");
        Game instance = null;
        instance.nextPeriod();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of endGame method, of class Game.
     */
    @Test
    public void testEndGame() {
        System.out.println("endGame");
        Game instance = null;
        JsonObject expResult = null;
        JsonObject result = instance.endGame();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFinished method, of class Game.
     */
    @Test
    public void testIsFinished() {
        System.out.println("isFinished");
        Game instance = null;
        boolean expResult = false;
        boolean result = instance.isFinished();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentPeriod method, of class Game.
     */
    @Test
    public void testGetCurrentPeriod() {
        System.out.println("getCurrentPeriod");
        Game instance = null;
        int expResult = 0;
        int result = instance.getCurrentPeriod();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @After
    public void tearDown() {
    }
}
