package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCourse(String name, String id, String subject, String description, int seats,
            int waitlistSeats, Course course) {
        assertEquals(name, course.getName());
        assertEquals(id, course.getID());
        assertEquals(subject, course.getSubject());
        assertEquals(description, course.getDescription());
        assertEquals(seats, course.getSeats());
        assertEquals(waitlistSeats, course.getWaitlistSeats());
    }
}
