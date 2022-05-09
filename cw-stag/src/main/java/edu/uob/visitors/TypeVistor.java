package edu.uob.visitors;

import edu.uob.Vistor;
import edu.uob.entities.*;

public class TypeVistor extends Vistor {
    String type;

    @Override
    public void visit(Artefact a) {
        type = "Artefact";
    }
    public void visit(Furniture f) {
        type = "Furniture";
    }
    public void visit(Location l) {
        type = "Location";
    }
    public void visit(NPC n) {
        type = "Character";
    }
    public void visit(Player p) {
        type = "Player";
    }
}
