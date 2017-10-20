socket.send (
    {
        action: "start_game",
        cookie: document.cookie,
    }
);


var array_input = [
    {
        id: "mar_pla",
        name: "Marketing1"
    }, {
        id: "mar_tvw",
        name: "Marketing2"
    }, {
        id: "mar_rad",
        name: "Marketing3"
    }, {
        id: "dev_bie",
        name: "Development1"
    }, {
        id: "dev_hop",
        name: "Development2"
    }, {
        id: "dev_get",
        name: "Development3"
    }];

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


var get_ausgaben_sum = function () {
    // hier müssen nun alle input Felder überprüft werden und geschaut werden ob 
    // alle eingaben valide sind
    // man könnte auch alle Eingabefelder mit 0 vorinitalisieren
    var sum = 0;
    var temp_value = 0;

    array_input.forEach(function (element) {
        var temp = document.getElementById(element.id);
        temp_value = validate_value(temp);

        sum += temp_value;
    }, this);

    return sum;
};

var validate_value = function (value) {
    if (value === undefined || value === "")
        return 0;
    else
        return parseInt(value);
};


var get_game_data = function () {

    var result = [];

    array_input.forEach(function (element) {
        result.push(
            {
                name: element.name,
                expense: validate_value(document.getElementById(element.id).value)
            });
    }, this);

    return result;
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

array_input.forEach(function (element) {
    document.getElementById(element.id).onchange = function (){
        gameObj.changeExpensesArray(get_game_data());

        var expenseData = gameObj.getExpenseData();
        console.log(expenseData);

        data_plakate = expenseData[0];
        data_tv = expenseData[1];
        data_radio = expenseData[2];

        data_geschmack = expenseData[3];
        data_chemie = expenseData[4];
        data_hopfen = expenseData[5];

        charts.forEach( function (element) {
            console.log("UPDATE");
            element.update();
        }, this)
    }
}, this);



var gameObj = new Game(100, get_game_data());
gameObj.addPeriod (100, get_game_data());
ko.applyBindings(gameObj);


/******/
//CHARTS
/******/

var color = new Colors();

var expenseData = gameObj.getExpenseData();
console.log(expenseData);

var data_plakate = expenseData[0];
var data_tv = expenseData[1];
var data_radio = expenseData[2];

var data_geschmack = expenseData[3];
var data_chemie = expenseData[4];
var data_hopfen = expenseData[5];

var charts = [];

//Marketing
var ctx = document.getElementById("marketing-plakat").getContext("2d");
charts.push(new Chart(ctx, getPieChart(data_plakate, "Plakatwerbung")));

var ctx = document.getElementById("marketing-tv").getContext("2d");
charts.push(new Chart(ctx, getPieChart(data_tv, "TV-Werbung")));

var ctx = document.getElementById("marketing-radio").getContext("2d");
charts.push(new Chart(ctx, getPieChart(data_radio, "Radiowerbung")));

var ctx = document.getElementById("marketing-vergleich").getContext("2d");
var datasets =
    [
        getDataset("Plakate", color.red, data_plakate),
        getDataset("TV-Werbung", color.blue, data_tv),
        getDataset("Radio-Werbung", color.green, data_radio)
    ];

charts.push(new Chart(ctx,
    getLineChart(
        ["1 Periode", "2 Periode", "3 Periode"],
        datasets
    )
));


//Forschung und Entwicklung
var ctx = document.getElementById("FuE-geschmack").getContext("2d");
charts.push(new Chart(ctx, getPieChart(data_geschmack, "Geschmack")));

var ctx = document.getElementById("FuE-chemie").getContext("2d");
charts.push(new Chart(ctx, getPieChart(data_chemie, "Chemie")));

var ctx = document.getElementById("FuE-hopfen").getContext("2d");
charts.push(new Chart(ctx, getPieChart(data_hopfen, "Hopfen")));

var ctx = document.getElementById("FuE-vergleich").getContext("2d");
var datasets =
    [
        getDataset("Geschmack", color.red, data_geschmack),
        getDataset("Chemie", color.blue, data_chemie),
        getDataset("Hopfen", color.green, data_hopfen)
    ];

charts.push(new Chart(ctx,
    getLineChart(
        ["1 Periode", "2 Periode", "3 Periode"],
        datasets
    )
));


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