package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;

public class InvAct extends GameAction {

    public InvAct(String username) {
        setUsername(username);
    }

    @Override
    public String execute(GameServer server) {
        return server.lookInventory(getUsername());
    }
}
