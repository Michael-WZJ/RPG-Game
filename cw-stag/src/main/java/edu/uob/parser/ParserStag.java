package edu.uob.parser;

import edu.uob.GameAction;
import edu.uob.actions.*;
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
        if (! username.matches("^[A-Za-z' \\-]*$")) {
            throw new InvalidCommandException("Not type valid username ?");
        }
        if (triggers.isEmpty()) {
            throw new InvalidCommandException("Not type valid trigger words ?");
        } else if (triggers.size() > 1) {
            throw new InvalidCommandException("Not perform one action at a time ?");
        }

        return switch (triggers.get(0)) {
            case "look" -> new LookAct(username);
            case "inventory", "inv" -> new InvAct(username);
            case "get" -> buildGetAct();
            case "drop" -> buildDropAct();
            case "goto" -> buildGotoAct();
            case "health" -> new HealthAct(username);
            default -> buildFindCustomAct();
        };
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
            throw new InvalidCommandException("Not type valid subject for 'get' ?");
        } else {
            return new GetAct(username, subjects);
        }
    }

    public DropAct buildDropAct() throws STAGException {
        if (subjects.isEmpty()) {
            throw new InvalidCommandException("Not type valid subject for 'drop' ?");
        } else {
            return new DropAct(username, subjects);
        }
    }

    public GotoAct buildGotoAct() throws STAGException {
        if (subjects.isEmpty()) {
            throw new InvalidCommandException("Not type valid subject for 'goto' ?");
        } else {
            return new GotoAct(username, subjects);
        }
    }

    public FindCustomAct buildFindCustomAct() throws STAGException {
        String trigger = triggers.get(0);
        if (subjects.isEmpty()) {
            throw new InvalidCommandException("Not type valid subject for '"+ trigger +"' ?");
        } else {
            return new FindCustomAct(username, trigger, subjects);
        }
    }

    public String getTokenLabel() {
        return tokenizer.getTokenLabel();
    }

    public String getToken() {
        return tokenizer.getToken();
    }
}
