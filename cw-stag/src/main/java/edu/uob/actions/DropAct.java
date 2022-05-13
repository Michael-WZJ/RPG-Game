package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;
import edu.uob.STAGException;
import edu.uob.STAGException.InvalidCommandException;

import java.util.List;
import java.util.ArrayList;

public class DropAct extends GameAction {
    private final List<String> subjects;

    public DropAct(String username, List<String> subList) {
        setUsername(username);
        subjects = subList;
    }

    @Override
    public String execute(GameServer server) throws STAGException {
        String subject = getValidSubject(server);
        return server.dropArtefact(getUsername(), subject);
    }

    public String getValidSubject(GameServer server) throws STAGException {
        List<String> validSubList = new ArrayList<>();
        for (String str : subjects) {
            if (server.canDrop(getUsername(), str)) {
                validSubList.add(str);
            }
        }
        if (validSubList.isEmpty()) {
            throw new InvalidCommandException("Nothing can be dropped ?");
        } else if (validSubList.size() > 1) {
            throw new InvalidCommandException("Drop one thing at a time ?");
        } else return validSubList.get(0);
    }

    @Override
    public String toString() {
        String result = getUsername() + ":\ndrop:\n";
        for (String str : subjects) {
            result = result.concat(str + ", ");
        }
        return result;
    }
}
