package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCourse {
    private Course c1;
    private Course c2;
    private Course c3;
    private Course c4;

    @BeforeEach
    void runBefore() {
        c1 = new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10);
        c2 = new Course("CPSC 210", "210", "CPSC", "Second year Computer Science Course", 10, 10);
        c3 = new Course("CPSC 110", "210", "CPSC", "First year Computer Science Course", 10, 10);
        c4 = new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10);
    }

    @Test
    void testSetName() {
        c1.setName("CPSC 101");
        assertEquals("CPSC 101", c1.getName());
    }

    @Test
    void testSetSubject() {
        c1.setSubject("Physics");
        assertEquals("Physics", c1.getSubject());
    }

    @Test
    void testSetDescription() {
        c1.setDescription("First year math course");
        assertEquals("First year math course", c1.getDescription());
    }

    @Test
    void testSetSeats() {
        c1.setSeats(20);
        assertEquals(20, c1.getSeats());
    }

    @Test
    void testSetWaitlistSeats() {
        c1.setWaitlistSeats(20);
        assertEquals(20, c1.getWaitlistSeats());
    }

    @Test
    void testSetID() {
        c1.setID("210");
        assertEquals("210", c1.getID());
    }

    @Test
    void testUpdateCourse() {
        c1.updateCourse("Math 100", "100", "Math", "Math course", 100, 100);
        assertEquals("Math 100", c1.getName());
        assertEquals("100", c1.getID());
        assertEquals("Math", c1.getSubject());
        assertEquals("Math course", c1.getDescription());
        assertEquals(100, c1.getSeats());
        assertEquals(100, c1.getWaitlistSeats());
    }

    @Test 
    void testEquals() {
        assertTrue(c1.equals(c4));
        assertFalse(c1.equals(c3));
    }
}
