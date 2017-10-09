/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.model;

public class User{
	private Company company;
	
	public User(String cname, float budget, float fixCost){
		this.company = new Company(cname, budget, fixCost);
	}

	public Company getCompany() {
		return company;
	}


}
