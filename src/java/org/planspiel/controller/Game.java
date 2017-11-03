/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import org.planspiel.model.Period;
import org.planspiel.model.User;

public class Game {
//private ArrayList <org.planspiel.model.User> players = new ArrayList<org.planspiel.model.User>();

    private HashMap<String, User> players = new HashMap<>();

    private int currentPeriod = 0;
    private float budget, fixCost, costPerHectolitre;
    private int rounds; //important for the end of the game
    private String id;
    private int maxPeriods;
    private boolean initiated = false;
    private Market market;

    public Game(float budget, int rounds, String id, String playerName, String cookie) {
        this.id = id;
        this.budget = budget;
        this.rounds = rounds;
        this.fixCost = (float) (budget * 0.234);
        this.costPerHectolitre = 420;
        this.maxPeriods = 3;
        market = new Market();
    }

    public JsonArray showPlayers() {

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (User user : players.values()) {
            builder.add(Json.createObjectBuilder()
                    .add("name", user.getCompany().getName())
                    .add("admin", user.getAdmin()));
        }

        return builder.build();
    }

    public String getId() {
        return id;
    }

//adding players before the game starts
    public void addPlayer(String name, String cookie, Boolean admin) {
        players.put(helper.getUserHash(cookie), new User(name, cookie, admin));
    }

//start button
    public void initialize() {
        if (!initiated) {
            Collection<User> al = players.values();

            double initialMarketShare = (1d / (double) al.size());

            for (User u : al) {
                u.getCompany().initializePeriods(budget, fixCost, costPerHectolitre, initialMarketShare);
            }

            ArrayList<User> users = new ArrayList<User>(players.values());
            market.makeSimulation(users, currentPeriod);

            currentPeriod++;

            for (User u : al) {
                u.getCompany().addPeriod(u.getCompany().getCurrentPeriod());
            }

            initiated = true;
        }
    }

    public HashMap<String, User> getUsers() {
        return players;
    }

    public Boolean checkClosed() {
        Boolean closed = true;
        for (Iterator<User> it = players.values().iterator(); it.hasNext();) {
            if (!it.next().getCompany().getCurrentPeriod().getClosed()) {
                closed = false;
            }
        }
        return closed;
    }

    public Boolean submitValues(String cookie, double producedHectolitres, double pricePerHectolitre,
            double optionMarketing1, double optionMarketing2, double optionMarketing3,
            double optionDevelopment1, double optionDevelopment2, double optionDevelopment3) {

        //wenn das Spiel bereits beendet ist
        if (isFinished()) return false;
        
        Period p = players.get(helper.getUserHash(cookie)).getCompany().getCurrentPeriod();

        if (p.getClosed()) return false;
        
        p.setPricePerHectolitre(pricePerHectolitre);
        p.setProducedHectolitres(producedHectolitres);
        p.setOptionMarketing1(optionMarketing1);
        p.setOptionMarketing2(optionMarketing2);
        p.setOptionMarketing3(optionMarketing3);
        p.setOptionDevelopment1(optionDevelopment1);
        p.setOptionDevelopment2(optionDevelopment2);
        p.setOptionDevelopment3(optionDevelopment3);

        p.setClosed(true);
        return checkClosed();
    }

    public void nextPeriod() {

        System.out.println("Simulation of Period: " + currentPeriod + " started.");

        //Markt simulieren
        ArrayList<User> users = new ArrayList(players.values());
        market.makeSimulation(users, currentPeriod);

        if (currentPeriod == maxPeriods) {

            System.out.println("Game ended.");
            endGame();
        } else {
            //Neue Periode erstellen
            Collection<User> al = players.values();
            for (User user : al) {
                user.getCompany().addPeriod(user.getCompany().getCurrentPeriod());
            }
            currentPeriod++;
        }
    }

    protected JsonObject endGame() {
        String error = "";

        User winner = null;

        Iterator<User> it = players.values().iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (winner == null || user.getCompany().getMarketShare() > winner.getCompany().getMarketShare()) {
                winner = user;
            }
        }

        JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", "end")
                .add("winner", winner.getCompany().getName())
                .add("error", error)
                .build();
        
        return message;
    }

    public boolean isFinished() {
        return currentPeriod == maxPeriods;
    }

    public int getCurrentPeriod() {
        return currentPeriod;
    }
}
