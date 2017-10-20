class Game {
    constructor() {
        this.periodCount = ko.observable(0);

        this.budget = ko.observableArray();

        this.marketing1 = ko.observableArray();
        this.marketing2 = ko.observableArray();
        this.marketing3 = ko.observableArray();
        this.development1 = ko.observableArray();
        this.development2 = ko.observableArray();
        this.development3 = ko.observableArray();

        this.fixcost = ko.observableArray();
        this.variablecost = ko.observableArray();

    //     this.periods = ko.computed(function() {
    //         var result = [];
    //         for (var i = 0; i < this.budget.length; i++)
    //         {
    //             result.push (
    //                 new Period (
    //                     budget[i],
    //                     marketing1[i],
    //                     marketing2[i],
    //                     marketing3[i],
    //                     development1[i],
    //                     development2[i],
    //                     development3[i],
    //                     fixcost[i],
    //                     variablecost[i]
    //                 )
    //             )
    //         }
    //         return result;
    //     })

    //     this.currentExpenses = ko.computed(function() {
    //         return this.periods()[this.periods().length];
    //     })
    }

    addPeriod(
        budget,
        marketing1,
        marketing2,
        marketing3,
        development1,
        development2,
        development3,
        fixcost,
        variablecost
    ) {
        this.periodCount(this.periodCount() + 1);
        this.budget.push(budget);
        this.marketing1.push(marketing1);
        this.marketing2.push(marketing2);
        this.marketing3.push(marketing3);
        this.development1.push(development1);
        this.development2.push(development2);
        this.development3.push(development3);
        this.fixcost.push(fixcost);
        this.variablecost.push(variablecost);
    }
}

class Period {
    constructor(
        budget,
        marketing1,
        marketing2,
        marketing3,
        development1,
        development2,
        development3,
        fixcost,
        variablecost
    ) {
        this.budget = ko.observable();

        this.marketing1 = ko.observable();
        this.marketing2 = ko.observable();
        this.marketing3 = ko.observable();
        this.development1 = ko.observable();
        this.development2 = ko.observable();
        this.development3 = ko.observable();

        this.fixcost = ko.observableArray();
        this.variablecost = ko.observableArray();

    }
}