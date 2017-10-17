class Game {
    constructor () {
        this.expenses = ko.observableArray();
    }

    changeExpensesArray (newExpenses) {
        this.expenses.removeAll();
        newExpenses.forEach(function(element) {
            this.expenses.push(element)
        }, this);
    }

    expenses() {
        return expenses;
    }

}