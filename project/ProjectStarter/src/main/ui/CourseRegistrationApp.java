package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Course;
import model.Courses;
import model.Student;
import persistence.JsonReader;
import persistence.JsonWriter;

//Student Registration Application
//The following ui references TellerApp
//The following persistence code references JsonSerializationDemo app
public class CourseRegistrationApp {

    private static final String JSON_STORE = "./data/student.json";
    private static final String JSON_STORE_COURSES = "./data/courses.json";
    private Student student;
    private Scanner input;
    private String view;
    private Courses courses;
    private Course selectedCourse;
    private Boolean studentMode;
    private JsonWriter jsonWriter;
    private JsonWriter jsonWriterCourses;
    private JsonReader jsonReader;
    private JsonReader jsonReaderCourses;

    // EFFECTs: runs application
    public CourseRegistrationApp() throws FileNotFoundException {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: runs application home page
    public void runApp() {
        boolean keepRunning = true;
        String command = null;
        view = "menu";

        initialize();

        while (keepRunning) {
            if (view.equals("menu")) {
                displayMenu();
            } else if (view.equals("student")) {
                displayStudentView();
                studentMode = true;
            } else if (view.equals("admin")) {
                displayAdminView();
                studentMode = false;
            }

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                chooseMode(command);
            }
        }
        System.out.println("App closed");
    }

    // MODIFIES: this
    // EFFECTS: runs application's student home page
    public void runStudent() {
        boolean keepRunning = true;
        String command = null;
        studentMode = true;

        while (keepRunning) {
            displayStudentView();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepRunning = false;
                view = "menu";
            } else {
                processStudentCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: runs application's admin home page
    public void runAdmin() {
        boolean keepRunning = true;
        String command = null;
        studentMode = false;

        while (keepRunning) {
            displayAdminView();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepRunning = false;
                view = "menu";
            } else {
                processAdminCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: runs page to allow user to view all courses and select a course
    private void selectCourse() {
        boolean keepRunning = true;
        String command = null;

        while (keepRunning) {
            viewAllCourses();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepRunning = false;
                view = "Student";
            } else {
                if (findCourse(command)) {
                    runCourseInformation(command);
                } else {
                    System.out.println("Invalid Course Id, Please Try Again!");
                }

            }
        }
    }

    // MODIFIES: this
    // EFFECTS: runs course information page and allows registration
    private void runCourseInformation(String command) {
        boolean keepRunning = true;

        while (keepRunning) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepRunning = false;
                view = "Student";
                selectedCourse = null;
            } else {
                if (studentMode) {
                    selectCourseCommandStudent(command);
                } else if (!studentMode) {
                    selectCourseCommandAdmin(command);
                }
            }

        }
    }

    // REQUIRES: student.getRegisteredCourses().size() >= 0
    // EFFECTS: Views all of student's registered courses
    private void viewAllRegisteredCourses() {
        if (student.getRegisteredCourses().size() == 0) {
            System.out.println("No registered courses");
        } else {
            for (Course c : student.getRegisteredCourses()) {
                System.out.println("\n" + c.getName());
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: intialize student, scanner, fields
    public void initialize() {
        student = new Student("Jane Doe");
        courses = new Courses();
        selectedCourse = null;
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriterCourses = new JsonWriter(JSON_STORE_COURSES);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReaderCourses = new JsonReader(JSON_STORE_COURSES);

        //Course course = new Course("CPSC 110", "110", "CPSC", "First year cpsc course", 100, 100);
        //courses.add(course);
    }

    // EFFECTS: displays menu to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> Student Access");
        System.out.println("\ta -> Administrative Access");
        System.out.println("\tsa - Save Information");
        System.out.println("\tl -> Load Infomation");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: display options in student view home page
    private void displayStudentView() {
        System.out.println("\nWelcome " + student.getName());
        System.out.println("\nSelect from:");
        System.out.println("\tv -> See all courses");
        System.out.println("\tc -> View All Registered Courses");
        System.out.println("\tw -> View All Registered Waitlists");
        System.out.println("\tb -> Back");
    }

    // EFFECTS: display options in admin home page
    private void displayAdminView() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> See All Courses");
        System.out.println("\ta -> Add New Course");
        System.out.println("\tb -> Back");
    }

    // EFFECTS: displays an individual course's information and possible command
    // actions for
    // student
    private void displayCourseinformationStudent(Course course) {
        System.out.println("Course Name:" + course.getName());
        System.out.println("Course id:" + course.getID());
        System.out.println("Course subject:" + course.getSubject());
        System.out.println("Course Description:" + course.getDescription());
        System.out.println("Number of Seats:" + course.getSeats());
        System.out.println("Number of Waitlist Seats:" + course.getWaitlistSeats());
        System.out.println("c -> Register Course");
        System.out.println("w -> Register Waitlist");
        System.out.println("dc -> Drop Course");
        System.out.println("dw -> Drop Waitlist");
        System.out.println("b -> Back");
    }

    // EFFECTS: displays an individual course's information and possible command
    // actions for admin user
    private void displayCourseinformationAdmin(Course course) {
        System.out.println("Course Name:" + course.getName());
        System.out.println("Course id:" + course.getID());
        System.out.println("Course subject:" + course.getSubject());
        System.out.println("Course Description:" + course.getDescription());
        System.out.println("Number of Seats:" + course.getSeats());
        System.out.println("Number of Waitlist Seats:" + course.getWaitlistSeats());
        System.out.println("m -> Modify Course Information");
        System.out.println("b -> back");
    }

    // MODIFIES: this
    // EFFECTS: chooses between running student mode or administrator mode from home
    // page
    private void chooseMode(String command) {
        if (command.equals("s")) {
            view = "student";
            studentMode = true;
            runStudent();
        } else if (command.equals("a")) {
            view = "admin";
            studentMode = false;
            runAdmin();
        } else if (command.equals("sa")) {
            saveInformation();
        } else if (command.equals("l")) {
            loadInformation();
        } else {
            System.out.println("Invalid Input");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes student command from student view page
    private void processStudentCommand(String command) {
        if (command.equals("v")) {
            selectCourse();
        } else if (command.equals("c")) {
            viewAllRegisteredCourses();
        } else if (command.equals("w")) {
            viewAllWaitlistCourses();
        } else {
            System.out.println("Invalid Input");
        }
    }

    // MODIFIES: this
    // EFFECTS: process admin command from admin view page
    private void processAdminCommand(String command) {
        if (command.equals("v")) {
            selectCourse();
        } else if (command.equals("a")) {
            createNewCourse();
        } else {
            System.out.println("Invalid Input");
        }
    }

    // EFFECTS: processes user input on whether to registers/drops course or course
    // waitlist
    private void selectCourseCommandStudent(String command) {
        if (command.equals("c")) {
            studentCourseAction("c");
        } else if (command.equals("w")) {
            studentWaitlistAction("w");
        } else if (command.equals("dc")) {
            studentCourseAction("dc");
        } else if (command.equals("dw")) {
            studentWaitlistAction("dw");
        } else {
            System.out.println("Invalid Input");
        }
    }

    // MODIFIES: this, student
    // EFFECTS: add/drops student from course based on given command
    private void studentCourseAction(String action) {
        if (action.equals("c")) {
            if (!student.inCourse(selectedCourse) && selectedCourse.getSeats() > 0) {
                student.registerCourse(selectedCourse);
                System.out.println("Registered for Course!");
                displayCourseinformationStudent(selectedCourse);
            } else {
                if (student.inCourse(selectedCourse)) {
                    System.out.println("Already Registered!");
                } else {
                    System.out.println("No Seats!");
                }
            }
        }
        if (action.equals("dc")) {
            if (student.inCourse(selectedCourse)) {
                student.dropCourse(selectedCourse);
                System.out.println("Dropped Course!");
                displayCourseinformationStudent(selectedCourse);
            } else {
                System.out.println("Not yet in Course!");
            }
        }

    }

    // MODIFIES: this, student
    // EFFECTS: add/drops student from waitlist based on given command
    private void studentWaitlistAction(String action) {
        if (action.equals("w")) {
            if (!student.inWaitlist(selectedCourse) && selectedCourse.getWaitlistSeats() > 0) {
                student.registerWaitlist(selectedCourse);
                System.out.println("Registered for Course Waitlist!");
                displayCourseinformationStudent(selectedCourse);
            } else {
                if (student.inWaitlist(selectedCourse)) {
                    System.out.println("Already Registered!");
                } else {
                    System.out.println("No Seats!");
                }
            }
        }
        if (action.equals("dw")) {
            if (student.inWaitlist(selectedCourse)) {
                student.dropWaitlist(selectedCourse);
                System.out.println("Dropped Course Waitlist!");
                displayCourseinformationStudent(selectedCourse);
            } else {
                System.out.println("Not yet in Course Waitlist!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: runs specified admin action(modify course information)
    private void selectCourseCommandAdmin(String command) {
        if (command.equals("m")) {
            System.out.println("Enter new course name:");
            String name = input.next();
            System.out.println("Enter new course year level:");
            String yearLevel = input.next();
            System.out.println("Enter new course id:");
            String id = input.next();
            System.out.println("Enter new course subject");
            String subject = input.next();
            System.out.println("Enter new course description:");
            String courseDescription = input.next();
            System.out.println("Enter new number of course seats:");
            String seats = input.next();
            int seatsInt = Integer.parseInt(seats);
            System.out.println("Enter new number of course waitlist seats:");
            String waitlistSeats = input.next();
            int waitlistSeatsInt = Integer.parseInt(waitlistSeats);
            selectedCourse.updateCourse(name, id, subject, courseDescription, seatsInt, waitlistSeatsInt);
            System.out.println("Course Modified!");
            displayCourseinformationAdmin(selectedCourse);
        }
    }

    // REQUIRES: student.getRegisteredWaitlists().size() >= 0
    // EFFECTS: Views all of student's waitlisted courses
    private void viewAllWaitlistCourses() {
        if (student.getRegisteredWaitlists().size() == 0) {
            System.out.println("No registered Waitlists");
        } else {
            for (Course c : student.getRegisteredWaitlists()) {
                System.out.println("\n" + c.getName());
            }
        }

    }

    // REQUIRES: course.size() >= 0
    // EFFECTS: Views all available courses.
    private void viewAllCourses() {
        if (courses.getCourseNum() == 0) {
            System.out.println("No courses available");
            System.out.println("b -> Back");
        } else {
            for (Course c : courses.getCourses()) {
                System.out.println("\n" + c.getName());
            }
            System.out.println("\nEnter Course id to view more information about that course");
            System.out.println("\nb -> back");
        }
    }

    // EFFECTS: find course with given id
    // return true if successful, false otherwise
    // sets selectedCourse to found course if successful
    private Boolean findCourse(String command) {
        for (Course course : courses.getCourses()) {
            if (course.getID().equals(command)) {
                if (studentMode) {
                    displayCourseinformationStudent(course);
                } else {
                    displayCourseinformationAdmin(course);
                }
                selectedCourse = course;

                return true;
            }
        }
        return false;
    }

    // MODIFIES: Course
    // EFFECTS: create a new course
    private void createNewCourse() {
        System.out.println("Enter course name:");
        String name = input.next();
        System.out.println("Enter course id:");
        String id = input.next();
        System.out.println("Enter course subject");
        String subject = input.next();
        System.out.println("Enter course description:");
        String courseDescription = input.next();
        System.out.println("Enter number of course seats:");
        String seats = input.next();
        int seatsInt = Integer.parseInt(seats);
        System.out.println("Enter number of course waitlist seats:");
        String waitlistSeats = input.next();
        int waitlistSeatsInt = Integer.parseInt(waitlistSeats);
        Course course = new Course(name, id, subject, courseDescription, seatsInt, waitlistSeatsInt);
        courses.add(course);
        System.out.println("New Course Created!");
    }

    // EFFECTS: saves the student to file
    private void saveInformation() {
        try {
            jsonWriter.open();
            jsonWriterCourses.open();
            jsonWriter.write(student);
            jsonWriterCourses.write(courses);
            jsonWriter.close();
            jsonWriterCourses.close();
            System.out.println("Saved " + student.getName() + "'s' information to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, student
    // EFFECTS: loads student from file
    private void loadInformation() {
        try {
            courses = jsonReaderCourses.readCourses(courses);
            student = jsonReader.read(courses);
            System.out.println("Loaded " + student.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
