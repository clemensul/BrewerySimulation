/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.controller;

import java.util.ArrayList;
//quote: "Wenn ihr mir nen Button gebt, läuft der Scheiß"
//#1 noone understands the market

//TODO statistics (read from periods?)
//TODO how to win?
//TODO development, marketing, investments -> specialisation
//TODO get credits from bank?
//TODO information field where credits have to be redeemed, events occur, ...

public class Market {
	private int min; 
	private int max; 
	private double marketVolume; //how many hectolitres the market buys
	
	private final int pricePerHectolitreAvg = 50;  //TODO useful?
	
	//TODO add risk variable?
	private int risk; // between 0 and 1 --> maybe we need this later
	
	public Market(){
		
	}
        public Market(int min, int max){
            this.min = min;
            this.max = max;
        }
	
	public void makeSimulation(ArrayList<org.planspiel.model.User> players, int currentPeriod){
		marketVolume = getMarketVolume(currentPeriod);
		calcMarketShare(players, currentPeriod);
		for(org.planspiel.model.User u: players){
			org.planspiel.model.Period p = u.getCompany().getCurrentPeriod(currentPeriod);
			disDevelopment(p);
			calcCost(p);
			calcRevenue(p);
			calcProfit(p);
			calcBudget(p);
			calcLeftLitres(p);
		}
	}
	
	private void calcCost(org.planspiel.model.Period period){
		period.setTotalCosts(period.getProductionPricePerHectolitre() * period.getProducedHectolitres() + period.getFixedCosts());
	}
	
	private void calcBudget(org.planspiel.model.Period period){
		period.setBudget(period.getBudget() + period.getProfit());
	}
	
	private void calcRevenue(org.planspiel.model.Period period){
		period.setRevenue(period.getPricePerHectolitre() * period.getSoldHectolitres());
	}
	
	private void calcProfit(org.planspiel.model.Period period){
		period.setProfit(period.getRevenue() - period.getTotalCosts());
	}
	
	private void calcLeftLitres(org.planspiel.model.Period period){
		period.setHectolitresLeft(period.getProducedHectolitres() - period.getSoldHectolitres());
	}
	
	//percentage of market/revenue the company gets from the cake
	private void calcMarketShare(ArrayList<org.planspiel.model.User> players, int currentPeriod){
		float sum = 0;
		float sumMarketingOption1 = 0;
		float sumMarketingOption2 = 0;
		float sumMarketingOption3 = 0;
		for(org.planspiel.model.User u: players){
			sum += u.getCompany().getCurrentPeriod(currentPeriod).getProducedHectolitres();
			sumMarketingOption1 += u.getCompany().getCurrentPeriod(currentPeriod).getOptionMarketing1();
			sumMarketingOption2 += u.getCompany().getCurrentPeriod(currentPeriod).getOptionMarketing2();
			sumMarketingOption3 += u.getCompany().getCurrentPeriod(currentPeriod).getOptionMarketing3();
		}
		
		//setMarketShare with the players share of the market and the marketing
		for(org.planspiel.model.User u: players){
			u.getCompany().getCurrentPeriod(currentPeriod).setMarketShare(
					u.getCompany().getCurrentPeriod(currentPeriod).getProducedHectolitres() / (sum * (1- marketingShareMarket))
					);
			calcHectolitresSold(u, currentPeriod, sumMarketingOption1, sumMarketingOption2, sumMarketingOption3);
		}
	}
	
	//share of the market each marketing option can influence
	private double marketingOptionShare1 = 0.1;
	private double marketingOptionShare2 = 0.15;
	private double marketingOptionShare3 = 0.15;
	private double marketingShareMarket = marketingOptionShare1 + marketingOptionShare2 + marketingOptionShare3;
	
	private void calcHectolitresSold(org.planspiel.model.User user, int currentPeriod, float sumMarketingOption1, float sumMarketingOption2, float sumMarketingOption3){
			float soldHectolitres = 
									(float)(user.getCompany().getCurrentPeriod(currentPeriod).getMarketShare() * marketVolume * (1-marketingShareMarket)//market not influenced by marketing
									+ disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare1, 1) 
									+ disMarketing(user, currentPeriod, sumMarketingOption2, marketingOptionShare2, 2) 
									+ disMarketing(user, currentPeriod, sumMarketingOption3, marketingOptionShare3, 3));
                        user.getCompany().getCurrentPeriod(currentPeriod).setSoldHectolitresM1(disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare1, 1));
                        user.getCompany().getCurrentPeriod(currentPeriod).setSoldHectolitresM2(disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare2, 2));
                        user.getCompany().getCurrentPeriod(currentPeriod).setSoldHectolitresM3(disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare3, 3));
			user.getCompany().getCurrentPeriod(currentPeriod).setSoldHectolitres(soldHectolitres);
		
	}
	
	//calculates the litres User u gets with his investment in OptionX
	private float disMarketing(org.planspiel.model.User u, int currentPeriod, float sumMarketingOption, double marketingOptionShare, int i){
		
		float marketingShare = 0;
		if(i == 1) {
			marketingShare = u.getCompany().getCurrentPeriod(currentPeriod).getOptionMarketing1() / sumMarketingOption;
		}
                else if(i == 2) {
			marketingShare = u.getCompany().getCurrentPeriod(currentPeriod).getOptionMarketing2() / sumMarketingOption;
		}
		else { //i ==3
			marketingShare = u.getCompany().getCurrentPeriod(currentPeriod).getOptionMarketing3() / sumMarketingOption;
		}
		 
		return (float) (marketingShare * marketingOptionShare * marketVolume);
		
	}
	
	
	private float developmentEdge = 10000;
	
	private void disDevelopment(org.planspiel.model.Period p){
		
		float dev = p.getDevelopment() / developmentEdge;
		double sumDev = 0;
		
		for(int i = 0; i < dev; i++){
			sumDev += (double)Math.random() * (0.002 - 0.001) + 0.001;
		}
		
		p.setPricePerHectolitre((float)(p.getPricePerHectolitre() * sumDev));
		p.setOtherFixedCosts((float)(p.getOtherFixedCosts() * sumDev));		
	}
	
	private double getMarketVolume(int currentPeriod){
		if(currentPeriod == 6) {
			//WM or EM (every 2 years one of them), chance that Germany wins a lot of games and more beer is sold
			return marketFunction(currentPeriod +1) * (double)Math.random() * (1.1 - 1) + 1;	
		}
		else {
			//no WM, some random variable
			return marketFunction(currentPeriod +1) * (double)Math.random() * (1 - 0.9) + 0.9;		
		}
	}
	
	//periods 1-8
	private float marketFunction(int x) {
		return (float) (1000000 * (-0.000731785 * Math.pow(x, 9) + 0.0358807 * Math.pow(x, 8) - 0.753038 * Math.pow(x, 7) + 8.84993 * Math.pow(x, 6) - 63.965 * Math.pow(x, 5) + 293.709 * Math.pow(x, 4) - 852.146 * Math.pow(x, 3) + 1492.56 * Math.pow(x, 2) - 1407.76 * x + 548.12));
	}
	}
