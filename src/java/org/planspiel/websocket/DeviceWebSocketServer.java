package org.planspiel.websocket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
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
import org.planspiel.model.Device;
    
@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {
 @Inject
    private DeviceSessionHandler sessionHandler;
 
    @OnOpen
    public void open(Session session) {
    sessionHandler.addSession(session);
    }
    
    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            System.out.println(message);
            
            if ("login".equals(jsonMessage.getString("action"))) {
                System.out.println(jsonMessage.getString("name") + " - " + jsonMessage.getString("game_id"));
                //if(jsonMessage.getString("game_id") != HASHMAP GAME_ID)
                //create new game id
                
                //send game_id to session
                sessionHandler.sendGameId(0, session);
                //sth happens | game->hashmap->game_id
                
                
            }
            if ("add".equals(jsonMessage.getString("action"))) {
                Device device = new Device();
                device.setName(jsonMessage.getString("name"));
                device.setDescription(jsonMessage.getString("description"));
                device.setType(jsonMessage.getString("type"));
                device.setStatus("Off");
                sessionHandler.addDevice(device);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeDevice(id);
            }

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.toggleDevice(id);
            }
        }
    }
    
    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(DeviceWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }
}  