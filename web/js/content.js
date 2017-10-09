
//Build initial view
var cards = document.getElementsByClassName("card");
Array.prototype.forEach.call(cards, function(element) {
    element.style.display = "none";
}, this);

start();

function start () {
    document.getElementById("start").style.display = "";
}

function lobby () {
    document.getElementById("start").style.display = "none";
    document.getElementById("lobby").style.display = "";
}