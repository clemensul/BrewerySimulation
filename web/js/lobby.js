/*
function gameModel() {
    this.game_id = ko.observable(12345678),
    this.players = ko.observableArray([new player(), new player()]);
};



var game = new gameModel();
ko.applyBindings(game);

*/

console.log("Lobby: Entered");

let game_id = localStorage.getItem("game_id");
let player = JSON.parse(localStorage.getItem("player"));
let admin = localStorage.getItem("admin");

lobbyObj = new Lobby(game_id, player, admin);
ko.applyBindings(lobbyObj);



