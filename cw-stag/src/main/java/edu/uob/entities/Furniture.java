package edu.uob.entities;

import edu.uob.GameEntity;

public class Furniture extends GameEntity {

    public Furniture(String name, String description) {
        super(name, description);
    }


    @Override
    public String toString() {
        return this.getName() + ": " + this.getDescription();
    }
}
