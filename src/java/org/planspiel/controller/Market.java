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
    final double changingMarketShare = 0.3;

    //share of the market each marketing option can influence
    private double marketingOptionEffect1 = 0.15;
    private double marketingOptionEffect2 = 0.15;
    private double marketingOptionEffect3 = 0.15;

    //threshhold to pass for a free lottery spin
    private double developmentEdge1 = 10000;
    private double developmentEdge2 = 12000;
    private double developmentEdge3 = 14000;

    public void makeSimulation(ArrayList<User> players, int currentPeriod) {

        marketVolume = getMarketVolume(currentPeriod);
        this.players = players;

        calcMarketShare();

        sellBeer(players, marketVolume);
    }

    //percentage of market/revenue the company gets from the cake
    protected void calcMarketShare() {

        double totalProducedBeer = 0;  //all produced hectolitres of everyone

        double sumMarketingOption1 = 0;  //marketing spent by everyone
        double sumMarketingOption2 = 0;
        double sumMarketingOption3 = 0;

        double totalChangingMarketShare = 0;

        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
            Period p = it.next().getCompany().getCurrentPeriod();

            //Combine the amount of produced Beer  
            totalProducedBeer += p.getProducedHectolitres();

            //Combine the amount of money spend on Marketing
            sumMarketingOption1 += p.getOptionMarketing1();
            sumMarketingOption2 += p.getOptionMarketing2();
            sumMarketingOption3 += p.getOptionMarketing3();

            //Anteil der Käufer des Marktanteils die Wechseln
            totalChangingMarketShare += p.getMarketShare() * changingMarketShare;
            //Neuer Marktanteil bevor die wechselnden hinzu kommen
            p.setMarketShare(p.getMarketShare() * (1 - changingMarketShare));
        }

        //Gesamte Marketingausgaben der Periode mit MarketingOptionsEffect
        double sumMarketing1 = sumMarketingOption1 * marketingOptionEffect1;
        double sumMarketing2 = sumMarketingOption2 * marketingOptionEffect2;
        double sumMarketing3 = sumMarketingOption3 * marketingOptionEffect3;

        it = players.iterator();
        while (it.hasNext()) {
            Period p = it.next().getCompany().getCurrentPeriod();

            //Marketingausgaben der Company mit MarketingOptionEffect ausrechnen
            double m1 = p.getOptionMarketing1() * marketingOptionEffect1;
            double m2 = p.getOptionMarketing2() * marketingOptionEffect2;
            double m3 = p.getOptionMarketing3() * marketingOptionEffect3;

            p.setMarketShareMarketing1(sumMarketing1 != 0 ? m1 / sumMarketing1 : 0);
            p.setMarketShareMarketing2(sumMarketing1 != 0 ? m2 / sumMarketing2 : 0);
            p.setMarketShareMarketing3(sumMarketing1 != 0 ? m3 / sumMarketing3 : 0);

            double MarketingShare = p.getMarketShareMarketing1() + p.getMarketShareMarketing2() + p.getMarketShareMarketing3();

            double MarketShareToAdd = totalChangingMarketShare * MarketingShare;

            p.setMarketShare(p.getMarketShare() + MarketShareToAdd);
        }
    }

    private void calcHectolitresSold(User user, int currentPeriod, float sumMarketingOption1, float sumMarketingOption2, float sumMarketingOption3, float marketVolume) {

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

    protected void sellBeer(ArrayList<User> players, double marketVolume) {
        double leftOver = marketVolume;
        ArrayList<User> UserNotSoldOut = new ArrayList<User>();

        //Berechnet den Marktanteil auf Grund der Summe alle Marktanteile     
        //Wichtig für Rekursion, wenn nicht mehr alle Spiele dabei sind
        double marketShare = 0;
        Iterator<User> it = players.iterator();
        while (it.hasNext()) {
            User user = it.next();
            Period period = user.getCompany().getCurrentPeriod();

            marketShare += period.getMarketShare();
        }
        if (marketShare != 1) {
            it = players.iterator();
            while (it.hasNext()) {
                User user = it.next();
                Period period = user.getCompany().getCurrentPeriod();

                period.setMarketShare(period.getMarketShare() / marketShare);
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

            if (period.getProducedHectolitres() <= period.getMarketShare() * marketVolume) {
                absatz[index] = (period.getProducedHectolitres()) - period.getSoldHectolitres();
                period.setSoldHectolitres(period.getSoldHectolitres() + absatz[index]);
            } else {
                absatz[index] = period.getMarketShare() * marketVolume;
                period.setSoldHectolitres(period.getSoldHectolitres() + absatz[index]);
                UserNotSoldOut.add(user);
            }
            leftOver -= absatz[index];

            index++;
        }

        //Rekursione wenn Spiele nicht alles verkauft haben 
        //und noch Bier verkaufbar ist.
        if (UserNotSoldOut.size() != 0 && leftOver > 0) {
            sellBeer(UserNotSoldOut, leftOver);
        }
    }
}
