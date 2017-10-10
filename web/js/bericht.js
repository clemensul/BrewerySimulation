var red = "#da0c0c";
var blue = "#265dc5";
var green = "#059711";

function getLineChart(color) {
    return config_2 = {
        type: 'line',
        data: {
            labels: ["Periode 1", "Periode 2", "Periode 3", "Periode 4", "Periode 5", "Periode 6"],
            datasets: [{
                label: false,
                borderColor: color,
                backgroundColor: color,
                data: [
                    randomscalingfactor(),
                    randomscalingfactor(),
                    randomscalingfactor(),
                    randomscalingfactor(),
                    randomscalingfactor(),
                    randomscalingfactor(),
                ],
                pointRadius: 0.1,
                pointHitRadius: 10
            }]
        },
        options: {
            legend: {
                display: false
            },
            responsive: true,
            tooltips: {
                mode: 'index',
            },
            hover: {
                mode: 'index'
            },
            scales: {
                display: false,
                xAxes: [{
                    display: false
                }],
                yAxes: [{
                    display: false
                }]
            }
        }
    };
}

function getBarChartData() {
    return barChartData = {
        labels: ["January", "February", "March", "April", "May", "June"],
        datasets: [
            getDataset("Personal", red),
            getDataset("Rohstoffe", blue),
            getDataset("Sonstige", green)
        ]

    };

    function getDataset(name, color) { 
        return {
            label: name,
            backgroundColor: color,
            borderColor: color,
            borderWidth: 1,
            data: [
                randomscalingfactor(),
                randomscalingfactor(),
                randomscalingfactor(),
                randomscalingfactor(),
                randomscalingfactor(),
                randomscalingfactor()
            ]
        }
    }
}

window.onload = function () {
    var ctx = document.getElementById("canvas-bericht-1").getContext("2d");
    window.myLine_1 = new Chart(ctx, getLineChart(red));

    var ctx = document.getElementById("canvas-bericht-2").getContext("2d");
    window.myLine_2 = new Chart(ctx, getLineChart(blue));

    var ctx = document.getElementById("canvas-bericht-3").getContext("2d");
    window.myLine_3 = new Chart(ctx, getLineChart(blue));

    var ctx = document.getElementById("canvas-bericht-4").getContext("2d");
    window.myLine_4 = new Chart(ctx, getLineChart(green));

    var ctx = document.getElementById("canvas-bericht-5").getContext("2d");
    window.myBar_1 = new Chart(ctx, {
        type: 'bar',
        data: getBarChartData(),
        options: {
            responsive: true,
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Ausgaben nach Kategorie'
            }
        }
    });

    var ctx = document.getElementById("canvas-bericht-6").getContext("2d");
    window.myBar_2 = new Chart(ctx, {
        type: 'bar',
        data: getBarChartData(),
        options: {
            responsive: true,
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Einnahmen nach Kategorie'
            }
        }
    });
};

function randomscalingfactor() {
    return Math.round(Math.random(10) * 10000);
}