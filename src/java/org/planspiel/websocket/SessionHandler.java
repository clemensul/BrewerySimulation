package org.planspiel.websocket;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import org.planspiel.controller.Game;
import org.planspiel.controller.helper;
import org.planspiel.model.User;

@ApplicationScoped
public class SessionHandler {

    private final HashMap<String, org.planspiel.model.User> users = new HashMap<>();
    private final HashMap<String, Game> gamesActive = new HashMap<>();
    private final HashMap<String, Session> sessions = new HashMap<>();

    public synchronized void add(Game addGame, String hashValue) {
        gamesActive.put(hashValue, addGame);
    }

    public synchronized void login(JsonObject jsonMessage, Session session) {
        System.out.println(jsonMessage.getString("name") + " - " + jsonMessage.getString("game_id"));

        String name = jsonMessage.getString("name");
        String game_id = jsonMessage.getString("game_id");
        String user_hash = helper.hashItUp(name);
        String game_hash = helper.hashItUp(game_id.toLowerCase());
        String cookie = user_hash + "x" + game_hash;
        Boolean admin = false;
        JsonArray players;
        String error = "";   //TODO errorhandling add errors here
        
        this.addSession(session, cookie);
       
        if (gamesActive.containsKey(game_hash)) {
            Game game = gamesActive.get(game_hash);
            game.addPlayer(name, cookie, false);
            players = gamesActive.get(game_hash).showPlayers();

            System.out.println("Added " + name + " to game " + game_id);
        } //else create a new game, add a new player to it
        else {
            Game game = new Game(1000000, 2, game_id, name, cookie);
            game.addPlayer(name, cookie, true);
            gamesActive.put(game_hash, game); //TODO .add not working properly 
            admin = true;
            players = game.showPlayers();

            System.out.println("Created new Game and Added " + name + " to game " + game_id);
        }
        JsonProvider provider = JsonProvider.provider();

        //return information
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "login")
                .add("name", name)
                .add("game_id", game_id)
                .add("admin", admin)
                .add("player", players)
                .add("cookie", cookie)
                .add("error", error)
                .build();
        sendToSession(session, addMessage);

        JsonObject lobbyMsg = provider.createObjectBuilder()
                .add("action", "lobby")
                .add("name", name) //name of admin?
                .add("game_id", game_id)
                .add("player", players)
                .add("error", error)
                .build();
        sendToGame(game_hash, lobbyMsg);
    }

    //When the StartButton is pressed.
    //Guides all Users to the GameSite
    public synchronized void startGame(JsonObject jsonMessage, Session session) {
        String error = "";
        String cookie = jsonMessage.getString("cookie");

        gamesActive.get(helper.getGameHash(cookie)).initialize();

        JsonObject startMessage = Json.createObjectBuilder()
                .add("action", "start_game")
                .add("error", error)
                .build();

        sendToGame(helper.getGameHash(cookie), startMessage);

    }

    public synchronized void submit(JsonObject jsonMessage, Session session) {
        //search correct user in the current game
        String cookie = jsonMessage.getString("cookie");
        Game game = gamesActive.get(helper.getGameHash(cookie));

        String error = "";
        JsonObject jsonObj = jsonMessage.getJsonObject("data");

        double producedLitres = Double.parseDouble(jsonObj.getString("produced_litres"));
        double priceLitre = Double.parseDouble(jsonObj.getString("price_litre"));
        double marketing1 = Double.parseDouble(jsonObj.getString("cost_m1"));
        double marketing2 = Double.parseDouble(jsonObj.getString("cost_m2"));
        double marketing3 = Double.parseDouble(jsonObj.getString("cost_m3"));
        double development1 = Double.parseDouble(jsonObj.getString("cost_d1"));
        double development2 = Double.parseDouble(jsonObj.getString("cost_d2"));
        double development3 = Double.parseDouble(jsonObj.getString("cost_d3"));

        Boolean finish = game.submitValues(jsonMessage.getString("cookie"), producedLitres, priceLitre,
                marketing1, marketing2, marketing3,
                development1, development2, development3);

        if (finish) {
            game.nextPeriod();

            Iterator<User> it = game.getUsers().values().iterator();
            while (it.hasNext()) {
                String keks = it.next().getCookie();
                sendToCookie(keks, get_report(helper.getGameHash(keks), helper.getUserHash(keks)));
            }
        }
    }

    //Gets full Report of one Company
    private synchronized JsonObject get_report(String gameHash, String userHash) {
        String error = "";

        Game game = gamesActive.get(gameHash);

        if (game.isFinished()) {
            //Gewinner bekannt geben
            //sendToGame(gameHash, message);
            return game.endGame();  //TODO not getting there
        } else {

            User user = game.getUsers().get(userHash);
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("action", "report");
            builder.add("error", error);
            builder.add("report", user.getCompany().toJson());

            return builder.build();
        }
    }

    //Sendet an einen Client mit spezifischem Cookie
    public synchronized void addSession(Session session, String cookie) {
        sessions.put(cookie, session);
    }

    public synchronized void renewSession(Session session, JsonObject jsonMessage) {
        String cookie = jsonMessage.getString("cookie");

        if (sessions.containsKey(jsonMessage.getString("cookie"))) {
            sessions.replace(jsonMessage.getString("cookie"), session);
        } else {
            sessions.put(jsonMessage.getString("cookie"), session);
        }

        JsonProvider provider = JsonProvider.provider();
        JsonObject status = provider.createObjectBuilder()
                .add("action", "status")
                .add("status", "200") //neededS so sendToAll doesnt trigger the wrong games
                .add("error", "")
                .build();
        sendToSession(session, status);

        System.out.println("Cookie: " + cookie + " entered: " + jsonMessage.getString("site"));
        
        if ("game.html".equals(jsonMessage.getString("site"))) {
            sendToCookie(cookie, get_report(helper.getGameHash(cookie), helper.getUserHash(cookie)));
        }
    }

    private synchronized void sendToGame(String game_hash, JsonObject jsonMessage) {
        Game g = gamesActive.get(game_hash);
        Iterator<User> it = g.getUsers().values().iterator();
        while (it.hasNext()) {
            try {
                User u = (User) it.next();
                sendToCookie(u.getCookie(), jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private synchronized void sendToCookie(String cookie, JsonObject message) {
        sendToSession(sessions.get(cookie), message);
        System.out.println("Sent \"action: " + message.getString(("action")) + "\" to Session with Cookie: " + cookie);
    }
    //Sendet an eine spezifische Session

    private synchronized void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger
                    .getLogger(SessionHandler.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Sendet an alle Sessions
    //unabhängig in welchem Spiel sie sind!
    private synchronized void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions.values()) {
            sendToSession(session, message);
            System.out.println("Sent to Session");
        }
    }
}
