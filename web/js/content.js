//document.getElementById("start").style.display = "none";
//document.getElementById("lobby").style.display = "none";
//document.getElementById("marketing").style.display = "none";
//document.getElementById("forschung").style.display = "none";
//document.getElementById("produktion").style.display = "none";
//document.getElementById("bericht").style.display = "none";

function start () {
    console.log("START");    
}

function lobby (game_id, player, socket) {
    console.log("LOBBY");
    
    window.location.href = "lobby.html";
    //document.getElementById("lobby_game_id").value = game_id;
    
    if (player !== "")
    document.getElementById("lobby_player").textContent = "Spieler: " + JSON.stringify(player);
}

function game () { 
    window.location.href = "game.html";
   
}

function finish(){
    if(validparameter()){ 
        send_game_data();
        //window.location.href = "report.html";
    }
    else{
        alert("Du hast zu viel ausgaben");
    } // --> reicht der alert hier?
      // auch visuel was ändern ?
}