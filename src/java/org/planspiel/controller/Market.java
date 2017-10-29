/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.controller;

import java.util.ArrayList;
import org.planspiel.model.Period;
import org.planspiel.model.User;
import java.util.Iterator;
//quote: "Wenn ihr mir nen Button gebt, läuft der Scheiß"
//#1 noone understands the market

//TODO statistics (read from periods?)
//TODO how to win?
//TODO development, marketing, investments -> specialisation
//TODO get credits from bank?
//TODO information field where credits have to be redeemed, events occur, ...
public class Market {

    private float marketVolume; //how many hectolitres the market buys
    private float notUsedMarketVolume;
    private float sum; //sold by everyone sum
    //TODO add risk variable?
    private int risk; // between 0 and 1 --> maybe we need this later
    private float notUsedMarketPercent;
    private ArrayList<User> players;
    public Market() {

    }

    public void makeSimulation(ArrayList<User> players, int currentPeriod) {

        marketVolume = (float) getMarketVolume(currentPeriod);
        notUsedMarketVolume = marketVolume;
        this.players = players;
        calcMarketShare(players, currentPeriod);
        
        for (User u : players) {
            Period p = u.getCompany().getCurrentPeriod(currentPeriod);
            disDevelopment(p);
            calcCost(p);
            calcRevenue(p);
            calcProfit(p);
            calcBudget(p);
            calcLeftLitres(p);
        }
        
        if(notUsedMarketVolume > 0){
            if(compensateMarketVolume(players, currentPeriod, notUsedMarketVolume, notUsedMarketPercent, sum)){
                //calc new market shares here
                //sum and sold for everyone (sold/sum) = new marketshare
            }
        }
    }

    //if some marketshare is left, it is given to companys who were not selling all of their storage
    //players are all these companys
    //marketVolume is the marketshare left
  
    public boolean compensateMarketVolume(ArrayList<org.planspiel.model.User> players, int currentPeriod, float marketVolume, float marketPercent, float oldSum) {
        this.marketVolume = marketVolume;
        double marketShareNew = 0;
        calcLeftMarketShare(players, currentPeriod);
        
        for (org.planspiel.model.User u : players) {
            marketShareNew += u.getCompany().getCurrentPeriod(currentPeriod).getMarketShare();
        }
        
        for (User u : players) {

            Period p = u.getCompany().getCurrentPeriod(currentPeriod);
            
            disDevelopment(p);
            calcCost(p);
            calcRevenue(p);
            calcProfit(p);
            calcBudget(p);
            calcLeftLitres(p);
        }
        //threshold of 100 is needed, because it'll probably never reach 0
        if(notUsedMarketVolume > 100){
            return compensateMarketVolume(players, currentPeriod, notUsedMarketVolume, notUsedMarketPercent, oldSum);
        }
        else{
            //if the marketshare is shared to the leftover players, a true can be returned 
            //and the market share calculation can be triggered
            return true;
        }
    }
    
    private void calcLeftMarketShare(ArrayList<User> players, int currentPeriod){
      sum = 0;
        for (User u : players) {
            sum += u.getCompany().getCurrentPeriod(currentPeriod).getProducedHectolitres();
        }
    
        //setMarketShare with the players share of the market and the marketing
        for (User u : players) {
            //TODO renew Method
            Period p = u.getCompany().getCurrentPeriod(currentPeriod);
            p.setMarketShare(
                    (sum * (1 - marketingShareMarket) / p.getProducedHectolitres())
            );
            calcHectolitresSold(u, currentPeriod, 0, 0, 0, marketVolume);
        }
    }
    private void calcCost(Period period) {
        period.setTotalCosts(period.getCostPerHectolitre() * period.getProducedHectolitres() + period.getFixedCosts());
    }

    private void calcBudget(Period period) {
        period.setBudget(period.getBudgetLeft() + period.getProfit());
    }

    private void calcRevenue(Period period) {
        period.setRevenue(period.getPricePerHectolitre() * period.getSoldHectolitres());
    }

    private void calcProfit(Period period) {
        period.setProfit(period.getRevenue() - period.getTotalCosts());
    }

    private void calcLeftLitres(Period period) {
        period.setHectolitresLeft(period.getProducedHectolitres() - period.getSoldHectolitres());
    }

    //percentage of market/revenue the company gets from the cake
    private void calcMarketShare(ArrayList<User> players, int currentPeriod) {
        sum = 0;  //all produced hectolitres of everyone
        float sumMarketingOption1 = 0;  //marketing spent by everyone
        float sumMarketingOption2 = 0;
        float sumMarketingOption3 = 0;
        
        Iterator<User> it = players.iterator();
        
        while (it.hasNext()) {
            Period p = it.next().getCompany().getCurrentPeriod(currentPeriod);
            
            sum += p.getProducedHectolitres();
            sumMarketingOption1 += p.getOptionMarketing1();
            sumMarketingOption2 += p.getOptionMarketing2();
            sumMarketingOption3 += p.getOptionMarketing3();
        }

        it = players.iterator();
        //setMarketShare with the players share of the market and the marketing
        while (it.hasNext()) {
            //TODO renew Method
            User u = it.next();
            
            Period p = u.getCompany().getCurrentPeriod(currentPeriod);
            p.setMarketShare(
                    (sum * (1 - marketingShareMarket) / p.getProducedHectolitres())
            );
            calcHectolitresSold(u, currentPeriod, sumMarketingOption1, sumMarketingOption2, sumMarketingOption3, marketVolume);
        }
        notUsedMarketPercent = notUsedMarketVolume / sum;
    }

    //share of the market each marketing option can influence
    private double marketingOptionShare1 = 0.1;
    private double marketingOptionShare2 = 0.15;
    private double marketingOptionShare3 = 0.15;
    private double marketingShareMarket = marketingOptionShare1 + marketingOptionShare2 + marketingOptionShare3;

    private void calcHectolitresSold(User user, int currentPeriod, float sumMarketingOption1, float sumMarketingOption2, float sumMarketingOption3, float marketVolume) {
        Period p = user.getCompany().getCurrentPeriod(currentPeriod);
        
        float grantedHecolitresMarket = (float) (p.getMarketShare() * marketVolume * (1 - marketingShareMarket));//market not influenced by marketing
        float grantedHectolitresMarketing =    
                disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare1, 1)
                + disMarketing(user, currentPeriod, sumMarketingOption2, marketingOptionShare2, 2)
                + disMarketing(user, currentPeriod, sumMarketingOption3, marketingOptionShare3, 3); 
        
        float grantedHectolitresTotal = grantedHecolitresMarket + grantedHectolitresMarketing;
        
        p.setSoldHectolitresM1(disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare1, 1));
        p.setSoldHectolitresM2(disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare2, 2));
        p.setSoldHectolitresM3(disMarketing(user, currentPeriod, sumMarketingOption1, marketingOptionShare3, 3));
        
        float producedHectolitres = user.getCompany().getCurrentPeriod(currentPeriod).getProducedHectolitres();
        if (producedHectolitres <= grantedHectolitresTotal ) {
            //user sells all his produced litres
            p.setSoldHectolitres(p.getProducedHectolitres());
            //the rest is free for the others
            notUsedMarketVolume -= p.getProducedHectolitres();
            //the user loses market share
           p.setMarketShare(p.getMarketShare()-(grantedHectolitresTotal-producedHectolitres)/sum);
            //the player is removed from the compensateMarket-User-List
            players.remove(user);
        }
        else{
            //added add up
            p.setSoldHectolitres(grantedHectolitresTotal + p.getSoldHectolitres());
        }

    }

    //calculates the litres User u gets with his investment in OptionX
    private float disMarketing(User user, int currentPeriod, float sumMarketingOption, double marketingOptionShare, int i) {
        Period p = user.getCompany().getCurrentPeriod(currentPeriod);
        float marketingShare = 0;
        if (i == 1) {
            marketingShare = p.getOptionMarketing1() / sumMarketingOption;
        } else if (i == 2) {
            marketingShare = p.getOptionMarketing2() / sumMarketingOption;
        } else { //i ==3
            marketingShare = p.getOptionMarketing3() / sumMarketingOption;
        }

        return (float) (marketingShare * marketingOptionShare * marketVolume);

    }
    //threshhold to pass for a free lottery spin
    private float developmentEdge1 = 10000;
    private float developmentEdge2 = 12000;
    private float developmentEdge3 = 14000;

    private void disDevelopment(Period p) {

        int dev3 = (int) (p.getOptionDevelopment3() / developmentEdge3);
        int dev2 = (int) (p.getOptionDevelopment2() / developmentEdge2 + (dev3 / 2));
        int dev1 = (int) (p.getOptionDevelopment1() / developmentEdge1 + (dev3 / 2));
        double sumDev2 = 0;
        double sumDev1 = 0;

        for (int i = 0; i < dev2; i++) {
            sumDev2 += (double) Math.random() * (0.002 - 0.001) + 0.001;
        }
        for (int i = 0; i < dev1; i++) {
            sumDev1 += (double) Math.random() * (0.002 - 0.001) + 0.001;
        }

        p.setPricePerHectolitre((float) (p.getPricePerHectolitre() * sumDev2));
        p.setOtherFixedCosts((float) (p.getOtherFixedCosts() * sumDev1));
    }

    private double getMarketVolume(int currentPeriod) {
        int currentPeriodCalc = (currentPeriod%8)+1;
        //only values between 1-8
        if ((currentPeriodCalc%2) == 0) {
            //WM or EM (every 2 years one of them), chance that Germany wins a lot of games and more beer is sold
            return marketFunction(currentPeriod + 1) * (double) Math.random() * (1.2 - 1.1) + 1.1;
        } else {
            //no WM, some random variable
            return marketFunction(currentPeriod + 1) * (double) Math.random() * (1 - 0.9) + 0.9;
        }
    }

    //periods 1-8
    private float marketFunction(int x) {
        return (float) (1000000 * (-0.000731785 * Math.pow(x, 9) + 0.0358807 * Math.pow(x, 8) - 0.753038 * Math.pow(x, 7) + 8.84993 * Math.pow(x, 6) - 63.965 * Math.pow(x, 5) + 293.709 * Math.pow(x, 4) - 852.146 * Math.pow(x, 3) + 1492.56 * Math.pow(x, 2) - 1407.76 * x + 548.12));
    }
}
