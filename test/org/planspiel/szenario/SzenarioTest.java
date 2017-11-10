/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.szenario;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.planspiel.controller.*;
import org.planspiel.model.*;
import org.planspiel.websocket.*;

/**
 *
 * @author Marvin und Caterian
 */
public class SzenarioTest {

    private Game game;

    public SzenarioTest() {

        game = new Game(10000, 4, "Szenario Test", "sTest");

    }

    @Test
    public void szenario() {

        game.addPlayer("Alice", "ACookie", false);
        User alice = game.getUsers().get("ACookie");

        game.addPlayer("Bob", "BCookie", true);
        User bob = game.getUsers().get("BCookie");
        
        game.initialize();

        while (!game.isFinished()) {
            
            //Alice
            submitValues(alice, 1.3);
            System.out.println(alice.getCompany().toJson().toString());
            
            //Bob
            submitValues(bob, 1.1);
            System.out.println(bob.getCompany().toJson().toString());

            game.nextPeriod();
        }

        game.endGame();
    }

    private void submitValues(User user, double factor) {
        double budget = game.getUsers().get(user.getCookie()).getCompany().getLastPeriod().getBudget();
        double marketing = budget / 4;
        double marketing1 = marketing / 3;
        double marketing2 = marketing / 3;
        double marketing3 = marketing / 3;

        double forschung = budget / 4;
        double forschung1 = budget / 3;
        double forschung2 = budget / 3;
        double forschung3 = budget / 3;

        double produktion = budget / 2;

        System.out.println("Submit für: " + user.getCompany().getName());
        
        
        System.out.println("\t Budget: " + budget);
        
        int producedHectolitres = (int) ((produktion
                - user.getCompany().getCurrentPeriod().getOtherFixedCosts())
                / (user.getCompany().getCurrentPeriod().getCostPerHectolitre()));

        System.out.println("\t produzierte Hektoliter: " + producedHectolitres);
        
        double totalCost
                = ((user.getCompany().getCurrentPeriod().getOtherFixedCosts())
                + (user.getCompany().getCurrentPeriod().getCostPerHectolitre())
                * producedHectolitres);

        System.out.println("\t gesamte Kosten: " + totalCost);
        
        int pricePerHectolitre = (int) ((totalCost / producedHectolitres) * factor);

        System.out.println("\t Preis pro Hektoliter: " + pricePerHectolitre);
        
        game.submitValues(user.getCookie(), producedHectolitres, pricePerHectolitre,
                marketing1, marketing2, marketing3,
                forschung1, forschung2, forschung3);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
