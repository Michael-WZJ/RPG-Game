package edu.uob.entities;

import edu.uob.GameEntity;

import java.util.*;

public class Location extends GameEntity {
    private final List<String> paths;
    private final Map<String, GameEntity> entities;
    private final HashSet<String> players;

    public Location(String name, String description) {
        super(name, description);
        paths = new ArrayList<>();
        entities = new HashMap<>();
        players = new HashSet<>();
    }


    public void addPath(String path) {
        paths.add(path);
    }

    public void addEntities(GameEntity entity) {
        entities.put(entity.getName(), entity);
    }

    public void addPlayer(String username) {
        players.add(username);
    }


    public Set<String> getEntitynames() {
        return entities.keySet();
    }


    @Override
    public String toString() {
        String result = "";
        result = result.concat("Name: " + this.getName() + "\n");
        result = result.concat("Desc: " + this.getDescription()  + "\n");
        result = result.concat("Entities: "+ "\n" + printEntities());
        result = result.concat("Path: " + printPaths()  + "\n");
        result = result.concat("Players: " + printPlayers()  + "\n");
        return result;
    }

    // wzj
    public String printPaths() {
        String result = "";
        for (String p : paths) {
            result = result.concat(p + "\n");
        }
        return result;
    }

    public String printEntities() {
        String result = "";
        for (GameEntity e : entities.values()) {
            result = result.concat(e.toString() + "\n");
        }
        return result;
    }

    public String printPlayers() {
        String result = "";
        for (String p : players) {
            result = result.concat(p + "\n");
        }
        return result;
    }
}
