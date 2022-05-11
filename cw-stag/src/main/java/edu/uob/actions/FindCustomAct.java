package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;
import edu.uob.STAGException;
import edu.uob.STAGException.AmbiguityException;
import edu.uob.STAGException.NoSubjectException;
import edu.uob.STAGException.InvalidCommandException;

import java.util.HashSet;
import java.util.List;

public class FindCustomAct extends GameAction {
    private final String trigger;
    private final List<String> subjects;

    public FindCustomAct(String username, String trigger, List<String> subList) {
        setUsername(username);
        this.trigger = trigger;
        subjects = subList;
    }

    @Override
    public String execute(GameServer server) throws STAGException {
        CustomAct action = findCustomAct(server);
        action.setUsername(getUsername());
        return action.execute(server);
    }


    public CustomAct findCustomAct(GameServer server) throws STAGException {
        HashSet<CustomAct> validCustomActs = new HashSet<>();
        HashSet<CustomAct> allActions = server.getAction(trigger);

        // Get the actions corresponding to command
        HashSet<CustomAct> fetchedActs = fetchAction(allActions);

        // Get the actions can be performed
        for (CustomAct act : fetchedActs) {
            if (server.canAct(getUsername(), act)) {
                validCustomActs.add(act);
            }
        }

        // How many actions are eventually valid ?
        if (validCustomActs.isEmpty()) {
            throw new NoSubjectException(trigger);
        } else if (validCustomActs.size() > 1) {
            throw new AmbiguityException(trigger);
        }
        return validCustomActs.iterator().next();
    }


    public HashSet<CustomAct> fetchAction(HashSet<CustomAct> allActions) throws STAGException {
        HashSet<String> subjectsInAllActs = new HashSet<>();
        HashSet<CustomAct> resultSet = new HashSet<>();
        for (CustomAct act : allActions) {
            subjectsInAllActs.addAll(act.getSubjects());
        }
        HashSet<String> subjectsInCommand = new HashSet<>(subjects);
        subjectsInCommand.retainAll(subjectsInAllActs);
        if (subjectsInCommand.isEmpty()) {
            throw new InvalidCommandException("No valid subject for '"+ trigger +"' ?");
        }

        for (CustomAct act : allActions) {
            if (act.getSubjects().containsAll(subjectsInCommand)) {
                resultSet.add(act);
            }
        }

        for (CustomAct act : allActions) {
            if (subjectsInCommand.containsAll(act.getSubjects())) {
                resultSet.add(act);
            }
        }
        return resultSet;
    }





    @Override
    public String toString() {
        String result = getUsername() + ":\n" + trigger + ":\n";
        for (String str : subjects) {
            result = result.concat(str + ", ");
        }
        return result;
    }
}
