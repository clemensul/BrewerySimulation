/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.controller;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.planspiel.model.User;

/**
 *
 * @author cleme
 */
public class MarketTest {
    
    public MarketTest() {
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
     * Test of makeSimulation method, of class Market.
     */
    @Test
    public void testMakeSimulation() {
        System.out.println("makeSimulation");
        ArrayList<User> players = null;
        int currentPeriod = 0;
        Market instance = new Market();
        instance.makeSimulation(players, currentPeriod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcMarketShare method, of class Market.
     */
    @Test
    public void testCalcMarketShare() {
        System.out.println("calcMarketShare");
        Market instance = new Market();
        instance.calcMarketShare();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of calcMarketShare method, of class Market.
     */
    @Test
    public void testSellBeer() {
        System.out.println("calcMarketShare");
        Market instance = new Market();
        double marketVolume = 500;
        
        ArrayList<User> players = new ArrayList<User>();
       
        User u1 = new User("a", "cookie", true);
        u1.getCompany().initializePeriods(0, 0, 0, 0.2);
        u1.getCompany().getCurrentPeriod().setProducedHectolitres(500);
        
        User u2 = new User("b", "cookie", false);
        u2.getCompany().initializePeriods(0, 0, 0, 0.45);
        u2.getCompany().getCurrentPeriod().setProducedHectolitres(500);
        
        User u3 = new User("c", "cookie", false);
        u3.getCompany().initializePeriods(0, 0, 0, 0.35);
        u3.getCompany().getCurrentPeriod().setProducedHectolitres(500);
        
        players.add(u1);
        players.add(u2);
        players.add(u3);
        
        instance.sellBeer(players, marketVolume);
        System.out.println("MarketVolume: " + marketVolume);
        
        System.out.println("User: " + players.get(0).getCompany().getName());
        System.out.println("\tSold: " + players.get(0).getCompany().getCurrentPeriod().getSoldHectolitres());
        
        System.out.println("User: " + players.get(1).getCompany().getName());
        System.out.println("\tSold: " + players.get(1).getCompany().getCurrentPeriod().getSoldHectolitres());
        
        System.out.println("User: " + players.get(2).getCompany().getName());
        System.out.println("\tSold: " + players.get(2).getCompany().getCurrentPeriod().getSoldHectolitres());
    }
    
}
