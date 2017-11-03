/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.model;

import javax.json.Json;
import javax.json.JsonObject;

public class Period {

    private double budget;	//start	
    private double revenue;

    private double optionMarketing1;
    private double optionMarketing2;
    private double optionMarketing3;

    private double optionDevelopment1;
    private double optionDevelopment2;
    private double optionDevelopment3;

    private double otherFixedCosts;
    private double costPerHectolitre;

    private double producedHectolitres;	//input
    private double soldHectolitres;	//input
    private double pricePerHectolitre;	//input start + value from calc

    //MarketShare based on investments
    private double marketShare;
    //MarketShare based on sold beer
    private double realMarketShare;
    //temp MarketShare für rekursiven Absatz
    private double tempMarketShare;

    private double marketShareMarketing1;
    private double marketShareMarketing2;
    private double marketShareMarketing3;

    private double marketShareDevelopment1;
    private double marketShareDevelopment2;
    private double marketShareDevelopment3;

    private Boolean closed = false;

    public Period(double budget, double fixCost, double costPerHectolitre, double initialMarketShare) {
        this.budget = budget;
        this.otherFixedCosts = fixCost;
        this.costPerHectolitre = costPerHectolitre;
        this.marketShare = initialMarketShare;

        //initial Values for Period 0 Simulation
        this.producedHectolitres = 1000;
        this.pricePerHectolitre = 1000;
        this.optionMarketing1 = 1;
        this.optionMarketing2 = 1;
        this.optionMarketing3 = 1;
        this.optionDevelopment1 = 1;
        this.optionDevelopment2 = 1;
        this.optionDevelopment3 = 1;
    }

    public Period(Period lastPeriod) {
        this.budget = lastPeriod.getBudgetLeft() + lastPeriod.getRevenue();
        this.otherFixedCosts = lastPeriod.otherFixedCosts;
        this.costPerHectolitre = lastPeriod.costPerHectolitre;

        //Vorläufig den alten Marktanteil eintragen. Wird später mit Marketingmaßnahem neu berechnet.
        this.marketShare = lastPeriod.marketShare;
    }
    
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("budget", this.getBudget())
                .add("produced_litres", this.getProducedHectolitres())
                .add("sold_litres", this.getSoldHectolitres())
                .add("price_litre", this.getPricePerHectolitre())
                .add("left_litre", this.getHectolitresLeft())
                .add("fixed_cost", this.getFixedCosts())
                .add("variable_cost", this.getCostPerHectolitre())
                .add("cost_m1", this.getOptionMarketing1())
                .add("impact_m1", this.getMarketShareMarketing1())
                .add("cost_m2", this.getOptionMarketing2())
                .add("impact_m2", this.getMarketShareMarketing2())
                .add("cost_m3", this.getOptionMarketing3())
                .add("impact_m3", this.getMarketShareMarketing3())
                .add("cost_d1", this.getOptionDevelopment1())
                .add("impact_d1", this.getMarketShareDevelopment1())
                .add("cost_d2", this.getOptionDevelopment2()    )
                .add("impact_d2", this.getMarketShareDevelopment2())
                .add("cost_d3", this.getOptionDevelopment3())
                .add("impact_d3", this.getMarketShareDevelopment3())
                
                .add("revenue", this.getRevenue())
                .add("profit", this.getProfit())
                .add("cost", this.getTotalCosts())
                .build();
    }

// <editor-fold defaultstate="collapsed" desc="Getter and Setter">    
    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getRevenue() {
        return pricePerHectolitre * soldHectolitres;
    }

    public double getFixedCosts() {
        return this.getMarketing() + this.getDevelopment() + this.getOtherFixedCosts();
    }

    public double getProducedHectolitres() {
        return producedHectolitres;
    }

    public void setProducedHectolitres(double producedHectolitres) {
        this.producedHectolitres = producedHectolitres;
    }

    public double getPricePerHectolitre() {
        return pricePerHectolitre;
    }

    public void setPricePerHectolitre(double pricePerHectolitre) {
        this.pricePerHectolitre = pricePerHectolitre;
    }

    public double getDevelopment() {
        return optionDevelopment1 + optionDevelopment2 + optionDevelopment3;
    }

    public double getMarketing() {
        return optionMarketing1 + optionMarketing2 + optionMarketing3;
    }

    public double getOptionMarketing1() {
        return optionMarketing1;
    }

    public void setOptionMarketing1(double optionMarketing1) {
        this.optionMarketing1 = optionMarketing1;
    }

    public double getOptionMarketing2() {
        return optionMarketing2;
    }

    public void setOptionMarketing2(double optionMarketing2) {
        this.optionMarketing2 = optionMarketing2;
    }

    public double getOptionMarketing3() {
        return optionMarketing3;
    }

    public void setOptionMarketing3(double optionMarketing3) {
        this.optionMarketing3 = optionMarketing3;
    }

    public double getOptionDevelopment1() {
        return optionDevelopment1;
    }

    public void setOptionDevelopment1(double optionDevelopment1) {
        this.optionDevelopment1 = optionDevelopment1;
    }

    public double getOptionDevelopment2() {
        return optionDevelopment2;
    }

    public void setOptionDevelopment2(double optionDevelopment2) {
        this.optionDevelopment2 = optionDevelopment2;
    }

    public double getOptionDevelopment3() {
        return optionDevelopment3;
    }

    public void setOptionDevelopment3(double optionDevelopment3) {
        this.optionDevelopment3 = optionDevelopment3;
    }

    public double getBudgetLeft() {
        return budget - getTotalCosts();
    }

    public double getHectolitresLeft() {
        return producedHectolitres - soldHectolitres;
    }

    public double getTotalCosts() {
        return costPerHectolitre * producedHectolitres + getFixedCosts();
    }

    public double getProfit() {
        return revenue - getTotalCosts();
    }

    public double getMarketShare() {
        return marketShare;
    }

    public void setMarketShare(double marketShare) {
        this.marketShare = marketShare;
    }

    public double getSoldHectolitres() {
        return soldHectolitres;
    }

    public void setSoldHectolitres(double soldHectolitres) {
        this.soldHectolitres = soldHectolitres;
    }

    public double getOtherFixedCosts() {
        return otherFixedCosts;
    }

    public void setOtherFixedCosts(double otherFixedCosts) {
        this.otherFixedCosts = otherFixedCosts;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public double getCostPerHectolitre() {
        return costPerHectolitre;
    }

    public void setCostPerHectolitre(double costPerHectolitre) {
        this.costPerHectolitre = costPerHectolitre;
    }

    public double getMarketShareMarketing1() {
        return marketShareMarketing1;
    }

    public void setMarketShareMarketing1(double marketShareMarketing1) {
        this.marketShareMarketing1 = marketShareMarketing1;
    }

    public double getMarketShareMarketing2() {
        return marketShareMarketing2;
    }

    public void setMarketShareMarketing2(double marketShareMarketing2) {
        this.marketShareMarketing2 = marketShareMarketing2;
    }

    public double getMarketShareMarketing3() {
        return marketShareMarketing3;
    }

    public void setMarketShareMarketing3(double marketShareMarketing3) {
        this.marketShareMarketing3 = marketShareMarketing3;
    }

    public double getMarketShareDevelopment1() {
        return marketShareDevelopment1;
    }

    public void setMarketShareDevelopment1(double marketShareDevelopment1) {
        this.marketShareDevelopment1 = marketShareDevelopment1;
    }

    public double getMarketShareDevelopment2() {
        return marketShareDevelopment2;
    }

    public void setMarketShareDevelopment2(double marketShareDevelopment2) {
        this.marketShareDevelopment2 = marketShareDevelopment2;
    }

    public double getMarketShareDevelopment3() {
        return marketShareDevelopment3;
    }

    public void setMarketShareDevelopment3(double marketShareDevelopment3) {
        this.marketShareDevelopment3 = marketShareDevelopment3;
    }

    public double getRealMarketShare() {
        return realMarketShare;
    }

    public void setRealMarketShare(double realMarketShare) {
        this.realMarketShare = realMarketShare;
    }
    
    public double getTempMarketShare() {
        return tempMarketShare;
    }

    public void setTempMarketShare(double tempMarketShare) {
        this.tempMarketShare = tempMarketShare;
    }

    // </editor-fold>
}
