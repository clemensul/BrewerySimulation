socket = new WebSocket("ws://localhost:8080/planspielWebWeb/actions");
socket.onmessage = onMessage;
socket.onopen = onOpen;

function getCookie() {
    var name = "_bs";
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
  }


function onMessage(event) {
    json = event.data;
    reply = JSON.parse(json);
    console.log(reply);
    switch (reply.action) {

        case "login": {
            if (reply.error === "") {
                document.cookie = "_bs=" + reply.cookie;
                console.log(reply);

                //Give data to Lobby Screen
                ChangeToLobby(reply.game_id, reply.player, reply.admin);

            } else {
                alert(reply.error);
            }
            break;
        }
        case "lobby": {
            if (reply.error === "") {

                //Give data to Lobby Screen
                if (lobbyObj !== undefined) {
                    lobbyObj.changePlayerArray(reply.players);
                }
            } else {
                alert(reply.error);
            }
            break;
        }
        case "start_game": {
            if (reply.error === "") {
                ChangeToGame();
            } else {
                alert(reply.error);
            }
            break;
        }
        case "submit": {
            if (reply.error === "") {
                //pass values to backend
                //do magic with the values 
                //send computed values to all sessions of a game
            } else {
                alert(reply.error);
            }
            break;
        }
        case "end": {
            if (reply.error === "") {
                //a player has reached the game-goal or the number of periods is reached
                //send to all sessions of a game
                //game is stopped and a winning/loosing screen appears
            } else {
                alert(reply.error);
            }
            break;
        }
        case "status": {
            if (reply.error === "") {
                console.log(reply.status + " status code");
            } else {
                alert(reply.error);
            }
            break;
        }
        case "report": {
            if (reply.error === "") {
                console.log(reply.report.periods);
                init(reply.report.periods);
            } else {
                alert(reply.error);
            }
        }

        default: {
        }
    }
}

function onOpen(event) {
    console.log("Connection open.");

    $('document').ready(
        function () {
            var path = window.location.pathname;
            var page = path.split("/").pop();

            if (page !== "") {
                var cookieVal = getCookie();

                var Cookie = {
                    cookie: cookieVal,
                    action: "newSession",
                    site: page
                };

                socket.send(JSON.stringify(Cookie));
            }
        }
    )

}

function start_game() {
    socket.send(
        JSON.stringify(
            {
                action: "start_game",
                cookie: getCookie()
            })
    );
}

function signin() {
    var form = document.forms["anmeldung"];
    var name = form.elements["name"].value;
    var game_id = form.elements["game-id"].value;

    var Player = {
        name: name,
        game_id: game_id,
        action: "login"
    };

    socket.send(JSON.stringify(Player));
    console.log(JSON.stringify(Player));
}

function send_game_data() {

    if (knockout.budgetLeft() < 0)
        alert("Du hast dein Budget überschritten!");
    else {
        var content = {
            action: "submit",
            cookie: getCookie(),
            data: get_game_data()
        };
        console.log(content);
        socket.send(JSON.stringify(content));
    }
}