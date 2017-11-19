console.log("Lobby: Entered");

let game_id = localStorage.getItem("game_id");
let player = JSON.parse(localStorage.getItem("player"));
let admin = localStorage.getItem("admin");

lobbyObj = new Lobby(game_id, player, admin);
ko.applyBindings(lobbyObj);