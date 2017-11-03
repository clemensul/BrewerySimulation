/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.controller;

import java.util.ArrayList;
import java.util.Collection;
import javax.json.JsonObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.planspiel.model.User;

/**
 *
 * @author Caterina
 */
public class GameIT {
    
    public GameIT() {
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
     * Test of showPlayers method, of class Game.
     */
    @Test
    public void testShowPlayers() {
        System.out.println("showPlayers");
        Game instance = null;
        JsonObject expResult = null;
        JsonObject result = instance.showPlayers();
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
     * Test of startGame method, of class Game.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        Game instance = null;
        Collection<User> expResult = null;
        Collection<User> result = instance.startGame();
        assertEquals(expResult, result);
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
        ArrayList<User> expResult = null;
        ArrayList<User> result = instance.getUsers();
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
        float producedHectolitres = 0.0F;
        float pricePerHectolitre = 0.0F;
        float optionMarketing1 = 0.0F;
        float optionMarketing2 = 0.0F;
        float optionMarketing3 = 0.0F;
        float development = 0.0F;
        Game instance = null;
        instance.submitValues(producedHectolitres, pricePerHectolitre, optionMarketing1, optionMarketing2, optionMarketing3, development);
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
    
}
