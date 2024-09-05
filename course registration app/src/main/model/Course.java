package model;

import org.json.JSONObject;

import persistence.Writable;

// represents a course with course name, year level, course id, subject, description, seat number, waitlist seat number.
public class Course implements Writable {
    private String description; //tracks course description
    private String id; //tracks course id
    private String courseName; // tracks course name
    private String subject; // tracks course subject
    private int seats; // tracks course seats
    private int waitlistSeats; // tracks course waitlist seats
    

    private String name;

    // EFFECTS: Creates a course with given name, description, number of seats, and
    // number of waitlist seats
    public Course(String name, String id, String subject, String description, int seats,
            int waitlistSeats) {
        this.courseName = name;
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.seats = seats;
        this.waitlistSeats = waitlistSeats;
    }

    // MODIFIES: this
    // EFFECTS: changes seat number by given number
    public void changeSeatNumber(int num) {
        seats = seats + num;

    }

    // MODIFIES: this
    // EFFECTS: changes waitlist seat number by given number
    public void changeWaitlistNumber(int num) {
        waitlistSeats = waitlistSeats + num;

    }

    // MODIFIES: this
    // EFFECTS: sets course name to given name
    public void setName(String name) {
        this.courseName = name;
    }

    // MODIFIES: this
    // EFFECTS: sets course id to given id
    public void setID(String id) {
        this.id = id;
    }

    // MODIFIES: this
    // EFFECTS: sets course subject to given subject
    public void setSubject(String subject) {
        this.subject = subject;
    }

    // MODIFIES: this
    // EFFECTS: sets course description to given description
    public void setDescription(String description) {
        this.description = description;
    }


    // REQUIRES: seats >= 0
    // MODIFIES: this
    // EFFECTS: sets course seat number to given seat number
    public void setSeats(int seats) {
        this.seats = seats;
    }

    // REQUIRES: waitlistSeats >= 0
    // MODIFIES: this
    // EFFECTS: sets course waitlist seat number to given waitlist seat number
    public void setWaitlistSeats(int waitlistSeats) {
        this.waitlistSeats = waitlistSeats;
    }

    // MODIFIES: this
    // EFFECTS: updates all parameters of a course
    public void updateCourse(String name, String id, String subject, String courseDescription,
            int seatsInt, int waitlistSeatsInt) {
        setName(name);
        setID(id);
        setSubject(subject);
        setDescription(courseDescription);
        setSeats(seatsInt);
        setWaitlistSeats(waitlistSeatsInt);
        Eventlog.getInstance().logEvent(new Event("Course Information updated"));

    }

    // EFFECTS: gets course name
    public String getName() {
        return courseName;
    }

    // EFFECTs: gets course subject
    public String getSubject() {
        return subject;
    }

    // EFFECTS: gets course description
    public String getDescription() {
        return description;
    }

    // EFFECTS: gets course seat number
    public int getSeats() {
        return seats;
    }

    // EFFECTS: gets course waitlist seat number
    public int getWaitlistSeats() {
        return waitlistSeats;
    }

    // EFFECTS: gets id
    public String getID() {
        return id;
    }

    // EFFECTS: turns given Course into a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", courseName);
        json.put("id", id);
        json.put("subject", subject);
        json.put("description", description);
        json.put("seats", seats);
        json.put("waitlist seats", waitlistSeats);
        return json;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Course other = (Course) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    

}
