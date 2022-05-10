package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;

public class HealthAct extends GameAction {
    public HealthAct(String username) {
        setUsername(username);
    }

    @Override
    public String execute(GameServer server) {
        return server.lookHealth(getUsername());
    }
}
