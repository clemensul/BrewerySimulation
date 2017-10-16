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
                    var x = reply.player;
                    console.log("Lobby data received: " + JSON.parse(reply.player));
                }else {
                alert(reply.error);
            }
            break;
        }
        case "game_start":{
                if(reply.error ===""){
                    //game got started by the admin
                    //game-screens are visible
                }else {
                alert(reply.error);
            }
            break;
        }
        case "submit":{
                if(reply.error ===""){
                    //pass values to backend
                    //do magic with the values 
                    //send computed values to all sessions of a game
                }else {
                alert(reply.error);
            }
            break;
        }
        case "end":{
                if(reply.error ===""){
                    //a player has reached the game-goal or the number of periods is reached
                    //send to all sessions of a game
                    //game is stopped and a winning/loosing screen appears
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
