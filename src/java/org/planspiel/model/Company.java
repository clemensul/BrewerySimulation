/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.model;

import java.util.ArrayList;

public class Company {
	
	private String name;
	private ArrayList <Period> periods = new ArrayList<Period>();
		
	public Company(String name, float budget, float fixCost){
		this.name = name;
		
		//first Period with fixed Budget
		addPeriod(budget, fixCost);
	}
	
	//Returns the name of the company
	public String getName() {
		return name;
	}

	//First Period of the company with predefined Budget
	public void addPeriod(float budget, float fixCost){
		periods.add(new Period(budget, fixCost));
	}
	//Following Period on the stats of the previous Period
	public void addPeriod(Period previousPeriod){
		periods.add(previousPeriod);
	}
	
	
	public Period getCurrentPeriod(int currentPeriod){
		return periods.get(currentPeriod);
	}
}
