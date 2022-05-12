package edu.uob.entities;

import edu.uob.GameEntity;
import edu.uob.Visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Player extends GameEntity {
    private final Map<String, GameEntity> inventory;
    private String location;
    private int healthLevel;

    public Player(String name, String description, String startLoc) {
        super(name, description);
        inventory = new HashMap<>();
        location = startLoc;
        healthLevel = 3;
    }


    public void setLocation(String newLocation) {
        location = newLocation;
    }

    public String getLocation() {
        return location;
    }


    public void setHealthLevel(int level) {
        healthLevel = level;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    public void increaseHealth() {
        if (healthLevel < 3) {
            healthLevel += 1;
        }
    }

    public void decreaseHealth() {
        if (healthLevel > 0) {
            healthLevel -= 1;
        }
    }


    public void addArtefact(GameEntity entity) {
        inventory.put(entity.getName(), entity);
    }

    public GameEntity removeArtefact(String name) {
        return inventory.remove(name);
    }

    public GameEntity getArtefact(String name) {
        return inventory.get(name);
    }

    public HashSet<String> listAllArtefacts() {
        return new HashSet<>(inventory.keySet());
    }

    public boolean hasArtefact(String entity) {
        return inventory.containsKey(entity);
    }


    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String getType() {
        return "Player";
    }

    @Override
    public String toString() {
        String result = "";
        result = result.concat("Name: " + this.getName() + "\n");
        result = result.concat("Desc: " + this.getDescription());
        return result;
    }

    public String printInventory() {
        String result = "";
        for (GameEntity e : inventory.values()) {
            result = result.concat(e.toString() + "\n");
        }
        return result;
    }
}
