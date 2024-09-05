package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;


// represents a list of all courses available in the application
public class Courses implements Writable {
    private List<Course> courses;
    private String name;

    // EFFECTS: creates a new Courses object
    public Courses() {
        this.courses = new ArrayList<>();
        this.name = "Courses";
    }

    // EFFECTS: returns number of available courses
    public int getCourseNum() {
        return courses.size();
    }

    // EFFECTS: adds courses to all available courses
    public void add(Course c) {
        courses.add(c);
    }

    // EFFECTS: returns all available courses as a list
    public List<Course> getCourses() {
        return courses;
    }

    // EFFECTS: creates new JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("Courses", coursesToJson());
        return json;
    }

    // EFFECTS: returns all courses in this student as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }


}
