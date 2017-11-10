var color = new Colors();
var charts = [];

var marketing = [
    {
        id: "mar_pla",
        name: "cost_m1",
        display: "Marketing1",
        value: ko.observable(0)
    }, {
        id: "mar_tvw",
        name: "cost_m2",
        display: "Marketing2",
        value: ko.observable(0)
    }, {
        id: "mar_rad",
        name: "cost_m3",
        display: "Marketing3",
        value: ko.observable(0)
    }
];
var development = [
    {
        id: "dev_bie",
        name: "cost_d1",
        display: "Development1",
        value: ko.observable(0)
    }, {
        id: "dev_hop",
        name: "cost_d2",
        display: "Development2",
        value: ko.observable(0)
    }, {
        id: "dev_get",
        name: "cost_d3",
        display: "Development3",
        value: ko.observable(0)
    }
];

var production = [
    {
        id: "pro_amount",
        name: "produced_litres",
        display: "Produziert",
        value: ko.observable(0)
    }, {
        id: "pro_price",
        name: "price_litre",
        display: "Preis",
        value: ko.observable(0)
    }
];

function cleanFromCommas(nStr) {
    return nStr.split(".").join("");
}

function numberWithCommas(x) {
    x = x + "";
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

ko.bindingHandlers.currencyText = {
    update: function (elem, valueAccessor) {
        var amount = valueAccessor();
        var formattedAmount = numberWithCommas(amount()) + " €";
        $(elem).text(formattedAmount);
    }
};

ko.bindingHandlers.hektoLiter = {
    update: function (elem, valueAccessor) {
        var amount = valueAccessor();
        var formattedAmount = numberWithCommas(amount()) + " hl";
        $(elem).text(formattedAmount);
    }
};

class KNOCKOUT {
    constructor() {
        this.report = ko.observable(new Report());

        this.marketing = ko.observableArray(marketing);
        this.development = ko.observableArray(development);
        this.productionAmount = ko.observable(production[0]);
        this.price = ko.observable(production[1]);

        this.budget = ko.observable(0);
        this.fixedcost = ko.observable(0);
        this.variablecost = ko.observable(0);

        this.totalVariableCost = ko.computed(function () {
            return this.productionAmount().value() * this.variablecost();
        }, this);

        this.productionCost = ko.computed(function () {
            return this.totalVariableCost() + this.fixedcost();
        }, this);

        this.investmentCost = ko.computed(function () {
            var result = 0;
            this.marketing().forEach(function (element) {
                result += parseInt(element.value());
            }, this);
            this.development().forEach(function (element) {
                result += parseInt(element.value());
            }, this);

            return result;
        }, this);

        this.cost = ko.computed(function () {
            var result = 0;
            result += this.investmentCost();
            result += this.productionCost();

            return result;
        }, this);

        this.budgetLeft = ko.computed(function () {
            return this.budget() - this.cost();
        }, this);

        this.error = ko.observable("");
    }
}

var knockout = new KNOCKOUT();
ko.applyBindings(knockout);

setListeners(marketing);
setListeners(development);
setListeners(production);

function setListeners(array) {
    array.forEach(function (element) {
        var elem = document.getElementById(element.id);
        elem.onkeydown = function (e) {
            // Allow: backspace, delete, tab, escape, enter and .
            if ($.inArray(e.keyCode, [8, 9, 27, 13, 110, 190]) !== -1 ||
                // Allow: Ctrl/cmd+A
                (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
                // Allow: Ctrl/cmd+C
                (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
                // Allow: Ctrl/cmd+X
                (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
                // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) {
                // let it happen, don't do anything
                return;
            }

            // Ensure that it is a number and stop the keypress
            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                e.preventDefault();
            }
        };
    });
}

function init(periods) {
    knockout.report().newPeriods(periods);

    var period = periods[periods.length - 1];

    console.log("Budget: " + period.budget);
    knockout.budget(period.budget);
    console.log("Fixcost: " + period.fixed_cost);
    knockout.fixedcost(period.fixed_cost);
    console.log("Variablenkosten: " + period.variable_cost);
    knockout.variablecost(period.variable_cost);

    console.log(knockout);
}

var validate_value = function (value) {
    if (value === undefined || value === "")
        return 0;
    else
        return parseInt(value);
};

var get_game_data = function () {

    var result = "{";

    marketing.forEach(function (element) {
        result += "\""
            + element.name
            + "\""
            + ": \""
            + validate_value(document.getElementById(element.id).value)
            + "\",";
    });

    development.forEach(function (element) {
        result += "\""
            + element.name
            + "\""
            + ": \""
            + validate_value(document.getElementById(element.id).value)
            + "\",";
    });

    production.forEach(function (element) {
        result += "\""
            + element.name
            + "\""
            + ": \""
            + validate_value(document.getElementById(element.id).value)
            + "\",";
    });

    result = result.substring(0, result.length - 1);
    result += "}";

    console.log(result);
    console.log(JSON.parse(result));

    return JSON.parse(result);
}




/******/
//CHARTS
/******/

function getUmsatz(step) {
    var umsatz = [];
    for (var i = 0; i <= 10; i++) {
        var sold = step * i;
        umsatz.push(knockout.price().value() * sold);
    }
    return umsatz;
}
function getGewinn(step) {
    var gewinn = [];
    var umsatz = getUmsatz(step);
    for (var i = 0; i <= 10; i++) {
        var sold = step * i;
        var value = umsatz[i] - knockout.fixedcost() - knockout.variablecost() * sold - knockout.investmentCost();
        gewinn.push(value);
    }
    return gewinn;
}

function updateCharts() {
    var target = knockout.productionAmount().value();
    var step = target / 10;

    charts[0].config.data.datasets[0].data = getGewinn(step);
    charts[0].config.data.datasets[1].data = getUmsatz(step);
    charts[0].update();
};

function getPreisfunktionen() {

    var target = knockout.productionAmount().value();
    var step = target / 10;

    var labels = [];
    for (var i = 0; i <= 10; i++) {
        var sold = step * i;
        labels.push((i * 10).toString() + "%");
    }

    var umsatz = getUmsatz(step);
    var gewinn = getGewinn(step);


    var datasets = [];
    datasets.push(getData(gewinn, "Gewinn", color.green[0]));
    datasets.push(getData(umsatz, "Umsatz", color.blue[0]));

    return getLineChart(datasets, labels, "Umsatzfunktion", "abgesetzter Anteil vom Produziertem", "Umsatz");
}

function getData(datasets, title, color) {
    return {
        label: title,
        backgroundColor: color,
        borderColor: color,
        data: datasets,
        fill: true,
    };
}

function getLineChart(datasets, labels, title, xAxis, yAxis) {
    return config = {
        type: "line",
        data: {
            labels: labels,
            datasets: datasets
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: title
            },
            tooltips: {
                mode: "index",
                intersect: false,
            },
            hover: {
                mode: "nearest",
                intersect: true
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: xAxis
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: yAxis
                    }
                }]
            }
        }
    };
}

//Prduktion
var ctx = document.getElementById("umsatzfunktion").getContext("2d");
charts.push(new Chart(ctx, getPreisfunktionen()));
production.forEach(function (elem) {
    elem.value.subscribe(function (value) {
        updateCharts();
    });
});
marketing.forEach(function (elem) {
    elem.value.subscribe(function (value) {
        updateCharts();
    });
});
development.forEach(function (elem) {
    elem.value.subscribe(function (value) {
        updateCharts();
    });
});




/* DEMO */



function getBarChartData() {

    return barChartData = {
        labels: ["January", "February", "March", "April", "May", "June"],
        datasets: [
            getDataset("Personal", color.red[0], report.ausgaben()[0]),
            getDataset("Rohstoffe", color.blue[0], report.ausgaben()[1]),
            getDataset("Sonstige", color.green[0], report.ausgaben()[2])
        ]

    };

    function getDataset(name, color, data) {
        return {
            label: name,
            backgroundColor: color,
            borderColor: color,
            borderWidth: 1,
            data: data
        }
    }
}

// function getDataset(label, color, data) {
//     return {
//         label: label,
//         backgroundColor: color[0],
//         borderColor: color[0],
//         data: data,
//         fill: false
//     }
// }

// function getDemoLineChart(color, data) {
//     return config_2 = {
//         type: 'line',
//         data: {
//             labels: ["Periode 1", "Periode 2", "Periode 3", "Periode 4", "Periode 5", "Periode 6"],
//             datasets: [{
//                 label: false,
//                 borderColor: color,
//                 backgroundColor: color,
//                 data: data,
//                 pointRadius: 0.1,
//                 pointHitRadius: 10
//             }]
//         },
//         options: {
//             legend: {
//                 display: false
//             },
//             responsive: true,
//             tooltips: {
//                 mode: 'index',
//             },
//             hover: {
//                 mode: 'index'
//             },
//             scales: {
//                 display: false,
//                 xAxes: [{
//                     display: false
//                 }],
//                 yAxes: [{
//                     display: false
//                 }]
//             }
//         }
//     };
// }

// function randomscalingfactor() {
//     return Math.round(Math.random(10) * 10000);
// }


// var ctx = document.getElementById("canvas-bericht-1").getContext("2d");
// window.myLine_1 = new Chart(ctx, getDemoLineChart(color.red[0], [
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
// ]));

// var ctx = document.getElementById("canvas-bericht-2").getContext("2d");
// window.myLine_2 = new Chart(ctx, getDemoLineChart(color.blue[0], [
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
// ]));

// var ctx = document.getElementById("canvas-bericht-3").getContext("2d");
// window.myLine_3 = new Chart(ctx, getDemoLineChart(color.blue[0], [
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
// ]));

// var ctx = document.getElementById("canvas-bericht-4").getContext("2d");
// window.myLine_4 = new Chart(ctx, getDemoLineChart(color.green[0], [
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
//     randomscalingfactor(),
// ]));

// var ctx = document.getElementById("canvas-bericht-5").getContext("2d");
// window.myBar_1 = new Chart(ctx, {
//     type: 'bar',
//     data: getBarChartData(),
//     options: {
//         responsive: true,
//         legend: {
//             position: 'top',
//         },
//         title: {
//             display: false,
//             text: 'Ausgaben nach Kategorie'
//         }
//     }
// });

// var ctx = document.getElementById("canvas-bericht-6").getContext("2d");
// window.myBar_2 = new Chart(ctx, {
//     type: 'bar',
//     data: getBarChartData(),
//     options: {
//         responsive: true,
//         legend: {
//             position: 'top',
//         },
//         title: {
//             display: false,
//             text: 'Einnahmen nach Kategorie'
//         }
//     }
// });