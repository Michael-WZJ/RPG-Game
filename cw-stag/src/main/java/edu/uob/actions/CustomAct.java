package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;
import java.util.HashSet;

public class CustomAct extends GameAction {
    private String trigger;
    private HashSet<String> subjects;
    private HashSet<String> consumes;
    private HashSet<String> produces;
    private String narration;

    public CustomAct(String trigger) {
        this.trigger = trigger;
        subjects = new HashSet<>();
        consumes = new HashSet<>();
        produces = new HashSet<>();
    }

    public String execute(GameServer server) {
        return "";
    }


    public void addSubject(String subject) {
        subjects.add(subject);
    }

    public void addConsumes(String consume) {
        consumes.add(consume);
    }

    public void addProduces(String produce) {
        produces.add(produce);
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getNarration() {
        return narration;
    }

    // wzj
    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getTrigger() {
        return trigger;
    }

    @Override
    public String toString() {
        String result = "trigger: " + trigger + "\n" + "subjects: ";
        for (String s : subjects) {
            result = result.concat(s + ", ");
        }
        result = result.concat("\nconsumed: ");
        for (String s : consumes) {
            result = result.concat(s + ", ");
        }
        result = result.concat("\nproduced: ");
        for (String s : produces) {
            result = result.concat(s + ", ");
        }
        result = result.concat("\nnarration: " + narration);
        return result;
    }
}
