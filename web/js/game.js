var array_input = [
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
    }, {
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
    }, {
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

ko.bindingHandlers.currencyText = {
    update: function (elem, valueAccessor) {
        var amount = valueAccessor();
        var formattedAmount = amount() + " €";
        $(elem).text(formattedAmount);
    }
};

array_input.forEach(function (element) {
    var elem = document.getElementById(element.id);
    elem.onkeydown = function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
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

    elem.onkeyup = function (e) {
        for (var i = 0; i < array_input.length; i++) {
            if (array_input[i].id == e.target.id) {
                array_input[i].value(validate_value(e.target.value));
                break;
            }
        }
    }
});

class KNOCKOUT {
    constructor() {
        this.investment = ko.observableArray(array_input)

        this.buget = ko.observable(0);
        this.fixedcost = ko.observable(0);
        this.variablecost = ko.observable(0);
        this.productionAmount = ko.observable(0);

        this.totalVariableCost = ko.computed(function () {
            return this.productionAmount() * this.variablecost();
        }, this);

        this.cost = ko.computed(function () {
            return this.productionAmount() * this.variablecost() + this.fixedcost();
        }, this);
    }
}

var knockout = new KNOCKOUT();
ko.applyBindings(knockout);


function init(budget, fixedcost, variablecost) {
    console.log("init");
    knockout.buget(budget);
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

    array_input.forEach(function (element) {
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

    ChangeToReport();
}




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

/******/
//CHARTS
/******/

// var color = new Colors();


// var charts = [];

// //Marketing
// var ctx = document.getElementById("marketing-plakat").getContext("2d");
// charts.push(new Chart(ctx, getPieChart(gameObj.marketing1(), "Plakatwerbung")));

// var ctx = document.getElementById("marketing-tv").getContext("2d");
// charts.push(new Chart(ctx, getPieChart(gameObj.marketing2(), "TV-Werbung")));

// var ctx = document.getElementById("marketing-radio").getContext("2d");
// charts.push(new Chart(ctx, getPieChart(gameObj.marketing3(), "Radiowerbung")));

// var ctx = document.getElementById("marketing-vergleich").getContext("2d");
// var datasets =
//     [
//         getDataset("Plakate", color.red, gameObj.marketing1()),
//         getDataset("TV-Werbung", color.blue, gameObj.marketing2()),
//         getDataset("Radio-Werbung", color.green, gameObj.marketing3())
//     ];

// charts.push(new Chart(ctx,
//     getLineChart(
//         ["1 Periode", "2 Periode", "3 Periode"],
//         datasets
//     )
// ));


// //Forschung und Entwicklung
// var ctx = document.getElementById("FuE-geschmack").getContext("2d");
// charts.push(new Chart(ctx, getPieChart(gameObj.development1(), "Geschmack")));

// var ctx = document.getElementById("FuE-chemie").getContext("2d");
// charts.push(new Chart(ctx, getPieChart(gameObj.development2(), "Chemie")));

// var ctx = document.getElementById("FuE-hopfen").getContext("2d");
// charts.push(new Chart(ctx, getPieChart(gameObj.development3(), "Hopfen")));

// var ctx = document.getElementById("FuE-vergleich").getContext("2d");
// var datasets =
//     [
//         getDataset("Geschmack", color.red, gameObj.development1()),
//         getDataset("Chemie", color.blue, gameObj.development2()),
//         getDataset("Hopfen", color.green, gameObj.development3())
//     ];

// charts.push(new Chart(ctx,
//     getLineChart(
//         ["1 Periode", "2 Periode", "3 Periode"],
//         datasets
//     )
// ));


// function getLineChart(labels, datasets) {
//     var config = {
//         type: "line",
//         data: {
//             labels: labels,
//             datasets: datasets,
//         },
//         options: {
//             responsive: true,
//             title: {
//                 display: true,
//                 text: "Chart.js Line Chart"
//             },
//             tooltips: {
//                 mode: "index",
//                 intersect: false,
//             },
//             hover: {
//                 mode: "nearest",
//                 intersect: true
//             },
//             scales: {
//                 xAxes: [{
//                     display: true,
//                     scaleLabel: {
//                         display: true,
//                         labelString: "Perioden"
//                     }
//                 }],
//                 yAxes: [{
//                     display: true,
//                     scaleLabel: {
//                         display: true,
//                         labelString: "Investment"
//                     }
//                 }]
//             }
//         }
//     };

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