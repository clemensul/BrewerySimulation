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
                result += element.value();
            }, this);
            this.development().forEach(function (element) {
                result += element.value();
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

        elem.oninput = function (e) {
            // var value = cleanFromCommas(e.target.value);
            // e.target.value = numberWithCommas(value);

            // for (var i = 0; i < investment.length; i++) {
            //     if (investment[i].id == e.target.id) {
            //         investment[i].value(validate_value(value));
            //         break;
            //     }
            // }
        }
    });
}

function init(budget, fixedcost, variablecost) {
    console.log("init");
    knockout.budget(budget);
    knockout.fixedcost(fixedcost);
    knockout.variablecost(variablecost);
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

var send_game_data = function () {

    var content = {
        action: "submit",
        cookie: document.cookie,
        data: get_game_data()
    };
    console.log(content);
    socket.send(JSON.stringify(content));

    //ChangeToReport();
}


/******/
//CHARTS
/******/

function getUmsatz(step) {
    var umsatz = [];
    for (var i = 0; i <= 10; i++) {
        var sold = step * i;
        umsatz.push(knockout.price().value()*sold);
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

//     return config;
// }

// function getPieChart(data, label) {
//     return config_pie1 = {
//         type: "pie",
//         data: {
//             labels: ["Periode 1", "Periode 2", "Periode 3"],
//             datasets: [{
//                 label: label,
//                 backgroundColor: color.red,
//                 borderColor: color.red,
//                 data: data,
//                 fill: false,
//             }]
//         },
//         options: {
//             responsive: true,
//             title: {
//                 display: true,
//                 text: label
//             },
//             tooltips: {
//                 mode: "index",
//                 intersect: false,
//             },
//             hover: {
//                 mode: "nearest",
//                 intersect: true
//             }
//         }
//     };
// }

// function getDataset(label, color, data) {
//     return {
//         label: label,
//         backgroundColor: color[0],
//         borderColor: color[0],
//         data: data,
//         fill: false
//     }
// }


// var validparameter= function(){
//     // hier müssen nun alle input Felder überprüft werden und geschaut werden ob 
//     // alle eingaben valide sind
//     // man könnte auch alle Eingabefelder mit 0 vorinitalisieren

//     var temp = document.getElementById("mar_pla");
//     if (temp !== null && temp.value === ""){
//       mar_pl=0;
//     }else mar_pl = parseInt(temp.value);

//     var temp = document.getElementById("mar_tvw");
//     if (temp !== null && temp.value === ""){
//       mar_tv=0;
//     }else mar_tv = parseInt(temp.value);
//     var temp = document.getElementById("mar_rad");
//     if (temp !== null && temp.value === ""){
//       mar_ra=0;
//     }else mar_ra = parseInt(temp.value);

//     var temp = document.getElementById("for_bie");
//     if (temp !== null && temp.value === ""){
//       for_bi=0;
//     }else for_bi = parseInt(temp.value);
//     var temp= document.getElementById("for_che");
//     if (temp !== null && temp.value === ""){
//       for_ch=0;
//     }else for_ch = parseInt(temp.value);
//     var temp= document.getElementById("for_wei");
//     if (temp !== null && temp.value === ""){
//       for_we=0;
//     }else for_we = parseInt(temp.value);

//     // Kapital abrufen und testen. Testen wird aber unnötig, weil es nachher kein eingabe Feld mehr ist

//     var kap = document.getElementById("kapital");
//     if (kap !== null && kap.value === ""){
//       kapital=0;
//     }else kapital = parseInt(kap.value);

//     var ausgaben = mar_pl + mar_tv + mar_ra + for_bi + for_ch + for_we;


//     // test ob ich zu viele Ausgaben hätte
//     // später vllt ummodeln mit Kredtvergabe oder so

//      if((kapital-ausgaben)<0){ 
//          return false;
//      }else return true;
// };