package edu.uob.entities;

import edu.uob.GameEntity;
import edu.uob.Visitor;

public class NPC extends GameEntity {

    public NPC(String name, String description) {
        super(name, description);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String getType() {
        return "Character";
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getDescription();
    }
}
