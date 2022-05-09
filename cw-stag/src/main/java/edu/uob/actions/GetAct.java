package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;
import java.util.List;
import java.util.ArrayList;

public class GetAct extends GameAction {
    private final List<String> subjects;

    public GetAct(String username, List<String> subList) {
        setUsername(username);
        subjects = subList;
    }

    public String execute(GameServer server) {

        return "";
    }


    @Override
    public String toString() {
        String result = getUsername() + ":\nget:\n";
        for (String str : subjects) {
            result = result.concat(str + ", ");
        }
        return result;
    }
}
