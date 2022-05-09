package edu.uob.parser;

import edu.uob.GameAction;
import edu.uob.actions.InvAct;
import edu.uob.actions.LookAct;

import java.util.HashSet;
import java.util.List;

public class ParserStag {
    private final Tokenizer tokenizer;
    private final String username;

    public ParserStag(String command, List<HashSet<String>> identifiers) {
        tokenizer = new Tokenizer(command, identifiers);
        if (getTokenLabel().equals("User")) {
            username = getToken();
            tokenizer.nextToken();
            tokenizer.nextToken();
        }
        else {
            username = "default-Player";
        }
    }

    public GameAction parse() throws Exception {
        while (! getToken().equals("")) {
            if (getToken().equals("look")) {
                return new LookAct(username);
            } else if (getToken().matches("inventory|inv")) {
                return new InvAct(username);
            }
            else {
                tokenizer.nextToken();
            }
        }
        throw new Exception("Invalid Command ?");
    }


    public String getTokenLabel() {
        return tokenizer.getTokenLabel();
    }

    public String getToken() {
        return tokenizer.getToken();
    }
}
