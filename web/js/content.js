//document.getElementById("start").style.display = "none";
//document.getElementById("lobby").style.display = "none";
//document.getElementById("marketing").style.display = "none";
//document.getElementById("forschung").style.display = "none";
//document.getElementById("produktion").style.display = "none";
//document.getElementById("bericht").style.display = "none";

function start () {
    console.log("START");
    document.getElementById("start").style.display = "";
    document.getElementById("nav_start").classList.add("active");
}

function lobby (game_id, player) {
    console.log("LOBBY");
    document.getElementById("start").style.display = "none";
    document.getElementById("nav_start").classList.remove("active");
    document.getElementById("nav_lobby").classList.add("active");
    document.getElementById("lobby").style.display = "";

    if (player !== "")
    document.getElementById("lobby_player").textContent = "Spieler: " + JSON.stringify(player);

}

function game () {
    console.log("GAME");
    document.getElementById("lobby").style.display = "none";
    document.getElementById("nav_lobby").classList.remove("active");
    document.getElementById("nav_spiel").classList.add("active");
    document.getElementById("marketing").style.display = "";
    document.getElementById("forschung").style.display = "";
    document.getElementById("produktion").style.display = "";
}