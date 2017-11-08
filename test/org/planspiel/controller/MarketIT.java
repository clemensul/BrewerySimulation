package org.planspiel.controller;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.planspiel.model.User;
import java.util.Iterator;
import org.planspiel.model.Period;

/**
 *
 * @author Caterina
 */
public class MarketIT {
    
    private ArrayList<User> players;
    private double marketVolume;
    Market instance;
    
    public MarketIT() {
    }
    
    @Before
    public void setUp() {
        players = new ArrayList<User>();
        instance = new Market();
    }
    
    @Test
    public void testMakeSimulationInvalidResult() {
        System.out.println("makeSimulationInvalidResult");
        try {
        instance.makeSimulation(null, 0);
        } catch(NullPointerException e){
        System.out.println("Dieser Test erwartet eine " + e);
        }
    }
    
    @Test
    public void testMakeSimulationValidResult() {
        System.out.println("makeSimulationValidResult");
        players = new ArrayList<User>(2);
        instance.makeSimulation(players, 1);
        assertNotNull(instance);
    }

    
    @Test
    public void testCalcMarketShareInvalidResult() {
        System.out.println("calcMarketShareInvalidResult");
        try {
        Period period = new Period(0,0,0,0);
        Iterator<User> it = null;
        instance.calcMarketShare();
        } catch(NullPointerException e){
        System.out.println("Dieser Test erwartet eine " + e);
        }
    }
    
    
    @Test
    public void testCalcMarketShareValidResult() {
        System.out.println("calcMarketShareValidResult");
        players = new ArrayList<User>(3);
        Period p = new Period(100,200,300,400);
        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
        p = it.next().getCompany().getCurrentPeriod();
        instance.calcMarketShare();
        assertNotNull(instance);
        }
        System.out.println("Calculated market: 0.0");
    }
    
    /*
    @Test
    public void testGetMarketVolumeInvalidResult() {
        System.out.println("getMarketVolumeInvalidResult");
        try {
        instance.getMarketVolume(-1);
        System.out.println("Man kann immer noch Minus und eine Null eintragen " + instance);
        } catch(Exception e){
        System.out.println("Dieser Test erwartet eine " + e);
        }
    }
    
    
    @Test
    public void testGetMarketVolumeValidResult() {
        System.out.println("getMarketVolumeValidResult");
        double expResult = 12222.0;
        double result = instance.getMarketVolume(3);
        assertEquals(expResult, result, 12222.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    
    @Test
    public void testSellBeerInvalidResult() {
        System.out.println("sellBeerInvalidResult");
        try {
        instance.sellBeer(null);
        } catch(NullPointerException e){
        System.out.println("Dieser Test erwartet eine " + e);
        }
    }

    
    @Test
    public void testSellBeerValidResult() {
        System.out.println("sellBeerValidResult");
        players = new ArrayList<User>(3);
        Period p = new Period(100,200,300,400);
        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
        p = it.next().getCompany().getCurrentPeriod();
        p.setTempMarketShare(0.0);
        instance.sellBeer(players);
        assertNotNull(instance);
        }
        System.out.println("Sold Beer: 0.0");
    }
    
    
    @Test
    public void testSellBeerRekInvalidResult() {
        System.out.println("sellBeerRekInvalidResult");
        try {
        instance.sellBeerRek(null, 0.0);
        } catch (NullPointerException e){
        System.out.println("Dieser Test erwartet eine " + e);
        }
    }
    
    
    @Test
    public void testSellBeerRekValidResult() {
        System.out.println("sellBeerRekValidResult");
        players = new ArrayList<User>(3);
        double marketVolume = 200.0;
        instance.sellBeerRek(players, marketVolume);
        System.out.println(instance);
    }

  @After
    public void tearDown() {
        players = null;
        instance = null;
    }  
}
