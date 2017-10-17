
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
        $("#" + element.id).keydown(function (e) {
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
        }, this);
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
    }
    }, this);

var gameObj = new Game();
gameObj.changeExpensesArray(get_game_data());

ko.applyBindings(gameObj);

