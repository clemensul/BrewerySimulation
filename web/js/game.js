var array_input = [
    {
        id: "mar_pla",
        name: "cost_m1",
        value: undefined
    }, {
        id: "mar_tvw",
        name: "cost_m2",
        value: undefined
    }, {
        id: "mar_rad",
        name: "cost_m3",
        value: undefined
    }, {
        id: "dev_bie",
        name: "cost_d1",
        value: undefined
    }, {
        id: "dev_hop",
        name: "cost_d2",
        value: undefined
    }, {
        id: "dev_get",
        name: "cost_d3",
        value: undefined
    }, {
        id: "pro_amount",
        name: "produced_litres",
        value: undefined
    }, {
        id: "pro_price",
        name: "price_litre",
        value: undefined
    }
];

onload = function () {
    array_input.forEach(function (element) {
        document.getElementById(element.id).onkeydown = function (e) {
            // Allow: backspace, delete, tab, escape, enter and .
            if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
                // Allow: Ctrl/cmd+A
                (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
                // Allow: Ctrl/cmd+C
                (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
                // Allow: Ctrl/cmd+X
                (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
                // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) 
            {
                    // let it happen, don't do anything
                    return;
            }

            // Ensure that it is a number and stop the keypress
            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105))
            {
                e.preventDefault();
            }

            
        };
    });
};


var validate_value = function (value) {
    if (value === undefined || value === "")
        return 0;
    else
        return parseInt(value);
};


var get_game_data = function () {

    var result = "{";

    array_input.forEach (function (element) {
        result +=   "\""
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

var send_game_data = function() {

    var content = {
        action: "submit",
        cookie: document.cookie,
        data: get_game_data()
    };
    console.log(content);
    socket.send(JSON.stringify(content));
}


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