var socket = new WebSocket("ws://localhost:8080/planspielWebWeb/actions");
socket.onmessage = onMessage;
socket.onopen = onOpen;


function onMessage(event) {

    console.log(event);

    json = event.data;
    reply = JSON.parse(json);

    switch (reply.action) {
        case "login": {
            if (reply.error == undefined) {
                
                    lobby(reply.game_id);
                    document.cookie = reply.cookie;
            } else {

            }
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
        action: "login"
    };
    
    socket.send(JSON.stringify(Player));
    console.log(JSON.stringify(Player));

    lobby();
}
