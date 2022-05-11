package edu.uob.actions;

import edu.uob.GameAction;
import edu.uob.GameServer;
import edu.uob.GameEntity;
import java.util.HashSet;

import edu.uob.entities.Player;
import edu.uob.visitors.*;
import edu.uob.STAGException;
import edu.uob.STAGException.ConflictException;

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
        if (! consumes.isEmpty()) {
            doConsumeAct(server);
        }
        if (! produces.isEmpty()) {
            doProduceAct(server);
        }

        // Player still alive ?
        Player p = server.getPlayer(getUsername());
        if (p.getHealthLevel() == 0) {
            server.dieByHarm(p);
            return narration +
                    "\nYou died and lost all of your items, " +
                    "you must return to the start of the game";
        } else {
            return narration;
        }
    }


    public void doConsumeAct(GameServer server) throws STAGException {
        HashSet<String> consumedNames = new HashSet<>(consumes);
        HashSet<GameEntity> consumedEntitySet = getEntitySet(consumedNames, server);
        if (consumedEntitySet.size() != consumes.size()) {
            throw new ConflictException("consumed", consumes.toString());
        }
        ConsumeVisitor conV = new ConsumeVisitor(getUsername(), server);
        for (GameEntity e : consumedEntitySet) {
            e.accept(conV);
        }
    }

    public void doProduceAct(GameServer server) throws STAGException {
        HashSet<String> producedNames = new HashSet<>(produces);
        HashSet<GameEntity> producedEntitySet = getEntitySet(producedNames, server);
        if (producedEntitySet.size() != produces.size()) {
            throw new ConflictException("produced", produces.toString());
        }
        ProduceVisitor proV = new ProduceVisitor(getUsername(), server);
        for (GameEntity e : producedEntitySet) {
            e.accept(proV);
        }
    }

    public HashSet<GameEntity> getEntitySet(HashSet<String> names, GameServer server) {
        HashSet<GameEntity> result;
        if (names.remove("health")) {
            // Contains 'health'
            result = server.getEntitiesByName(names, getUsername());
            result.add(server.getPlayer(getUsername()));
        } else {
            result = server.getEntitiesByName(names, getUsername());
        }
        return result;
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
