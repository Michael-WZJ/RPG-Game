package edu.uob.entities;

import edu.uob.GameEntity;
import edu.uob.Vistor;

import java.util.*;

public class Location extends GameEntity {
    private final HashSet<String> paths;
    private final Map<String, GameEntity> entities;
    private final HashSet<String> players;

    public Location(String name, String description) {
        super(name, description);
        paths = new HashSet<>();
        entities = new HashMap<>();
        players = new HashSet<>();
    }


    public void addPath(String path) {
        paths.add(path);
    }

    public void removePath(String path) {
        paths.remove(path);
    }


    public Set<String> getEntityNames() {
        return entities.keySet();
    }

    public GameEntity getEntity(String entity) {
        return entities.get(entity);
    }

    public void addEntity(GameEntity entity) {
        entities.put(entity.getName(), entity);
    }

    public GameEntity removeEntity(String name) {
        return entities.remove(name);
    }


    public void addPlayer(String username) {
        players.add(username);
    }

    public void removePlayer(String name) {
        players.remove(name);
    }


    public boolean hasEntity(String entity) {
        return entities.containsKey(entity);
    }

    public boolean hasPath(String path) {
        return paths.contains(path);
    }


    @Override
    public void accept(Vistor v) {
        v.visit(this);
    }

    @Override
    public String getType() {
        return "Location";
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
