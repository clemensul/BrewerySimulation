package org.planspiel.websocket;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import org.planspiel.controller.Game;
import org.planspiel.model.Period;
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
        //String players = "";  //TODO cant put array in json | useful at all?
        JsonArray players;
        String user_hash = hashItUp(name);
        String game_hash = hashItUp(game_id.toLowerCase());
        String cookie = user_hash + "x" + game_hash;
        String hashCodeGame = hashItUp(name);     
        
        this.addSession(session, cookie);

        String error = "";   //TODO errorhandling add errors here

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
        //System.out.println(addMessage);
        sendToSession(session, addMessage);
        
        JsonObject lobbyMsg = provider.createObjectBuilder()
                .add("action", "lobby")
                .add("name", name)  //name of admin?
                .add("game_id", game_id)
                .add("player", players)
                .add("error", error)
                .build();
        //System.out.println(lobbyMsg);
        //sendToAllConnectedSessions(lobbyMsg);
        sendToGame(game_hash, lobbyMsg);
    }
    public void startGame(JsonObject jsonMessage, Session session){
        String error = "";
        String hash = jsonMessage.getString("cookie");
        String[] hashes = hash.split("x");
        
        ArrayList<User> al = gamesActive.get(hashes[1]).getUsers();
        for(User u : al){
            JsonProvider provider = JsonProvider.provider();
                JsonObject startMessage = provider.createObjectBuilder()
                .add("action", "start_game")
                .add("error", error)
                .build();
                
                sendToCookie(u.getCookie(), startMessage);
        }
    }
    private void initiateGame(JsonObject jsonMessage, Session session) {
        String error = "";
        String hash = jsonMessage.getString("cookie");
        String[] hashes = hash.split("x");
        
        if (hashes.length != 2) {
                error = "Cookie is not valid!";
            } 
            else{ 
                //if("true".equals(jsonMessage.getString("admin"))){
                    gamesActive.get(hashes[1]).initialize();
                //}
                //else{
                    //error = "No admin!";
                //}
            }
            
        ArrayList<User> al = gamesActive.get(hashes[1]).getUsers();
        for(User u : al){
            Period p = u.getCompany().getCurrentPeriod(gamesActive.get(hashes[1]).getCurrentPeriod());
            JsonProvider provider = JsonProvider.provider();
                JsonObject startMessage = provider.createObjectBuilder()
                .add("action", "initiate_game")
                .add("budget", new BigDecimal(p.getBudget()))
                .add("fixed_cost", new BigDecimal(p.getFixedCosts()))
                .add("variable_cost", p.getCostPerHectolitre())
                .add("error", error)
                .build();
                
                sendToSession(session, startMessage);
               break;
        }
         
        //sendToGame(hashes[1], startMessage);
    }

    //thats where we can add some MAGIC to SPICE up the HashCodes ;)
    private String hashItUp(int value) {
        return Integer.toString(Integer.toString(value).hashCode());
    }

    private String hashItUp(String value) {
        return Integer.toString(value.hashCode());
    }
    
    
    //prod. Menge
    //preis p. Hectolitre
    //Marketing 1,2,3
    //Dev 1,2,3
    public void submit(JsonObject jsonMessage, Session session){
        //search correct user in the current game
        String [] hashes = jsonMessage.getString("cookie").split("x");
        Game g = gamesActive.get(hashes[1]);
        ArrayList<User> users = g.getUsers();
        
        for(Iterator it = users.iterator(); it.hasNext();){
            User u = (User)it.next();
            if(u.getCookie().equals(jsonMessage.getString("cookie"))){
                String error = "";
                JsonObject jsonObj = jsonMessage.getJsonObject("data");
                
                float producedLitres = Float.parseFloat(jsonObj.getString("produced_litres"));
                float priceLitre = Float.parseFloat(jsonObj.getString("price_litre"));
                float marketing1 = Float.parseFloat(jsonObj.getString("cost_m1"));
                float marketing2 = Float.parseFloat(jsonObj.getString("cost_m2"));
                float marketing3 = Float.parseFloat(jsonObj.getString("cost_m3"));
                float development1 = Float.parseFloat(jsonObj.getString("cost_d1"));
                float development2 = Float.parseFloat(jsonObj.getString("cost_d2"));
                float development3 = Float.parseFloat(jsonObj.getString("cost_d3"));
                
                Boolean finish = g.submitValues(jsonMessage.getString("cookie"), producedLitres, priceLitre, 
                                                marketing1, marketing2, marketing3, 
                                                development1, development2, development3);
                
                if(finish){
                    g.nextPeriod();
                }
               
                break;
            }
           
        }
    }
    
    private void report(JsonObject jsonMessage, Session session){
        String error ="";
        String[] hashes = jsonMessage.getString("cookie").split("x");
        
        ArrayList<User> al = gamesActive.get(hashes[1]).getUsers();
        for(User u : al){
            Period p = u.getCompany().getCurrentPeriod(gamesActive.get(hashes[1]).getCurrentPeriod());
            JsonProvider provider = JsonProvider.provider();
                JsonObject reportMessage = provider.createObjectBuilder()
                .add("action", "report")
                .add("budget", String.valueOf(p.getBudget()))
                .add("produced_litres", String.valueOf(p.getProducedHectolitres()))
                .add("sold_litres", String.valueOf(p.getSoldHectolitres()))
                .add("price_litre", String.valueOf(p.getPricePerHectolitre()))
                .add("left_litre", String.valueOf(p.getHectolitresLeft()))
                .add("fixed_cost", String.valueOf(p.getFixedCosts()))
                .add("variable_cost", String.valueOf(p.getCostPerHectolitre()))
                .add("cost_m1", String.valueOf(p.getOptionMarketing1()))
                .add("impact_m1", String.valueOf(p.getSoldHectolitresM1()))
                .add("cost_m2", String.valueOf(p.getOptionMarketing2()))
                .add("impact_m2", String.valueOf(p.getSoldHectolitresM2()))
                .add("cost_m3", String.valueOf(p.getOptionMarketing3()))
                .add("impact_m3", String.valueOf(p.getSoldHectolitresM3()))
                .add("cost_d1", String.valueOf(p.getOptionDevelopment1()))
                .add("cost_d2", String.valueOf(p.getOptionDevelopment2()))
                .add("cost_d3", String.valueOf(p.getOptionDevelopment3()))
                .add("error", error)
                .build();
                
                sendToCookie(u.getCookie(), reportMessage);
        }
    }
    //Sendet an alle Sessions
    //unabhängig in welchem Spiel sie sind!
    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions.values()) {
            sendToSession(session, message);
            System.out.println("Sent to Session");
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
        System.out.println("Sent to Session with Cookie: " + cookie);
    }

    public void addSession(Session session, String cookie) {
        sessions.put(cookie, session);
    }
    
    public void renewSession(Session session, JsonObject jsonMessage){
        Iterator it = sessions.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry mp = (Map.Entry)it.next();
            if(mp.getKey().equals(jsonMessage.getString("cookie"))){
                mp.setValue(session);
                
                JsonProvider provider = JsonProvider.provider();
                JsonObject status = provider.createObjectBuilder()
                .add("action", "status")
                .add("status", "200") //neededS so sendToAll doesnt trigger the wrong games
                .add("error", "")
                .build();
                sendToSession(session, status);
            }
        }
        
        if("game.html".equals(jsonMessage.getString("site"))){
            initiateGame(jsonMessage, session);
        }
        else if("report.html".equals(jsonMessage.getString("site"))){
            report(jsonMessage, session);
        }
    }
        //System.out.println("renewed");
    

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    private void sendToGame(String game_hash, JsonObject jsonMessage) {
        Game g = gamesActive.get(game_hash);
        ArrayList<User> gUser = g.getUsers();
        if(gUser!=null){
            for(Iterator it = gUser.iterator(); it.hasNext();){
                try{
                User u = (User) it.next();
                sendToCookie(u.getCookie(), jsonMessage);}catch(Exception e){e.printStackTrace();};
            }
        }else{
            System.out.print("Error, no Users exist!");
            }
    }
}
