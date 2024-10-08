package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event event;
    private Event event1;
    private Event event2;
    private Date date;
    
    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.
    
    @BeforeEach
    public void runBefore() {
        event = new Event("Sensor open at door");   // (1)
        event1 = new Event("Hello World");
        event2 = new Event("Sensor open at door"); 
        date = Calendar.getInstance().getTime();   // (2)
    }
    
    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Sensor open at door", event.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(event.equals(event2));
        assertFalse(event.equals(event1));
    }
}
