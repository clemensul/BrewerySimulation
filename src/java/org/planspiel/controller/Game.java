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
import org.planspiel.model.Period;
import org.planspiel.model.User;

public class Game {
//private ArrayList <org.planspiel.model.User> players = new ArrayList<org.planspiel.model.User>();
private HashMap<String, User> players = new HashMap<>();

private int currentPeriod = 0;
private int currentPlayer = 0;
private float budget, fixCost, costPerHectolitre;
private int rounds; //important for the end of the game
private String id;
private int maxPeriods;
private boolean initiated = false;
private Market market;

public Game(float budget, int rounds, String id, String playerName, String cookie){
        this.id = id;
	this.budget = budget;
	this.rounds = rounds;
	this.fixCost = (float) (budget * 0.234);
        this.costPerHectolitre = 500; //TODO manipulate value 
        this.maxPeriods = 8;
        market = new Market();
        //this.addPlayer(playerName, cookie, true);
}

public JsonArray showPlayers(){
    
    JsonArrayBuilder builder = Json.createArrayBuilder();
    
    for (User user: players.values())
    {
        builder.add(Json.createObjectBuilder()
                .add("name", user.getCompany().getName())
                .add("admin", user.getAdmin()));
    }
    
    return builder.build();
}

public String getId(){
    return id;
}

public int getCurrentPeriod(){
    return currentPeriod;
}

//adding players before the game starts
public void addPlayer(String name, String cookie, Boolean admin){
	players.put(cookie, new User(name, budget, fixCost, cookie, admin));
}

//start button
public void initialize(){
    if(!initiated){
        Collection<User> al = players.values();
        for(User u : al){
            u.getCompany().addPeriod(budget, fixCost, costPerHectolitre);
        }
        initiated = true;
    }
}

public ArrayList<User> getUsers(){
    
    ArrayList<User> users = new ArrayList<User>();
    
    for(String key: players.keySet()) {
        User u = players.get(key);
        System.out.print(u);
        users.add(u);
    }
    
    return users;
}

public Boolean checkClosed(){
    Boolean closed = true;
    for(Iterator it = players.keySet().iterator(); it.hasNext();){
        if(!players.get(it.next()).getCompany().getCurrentPeriod(currentPeriod).getClosed()){
            closed = false;
        }
    }
    return closed;
}

public Boolean submitValues(String cookie, float producedHectolitres, float pricePerHectolitre, 
                        float optionMarketing1, float optionMarketing2, float optionMarketing3, 
                        float optionDevelopment1, float optionDevelopment2, float optionDevelopment3){
                      
	Period p = players.get(cookie).getCompany().getCurrentPeriod(currentPeriod);
        
	p.setPricePerHectolitre(pricePerHectolitre);
	p.setProducedHectolitres(producedHectolitres);
	p.setOptionMarketing1(optionMarketing1);
	p.setOptionMarketing2(optionMarketing2);
	p.setOptionMarketing3(optionMarketing3);
        p.setOptionDevelopment1(optionDevelopment1);
        p.setOptionDevelopment2(optionDevelopment2);
        p.setOptionDevelopment3(optionDevelopment3);

	p.setBudgetLeft(p.getBudget()
                        - optionMarketing1 - optionMarketing2 - optionMarketing3
                        - optionDevelopment1 - optionDevelopment2 - optionDevelopment3
			- producedHectolitres * p.getCostPerHectolitre());
        
        p.setClosed(true);
	return checkClosed();
}

public void nextPeriod(){
	
	if(currentPeriod == maxPeriods){
            endGame();
        }
        else{
            Collection<User> al = players.values();
            for(User user : al){
                user.getCompany().addPeriod(user.getCompany().getCurrentPeriod(currentPeriod));
            }

            ArrayList<User> users = new ArrayList(players.values());

            currentPeriod++;
            market.makeSimulation(users, currentPeriod);
        //breakpoint biatch!
        int i = 0;
        i = 4*3;
        int x = i+2;
        }
}

private void endGame(){
    //do end game stuff
    //send to all players/sessions
    //send report data
    //crown a winner
}

}
