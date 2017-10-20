package org.planspiel.websocket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
    
@ApplicationScoped
@ServerEndpoint("/actions")
public class WebSocketServer {
    //gamesActive is reseted every time a new message comes in --> seperate class to control and save game-units
    //private Set<org.planspiel.controller.Game> gamesActive = new HashSet<>();
    
 @Inject
    private SessionHandler sessionHandler;
 
    @OnOpen
    public void open(Session session) 
    { 
        System.out.println ("NEW SESSION");
    }
    
    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            //System.out.println(message);
            
            if ("login".equals(jsonMessage.getString("action"))) {
                System.out.println("logging in");
                sessionHandler.login(jsonMessage, session);
                System.out.println("logged in");
//                sessionHandler.sendGameId(0, session);
            }
             if ("start_game".equals(jsonMessage.getString("action"))) {
                sessionHandler.startGame(jsonMessage, session);
                //System.out.println("started game");
            }
            if("newSession".equals(jsonMessage.getString("action"))){
                sessionHandler.renewSession(session,jsonMessage);
                //System.out.println("renewed game");
            }
            if("submit".equals(jsonMessage.getString("action"))){
                sessionHandler.submit(jsonMessage, session);
            }
//            if ("add".equals(jsonMessage.getString("action"))) {
//                Device device = new Device();
//                device.setName(jsonMessage.getString("name"));
//                device.setDescription(jsonMessage.getString("description"));
//                device.setType(jsonMessage.getString("type"));
//                device.setStatus("Off");
//                sessionHandler.addDevice(device);
//            }
//
//            if ("remove".equals(jsonMessage.getString("action"))) {
//                int id = (int) jsonMessage.getInt("id");
//                sessionHandler.removeDevice(id);
//            }
//
//            if ("toggle".equals(jsonMessage.getString("action"))) {
//                int id = (int) jsonMessage.getInt("id");
//                sessionHandler.toggleDevice(id);
//            }
        }
    }
    
    @OnClose
    public void close(Session session) {
        System.out.println("SESSION EXIT");
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }
}  