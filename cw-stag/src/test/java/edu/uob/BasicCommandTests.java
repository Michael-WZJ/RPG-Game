package edu.uob;

import edu.uob.actions.CustomAct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Paths;
import java.io.IOException;

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
    String response = server.handleCommand("player 1: look").toLowerCase();
    assertTrue(response.contains("empty room"), "Did not see description of room in response to look");
    assertTrue(response.contains("magic potion"), "Did not see description of artifacts in response to look");
    assertTrue(response.contains("wooden trapdoor"), "Did not see description of furniture in response to look");
  }

  // Add more unit tests or integration tests here.
  @Test
  void testwzj9() {
    System.out.println(serverExt.handleCommand("cutdown "));
    System.out.println(serverExt.handleCommand("player2: health"));

  }
  @Test
  void testwzj8() {
    System.out.println(serverExt.handleCommand("bob: look"));
    System.out.println(serverExt.handleCommand("bob: cutdown"));
    System.out.println();
    System.out.println(serverExt.handleCommand("bob: cutdown tree"));
    System.out.println();
    System.out.println(serverExt.handleCommand("bob: get axe"));
    System.out.println();
    System.out.println(serverExt.handleCommand("bob: goto forest"));
    System.out.println(serverExt.handleCommand("bob: inv"));
    System.out.println(serverExt.handleCommand("bob: cutdown tree"));
  }
  @Test
  // wzj 该测试 之后 放到其他测试类中，不要放在command测试里
  void testwzj7() {
    System.out.println(serverExt.handleCommand("bob: look"));
    CustomAct action = new CustomAct("cutdown");
    action.addSubject("axe");
    assertTrue(serverExt.canAct("bob", action));
    action.addSubject("key");
    assertFalse(serverExt.canAct("bob", action));
    System.out.println(serverExt.handleCommand("bob: goto forest"));
    System.out.println(serverExt.handleCommand("bob: get key"));
    System.out.println(serverExt.handleCommand("bob: goto cabin"));
    System.out.println(serverExt.handleCommand("bob: inv"));
    assertTrue(serverExt.canAct("bob", action));
  }
  @Test
  void testwzj6() {
    System.out.println(serverExt.handleCommand("health level"));
    System.out.println(serverExt.handleCommand("player2: health"));
  }
  @Test
  void testwzj5() {
    System.out.println(serverExt.handleCommand("get coin"));
    System.out.println(serverExt.handleCommand("player2: LooK"));
    System.out.println(serverExt.handleCommand("goto forest"));
    System.out.println(serverExt.handleCommand("player2: LooK"));
    System.out.println(serverExt.handleCommand("player2: goto forest"));
    System.out.println(serverExt.handleCommand("look"));
  }
  @Test
  void testwzj4() {
    System.out.println(serverExt.handleCommand("player2: get coin"));
    System.out.println(serverExt.handleCommand("get potion"));
    System.out.println(serverExt.handleCommand("inv"));
    System.out.println(serverExt.handleCommand("get axe"));
    System.out.println(serverExt.handleCommand("player2: INV"));
    System.out.println(serverExt.handleCommand("look"));

    System.out.println(serverExt.handleCommand("drop"));
    System.out.println(serverExt.handleCommand("drop potion and axe"));
    System.out.println(serverExt.handleCommand("drop the axe"));

    System.out.println(serverExt.handleCommand("look"));

  }
  @Test
  void testwzj3() {
    System.out.println(serverExt.handleCommand("look"));
    System.out.println(serverExt.handleCommand("player2: get coin"));
    System.out.println(serverExt.handleCommand("get coin and trapdoor"));
    System.out.println(serverExt.handleCommand("get axe and potion"));
    System.out.println(serverExt.handleCommand("get trapdoor"));
    System.out.println(serverExt.handleCommand("look"));
    System.out.println(serverExt.handleCommand("player2: INV"));
  }
  @Test
  void testwzj() {
    System.out.println(serverExt.handleCommand("player1: looK"));
    System.out.println(serverExt.handleCommand("player2: look"));
    System.out.println(serverExt.handleCommand(" look"));
  }
  @Test
  void testwzj2() {
    System.out.println(serverExt.handleCommand("player1: inV"));
    System.out.println(serverExt.handleCommand("player2: in"));
    System.out.println(serverExt.handleCommand(" inventory"));
  }

}
