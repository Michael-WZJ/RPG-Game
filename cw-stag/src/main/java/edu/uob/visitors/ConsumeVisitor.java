package edu.uob.visitors;

import edu.uob.GameServer;
import edu.uob.Visitor;
import edu.uob.entities.*;

public class ConsumeVisitor extends Visitor {
    private final String user;
    private final GameServer gameServer;

    public ConsumeVisitor(String username, GameServer server) {
        user = username;
        gameServer = server;
    }

    @Override
    public void visit(Artefact a) {
        Player p = gameServer.getPlayer(user);
        if (p.hasArtefact(a.getName())) {
            gameServer.dropArtefact(user, a.getName());
        }
        gameServer.moveEntityToLoc(a, "storeroom");
    }

    @Override
    public void visit(Furniture f) {
        gameServer.moveEntityToLoc(f, "storeroom");
    }

    @Override
    public void visit(Location l) {
        Player p = gameServer.getPlayer(user);
        Location currentLoc = gameServer.getLocation(p.getLocation());
        currentLoc.removePath(l.getName());
    }

    @Override
    public void visit(NPC n) {
        gameServer.moveEntityToLoc(n, "storeroom");
    }

    @Override
    public void visit(Player p) {
        p.decreaseHealth();
    }
}
