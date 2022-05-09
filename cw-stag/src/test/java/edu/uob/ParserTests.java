package edu.uob;

import edu.uob.entities.*;
import edu.uob.parser.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {
    String[] validCommands = {
            "chop down the tree using the axe",
            "CHOP down the TREE using the axe",
            "Simon: chop down the tree using the axe",
            "Bob:look",
            "Bob: look",
            "Bob : look"
    };
    String[] invalidCommands = {

    };
    private GameServer serverBas;
    private GameServer serverExt;

    @BeforeEach
    void setup() {
        File entitiesFile = Paths.get("config/basic-entities.dot").toAbsolutePath().toFile();
        File actionsFile = Paths.get("config/basic-actions.xml").toAbsolutePath().toFile();
        serverBas = new GameServer(entitiesFile, actionsFile);

        File entitiesFile1 = Paths.get("config/extended-entities.dot").toAbsolutePath().toFile();
        File actionsFile1 = Paths.get("config/extended-actions.xml").toAbsolutePath().toFile();
        serverExt = new GameServer(entitiesFile1, actionsFile1);
    }


    @Test
    //wzj
    void test1() {
        Tokenizer t;
        t = new Tokenizer(validCommands[5], serverExt.getGameModel().getIdentifiers());
        System.out.println(t);
    }
}
