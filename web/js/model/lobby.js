class Lobby {
    constructor (game_id, players, admin) {
        console.log("Lobby Obj created")
        
        console.log(admin);
        this.admin = ko.observable(admin);
        this.game_id = ko.observable(game_id);
        this.players = ko.observableArray(players);
    }

    changePlayerArray (newPlayers) {
        this.players.removeAll();
        newPlayers.forEach(function(element) {
            this.players.push(element);
        }, this);
    }

    players(){
        return players;
    }

    game_id() {
        return game_id;
    }
}
