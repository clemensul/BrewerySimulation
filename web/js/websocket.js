var socket = new WebSocket("ws://localhost:8080/planspielWebWeb/actions");
socket.onmessage = onMessage;
socket.onopen = onOpen;


function onMessage(event) {

    console.log(event);

}

function onOpen(event) {
    console.log("Connection open.")
}

function signin() {
    var form = document.forms["anmeldung"];

    console.log(form);
    console.log(socket);

    var name = form.elements["name"].value;
    var session_id = form.elements["session-id"].value;

    var Player = {
        name: name,
        session_id: session_id
    };
    
    socket.send(JSON.stringify(Player));
    console.log(JSON.stringify(Player));
}