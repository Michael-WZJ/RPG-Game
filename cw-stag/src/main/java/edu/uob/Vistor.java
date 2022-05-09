package edu.uob;

import edu.uob.entities.*;

abstract public class Vistor {
    public abstract void visit(Artefact a);
    public abstract void visit(Furniture f);
    public abstract void visit(Location l);
    public abstract void visit(NPC n);
    public abstract void visit(Player p);
}
