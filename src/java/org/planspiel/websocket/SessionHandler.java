package org.planspiel.websocket;

import java.io.IOException;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import org.planspiel.controller.Game;
import org.planspiel.model.User;

@ApplicationScoped
public class SessionHandler {

    private final HashMap<String, org.planspiel.model.User> users = new HashMap<>();
    private final HashMap<String, Game> gamesActive = new HashMap<>();
    private final HashMap<String, Session> sessions = new HashMap<>();

    public void add(Game addGame, String hashValue) {
        gamesActive.put(hashValue, addGame);
    }

    public void login(JsonObject jsonMessage, Session session) {
        System.out.println(jsonMessage.getString("name") + " - " + jsonMessage.getString("game_id"));

        String name = jsonMessage.getString("name");
        String game_id = jsonMessage.getString("game_id");
        Boolean admin = false;
        String players = "";  //TODO cant put array in json | useful at all?

        String user_hash = hashItUp(name);
        String game_hash = hashItUp(game_id.toLowerCase());
        String cookie = user_hash + ";" + game_hash;

        this.addSession(session, cookie);

        String error = "";   //TODO errorhandling add errors here

        if (gamesActive.containsKey(game_hash)) {
            String hashCodeGame = hashItUp(name);
            Game game = gamesActive.get(game_hash); 
            game.addPlayer(name, hashCodeGame);
            players = gamesActive.get(game_hash).showPlayers();

            System.out.println("Added " + name + " to game " + game_id);
        } //else create a new game, add a new player to it
        else {
            Game game = new Game(1, 2, game_id, name, cookie);
            gamesActive.put(game_hash, game); //TODO .add not working properly 
            admin = true;
            players = game.showPlayers();

            System.out.println("Created new Game and Added " + name + " to game " + game_id);
        }
        
        //return information
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "login")
                .add("name", name)
                .add("game_id", game_id)
                .add("admin", admin)
                .add("player", players)
                .add("cookie", cookie)
                .add("error", error)
                .build();

        System.out.println(addMessage);
        sendToSession(session, addMessage);
    }

    public void startGame(JsonObject jsonMessage, Session session) {
        String error = "";

        //check if user.admin==true
        //get game-id from cookie
        String[] hashes = jsonMessage.getString("cookie").split(";");
        if (hashes.length != 2) {
            error = "Cookie is not valid!";
        }

        //get needed values like BUDGET, FIXCOST, ROUNDS
        Collection<org.planspiel.model.User> users = gamesActive.get(hashes[1]).startGame();

        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "start_game")
                .add("game_id", jsonMessage.getString("game_id")) //neededS so sendToAll doesnt trigger the wrong games
                .build();

        sendToAllUsers(users, addMessage);

    }

    //thats where we can add some MAGIC to SPICE up the HashCodes ;)
    private String hashItUp(int value) {
        return Integer.toString(Integer.toString(value).hashCode());
    }

    private String hashItUp(String value) {
        return Integer.toString(value.hashCode());
    }
    
    //Sendet an alle Sessions
    //unabhängig in welchem Spiel sie sind!
    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions.values()) {
            sendToSession(session, message);
        }
    }

    //Sendet an eine spezifische Session
    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Sendet an einen Client mit spezifischem Cookie
    private void sendToCookie (String cookie, JsonObject message) {
        sendToSession(sessions.get(cookie), message);
    }

    public void addSession(Session session, String cookie) {
        sessions.put(cookie, session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    private void sendToAllUsers(Collection<User> users, JsonObject addMessage) {
        for (Iterator<User> it = users.iterator(); it.hasNext();) {
            sendToCookie(it.next().getCookie(), addMessage);
        }
    }
}
