
//Build initial view
var cards = document.getElementsByClassName("card");
Array.prototype.forEach.call(cards, function(element) {
    element.style.display = "none";
}, this);

document.getElementById("bericht").style.display = "none";

start();


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