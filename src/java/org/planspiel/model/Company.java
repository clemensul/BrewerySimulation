/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.model;

import java.util.ArrayList;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

public class Company {

    private String name;
    private ArrayList<Period> periods = new ArrayList<Period>();

    public Company(String name) {
        this.name = name;
    }

    //Returns the name of the company
    public String getName() {
        return name;
    }

    //First Period of the company with predefined Budget
    public void initializePeriods(float budget, float fixCost, float costPerHectolitre, double initialMarketShare) {
        Period p = new Period(budget, fixCost, costPerHectolitre, initialMarketShare);
        periods.add(p);
    }

    //Following Period on the stats of the previous Period
    public void addPeriod(Period previousPeriod) {
        periods.add(new Period(previousPeriod));
    }

    public Period getCurrentPeriod() {
        return periods.get(periods.size() - 1);
    }

    public Period getLastPeriod() {
        return periods.get(periods.size() - 2);
    }

    public double getMarketShare() {
        return getLastPeriod().getRealMarketShare();
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("marketShare", this.getCurrentPeriod().getRealMarketShare());

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Iterator<Period> it = periods.iterator();
        while (it.hasNext()) {
            Period period = it.next();
            arrayBuilder.add(period.toJson());
        }
        builder.add("periods", arrayBuilder);

        return builder.build();
    }
}
