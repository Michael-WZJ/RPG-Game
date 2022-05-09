package edu.uob;

import edu.uob.entities.Artefact;
import edu.uob.entities.NPC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicClassTests {

    @BeforeEach
    void setup() {

    }

    @Test
    // wzj
    void testEntities() {
        GameEntity lock = new Artefact("lock", "a new lock");
        GameEntity man = new NPC("Bob", "a npc");
        System.out.println(lock);
        System.out.println(man);

    }
}
