package edu.uob;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import edu.uob.actions.CustomAct;
import edu.uob.entities.*;

public class GameModel {
    private String startLocation;
    private final Map<String, Location> locations;
    private final Map<String, Player> players;
    private final Map<String, HashSet<CustomAct>> actions;

    private final HashSet<String> entityNames;
    private final HashSet<String> triggerNames;


    public GameModel() {
        locations = new HashMap<>();
        players = new HashMap<>();
        actions = new HashMap<>();
        entityNames = new HashSet<>();
        // Add Built-in Commands
        triggerNames = new HashSet<>(Arrays.asList("inventory", "inv",
                "get", "drop", "goto", "look", "health"));
    }

    public String describeLoc(String loc) {
        Location location = locations.get(loc);
        String desc = "You are in " + location.getDescription();
        desc = desc.concat(". You can see:\n" + location.printEntities());
        desc = desc.concat("You can access from here:\n" + location.printPaths());
        desc = desc.concat("Player in this location:\n" + location.printPlayers());
        return desc;
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /*                    Methods for Changing Game State                       */
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public void addPlayer(Player p) {
        String username = p.getName();
        players.put(username, p);
        locations.get(p.getLocation()).addPlayer(username);
    }

    public void addPath(String sourceLoc, String targetLoc) {
        locations.get(sourceLoc).addPath(targetLoc);
    }

    public void addEntities(String loc, GameEntity entity) {
        locations.get(loc).addEntities(entity);
    }

    public void updateState() {
        triggerNames.addAll(actions.keySet());
        entityNames.addAll(locations.keySet());
        for (Location loc : locations.values()) {
            entityNames.addAll(loc.getEntityNames());
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /*                      Methods for Doing Judgement                         */
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public boolean hasPlayer(String username) {
        return players.containsKey(username);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /*                      Accessor and Mutator Methods                        */
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public void setStartLocation(String location) {
        if (startLocation == null) {
            startLocation = location;
        }
    }

    public String getStartLoc() {
        return startLocation;
    }


    public void addLocation(Location loc) {
        locations.put(loc.getName(), loc);
    }
    // wzj
    public Map<String, Location> getLocations() {
        return locations;
    }

    public void addAction(CustomAct action) {
        String trigger = action.getTrigger();
        if (actions.containsKey(trigger)) {
            actions.get(trigger).add(action);
        } else {
            HashSet<CustomAct> actionSet = new HashSet<>();
            actionSet.add(action);
            actions.put(trigger,actionSet);
        }
    }
    // wzj
    public Map<String, HashSet<CustomAct>> getActions() {
        return actions;
    }

    public Player getPlayer(String username) {
        return players.get(username);
    }
    // wzj
    public Map<String, Player> getPlayers() {
        return players;
    }

    public List<HashSet<String>>  getIdentifiers() {
        List<HashSet<String>> identifiers = new ArrayList<>();
        identifiers.add(entityNames);
        identifiers.add(triggerNames);
        return identifiers;
    }
}
