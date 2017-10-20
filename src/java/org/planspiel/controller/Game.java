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
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import org.planspiel.model.Period;
import org.planspiel.model.User;

public class Game {
//private ArrayList <org.planspiel.model.User> players = new ArrayList<org.planspiel.model.User>();
private HashMap<String, User> players = new HashMap<>();

private int currentPeriod = 0;
private int currentPlayer = 0;
private float budget, fixCost;
private int rounds; //important for the end of the game
private String id;

private Market market;

public Game(float budget, int rounds, String id, String playerName, String cookie){
        this.id = id;
	this.budget = budget;
	this.rounds = rounds;
	this.fixCost = (float) (budget * 0.234);
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
        Collection<User> al = players.values();
        for(User u : al){
            u.getCompany().addPeriod(budget, fixCost);
        }
//    Iterator it = players.entrySet().iterator();
//        while(it.hasNext()){
//            User u = (User)it.next();
//            u.getCompany().addPeriod(budget, fixCost);
//        }
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
                      
	Period period = players.get(cookie).getCompany().getCurrentPeriod(currentPeriod);
        
	period.setPricePerHectolitre(pricePerHectolitre);
	period.setProducedHectolitres(producedHectolitres);
	period.setOptionMarketing1(optionMarketing1);
	period.setOptionMarketing2(optionMarketing2);
	period.setOptionMarketing3(optionMarketing3);
        period.setOptionDevelopment1(optionDevelopment1);
        period.setOptionDevelopment2(optionDevelopment2);
        period.setOptionDevelopment3(optionDevelopment3);
	
	
	period.setBudgetLeft(period.getBudget()
                        - optionMarketing1 - optionMarketing2 - optionMarketing3
                        - optionDevelopment1 - optionDevelopment2 - optionDevelopment3
			- producedHectolitres * period.getCostPerHectolitre());
        
        period.setClosed(true);
	return checkClosed();
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
	
	
//	for(org.planspiel.model.User user : players){
//		//add a new period to every company and pass on the old method, so the secondary constructor of period can get the old values
//		user.getCompany().addPeriod(user.getCompany().getCurrentPeriod(currentPeriod - 1));
//	}
//        Iterator it = players.entrySet().iterator();
//        while(it.hasNext()){
//            User user = (User)it.next();
//            user.getCompany().addPeriod(user.getCompany().getCurrentPeriod(currentPeriod - 1));
//        }
        
        Collection<User> al = players.values();
        for(User user : al){
            user.getCompany().addPeriod(user.getCompany().getCurrentPeriod(currentPeriod));
        }

        
        ArrayList<User> users = new ArrayList(players.values());
        market.makeSimulation(users, currentPeriod);
        
        currentPeriod++;
}

}
