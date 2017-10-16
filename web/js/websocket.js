var socket = new WebSocket("ws://localhost:8080/planspielWebWeb/actions");
socket.onmessage = onMessage;
socket.onopen = onOpen;


function onMessage(event) {
    json = event.data;
    reply = JSON.parse(json);
    //console.log(reply.action);
    switch (reply.action) {
        
        case "login": {
            if (reply.error === "") {
                    document.cookie = reply.cookie;
                    console.log("Cookie created: " + reply.cookie);
                    lobby(reply.game_id, reply.player, socket);
                    
            } else {
                alert(reply.error);
            }
            break;
        }
        case "lobby":{
                if(reply.error ===""){
                    console.log("Lobby data received: " + reply.player);
                }else {
                alert(reply.error);
            }
            break;
        }
        case "status":{
                if(reply.error ===""){
                    console.log(reply.status + " status code");
                }else {
                alert(reply.error);
            }
            break;
        }
        
        default: {
       }
    }
}

function onOpen(event) {
    console.log("Connection open.");
    
    $('document').ready(
            function () {
                let cookieVal = document.cookie;
                var Cookie = {
                     cookie: cookieVal,
                     action: "newSession"
                 };
                socket.send(JSON.stringify(Cookie));
            }
    )
    
}

function signin() {
    var form = document.forms["anmeldung"];

    //console.log(form);
    //console.log(socket);

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
