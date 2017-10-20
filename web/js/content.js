//document.getElementById("start").style.display = "none";
//document.getElementById("lobby").style.display = "none";
//document.getElementById("marketing").style.display = "none";
//document.getElementById("forschung").style.display = "none";
//document.getElementById("produktion").style.display = "none";
//document.getElementById("bericht").style.display = "none";

function start () {
    console.log("START");    
}

function ChangeToLobby (game_id, player, admin) {
    console.log("Content: LOBBY");
    
    localStorage.setItem("game_id", game_id);
    localStorage.setItem("player", JSON.stringify(player));
    localStorage.setItem("admin", admin);

    window.location.href = "lobby.html";
}

function ChangeToReport(){
  window.location.href = "report.html";
}

function ChangeToGame () { 
    window.location.href = "game.html";
}
