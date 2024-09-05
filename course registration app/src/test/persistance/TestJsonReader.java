package persistance;

import model.Student;
import model.Course;
import model.Courses;
import model.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import persistence.JsonReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader extends JsonTest {
    private Courses coursesList;

    @BeforeEach
    void runBefore() {
        coursesList = new Courses();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            Student student = reader.read(coursesList);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStudent() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStudent.json");
        try {
            Student student = reader.read(coursesList);
            assertEquals("Jane Doe", student.getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStudent() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudent.json");
        JsonReader readerCourses = new JsonReader("./data/testReaderGeneralCourses.json");
        try {
            testReaderGeneralCourses();
            Student student = reader.read(coursesList);
            assertEquals("Jane Doe", student.getName());
            List<Course> coursesRead = student.getRegisteredCourses();
            List<Course> waitlists = student.getRegisteredWaitlists();
            assertEquals(1, coursesRead.size());
            assertEquals(1, waitlists.size());
            checkCourse("CPSC 110", "110", "CPSC", "First year cpsc course", 100, 100, coursesRead.get(0));
            checkCourse("CPSC 110", "110", "CPSC", "First year cpsc course", 100, 100, waitlists.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCourses() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCourses.json");
        try {
            Courses courses = reader.readCourses(coursesList);
            List<Course> allCourses = courses.getCourses();
            assertEquals(1, allCourses.size());
            checkCourse("CPSC 110", "110", "CPSC", "First year cpsc course", 100, 100, allCourses.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderStudentNoneAvailable() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudent.json");
        try {
            Student student = reader.read(coursesList);
            assertEquals("Jane Doe", student.getName());
            List<Course> coursesRead = student.getRegisteredCourses();
            List<Course> waitlists = student.getRegisteredWaitlists();
            assertEquals(0, coursesRead.size());
            assertEquals(0, waitlists.size());
            //checkCourse("CPSC 110", "110", "CPSC", "First year cpsc course", 100, 100, coursesRead.get(0));
            //checkCourse("CPSC 110", "110", "CPSC", "First year cpsc course", 100, 100, waitlists.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}

