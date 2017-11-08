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

    private int currentPeriod;
    private double marketVolume; //how many hectolitres the market buys
    private ArrayList<User> players;

    //
    // Stellschrauben
    //
    //Anteil des Marktanteils der sich auf Grund von Werbung umentscheidet
    final double changingMarketShareMarketing = 0.3;
    //Anteil des Marktanteils der sich auf Grund von Forschung umentscheidet
    final double changingMarketShareDevelopment = 0.3;

    //share of the market each marketing option can influence
    private double marketingOptionEffect1 = 1;
    private double marketingOptionEffect2 = 1;
    private double marketingOptionEffect3 = 1;

    //share of the market each marketing option can influence
    private double developmentOptionEffect1 = 1;
    private double developmentOptionEffect2 = 1;
    private double developmentOptionEffect3 = 1;

    //threshhold to pass for a free lottery spin
    private double developmentEdge1 = 10000;
    private double developmentEdge2 = 12000;
    private double developmentEdge3 = 14000;

    public void makeSimulation(ArrayList<User> players, int currentPeriod) {

        marketVolume = getMarketVolume(currentPeriod);
        this.players = players;

        calcMarketShare();

        sellBeer(players);
        
        setRealMarketShare();
    }

    //percentage of market/revenue the company gets from the cake
    protected void calcMarketShare() {

        double totalProducedBeer = 0;  //all produced hectolitres of everyone

        //marketing spent by everyone
        double sumMarketingOption1 = 0;
        double sumMarketingOption2 = 0;
        double sumMarketingOption3 = 0;
        //development spent by everyone
        double sumDevelopmentOption1 = 0;
        double sumDevelopmentOption2 = 0;
        double sumDevelopmentOption3 = 0;

        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
            Period p = it.next().getCompany().getCurrentPeriod();

            //Combine the amount of produced Beer  
            totalProducedBeer += p.getProducedHectolitres();

            //Combine the amount of money spend on Marketing
            sumMarketingOption1 += p.getOptionMarketing1();
            sumMarketingOption2 += p.getOptionMarketing2();
            sumMarketingOption3 += p.getOptionMarketing3();
            //Combine the amount of money spend on Development
            sumDevelopmentOption1 += p.getOptionDevelopment1();
            sumDevelopmentOption2 += p.getOptionDevelopment2();
            sumDevelopmentOption3 += p.getOptionDevelopment3();

            //Neuer Marktanteil bevor die wechselnden hinzu kommen
            p.setMarketShare(p.getMarketShare() * (1 - changingMarketShareMarketing + changingMarketShareDevelopment));
        }

        //Gesamte Marketingausgaben der Periode mit MarketingOptionsEffect
        double sumMarketing1 = sumMarketingOption1 * marketingOptionEffect1;
        double sumMarketing2 = sumMarketingOption2 * marketingOptionEffect2;
        double sumMarketing3 = sumMarketingOption3 * marketingOptionEffect3;
        //Gesamte Forschungsausgaben der Periode mit DevelopmentOptionsEffect
        double sumDevelopment1 = sumDevelopmentOption1 * developmentOptionEffect1;
        double sumDevelopment2 = sumDevelopmentOption2 * developmentOptionEffect2;
        double sumDevelopment3 = sumDevelopmentOption3 * developmentOptionEffect3;

        it = players.iterator();
        while (it.hasNext()) {
            Period p = it.next().getCompany().getCurrentPeriod();

            //Marketingausgaben der Company mit MarketingOptionEffect ausrechnen
            double m1 = p.getOptionMarketing1() * marketingOptionEffect1;
            double m2 = p.getOptionMarketing2() * marketingOptionEffect2;
            double m3 = p.getOptionMarketing3() * marketingOptionEffect3;
            //Developmentausgaben der Company mit MarketingOptionEffect ausrechnen
            double d1 = p.getOptionDevelopment1() * developmentOptionEffect1;
            double d2 = p.getOptionDevelopment2() * developmentOptionEffect2;
            double d3 = p.getOptionDevelopment3() * developmentOptionEffect3;

            p.setMarketShareMarketing1(sumMarketing1 != 0 ? m1 / sumMarketing1 : 0);
            p.setMarketShareMarketing2(sumMarketing1 != 0 ? m2 / sumMarketing2 : 0);
            p.setMarketShareMarketing3(sumMarketing1 != 0 ? m3 / sumMarketing3 : 0);

            p.setMarketShareDevelopment1(sumDevelopment1 != 0 ? d1 / sumDevelopment1 : 0);
            p.setMarketShareDevelopment2(sumDevelopment2 != 0 ? d2 / sumDevelopment2 : 0);
            p.setMarketShareDevelopment3(sumDevelopment3 != 0 ? d3 / sumDevelopment3 : 0);

            double MarketingShare = p.getMarketShareMarketing1() + p.getMarketShareMarketing2() + p.getMarketShareMarketing3();
            double DevelopmentShare = p.getMarketShareDevelopment1() + p.getMarketShareDevelopment2() + p.getMarketShareDevelopment3();

            double MarketShareToAdd = 0;
            MarketShareToAdd += changingMarketShareMarketing * MarketingShare;
            MarketShareToAdd += changingMarketShareDevelopment * DevelopmentShare;

            p.setMarketShare(p.getMarketShare() + MarketShareToAdd);
        }
    }

    protected double getMarketVolume(int currentPeriod) {
        
        int currentPeriodCalc = (currentPeriod % 8) + 1;
        //only values between 1-8
        if ((currentPeriodCalc % 2) == 0) {
            //WM or EM (every 2 years one of them), chance that Germany wins a lot of games and more beer is sold
            return marketFunction(currentPeriod + 1) * (double) Math.random() * (1.2 - 1.1) + 1.1;
        } else {
            //no WM, some random variable
            return marketFunction(currentPeriod + 1) * (double) Math.random() * (1 - 0.9) + 0.9;
        }         
    }

    //periods 1-8
    private double marketFunction(int x) {
        return 1000000 * (-0.000731785 * Math.pow(x, 9) + 0.0358807 * Math.pow(x, 8) - 0.753038 * Math.pow(x, 7) + 8.84993 * Math.pow(x, 6) - 63.965 * Math.pow(x, 5) + 293.709 * Math.pow(x, 4) - 852.146 * Math.pow(x, 3) + 1492.56 * Math.pow(x, 2) - 1407.76 * x + 548.12);
    }

    protected void sellBeer (ArrayList<User> players) {
        
        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
            Period period = it.next().getCompany().getCurrentPeriod();
            period.setTempMarketShare(period.getMarketShare());
        }
        
        sellBeerRek(players, marketVolume);
    }
    
    protected void sellBeerRek(ArrayList<User> players, double marketVolume) {
        double leftOver = marketVolume;
        ArrayList<User> UserNotSoldOut = new ArrayList<User>();

        //Berechnet den Marktanteil auf Grund der Summe alle Marktanteile     
        //Wichtig für Rekursion, wenn nicht mehr alle Spiele dabei sind
        double marketShare = 0;
        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
            User user = it.next();
            Period period = user.getCompany().getCurrentPeriod();

            marketShare += period.getTempMarketShare();
      }
      
      if (marketShare != 1) {
            it = players.iterator();
            while (it.hasNext()) {
                User user = it.next();
                Period period = user.getCompany().getCurrentPeriod();

                period.setTempMarketShare(period.getTempMarketShare() / marketShare);
            }
        }

        //Berechnet den Absatz jedes Spielers
        //Zusätzlicher Absatz wird vorher separat in Array gespeichert
        double[] absatz = new double[players.size()];
        int index = 0;

        it = players.iterator();
        while (it.hasNext()) {
            User user = it.next();
            Period period = user.getCompany().getCurrentPeriod();

            if (period.getProducedHectolitres() <= period.getTempMarketShare() * marketVolume) {
                absatz[index] = (period.getProducedHectolitres()) - period.getSoldHectolitres();
                period.setSoldHectolitres(period.getSoldHectolitres() + absatz[index]);
            } else {
                absatz[index] = period.getTempMarketShare() * marketVolume;
                period.setSoldHectolitres(period.getSoldHectolitres() + absatz[index]);
                UserNotSoldOut.add(user);
            }
            leftOver -= absatz[index];

            index++;
        }

        //Rekursione wenn Spiele nicht alles verkauft haben 
        //und noch Bier verkaufbar ist.
        if (UserNotSoldOut.size() != 0 && leftOver > 0) {
            sellBeerRek(UserNotSoldOut, leftOver);
        }
    }

    private void setRealMarketShare() {
        double totalSoldBeer = 0;
        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
            totalSoldBeer += it.next().getCompany().getCurrentPeriod().getSoldHectolitres();
        }

        System.out.println("Total sold beer: " + totalSoldBeer);
        
        it = players.iterator();
        while (it.hasNext()) {
            Period period = it.next().getCompany().getCurrentPeriod();
            period.setRealMarketShare(
                   totalSoldBeer != 0 
                           ? period.getSoldHectolitres() / totalSoldBeer 
                           : 0
            );
        }
    }
}
