package edu.uob.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private final List<List<String>> tokens;
    private int currentIDX;
    private final HashSet<String> entityNames;
    private final HashSet<String> triggerNames;
    private static final int ENTITY = 0;
    private static final int ACTION = 1;

    public Tokenizer(String command, List<HashSet<String>> identifiers) {
        currentIDX = 0;
        tokens = new ArrayList<>();
        entityNames = identifiers.get(ENTITY);
        triggerNames = identifiers.get(ACTION);
        buildTokens(command);
    }


    public void buildTokens(String command) {
        // No username ?
        if (! command.contains(":")) {
            buildCommandTokens(splitBySpaces(command));
            return;
        }

        // Has username ?
        int idx = command.indexOf(':');
        String username = command.substring(0, idx);
        String commandStr = command.substring(idx + 1);
        tokens.add(Arrays.asList("User", username));
        tokens.add(Arrays.asList(":", command.substring(idx, idx+1)));
        buildCommandTokens(splitBySpaces(commandStr));
    }


    public List<String> splitBySpaces(String command) {
        List<String> firstSlicing;
        firstSlicing = new ArrayList<>(Arrays.asList(command.split("\\s+")));
        return firstSlicing;
    }

    private void buildCommandTokens(List<String> strList) {
        if (strList.isEmpty()) {
            return;
        }
        strList.remove("");
        for (String str : strList) {
            tokens.add(Arrays.asList("Command", str.toLowerCase()));
        }
    }


    public void nextToken() {
        currentIDX += 1;
    }

    public String getTokenLabel() {
        if (tokens.isEmpty()) {
            return "";
        }
        if (currentIDX < tokens.size()) {
            return tokens.get(currentIDX).get(0);
        } else {
            return "";
        }
    }

    public String getToken() {
        if (tokens.isEmpty()) {
            return "";
        }
        if (currentIDX < tokens.size()) {
            return tokens.get(currentIDX).get(1);
        } else {
            return "";
        }
    }


    @Override
    public String toString() {
        String result = "";
        for (List<String> token : tokens) {
            result = result.concat(token.toString() + "\n");
        }
        return result;
    }
}



