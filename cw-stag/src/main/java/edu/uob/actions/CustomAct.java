package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;
import java.util.HashSet;
import edu.uob.STAGException;

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

    @Override
    public String execute(GameServer server) throws STAGException {
        return narration;
    }


    public void consume() {
    }

    public void produce() {

    }



    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /*                  Methods for Building CustomAction                       */
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
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


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /*                      Accessor and Mutator Methods                        */
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public String getNarration() {
        return narration;
    }

    public String getTrigger() {
        return trigger;
    }

    public HashSet<String> getSubjects() {
        return subjects;
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
