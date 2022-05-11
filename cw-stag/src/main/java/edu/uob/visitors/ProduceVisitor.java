package edu.uob.visitors;

import edu.uob.GameServer;
import edu.uob.Vistor;
import edu.uob.entities.*;

public class ProduceVisitor extends Vistor {
    private final String user;
    private final GameServer gameServer;

    public ProduceVisitor(String username, GameServer server) {
        user = username;
        gameServer = server;
    }

    @Override
    public void visit(Artefact a) {
        Player p = gameServer.getPlayer(user);
        if (p.hasArtefact(a.getName())) {
            gameServer.dropArtefact(user, a.getName());
        }
        gameServer.moveEntityToLoc(a, p.getLocation());
    }

    @Override
    public void visit(Furniture f) {
        Player p = gameServer.getPlayer(user);
        gameServer.moveEntityToLoc(f, p.getLocation());
    }

    @Override
    public void visit(Location l) {
        Player p = gameServer.getPlayer(user);
        Location currentLoc = gameServer.getLocation(p.getLocation());
        currentLoc.addPath(l.getName());
    }

    @Override
    public void visit(NPC n) {
        Player p = gameServer.getPlayer(user);
        gameServer.moveEntityToLoc(n, p.getLocation());
    }

    @Override
    public void visit(Player p) {
        p.increaseHealth();
    }
}
