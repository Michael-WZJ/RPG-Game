package edu.uob;

import edu.uob.actions.CustomAct;
import edu.uob.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class LoadFileTests {
    private GameServer serverBas;
    private GameServer serverExt;
    private List<HashSet<String>> identifiersBas;
    private List<HashSet<String>> identifiersExt;
    private static final int ENTITY = 0;
    private static final int ACTION = 1;

    @BeforeEach
    void setup() {
        File entitiesFile = Paths.get("config/basic-entities.dot").toAbsolutePath().toFile();
        File actionsFile = Paths.get("config/basic-actions.xml").toAbsolutePath().toFile();
        serverBas = new GameServer(entitiesFile, actionsFile);
        identifiersBas = serverBas.getGameModel().getIdentifiers();

        File entitiesFile1 = Paths.get("config/extended-entities.dot").toAbsolutePath().toFile();
        File actionsFile1 = Paths.get("config/extended-actions.xml").toAbsolutePath().toFile();
        serverExt = new GameServer(entitiesFile1, actionsFile1);
        identifiersExt = serverExt.getGameModel().getIdentifiers();
    }

    @Test
    void testGameState() {
        // Test Basic files
        HashSet<String> entities = identifiersBas.get(ENTITY);
        HashSet<String> actions = identifiersBas.get(ACTION);
        List<String> entityList = Arrays.asList("cabin", "key", "log");
        List<String> actionList = Arrays.asList("inv", "look", "health",
                "cutdown", "fight", "attack");

        assertTrue(entities.containsAll(entityList));
        assertTrue(actions.containsAll(actionList));

        // Test Extended files
        entities = identifiersExt.get(ENTITY);
        actions = identifiersExt.get(ACTION);
        List<String> entListExt = new ArrayList<>(Arrays.asList("horn", "tree",
                "riverbank"));
        entListExt.addAll(entityList);
        List<String> actListExt = new ArrayList<>(Arrays.asList("pay", "bridge",
                "dig", "blow"));
        actListExt.addAll(actionList);

        assertTrue(entities.containsAll(entListExt));
        assertTrue(actions.containsAll(actListExt));
        ///* wzj
        System.out.println(entities);
        System.out.println(actions);
        //wzj */
    }


    @Test
    // wzj
    void testActions() {
        GameModel model = serverExt.getGameModel();
        for (HashSet<CustomAct> hSet : model.getActions().values()) {
            System.out.println(hSet);
            System.out.println();
        }
    }

    @Test
    // wzj
    void testActions2() {
        GameModel model = serverBas.getGameModel();
        for (HashSet<CustomAct> hSet : model.getActions().values()) {
            System.out.println(hSet);
            System.out.println();
        }
    }

    @Test
    // wzj
    void testEntities() {
        GameModel model = serverBas.getGameModel();
        assertEquals("cabin", model.getStartLoc());
        for (Location l : model.getLocations().values()) {
            System.out.println(l);
            System.out.println();
        }
    }
    @Test
    // wzj
    void testEntities2() {
        GameModel model = serverExt.getGameModel();
        assertEquals("cabin", model.getStartLoc());
        for (Location l : model.getLocations().values()) {
            System.out.println(l);
            System.out.println();
        }
    }
}


