package edu.uob.parser;

import edu.uob.GameAction;
import edu.uob.actions.CustomAct;
import edu.uob.actions.GetAct;
import edu.uob.actions.InvAct;
import edu.uob.actions.LookAct;
import edu.uob.STAGException;
import edu.uob.STAGException.InvalidCommandException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ParserStag {
    private final Tokenizer tokenizer;
    private final String username;
    private final List<String> triggers;
    private final List<String> subjects;
    private static final int LABEL = 0;
    private static final int WORD = 1;

    public ParserStag(String command, List<HashSet<String>> identifiers) {
        tokenizer = new Tokenizer(command, identifiers);
        triggers = new ArrayList<>();
        subjects = new ArrayList<>();
        if (getTokenLabel().equals("User")) {
            username = getToken();
        }
        else {
            username = "default-Player";
        }
        getCommandBody();
    }

    public GameAction parse() throws STAGException {
        if (triggers.isEmpty()) {
            throw new InvalidCommandException("No trigger words ?");
        }

        if (triggers.get(0).equals("look")) {
            return new LookAct(username);
        } else if (triggers.get(0).matches("inventory|inv")) {
            return new InvAct(username);
        } else if (triggers.get(0).equals("get")) {
            return buildGetAct();
        }
        else {
            return findCustomAct();
        }
    }

    public void getCommandBody() {
        for (List<String> token : tokenizer.getTokenList()) {
            if (token.get(LABEL).equals("Trigger")) {
                triggers.add(token.get(WORD));
            }else if (token.get(LABEL).equals("Entity")) {
                subjects.add(token.get(WORD));
            }
        }
    }

    public GetAct buildGetAct() throws STAGException {
        if (subjects.isEmpty()) {
            throw new InvalidCommandException("No subject for get ?");
        } else {
            return new GetAct(username, subjects);
        }
    }

    public CustomAct findCustomAct() {
        return new CustomAct("");
    }




    public String getTokenLabel() {
        return tokenizer.getTokenLabel();
    }

    public String getToken() {
        return tokenizer.getToken();
    }
}
