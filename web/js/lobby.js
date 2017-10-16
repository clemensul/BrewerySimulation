
console.log("Lobby entered");

function gameModel() {
    this.game_id = ko.observable(12345678),
    this.players = ko.observableArray([new player(), new player()]);
};

function player () {
    this.name = ko.observable("Clemens");
    this.admin = ko.observable(true);
}

var game = new gameModel();
ko.applyBindings(game);