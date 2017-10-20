class Game {
    constructor (budget, expenses) {
        this.currentPeriod = ko.observable(0);
        this.periods = ko.observableArray();
        this.periods.push (new Period("Periode " + (this.currentPeriod()), budget, 
        [
            {"name":"Marketing1","expense":100},
            {"name":"Marketing2","expense":200},
            {"name":"Marketing3","expense":300},
            {"name":"Development1","expense":100},
            {"name":"Development2","expense":200},
            {"name":"Development3","expense":300}
        ]));
        

        this.currentExpenses = ko.computed(function () { 
            return this.periods()[this.currentPeriod()].expenses();
        }, this);

        this.currentBudget = ko.computed(function () {
            return this.periods()[this.currentPeriod()].budget;
        }, this);
    }

    changeExpensesArray (newExpenses) {
        this.periods()[this.currentPeriod()].expenses.removeAll();
        newExpenses.forEach(function(element) {
            this.periods()[this.currentPeriod()].expenses.push(element)
        }, this);
    }

    addPeriod (budget, expenses) {
        this.periods().push (new Period("Periode " + (this.currentPeriod()), 100, expenses));
        this.currentPeriod(this.currentPeriod()+1);
    }

    getExpenseData () {
        var result = [];

        for (var i = 0; i < this.periods().length; i++) {
            var array = this.periods()[i].expenses();
            for (var z = 0; z < array.length; z++)
            {
                if (i == 0)
                    result[z] = [];

                result[z][i] = array[z].expense;
            }
        };

        return result;
    }
}

class Period {
    constructor (periodName, budget, expenses) {
        this.name = periodName;
        this.budget = ko.observable(budget);
        this.expenses = ko.observableArray(expenses);
    }
}