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
        addPlayer(playerName, cookie);
}

public String showPlayers(){
    String playersString = "";
    Iterator<org.planspiel.model.User> it = players.values().iterator();
    int i = 0;
        while(it.hasNext()){
            User u = (User)it.next();
            playersString = playersString + " - " + u.getCompany().getName();
                    i++;
        }
    return playersString;
}

public String getId(){
    return id;
}
//adding players before the game starts
public void addPlayer(String name, String cookie){
	players.put(cookie, new User(name, budget, fixCost, cookie));
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
