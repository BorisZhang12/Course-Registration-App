package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStudent {
    private Student s1;
    private Course c1;
    private Course c2;
    private Course fullCourse;
    private Course fullWaitlist;

    @BeforeEach
    void runBefore() {
        s1 = new Student("Andrew");
        c1 = new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10);
        c2 = new Course("CPSC 210", "210", "CPSC", "Second year Computer Science Course", 10, 10);
        fullCourse = new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 0, 10);
        fullWaitlist = new Course("CPSC 210", "210", "CPSC", "Second year Computer Science Course", 0, 10);

    }

    @Test
    void testResgisterCourse() {
        s1.registerCourse(c1);
        assertEquals(c1, s1.getRegisteredCourses().get(0));
        assertEquals(1, s1.getRegisteredCourses().size());
        s1.registerCourse(fullCourse);
        assertEquals(c1, s1.getRegisteredCourses().get(0));
        assertEquals(1, s1.getRegisteredCourses().size());
        s1.registerCourse(c1);
        assertEquals(c1, s1.getRegisteredCourses().get(0));
        assertEquals(1, s1.getRegisteredCourses().size());
    }

    @Test
    void testRegisterWaitList() {
        s1.registerWaitlist(c1);
        assertEquals(c1, s1.getRegisteredWaitlists().get(0));
        assertEquals(1, s1.getRegisteredWaitlists().size());
        s1.registerWaitlist(fullWaitlist);
        assertEquals(c1, s1.getRegisteredWaitlists().get(0));
        assertEquals(1, s1.getRegisteredWaitlists().size());
        s1.registerWaitlist(c1);
        assertEquals(c1, s1.getRegisteredWaitlists().get(0));
        assertEquals(1, s1.getRegisteredWaitlists().size());
    }

    @Test
    void testDropCourse() {
        s1.registerCourse(c1);
        s1.dropCourse(c1);
        assertEquals(0, s1.getRegisteredCourses().size());
        s1.dropCourse(c1);
        assertEquals(0, s1.getRegisteredCourses().size());
    }

    @Test
    void testDropWaitlist() {
        s1.registerWaitlist(c1);
        s1.dropWaitlist(c1);
        assertEquals(0, s1.getRegisteredWaitlists().size());
        s1.dropWaitlist(c1);
        assertEquals(0, s1.getRegisteredWaitlists().size());
    }

    @Test
    void testInCourse() {
        s1.registerCourse(c1);
        assertTrue(s1.inCourse(c1));
        assertFalse(s1.inCourse(c2));
    }

    @Test
    void testInWaitlist() {
        s1.registerWaitlist(c1);
        assertTrue(s1.inWaitlist(c1));
        assertFalse(s1.inWaitlist(c2));
    }

}
