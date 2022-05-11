package edu.uob.entities;

import edu.uob.GameEntity;
import edu.uob.GameServer;
import edu.uob.Vistor;

public class Artefact extends GameEntity {


    public Artefact(String name, String description) {
        super(name, description);
    }

    @Override
    public void accept(Vistor v) {
        v.visit(this);
    }

    @Override
    public String getType() {
        return "Artefact";
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getDescription();
    }
}
