package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class BasicCommandTests {

  private GameServer server;
  private GameServer serverExt;

  // Make a new server for every @Test (i.e. this method runs before every @Test test case)
  @BeforeEach
  void setup() {
    File entitiesFile = Paths.get("config/basic-entities.dot").toAbsolutePath().toFile();
    File actionsFile = Paths.get("config/basic-actions.xml").toAbsolutePath().toFile();
    server = new GameServer(entitiesFile, actionsFile);

    File entitiesFile1 = Paths.get("config/extended-entities.dot").toAbsolutePath().toFile();
    File actionsFile1 = Paths.get("config/extended-actions.xml").toAbsolutePath().toFile();
    serverExt = new GameServer(entitiesFile1, actionsFile1);
  }

  // Test to spawn a new server and send a simple "look" command
  @Test
  void testLookingAroundStartLocation() {
    String response = server.handleCommand("player : look").toLowerCase();
    assertTrue(response.contains("empty room"), "Did not see description of room in response to look");
    assertTrue(response.contains("magic potion"), "Did not see description of artifacts in response to look");
    assertTrue(response.contains("wooden trapdoor"), "Did not see description of furniture in response to look");
  }

  // Add more unit tests or integration tests here.
  @Test
  void testBuiltInAction() {
    assertTrue(serverExt.handleCommand("Bob: looK").contains("sharp axe"));
    assertTrue(serverExt.handleCommand("Tom: look").contains("wooden trapdoor"));
    assertTrue(serverExt.handleCommand(" look").contains("Bob"));

    serverExt.handleCommand("Bob: get coin");
    assertTrue(serverExt.handleCommand("get coin and trapdoor").contains("ERROR"));
    assertTrue(serverExt.handleCommand("get axe and potion").contains("ERROR"));
    assertTrue(serverExt.handleCommand("get trapdoor").contains("ERROR"));
    assertFalse(serverExt.handleCommand("look").contains("coin"));
    assertTrue(serverExt.handleCommand("Bob: INV").contains("coin"));
    assertTrue(serverExt.handleCommand("Tom: in").contains("ERROR"));

    serverExt.handleCommand("get potion");
    assertTrue(serverExt.handleCommand(" inventory").contains("potion"));

    serverExt.handleCommand("get axe");
    assertFalse(serverExt.handleCommand("look").contains("axe"));
    assertTrue(serverExt.handleCommand("drop").contains("ERROR"));
    assertTrue(serverExt.handleCommand("drop potion and axe").contains("ERROR"));

    serverExt.handleCommand("drop the axe");
    assertTrue(serverExt.handleCommand("Bob:look").contains("axe"));

    serverExt.handleCommand("Bob:goto forest");
    assertFalse(serverExt.handleCommand("LOOK").contains("Bob"));
    assertTrue(serverExt.handleCommand("Bob:LOOK").contains("pine tree"));

    assertTrue(serverExt.handleCommand("health level").contains("3"));
  }

  @Test
  void testCustomAction() {
    assertTrue(serverExt.handleCommand("bob: cutdown").contains("ERROR"));
    assertTrue(serverExt.handleCommand("bob: cutdown tree").contains("ERROR"));

    serverExt.handleCommand("bob: get axe");
    serverExt.handleCommand("bob: goto forest");
    serverExt.handleCommand("bob: cutdown tree");
    assertFalse(serverExt.handleCommand("bob: look").contains("tree"));
    serverExt.handleCommand("bob: get log");
    serverExt.handleCommand("bob: get key");
    serverExt.handleCommand("bob: goto cabin");
    assertTrue(serverExt.handleCommand("bob: INV").contains("key"));
    assertTrue(serverExt.handleCommand("bob: INV").contains("log"));
    serverExt.handleCommand("bob: open trapdoor");
    assertFalse(serverExt.handleCommand("bob: INV").contains("key"));
    assertTrue(serverExt.handleCommand("bob: look").contains("cellar"));
    serverExt.handleCommand("bob: goto cellar");

    serverExt.handleCommand("bob: hit elf");
    assertTrue(serverExt.handleCommand("bob: health").contains("2"));

    assertFalse(serverExt.handleCommand("bob: look").contains("log"));
    assertFalse(serverExt.handleCommand("bob: look").contains("axe"));
    serverExt.handleCommand("bob: hit elf");
    serverExt.handleCommand("bob: hit elf");
    assertTrue(serverExt.handleCommand("bob: look").contains("log cabin"));
    assertTrue(serverExt.handleCommand("health level").contains("3"));
    serverExt.handleCommand("bob: goto cellar");
    assertFalse(serverExt.handleCommand("bob: inv").contains("log"));
    assertFalse(serverExt.handleCommand("bob: inv").contains("axe"));
    assertTrue(serverExt.handleCommand("bob: look").contains("log"));
    assertTrue(serverExt.handleCommand("bob: look").contains("axe"));
  }

  @Test
  void testInvalidCommand() {
    assertTrue(serverExt.handleCommand("").contains("ERROR"));
    assertTrue(serverExt.handleCommand(" ").contains("ERROR"));
    assertTrue(serverExt.handleCommand(":").contains("ERROR"));
    assertTrue(serverExt.handleCommand("inv and look").contains("ERROR"));
    assertTrue(serverExt.handleCommand("player2: health").contains("ERROR"));
  }
}
