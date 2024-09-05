// The following code references JsonSerializationDemo app

package persistence;

import model.Student;
import model.Course;
import model.Courses;

import org.json.JSONObject;


import java.io.*;

// JsonReader class to write student object to json file when saving data
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of student to file
    public void write(Student student) {
        JSONObject json = student.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of courses to file
    public void write(Courses courses) {
        JSONObject json = courses.toJson();
        saveToFile(json.toString(TAB));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
