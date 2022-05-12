package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.uob.entities.*;

public class EntityTest {
    private Artefact a;
    private Furniture f;
    private Location l;
    private NPC n;
    private Player p;

    @BeforeEach
    void setup() {
        a = new Artefact("artefact1", "An apple");
        f = new Furniture("furniture1", "A bed");
        l = new Location("location1", "A room");
        n = new NPC("character1", "A professor");
        p = new Player("Bob", "A player", "location1");
    }

    @Test
    void testEntities() {
        assertEquals("artefact1", a.getName());
        assertEquals("Artefact", a.getType());
        assertTrue(a.toString().contains("apple"));

        assertEquals("furniture1", f.getName());
        assertEquals("Furniture", f.getType());
        assertTrue(f.toString().contains("bed"));

        assertEquals("location1", l.getName());
        assertEquals("Location", l.getType());
        assertTrue(l.toString().contains("room"));

        assertEquals("character1", n.getName());
        assertEquals("Character", n.getType());
        assertTrue(n.toString().contains("professor"));

        assertEquals("Bob", p.getName());
        assertEquals("Player", p.getType());
        assertTrue(p.toString().contains("player"));
    }

    @Test
    void testLocation() {
        l.addPath("forest");
        assertTrue(l.hasPath("forest"));
        assertTrue(l.printPaths().contains("forest"));

        l.addEntity(a); l.addEntity(f); l.addEntity(n);
        assertEquals(3, l.getEntityNames().size());
        assertTrue(l.hasEntity("character1"));
        assertEquals("an apple", l.getEntity("artefact1").getDescription());
        assertTrue(l.printEntities().contains("a bed"));

        l.addPlayer("Bob");
        assertTrue(l.printPlayers().contains("Bob"));

        assertTrue(l.toString().contains("furniture1"));
        assertTrue(l.toString().contains("a professor"));

        l.removePath("forest");
        assertFalse(l.hasPath("forest"));

        l.removeEntity("furniture1");
        assertEquals(2, l.getEntityNames().size());
        assertFalse(l.hasEntity("furniture1"));

        l.removePlayer("Bob");
        assertFalse(l.printPlayers().contains("Bob"));
    }

    @Test
    void testPlayer() {
        for (int i = 0; i < 5; i++) {
            p.increaseHealth();
        }
        assertEquals(3, p.getHealthLevel());

        p.decreaseHealth();
        assertEquals(2, p.getHealthLevel());
        for (int i = 0; i < 5; i++) {
            p.decreaseHealth();
        }
        assertEquals(0, p.getHealthLevel());

        p.addArtefact(a); p.addArtefact(new Artefact("artefact2", "a key"));
        assertEquals("a key", p.getArtefact("artefact2").getDescription());
        assertTrue(p.hasArtefact("artefact1"));
        assertEquals(2, p.listAllArtefacts().size());
        assertTrue(p.printInventory().contains("a key"));

        p.removeArtefact("artefact2");
        assertEquals(1, p.listAllArtefacts().size());
        assertFalse(p.printInventory().contains("a key"));
    }
}
