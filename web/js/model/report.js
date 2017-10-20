class Report {
    constructor(periods) {

        this.periods = ko.observableArray(periods);

        this.umsatz = ko.computed(function () {
                var result = [];
        
                for (var i = 0; i < this.periods().length; i++) {
                    var array = this.periods()[i].umsatz();
                    for (var z = 0; z < array.length; z++)
                    {
                        if (i == 0)
                            result[z] = [];
        
                        result[z][i] = array[z].umsatz();
                    }
                };
        
                return result;
        }, this);
    }
}

class Period {
    constructor (umsatz, gewinn, ausgaben, absatz) {
        this.umsatz = ko.observable(umsatz);
        this.gewinn = ko.observable(gewinn);
        this.ausgaben = ko.observable(ausgaben);
        this.absatz = ko.observable(absatz);
    }
}