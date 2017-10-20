var Periods = ["Periode 1", "Periode 2", "Periode 3"];

var color = new Colors();
console.log(color);


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
        getDataset("Plakate", color.red, data_plakate),
        getDataset("TV-Werbung", color.blue, data_tv),
        getDataset("Radio-Werbung", color.green, data_radio)
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
        getDataset("Geschmack", color.red, data_geschmack),
        getDataset("Chemie", color.blue, data_chemie),
        getDataset("Hopfen", color.green, data_hopfen)
    ];
window.FuE_line1 = new Chart(ctx,
    getLineChart(
        ["1 Periode", "2 Periode", "3 Periode"],
        datasets
    )
);


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
                backgroundColor: color.red,
                borderColor: color.red,
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
        backgroundColor: color[0],
        borderColor: color[0],
        data: data,
        fill: false
    }
}