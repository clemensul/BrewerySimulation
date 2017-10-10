var socket = new WebSocket("ws://localhost:8080/planspielWebWeb/actions");
socket.onmessage = onMessage;
socket.onopen = onOpen;


function onMessage(event) {

    console.log(event);

    json = event.data;
    reply = JSON.parse(json);

    switch (reply.action) {
        case "login": {
            switch (reply.status) {
                case "ok": lobby(reply.game_id);
                case "err": ;   
            };
            break;
        }
    }
}

function onOpen(event) {
    console.log("Connection open.")
}

function signin() {
    var form = document.forms["anmeldung"];

    console.log(form);
    console.log(socket);

    var name = form.elements["name"].value;
    var game_id = form.elements["game-id"].value;

    var Player = {
        name: name,
        game_id: game_id,
        action: "login",
        game_hash: "", //if game_hash empty, create new game
        user_hash: "" //userhash
    };
    
    socket.send(JSON.stringify(Player));
    console.log(JSON.stringify(Player));

    lobby();
}
