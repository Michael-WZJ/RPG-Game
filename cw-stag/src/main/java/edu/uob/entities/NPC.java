package edu.uob.entities;

import edu.uob.GameEntity;

public class NPC extends GameEntity {

    public NPC(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getDescription();
    }
}
