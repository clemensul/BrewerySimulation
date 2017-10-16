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
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.planspiel.model.User;

public class Game {
//private ArrayList <org.planspiel.model.User> players = new ArrayList<org.planspiel.model.User>();
private HashMap<String, User> players = new HashMap<>();

private int currentPeriod = 0;
private int currentPlayer = 0;
private int playerCount = 0;
private float budget, fixCost;
private int rounds; //important for the end of the game
private String id;

public Game(float budget, int rounds, String id, String playerName, String cookie){
        this.id = id;
	this.budget = budget;
	this.rounds = rounds;
	this.fixCost = (float) (budget * 0.234);
        //this.addPlayer(playerName, cookie, true);
}

public JsonObject showPlayers(){
    
    JsonObject jo = null;
    //String playersString = "";
    Iterator<org.planspiel.model.User> it = players.values().iterator();
    int i = 0;
        while(it.hasNext()){
            User u = (User)it.next();
            
            jo = Json.createObjectBuilder()
              .add("players", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                  .add("name", u.getCompany().getName())
                  .add("admin", u.getAdmin())))
              .build();
            
//            playersString = playersString + " - " + u.getCompany().getName();
//                    i++;
        }
    //return playersString;
    return jo;
}

public String getId(){
    return id;
}
//adding players before the game starts
public void addPlayer(String name, String cookie, Boolean admin){
	players.put(cookie, new User(name, budget, fixCost, cookie, admin));
}

//start button
public Collection<User> startGame(){
//	for(org.planspiel.model.User user : players){
//		user.getCompany().addPeriod(budget, fixCost);
//	}
        Iterator it = players.entrySet().iterator();
        while(it.hasNext()){
            User u = (User)it.next();
            u.getCompany().addPeriod(budget, fixCost);
        }
        
        return players.values();
}

public ArrayList<User> getUsers(){
    
    ArrayList<User> users = new ArrayList<User>();
    
    for(String key: players.keySet()) {
        User u = players.get(key);
        System.out.print(u);
        users.add(u);
    }
//    }
//    Set<String> s = players.keySet();
//    System.out.print(s);
//    Iterator it = s.iterator();
//    System.out.print("players:" + showPlayers());
//        while(it.hasNext()){
//            //System.out.println(players.get(it.next()));
//            String key = (String)it.next();
//            System.out.println(key);
//            User u = players.get(key);
//            users.add(u);
//        }
        
        return users;
}
//user submits all spendings by pressing a button
public void submitValues(float producedHectolitres, float pricePerHectolitre, float optionMarketing1, float optionMarketing2, float optionMarketing3, float development){
	org.planspiel.model.Period period = players.get(currentPlayer).getCompany().getCurrentPeriod(currentPeriod);
	
	period.setPricePerHectolitre(pricePerHectolitre);
	period.setProducedHectolitres(producedHectolitres);
	period.setOptionMarketing1(optionMarketing1);
	period.setOptionMarketing2(optionMarketing2);
	period.setOptionMarketing3(optionMarketing3);
	period.setDevelopment(development);
	
	//TODO produced litres and production price needs to be calculated 
	//TODO seperate BudgetCalc-Method
	period.setBudget(period.getBudget() - period.getOptionMarketing1() - period.getOptionMarketing2()- period.getOptionMarketing3() - period.getDevelopment() 
			- period.getProducedHectolitres() * period.getProductionPricePerHectolitre());
	
	//nextPlayer();

}

//public void nextPlayer(){
//	currentPlayer++;
//	
//	//do player things
//	//submitValues(...);
//	if(currentPlayer > playerCount)
//		nextPeriod();
//	else{
//	nextPlayer();}
//}

//TODO currently the market function only supports Periods between 1.5 - 10
public void nextPeriod(){
	currentPlayer = 0;
	currentPeriod++;
	
//	for(org.planspiel.model.User user : players){
//		//add a new period to every company and pass on the old method, so the secondary constructor of period can get the old values
//		user.getCompany().addPeriod(user.getCompany().getCurrentPeriod(currentPeriod - 1));
//	}
        
        Iterator it = players.entrySet().iterator();
        while(it.hasNext()){
            User user = (User)it.next();
            user.getCompany().addPeriod(user.getCompany().getCurrentPeriod(currentPeriod - 1));
        }
}

}
