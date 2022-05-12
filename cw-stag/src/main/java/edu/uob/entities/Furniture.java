package edu.uob.entities;

import edu.uob.GameEntity;
import edu.uob.Visitor;

public class Furniture extends GameEntity {

    public Furniture(String name, String description) {
        super(name, description);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String getType() {
        return "Furniture";
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getDescription();
    }
}
