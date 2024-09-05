// The following code references JsonSerializationDemo app

package persistence;

import model.Course;
import model.Courses;
import model.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

// JsonReader class to read from json file when loading data
public class JsonReader {
    private String source;
    //private Courses courses;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
        //courses = new Courses();
    }

    // EFFECTS: reads registered courses/waitlists info from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Student read(Courses courses) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudent(jsonObject, courses);
    }

    // EFFECTS: reads all course info from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Courses readCourses(Courses courses) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCourses(jsonObject, courses);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: student
    // EFFECTS: parses Student from JSON object and returns it with courses/waitlsits added
    private Student parseStudent(JSONObject jsonObject, Courses courses) {
        String name = jsonObject.getString("name");
        Student student = new Student(name);
        addCourses(student, jsonObject, courses);
        addWaitlists(student, jsonObject, courses);
        return student;
    }

    // MODIFIES: courses
    // EFFECTS: parses Courses from JSON object with courses added and returns it
    private Courses parseCourses(JSONObject jsonObject, Courses courses) {
        addCourses(courses, jsonObject);
        return courses;
    }


    // MODIFIES: student
    // EFFECTS: parses registered courses from JSON object and adds them to student
    private void addCourses(Student student, JSONObject jsonObject, Courses courses) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(student, nextCourse, courses);
        }
    }

    // MODIFIES: courses
    // EFFECTS: parses Courses from JSON object and adds them to courses
    private void addCourses(Courses courses, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(nextCourse, courses);
        }
    }

    // MODIFIES: student
    // EFFECTS: parses registered waitlists from JSON object and adds them to student
    private void addWaitlists(Student student, JSONObject jsonObject, Courses courses) {
        JSONArray jsonArray = jsonObject.getJSONArray("waitlists");
        for (Object json : jsonArray) {
            JSONObject nextWaitlist = (JSONObject) json;
            addWaitlist(student, nextWaitlist, courses);
        }
    }

    // MODIFIES: student
    // EFFECTS: parses course id  from JSON object and adds it to student
    // if course exists in courses
    private void addCourse(Student student, JSONObject jsonObject, Courses courses) {
        String id = jsonObject.getString("id");
        for (Course c: courses.getCourses()) {
            if (c.getID().equals(id)) {
                student.getRegisteredCourses().add(c);
            }
        }
    }

    // MODIFIES: courses
    // EFFECTS: parses course from JSON object and adds it to courses
    private void addCourse(JSONObject jsonObject, Courses courses) {
        String name = jsonObject.getString("name");
        String id = jsonObject.getString("id");
        String subject = jsonObject.getString("subject");
        String descripton = jsonObject.getString("description");
        int seats = jsonObject.getInt("seats");
        int waitlistSeats = jsonObject.getInt("waitlist seats");
        Course course = new Course(name, id, subject, descripton, seats, waitlistSeats);
        courses.add(course);
    }

    // MODIFIES: student
    // EFFECTS: parses waitlist id from JSON object and adds it to student
    // if the course exists in courses
    private void addWaitlist(Student student, JSONObject jsonObject, Courses courses) {
        String id = jsonObject.getString("id");
        for (Course c: courses.getCourses()) {
            if (c.getID().equals(id)) {
                student.getRegisteredWaitlists().add(c);
            }
        }
        
    }
}
