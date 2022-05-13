package edu.uob;

import edu.uob.actions.CustomAct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicClassTests {
    private GameServer serverExt;

    @BeforeEach
    void setup() {
        File entitiesFile1 = Paths.get("config/extended-entities.dot").toAbsolutePath().toFile();
        File actionsFile1 = Paths.get("config/extended-actions.xml").toAbsolutePath().toFile();
        serverExt = new GameServer(entitiesFile1, actionsFile1);
    }

    @Test
    void testCanAct() {
        serverExt.handleCommand("bob: look");
        CustomAct action = new CustomAct("cutdown");
        action.addSubject("axe");
        assertTrue(serverExt.canAct("bob", action));
        action.addSubject("key");
        assertFalse(serverExt.canAct("bob", action));
        serverExt.handleCommand("bob: goto forest");
        serverExt.handleCommand("bob: get key");
        serverExt.handleCommand("bob: goto cabin");
        String response = serverExt.handleCommand("bob: inv");
        assertTrue(response.contains("key"));
        assertTrue(serverExt.canAct("bob", action));
    }
}
