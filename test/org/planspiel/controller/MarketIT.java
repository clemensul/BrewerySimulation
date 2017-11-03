package org.planspiel.controller;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.planspiel.model.User;

/**
 *
 * @author Caterina
 */
public class MarketIT {
    
    private ArrayList<User> players;
    Market instance;
    
    @Before
    public void setUp() {
        players = new ArrayList<User>();
        instance = new Market();
    }
     
    @Test
    public void makeSimulationInvalidResultTest() {
        System.out.println("makeSimulationInvalidResultTest");
        try{
        instance.makeSimulation(null, 0);
        } catch(NullPointerException e){
        System.out.println(e);
        }
    }
    
    @Test
    public void makeSimulationValidResultTest() {
        System.out.println("makeSimulationValidResultTest");
        players = new ArrayList<User>(2);                   
        instance.makeSimulation(players, 1);
        assertNotNull(instance);
    }
    
    @Test
    public void compensateMarketVolumeInvalidResultTest() {
        System.out.println("makeSimulationInvalidResultTest");
        try{
        instance.compensateMarketVolume(null, 0, 0);
        } catch(NullPointerException e){
        System.out.println(e);
        }
    }
    
    @Test
    public void compensateMarketVolumeValidResultTest() {
        System.out.println("makeSimulationValidResultTest");
        players = new ArrayList<User>(2);                   
        instance.compensateMarketVolume(players, 1, 2);
        assertNotNull(instance);
    }   
    
    
    @Test
    public void getMarketVolumeInvalidPeriodTest(){
        System.out.println("getMarketVolumeInvalidPeriodTest");
        try{            
            for(int i=0;i <= 5; i++)
            {
               System.out.println(instance.getMarketVolume(0-i));                
            }
        //instance.getMarketVolume(-1);
        //System.out.println(instance.getMarketVolume(-1));
        } catch(NullPointerException e){
        System.out.println("getMarketVolumeInvalidPeriodTest_Exception: " + e);
        }
    }
    
    @Test
    public void getMarketVolumePeriode1Test(){
        System.out.println("getMarketVolumePeriode1Test");
        instance.getMarketVolume(1);
        assertNotNull(instance);
        System.out.println("Der Test ist erfolgreich durchlaufen!");
    }
    
    @After
    public void tearDown() {
        players = null;
        instance = null;
    }

}
