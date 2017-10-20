/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.model;

public class Period {

    private float budget;	//start	
    private float budgetLeft;	//calc
    private float revenue;	//calc
    private float profit;	//calc
    private float hectolitresLeft;	//calc

    private float marketing;	//start
    private float optionMarketing1;
    private float optionMarketing2;
    private float optionMarketing3;
    private float development;	//start
    private float optionDevelopment1;
    private float optionDevelopment2;
    private float optionDevelopment3;
    private float otherFixedCosts;	//calc

    //TODO how many litres the player wants to sell -->leftovers + produced -> maybe show in view that ... litres are left
    private float producedHectolitres;	//input
    private float soldHectolitres;	//input
    private float pricePerHectolitre;	//input start + value from calc
    private float costPerHectolitre;
    private float SoldHectolitresM1;
    private float SoldHectolitresM2;
    private float SoldHectolitresM3;

    private double marketShare;	//calc

    private float totalCosts;	//calc

    private Boolean closed = false;
    
    public Period(float budget, float fixCost, float costPerHectolitre) {
        this.budget = budget;
        this.otherFixedCosts = fixCost;
        this.costPerHectolitre = costPerHectolitre;
    }

    public Period(Period lastPeriod) {
        //TODO get all the attributes needed from the old period

        this.setOptionDevelopment1(lastPeriod.getOptionDevelopment1());
        this.setOptionDevelopment2(lastPeriod.getOptionDevelopment2());
        this.setOptionDevelopment3(lastPeriod.getOptionDevelopment3());

        this.setOtherFixedCosts(lastPeriod.getOtherFixedCosts());
        
        this.setCostPerHectolitre(lastPeriod.getCostPerHectolitre());
        
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public float getFixedCosts() {
        return this.getMarketing() + this.getDevelopment() + this.getOtherFixedCosts();
    }

    public float getProducedHectolitres() {
        return producedHectolitres;
    }

    public void setProducedHectolitres(float producedHectolitres) {
        this.producedHectolitres = producedHectolitres;
    }

    public float getPricePerHectolitre() {
        return pricePerHectolitre;
    }

    public void setPricePerHectolitre(float pricePerHectolitre) {
        this.pricePerHectolitre = pricePerHectolitre;
    }

    //enters the productionCost based on the produced hectolitres
    //TODO remove Ressources-class and this method?
//		public void setProductionPricePerHectolitre(float productionPricePerHectolitre) {
//			this.productionPricePerHectolitre = controller.Ressources.getProductionCost(producedHectolitres);
//		}

    //returnes kumulated cost of one period
//		public float getCosts(){
//			return marketing 
//					+ development 
//					+ otherFixedCosts 
//					+ (producedHectolitres * productionPricePerHectolitre);
//		}
    public float getDevelopment() {
        return optionDevelopment1+optionDevelopment2+optionDevelopment3;
    }
    public float getMarketing() {
        return optionMarketing1+optionMarketing2+optionMarketing3;
    }
    public float getOptionMarketing1() {
        return optionMarketing1;
    }

    public void setOptionMarketing1(float optionMarketing1) {
        this.optionMarketing1 = optionMarketing1;
    }

    public float getOptionMarketing2() {
        return optionMarketing2;
    }

    public void setOptionMarketing2(float optionMarketing2) {
        this.optionMarketing2 = optionMarketing2;
    }

    public float getOptionMarketing3() {
        return optionMarketing3;
    }

    public void setOptionMarketing3(float optionMarketing3) {
        this.optionMarketing3 = optionMarketing3;
    }
    public float getOptionDevelopment1() {
        return optionDevelopment1;
    }

    public void setOptionDevelopment1(float optionDevelopment1) {
        this.optionDevelopment1 = optionDevelopment1;
    }

    public float getOptionDevelopment2() {
        return optionDevelopment2;
    }

    public void setOptionDevelopment2(float optionDevelopment2) {
        this.optionDevelopment2 = optionDevelopment2;
    }

    public float getOptionDevelopment3() {
        return optionDevelopment3;
    }

    public void setOptionDevelopment3(float optionDevelopment3) {
        this.optionDevelopment3 = optionDevelopment3;
    }
    public void setDevelopment(float development) {
        this.development = development;
    }

    public float getBudgetLeft() {
        return budgetLeft;
    }

    public void setBudgetLeft(float budgetLeft) {
        this.budgetLeft = budgetLeft;
    }

    public float getHectolitresLeft() {
        return hectolitresLeft;
    }

    public void setHectolitresLeft(float hectolitresLeft) {
        this.hectolitresLeft = hectolitresLeft;
    }

    public float getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(float totalCosts) {
        this.totalCosts = totalCosts;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public double getMarketShare() {
        return marketShare;
    }

    public void setMarketShare(double marketShare) {
        this.marketShare = marketShare;
    }

    public float getSoldHectolitres() {
        return soldHectolitres;
    }

    public void setSoldHectolitres(float soldHectolitres) {
        this.soldHectolitres = soldHectolitres;
    }

    public float getOtherFixedCosts() {
        return otherFixedCosts;
    }

    public void setOtherFixedCosts(float otherFixedCosts) {
        this.otherFixedCosts = otherFixedCosts;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public float getSoldHectolitresM1() {
        return soldHectolitres/SoldHectolitresM1;
    }

    public void setSoldHectolitresM1(float SoldHectolitresM1) {
        this.SoldHectolitresM1 = SoldHectolitresM1;
    }

    public float getSoldHectolitresM2() {
        return soldHectolitres/SoldHectolitresM2;
    }

    public void setSoldHectolitresM2(float SoldHectolitresM2) {
        this.SoldHectolitresM2 = SoldHectolitresM2;
    }

    public float getSoldHectolitresM3() {
        return soldHectolitres/SoldHectolitresM3;
    }

    public void setSoldHectolitresM3(float SoldHectolitresM3) {
        this.SoldHectolitresM3 = SoldHectolitresM3;
    }

    public float getCostPerHectolitre() {
        return costPerHectolitre;
    }

    public void setCostPerHectolitre(float costPerHectolitre) {
        this.costPerHectolitre = costPerHectolitre;
    }
    
}
