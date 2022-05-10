package edu.uob;

import edu.uob.entities.*;
import edu.uob.parser.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {
    private GameServer serverBas;
    private GameServer serverExt;
    private List<HashSet<String>> identifiersBas;
    private List<HashSet<String>> identifiersExt;
    String[] validCommands = {
            "chop down the tree using the axe",
            "CHOP down the TREE using the axe",
            "Simon: chop down the Tree using the axe",
            "Bob:look",
            "Bob: look",
            "Bob : look",
            "6 wzj",
            "Bob:get a key TREE",
            "Tom:cutdown tree"
    };
    String[] invalidCommands = {

    };

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
        //wzj
    void test3() {
        ParserStag p = new ParserStag(validCommands[8], identifiersExt);
        try {
            GameAction action = p.parse();
            System.out.println(action);

        } catch (STAGException se) {

        }
    }
    @Test
    //wzj
    void test2() {
        ParserStag p = new ParserStag(validCommands[7], identifiersExt);
        try {
            GameAction action = p.parse();
            System.out.println(action);

        } catch (STAGException se) {

        }
    }

    @Test
    //wzj
    void test1() {
        Tokenizer t = new Tokenizer(validCommands[2], identifiersExt);
        System.out.println(t);
    }
}
