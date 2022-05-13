package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;
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
        assertEquals("cabin", serverBas.getGameModel().getStartLoc());
        HashSet<String> entities = identifiersBas.get(ENTITY);
        HashSet<String> actions = identifiersBas.get(ACTION);
        List<String> entityList = Arrays.asList("cabin", "key", "log", "cellar");
        List<String> actionList = Arrays.asList("inv", "look", "health",
                "inventory", "get", "drop", "goto", "cutdown", "fight", "attack");

        assertTrue(entities.containsAll(entityList));
        assertTrue(actions.containsAll(actionList));

        // Test Extended files
        assertEquals("cabin", serverExt.getGameModel().getStartLoc());
        entities = identifiersExt.get(ENTITY);
        actions = identifiersExt.get(ACTION);
        List<String> entListExt = new ArrayList<>(Arrays.asList("horn", "tree",
                "riverbank", "shovel"));
        entListExt.addAll(entityList);
        List<String> actListExt = new ArrayList<>(Arrays.asList("pay", "bridge",
                "dig", "blow"));
        actListExt.addAll(actionList);

        assertTrue(entities.containsAll(entListExt));
        assertTrue(actions.containsAll(actListExt));
    }
}


