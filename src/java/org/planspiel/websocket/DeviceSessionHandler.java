
package org.planspiel.websocket;

import java.io.IOException;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.planspiel.model.Device;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import org.planspiel.controller.Game;
import org.planspiel.model.Device; 

@ApplicationScoped
public class DeviceSessionHandler {
    private final Set<Session> sessions = new HashSet<>();
    //private final Set<Device> devices = new HashSet<>();
    
    private HashMap<String,Game> gamesActive = new HashMap<>();
    
    private int deviceId = 0;
    
    public void add(Game addGame, String hashValue){
        gamesActive.put(hashValue, addGame);
    }
    
    public void login(JsonObject jsonMessage){
           System.out.println(jsonMessage.getString("name") + " - " + jsonMessage.getString("game_id"));
                boolean alreadyExists = false;
                //if the game already exists, add a new player to it
                
               if(gamesActive.containsKey(jsonMessage.getString("game_hash"))){
                        gamesActive.get(jsonMessage.getString("game_hash")).addPlayer(jsonMessage.getString("name"));
                        System.out.println("Added " + jsonMessage.getString("name") + " to game " + jsonMessage.getString("game_id"));
                    }
                //else create a new game, add a new player to it
                else{
                        //newGame.addPlayer(jsonMessage.getString("name"));
                        String hash = jsonMessage.getString("game_id");
                        gamesActive.put(hash, new Game(1,2, jsonMessage.getString("game_id"), jsonMessage.getString("name"))); //TODO .add not working properly 
                        System.out.println("Created new Game and Added " + jsonMessage.getString("name") + " to game " + jsonMessage.getString("game_id"));
                }
                
                //send game_id to session
                //sessionHandler.sendGameId(0, session);
 
    }

    private JsonObject createAddMessage(Device device) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getStatus())
                .add("description", device.getDescription())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
         try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(DeviceSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendGameId(int game_id, Session session){
        //Integer.toString(game_id) to JsonObject
        //sendToSession(session, Integer.toString(game_id));
    }
    
//     public void addSession(Session session) {
//        sessions.add(session);
//        for (Device device : devices) {
//            JsonObject addMessage = createAddMessage(device);
//            sendToSession(session, addMessage);
//        }
//    }
//
//    public void removeSession(Session session) {
//        sessions.remove(session);
//    }
//    public List<Device> getDevices() {
//        return new ArrayList<>(devices);
    }

//    public void addDevice(Device device) {
//         device.setId(deviceId);
//        devices.add(device);
//        deviceId++;
//        JsonObject addMessage = createAddMessage(device);
//        sendToAllConnectedSessions(addMessage);
//    }
//
//    public void removeDevice(int id) {
//          Device device = getDeviceById(id);
//        if (device != null) {
//            devices.remove(device);
//            JsonProvider provider = JsonProvider.provider();
//            JsonObject removeMessage = provider.createObjectBuilder()
//                    .add("action", "remove")
//                    .add("id", id)
//                    .build();
//            sendToAllConnectedSessions(removeMessage);
//        }
//    }
//
//    public void toggleDevice(int id) {
//        JsonProvider provider = JsonProvider.provider();
//        Device device = getDeviceById(id);
//        if (device != null) {
//            if ("On".equals(device.getStatus())) {
//                device.setStatus("Off");
//            } else {
//                device.setStatus("On");
//            }
//            JsonObject updateDevMessage = provider.createObjectBuilder()
//                    .add("action", "toggle")
//                    .add("id", device.getId())
//                    .add("status", device.getStatus())
//                    .build();
//            sendToAllConnectedSessions(updateDevMessage);
//        }
//    }
//
//    private Device getDeviceById(int id) {
//         for (Device device : devices) {
//            if (device.getId() == id) {
//                return device;
//            }
//        }
//        return null;
//    }
//}