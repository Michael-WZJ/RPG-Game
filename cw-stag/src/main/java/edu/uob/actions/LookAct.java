package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;

public class LookAct extends GameAction {

    public LookAct(String username) {
        setUsername(username);
    }

    public String execute(GameServer server) {
        return server.lookAround(getUsername());
    }
}
