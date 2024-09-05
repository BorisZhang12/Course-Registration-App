

package model;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CoursesTest {
    private Course c1;
    private Course c2;
    private Courses courses;
   
    @BeforeEach
    void runBefore() {
        c1 = new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10);
        c2 = new Course("CPSC 210", "210", "CPSC", "Second year Computer Science Course", 10, 10);
        courses = new Courses();
    }


    @Test
    void testAdd() {
        courses.add(c1);
        assertEquals(1, courses.getCourseNum());
        assertEquals(c1, courses.getCourses().get(0));
    }
}




