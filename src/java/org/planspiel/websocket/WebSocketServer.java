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
            switch(jsonMessage.getString("action")){
                case "login":{
                    sessionHandler.login(jsonMessage, session);
                    break;
                }
                case "start_game":{
                    sessionHandler.startGame(jsonMessage, session);
                     break;
                }
                case "newSession":{
                    sessionHandler.renewSession(session,jsonMessage);
                     break;
                }
                case "submit":{
                    sessionHandler.submit(jsonMessage, session);
                     break;
                }
                default: break;
            }
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