checkInvestment = function () {
    document.forms["form_produktion"]["investment_pumpe"].classes.add(clicked);
}

addplayer = function () {
    var form = document.forms["anmeldung"];

    console.log(form);

    var name = form.elements["name"].value;

    var Player = {
        name: name
    };
    //socket.send(JSON.stringify(player));
    console.log(JSON.stringify(Player));
}