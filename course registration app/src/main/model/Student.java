package model;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// reprsents a student with name, student id number, and list of registered courses
public class Student implements Writable {
    private String studentName; // tracks student name
    private List<Course> registeredCourses; // tracks student's registered courses
    private List<Course> registeredWaitlists; // tracks student's registered waitlisted courses

    private String name;

    // EFFECTS: Creates a new student with given student Number, name, and no
    // courses registered
    public Student(String name) {
        this.studentName = name;
        this.registeredCourses = new ArrayList<>();
        this.registeredWaitlists = new ArrayList<>();
    }

    // REQUIRES: Student is not already registered for given course and course seats
    // > 0
    // MODIFIES: this
    // EFFECTS: Registers student for given course if possible
    public void registerCourse(Course course) {
        if (!inCourse(course) && course.getSeats() > 0) {
            registeredCourses.add(course);
            course.changeSeatNumber(-1);
            Eventlog.getInstance().logEvent(new Event("student registered for course: " + course.getName()));
        }
    }

    // REQUIRES: student is not already registered for given waitlist and waitlist
    // seats > 0
    // MODIFIES: this
    // EFFECTS: Registers student for given course's waitlist if possible
    public void registerWaitlist(Course course) {
        if (!inWaitlist(course) && course.getSeats() > 0) {
            registeredWaitlists.add(course);
            course.changeWaitlistNumber(-1);
            Eventlog.getInstance().logEvent(new Event("student registered for waitlist: " + course.getName()));
        }
    }

    // MODIFIES: this
    // EFFECTS: Drops student from given course
    public void dropCourse(Course course) {
        if (inCourse(course)) {
            registeredCourses.remove(course);
            course.changeSeatNumber(1);
            Eventlog.getInstance().logEvent(new Event("student dropped from course: " + course.getName()));
        }
    }

    // MODIFIES: this
    // EFFECTS: Drops student from given course's waitlist
    public void dropWaitlist(Course course) {
        if (inWaitlist(course)) {
            registeredWaitlists.remove(course);
            course.changeWaitlistNumber(1);
            Eventlog.getInstance().logEvent(new Event("student dropped from waitlist: " + course.getName()));
        }
    }

    // EFFECTS: Return true if student is registered for given course, return false
    // otherwise
    public Boolean inCourse(Course course) {
        for (Course c : registeredCourses) {
            if (c == course) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Return true if student is registered for given course, return false
    // otherwise
    public Boolean inWaitlist(Course course) {
        for (Course c : registeredWaitlists) {
            if (c == course) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns all courses student is registered in
    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    // EFFECTS: Returns all waitlisted courses student is registered in
    public List<Course> getRegisteredWaitlists() {
        return registeredWaitlists;
    }

    // EFFECTS: get name
    public String getName() {
        return studentName;
    }

    // EFFECTS: creates new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", studentName);
        json.put("courses", coursesToJson());
        json.put("waitlists", waitlistToJson());
        return json;
    }

    // EFFECTS: returns registered courses in this student as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Course c : registeredCourses) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns registered waitlist in this student as a JSON array
    private JSONArray waitlistToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Course c : registeredWaitlists) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
