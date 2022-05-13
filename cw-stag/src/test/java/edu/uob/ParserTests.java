package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

import edu.uob.parser.*;
import edu.uob.STAGException.InvalidCommandException;

public class ParserTests {
    private List<HashSet<String>> identifiersExt;
    String[] validCommands = {
            "Chop down the TREE using the axe",
            "Tom: goto forest",
            "Bob:look",
            "Bob: look",
            "Bob : look",
            "Tom:get a key TREE",
            "cutdown tree",
            ":look",
    };
    String[] invalidCommands = {
            "",
            " ",
            ":",
            "goto forest and look",
            "player1: get key",
    };

    @BeforeEach
    void setup() {
        File entitiesFile1 = Paths.get("config/extended-entities.dot").toAbsolutePath().toFile();
        File actionsFile1 = Paths.get("config/extended-actions.xml").toAbsolutePath().toFile();
        GameServer serverExt = new GameServer(entitiesFile1, actionsFile1);
        identifiersExt = serverExt.getGameModel().getIdentifiers();
    }

    @Test
    void testTokenizer() {
        Tokenizer t = new Tokenizer(validCommands[0], identifiersExt);
        assertTrue(t.isEntity("cellar"));
        assertTrue(t.isEntity("shovel"));
        assertEquals("Trigger", t.getTokenLabel());
        t.nextToken();
        assertEquals("Other", t.getTokenLabel());

        int triggerCnt = 0, entityCnt = 0;
        for (List<String> token : t.getTokenList()) {
            if (token.get(0).equals("Trigger")) {
                triggerCnt += 1;
            } else if (token.get(0).equals("Entity")) {
                entityCnt += 1;
            }
        }
        assertEquals(1, triggerCnt);
        assertEquals(2, entityCnt);
    }

    @Test
    void testInvalidCommand() {
        ParserStag p;
        for (String invalidCommand : invalidCommands) {
            p = new ParserStag(invalidCommand, identifiersExt);
            assertThrows(InvalidCommandException.class, p::parse);
        }
    }

    @Test
    void testValidCommand() {
        ParserStag p;
        String errorMessage = "";
        try {
            for (String command : validCommands) {
                p = new ParserStag(command, identifiersExt);
                GameAction action = p.parse();
                assertNotNull(action);
            }
        } catch (STAGException se) {
            errorMessage = se.getMessage();
        }
        assertEquals("", errorMessage);
    }
}
