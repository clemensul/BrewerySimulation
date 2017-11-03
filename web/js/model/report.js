class Report {
    constructor() {
        var self = this;

        self.periods = ko.observableArray();


        //     this.revenue = ko.computed(function () {
        //         var result = [];
        //         if (this.periods !== undefined)
        //             for (var i = 0; i < this.periods().length; i++) {
        //                 var array = this.periods()[i].revenue();
        //                 for (var z = 0; z < array.length; z++) {
        //                     if (i == 0)
        //                         result[z] = [];

        //                     result[z][i] = array[z].revenue();
        //                 }
        //             };
        //         return result;
        //     }, this);

        //     this.profit = ko.computed(function () {
        //         var result = [];
        //         if (this.periods !== undefined)
        //             for (var i = 0; i < this.periods().length; i++) {
        //                 var array = this.periods()[i].profit();
        //                 for (var z = 0; z < array.length; z++) {
        //                     if (i == 0)
        //                         result[z] = [];

        //                     result[z][i] = array[z].profit();
        //                 }
        //             };
        //         return result;
        //     }, this);

        //     this.sold_litres = ko.computed(function () {
        //         var result = [];
        //         if (this.periods !== undefined)
        //             for (var i = 0; i < this.periods().length; i++) {
        //                 var array = this.periods()[i].sold_litres();
        //                 for (var z = 0; z < array.length; z++) {
        //                     if (i == 0)
        //                         result[z] = [];

        //                     result[z][i] = array[z].sold_litres();
        //                 }
        //             };
        //         return result;
        //     }, this);
        //     this.cost = ko.computed(function () {
        //         var result = [];
        //         if (this.periods !== undefined)
        //             for (var i = 0; i < this.periods().length; i++) {
        //                 var array = this.periods()[i].cost();
        //                 for (var z = 0; z < array.length; z++) {
        //                     if (i == 0)
        //                         result[z] = [];

        //                     result[z][i] = array[z].cost();
        //                 }
        //             };
        //         return result;
        //     }, this);

    }

    addPeriod(inputPeriods) {
        console.log("Period added.")
        this.periods().push(
            ko.observable(
                new Period(
                    inputPeriods.budget,
                    inputPeriods.cost,
                    inputPeriods.cost_d1,
                    inputPeriods.cost_d2,
                    inputPeriods.cost_d3,
                    inputPeriods.cost_m1,
                    inputPeriods.cost_m2,
                    inputPeriods.cost_m3,
                    inputPeriods.fixed_cost,
                    inputPeriods.impact_m1,
                    inputPeriods.impact_m2,
                    inputPeriods.impact_m3,
                    inputPeriods.impact_d1,
                    inputPeriods.impact_d2,
                    inputPeriods.impact_d3,
                    inputPeriods.left_litre,
                    inputPeriods.price_litre,
                    inputPeriods.produced_litres,
                    inputPeriods.profit,
                    inputPeriods.revenue,
                    inputPeriods.sold_litres,
                    inputPeriods.variable_cost
                )
            )
        )

    }

    newPeriods(inputPeriods) {
        console.log("New Periods.")
        this.periods.removeAll();

        inputPeriods.forEach(function (elem) {
            this.periods().push(
                ko.observable(
                    new Period(
                        elem.budget,
                        elem.cost,
                        elem.cost_d1,
                        elem.cost_d2,
                        elem.cost_d3,
                        elem.cost_m1,
                        elem.cost_m2,
                        elem.cost_m3,
                        elem.fixed_cost,
                        elem.impact_m1,
                        elem.impact_m2,
                        elem.impact_m3,
                        elem.impact_d1,
                        elem.impact_d2,
                        elem.impact_d3,
                        elem.left_litre,
                        elem.price_litre,
                        elem.produced_litres,
                        elem.profit,
                        elem.revenue,
                        elem.sold_litres,
                        elem.variable_cost

                    )
                )
            );
        }, this);
    }
}

class Period {
    constructor(
        budget,
        cost,
        cost_d1,
        cost_d2,
        cost_d3,
        cost_m1,
        cost_m2,
        cost_m3,
        fixed_cost,
        impact_m1,
        impact_m2,
        impact_m3,
        impact_d1,
        impact_d2,
        impact_d3,
        left_litre,
        price_litre,
        produced_litres,
        profit,
        revenue,
        sold_litres,
        variable_cost
    ) {

        this.budget = ko.observable(budget);
        this.revenue = ko.observable(revenue);
        this.profit = ko.observable(profit);

        this.cost = ko.observable(cost);
        this.fixed_cost = ko.observable(fixed_cost);
        this.variable_cost = ko.observable(variable_cost);

        this.price_litre = ko.observable(price_litre);
        this.left_litre = ko.observable(left_litre);
        this.produced_litres = ko.observable(produced_litres);
        this.sold_litres = ko.observable(sold_litres);

        this.cost_d1 = ko.observable(cost_d1);
        this.cost_d2 = ko.observable(cost_d2);
        this.cost_d3 = ko.observable(cost_d3);
        this.impact_d1 = ko.observable(impact_d1);
        this.impact_d2 = ko.observable(impact_d2);
        this.impact_d3 = ko.observable(impact_d3);

        this.cost_m1 = ko.observable(cost_m1);
        this.cost_m2 = ko.observable(cost_m2);
        this.cost_m3 = ko.observable(cost_m3);
        this.impact_m1 = ko.observable(impact_m1);
        this.impact_m2 = ko.observable(impact_m2);
        this.impact_m3 = ko.observable(impact_m3);
    }
}