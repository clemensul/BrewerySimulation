var Periods = ["Periode 1", "Periode 2", "Periode 3"];


var color1 = "rgba(72 , 190, 255, 1)";
var color2 = "rgba(136, 217, 230, 1)";
var color3 = "rgba(139, 139, 174, 1)";
var color4 = "rgba(82 , 103, 96 , 1)";
var color5 = "rgba(55 , 75 , 74 , 1)";


var data_plakate = [500, 450, 0];
var data_tv = [2000, 2800, 0];
var data_radio = [1400, 800, 0];

var data_geschmack = [1400, 800, 0];
var data_chemie = [2000, 2800, 0];
var data_hopfen = [500, 450, 0];


//Marketing
var ctx = document.getElementById("marketing-plakat").getContext("2d");
window.marketing_pie1 = new Chart(ctx, getPieChart(data_plakate, "Plakatwerbung"));

var ctx = document.getElementById("marketing-tv").getContext("2d");
window.marketing_pie2 = new Chart(ctx, getPieChart(data_tv, "TV-Werbung"));

var ctx = document.getElementById("marketing-radio").getContext("2d");
window.marketing_pie3 = new Chart(ctx, getPieChart(data_radio, "Radiowerbung"));

var ctx = document.getElementById("marketing-vergleich").getContext("2d");
var datasets =
    [
        getDataset("Plakate", color1, data_plakate),
        getDataset("TV-Werbung", color2, data_tv),
        getDataset("Radio-Werbung", color3, data_radio)
    ];
window.marketing_line1 = new Chart(ctx,
    getLineChart(
        ["1 Periode", "2 Periode", "3 Periode"],
        datasets
    )
);

//Forschung und Entwicklung
var ctx = document.getElementById("FuE-geschmack").getContext("2d");
window.FuE_pie1 = new Chart(ctx, getPieChart(data_geschmack, "Geschmack"));

var ctx = document.getElementById("FuE-chemie").getContext("2d");
window.FuE_pie2 = new Chart(ctx, getPieChart(data_chemie, "Chemie"));

var ctx = document.getElementById("FuE-hopfen").getContext("2d");
window.FuE_pie3 = new Chart(ctx, getPieChart(data_hopfen, "Hopfen"));

var ctx = document.getElementById("FuE-vergleich").getContext("2d");
var datasets =
    [
        getDataset("Geschmack", color1, data_geschmack),
        getDataset("Chemie", color2, data_chemie),
        getDataset("Hopfen", color3, data_hopfen)
    ];
window.FuE_line1 = new Chart(ctx,
    getLineChart(
        ["1 Periode", "2 Periode", "3 Periode"],
        datasets
    )
);


setListener(
    document.forms["form_marketing"]["investment_plakate"],
    [window.marketing_pie1, window.marketing_line1],
    data_plakate);

setListener(
    document.forms["form_marketing"]["investment_tv"],
    [window.marketing_pie2, window.marketing_line1],
    data_tv);

setListener(
    document.forms["form_marketing"]["investment_radio"],
    [window.marketing_pie3, window.marketing_line1],
    data_radio);


//Forschung und Entwicklung
setListener(
    document.forms["form_FuE"]["investment_geschmack"],
    [window.FuE_pie1, window.FuE_line1],
    data_geschmack);

setListener(
    document.forms["form_FuE"]["investment_chemie"],
    [window.FuE_pie2, window.FuE_line1],
    data_chemie);

setListener(
    document.forms["form_FuE"]["investment_hopfen"],
    [window.FuE_pie3, window.FuE_line1],
    data_hopfen);;


function setListener(input, charts, data) {
    input.onchange = function () {
        data[2] = parseInt(input.value) // get the current value of the input field.
        console.log(input);
        console.log(charts);
        console.log(data);

        charts.forEach(function (element) {
            element.update();
        }, this);
    }
}

function getLineChart(labels, datasets) {
    var config = {
        type: "line",
        data: {
            labels: labels,
            datasets: datasets,
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: "Chart.js Line Chart"
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
                        labelString: "Perioden"
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: "Investment"
                    }
                }]
            }
        }
    };

    return config;
}

function getPieChart(data, label) {
    return config_pie1 = {
        type: "pie",
        data: {
            labels: ["Periode 1", "Periode 2", "Periode 3"],
            datasets: [{
                label: label,
                backgroundColor: [color1, color2, color3],
                borderColor: [color1, color2, color3],
                data: data,
                fill: false,
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: label
            },
            tooltips: {
                mode: "index",
                intersect: false,
            },
            hover: {
                mode: "nearest",
                intersect: true
            }
        }
    };
}

function getDataset(label, color, data) {
    return {
        label: label,
        backgroundColor: color,
        borderColor: color,
        data: data,
        fill: false
    }
}