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
import persistence.JsonWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter extends JsonTest {

    private Courses coursesList;
    private Courses courses;

    @BeforeEach
    void runBefore() {
        coursesList = new Courses();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Student student = new Student("John Doe");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStudent() {
        try {
            Student student = new Student("John Doe");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStudent.json");
            writer.open();
            writer.write(student);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStudent.json");
            student = reader.read(coursesList);
            assertEquals("John Doe", student.getName());
            assertEquals(0, student.getRegisteredCourses().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStudent() {
        try {
            testWriterGeneralCourses();
            Student student = new Student("John Doe");
            student.registerCourse(new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10));
            student.registerWaitlist(
                    new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudent.json");
            writer.open();
            writer.write(student);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralStudent.json");
            student = reader.read(courses);
            assertEquals("John Doe", student.getName());
            List<Course> courses = student.getRegisteredCourses();
            List<Course> waitlists = student.getRegisteredWaitlists();
            assertEquals(1, courses.size());
            assertEquals(1, waitlists.size());
            checkCourse("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10, courses.get(0));
            checkCourse("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10, waitlists.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCourses() {
        try {
            coursesList.add(new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10));
            assertEquals(1, coursesList.getCourses().size());
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCourses.json");
            writer.open();
            writer.write(coursesList);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralCourses.json");
            Courses readCourses = new Courses();
            courses = reader.readCourses(readCourses);
            List<Course> allCourses = courses.getCourses();
            assertEquals(1, allCourses.size());
            checkCourse("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10, allCourses.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStudentWrongCourses() {
        try {
            Student student = new Student("John Doe");
            student.registerCourse(new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10));
            student.registerWaitlist(
                    new Course("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudent.json");
            writer.open();
            writer.write(student);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralStudent.json");
            courses = new Courses();
            courses.add(new Course("CPSC 210", "210", "CPSC", "Second year Computer Science Course", 10, 10));
            student = reader.read(courses);
            assertEquals("John Doe", student.getName());
            List<Course> courses = student.getRegisteredCourses();
            List<Course> waitlists = student.getRegisteredWaitlists();
            assertEquals(0, courses.size());
            assertEquals(0, waitlists.size());
            //checkCourse("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10, courses.get(0));
            //checkCourse("CPSC 110", "110", "CPSC", "First year Computer Science Course", 10, 10, waitlists.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

