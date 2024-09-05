package ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Course;
import model.Courses;
import model.Event;
import model.Eventlog;
import model.Student;
import persistence.JsonReader;
import persistence.JsonWriter;

//Graphical interface of application
public class CourseRegistrationAppUI extends JFrame {
    private static final String JSON_STORE = "./data/student.json";
    private static final String JSON_STORE_COURSES = "./data/courses.json";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JsonWriter jsonWriter;
    private JsonWriter jsonWriterCourses;
    private JsonReader jsonReader;
    private JsonReader jsonReaderCourses;

    private Student student;
    private Courses courses;
    private Course selectedCourse;
    private CardLayout cardLayout;
    private JPanel parentPanel;

    private JFrame homeMenu; // project jframe
    private JPanel homeMenuPanel;

    private JPanel studentMenuPanel;
    private JPanel adminMenuPanel;

    private JButton studentAccessButton;
    private JButton adminAccessButton;
    private JButton save;
    private JButton load;

    private JButton backToHomeButton;
    private JButton backToHomeButton1;
    private JButton viewAllCoursesButton;
    private JButton viewAllCoursesAdminButton;
    private JButton backToAdminMenuButton;
    private JButton backToStudentMenuButton;
    private JButton addCourseButton;

    private JPanel viewAllCoursesMenuStudentPanel;
    private JPanel viewAllCoursesMenuAdminPanel;

    private JPanel viewRegisteredCoursePanel;
    private JPanel viewRegisteredWaitlistPanel;
    private JButton viewRegisteredCoursesButton;
    private JButton viewRegisteredWaitlistsButton;
    private JButton backToStudentMenuFromViewButton;
    private JButton backToStudentMenuFromViewButton1;

    private JLabel coursesStatusAdmin;
    private JLabel coursesStatus;

    private JPanel viewCourseInformationAdminPanel;
    private JButton backToAdminAllCoursesButton;
    private JButton deleteCourseButton;
    private JLabel courseNameLabel1;
    private JLabel courseSubjectLabel1;
    private JLabel courseDescriptionLabel1;
    private JLabel courseSeatsLabel1;
    private JLabel courseWaitlistLabel1;

    private JPanel viewCourseInformationMenuPanel;
    private JButton backToAllCoursesButton;
    private JButton registerCourseButton;
    private JButton registerWaitlistButton;
    private JButton dropCourseButton;
    private JButton dropWaitlistButton;
    private JLabel courseNameLabel;
    private JLabel courseSubjectLabel;
    private JLabel courseDescriptionLabel;
    private JLabel courseSeatsLabel;
    private JLabel courseWaitlistLabel;

    private JPanel addCourseMenu;
    private JButton createCourseButton;
    private JButton backToAdminFromCreateButton;
    private JTextField textBoxName;
    private JTextField textBoxID;
    private JTextField textBoxSubject;
    private JTextField textBoxDescription;
    private JTextField textBoxSeats;
    private JTextField textBoxWaitlist;

    // EFFECTS: initializes application
    public CourseRegistrationAppUI() {
        JPanel loading = runLoadingScreen();

        student = new Student("Jane Doe");
        courses = new Courses();
        Course course1 = new Course("CPSC 110", "110", "CPSC", "First year cpsc course", 100, 100);
        courses.add(course1);
        courses.add(new Course("CPSC 210", "210", "CPSC", "Second year cpsc course", 100, 100));
        selectedCourse = null;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriterCourses = new JsonWriter(JSON_STORE_COURSES);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReaderCourses = new JsonReader(JSON_STORE_COURSES);

        // set up home frame
        homeMenu = new JFrame();
        printLogOnClose();
        parentPanel = new JPanel();
        cardLayout = new CardLayout();
        parentPanel.setLayout(cardLayout);

        setUpPanels();

        // adds all panels to cardlayout
        homeMenu.setVisible(true);
        parentPanel.add(loading, "loading");
        addPanels();
        homeMenu.add(parentPanel);
        cardLayout.show(parentPanel, "loading");
        timeSplashScreen();

    }

    //MODIFIES: this
    //EFFECTS: prints log when application closes
    private void printLogOnClose() {
        homeMenu.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printLog(Eventlog.getInstance());
                homeMenu.setVisible(false);

            }
        });
    }

    //MODIFIES: this
    //EFFECTS: runs loading screen
    private JPanel runLoadingScreen() {
        JPanel loading = new JPanel();
        String sep = System.getProperty("file.separator");
        // Image comes from https://www.vecteezy.com/free-vector/loading
        JLabel background = new JLabel(new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "loading.jpg"));
        loading.add(background);
        return loading;
    }

    // EFFECTS: sets up all menu panels
    private void setUpPanels() {
        // sets up home menu panel
        setUpHomeMenu();

        // set up buttons in student view
        setUpStudentViewMenu();

        // set up button in view all courses view
        setUpAllCourseViewStudentMenu();

        // sets up admin view all courses panel.
        setUpAllCourseViewAdminMenu();

        // Set up Course information frame
        setUpViewCourseInformation();

        // sets up view course infomration for admin
        setUpViewCourseInformationAdmin();

        // sets up view all courses panel
        setUpViewAllCourses();

        // sets up view all waitlsits panel
        setUpViewAllWaitlists();

        // sets up admin access panel
        setUpAdminAccessMenu();

        // sets up add course panel
        setUpAddCourseMenu();
    }

    // MODIFIES: this
    // EFFECTS: starts timer to run home menu after splash screen
    private void timeSplashScreen() {
        Timer timer = new Timer(5000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runHomeMenu();
            }

        });
        timer.setRepeats(false);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: adds all menu panels to parent panel
    private void addPanels() {
        parentPanel.add(homeMenuPanel, "Home");
        parentPanel.add(studentMenuPanel, "Student");
        parentPanel.add(adminMenuPanel, "Admin");
        parentPanel.add(viewAllCoursesMenuAdminPanel, "View All Courses Admin");
        parentPanel.add(viewAllCoursesMenuStudentPanel, "View All Courses");
        parentPanel.add(viewCourseInformationMenuPanel, "Course Information");
        parentPanel.add(viewCourseInformationAdminPanel, "Course Information Admin");
        parentPanel.add(viewRegisteredCoursePanel, "Registered Courses");
        parentPanel.add(viewRegisteredWaitlistPanel, "Registered Waitlists");
        parentPanel.add(addCourseMenu, "Add Course");
    }

    // MODIFIES: this
    // EFFECTS: Sets up menu to allow user to create a new course
    private void setUpAddCourseMenu() {
        addCourseMenu = new JPanel();
        addCourseMenu.setLayout(new BoxLayout(addCourseMenu, BoxLayout.Y_AXIS));
        createCourseButton = new JButton("Create New Course");
        createCourseButton.setPreferredSize(new Dimension(200, 75));
        createNewCourse();
        backToAdminFromCreateButton = new JButton("Back");
        backToAdminFromCreateButton.setPreferredSize(new Dimension(200, 75));
        switchViewsAdmin(backToAdminFromCreateButton);
        setUpTextBoxes();
        addTextBoxToCourseMenu();
    }

    //MODIfIES: this
    //EFFECTS: allows user to create a new course based on given input
    private void createNewCourse() {
        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textBoxName.getText();
                String id = textBoxName.getText();
                String subject = textBoxSubject.getText();
                String description = textBoxDescription.getText();
                String seats = textBoxSeats.getText();
                String waitlists = textBoxWaitlist.getText();
                Course course = new Course(name, id, subject, description, Integer.parseInt(seats),
                        Integer.parseInt(waitlists));
                courses.add(course);
                showMessageDialog(null, "New Course Created!");
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: adds text Boxes to menu to add courses
    private void addTextBoxToCourseMenu() {
        addCourseMenu.add(textBoxName);
        addCourseMenu.add(textBoxID);
        addCourseMenu.add(textBoxSubject);
        addCourseMenu.add(textBoxDescription);
        addCourseMenu.add(textBoxSeats);
        addCourseMenu.add(textBoxWaitlist);
        addCourseMenu.add(createCourseButton);
        addCourseMenu.add(backToAdminFromCreateButton);
    }

    //MODIFIES: this
    // EFFECTS: initializes text boxes for user input
    private void setUpTextBoxes() {
        textBoxName = new JTextField("Enter Course Name:", 300);
        textBoxID = new JTextField("Enter Course Id: ", 300);
        textBoxDescription = new JTextField("Enter Course Description:", 300);
        textBoxSubject = new JTextField("Enter Course Subject: ", 300);
        textBoxSeats = new JTextField("Enter Number of Seats", 300);
        textBoxWaitlist = new JTextField("Enter Number of Waitlist Seats", 300);
    }

    // MODIFIES: this
    // EFFECTS: sets up admin menu
    private void setUpAdminAccessMenu() {
        adminMenuPanel = new JPanel();
        adminMenuPanel.setLayout(new BoxLayout(adminMenuPanel, BoxLayout.Y_AXIS));
        backToHomeButton1 = new JButton("Back");
        backToHomeButton1.setPreferredSize(new Dimension(200, 75));
        viewAllCoursesAdminButton = new JButton("View all courses");
        viewAllCoursesAdminButton.setPreferredSize(new Dimension(200, 75));
        addCourseButton = new JButton("Add Course");
        addCourseButton.setPreferredSize(new Dimension(200, 75));
        adminMenuPanel.add(new JLabel("Admin Access"));
        switchViewsAdmin(addCourseButton);
        switchViewsStudent(backToHomeButton1);
        switchViewsAdmin(viewAllCoursesAdminButton);
        adminMenuPanel.add(viewAllCoursesAdminButton);
        adminMenuPanel.add(addCourseButton);
        adminMenuPanel.add(backToHomeButton1);
    }

    // MODIFIES: this
    // EFFECTS: sets up viewing all registered waitlists for student
    private void setUpViewAllWaitlists() {
        viewRegisteredWaitlistPanel = new JPanel();
        viewRegisteredWaitlistPanel.setLayout(new BoxLayout(viewRegisteredWaitlistPanel, BoxLayout.Y_AXIS));
        backToStudentMenuFromViewButton1 = new JButton("Back");
        backToStudentMenuFromViewButton1.setPreferredSize(new Dimension(200, 75));
        viewRegisteredWaitlistPanel.add(backToStudentMenuFromViewButton1);
    }

    // MODIFIES: this
    // EFFECTS: sets up viewing all registered courses for student
    private void setUpViewAllCourses() {
        viewRegisteredCoursePanel = new JPanel();
        viewRegisteredCoursePanel.setLayout(new BoxLayout(viewRegisteredCoursePanel, BoxLayout.Y_AXIS));
        backToStudentMenuFromViewButton = new JButton("Back");
        backToStudentMenuFromViewButton.setPreferredSize(new Dimension(200, 75));
        viewRegisteredCoursePanel.add(backToStudentMenuFromViewButton);
    }

    // MODIFIES: this
    // EFFECTs: sets up viewing a course's information for admin
    private void setUpViewCourseInformationAdmin() {
        viewCourseInformationAdminPanel = new JPanel();
        viewCourseInformationAdminPanel.setLayout(new BoxLayout(viewCourseInformationAdminPanel, BoxLayout.Y_AXIS));
        backToAdminAllCoursesButton = new JButton("Back");
        switchViewsAdmin(backToAdminAllCoursesButton);
        deleteCourseButton = new JButton("Delete Course");
        courseNameLabel1 = new JLabel("");
        courseDescriptionLabel1 = new JLabel("");
        courseSubjectLabel1 = new JLabel("");
        courseSeatsLabel1 = new JLabel("");
        courseWaitlistLabel1 = new JLabel("");
        viewCourseInformationAdminPanel.add(courseNameLabel1);
        viewCourseInformationAdminPanel.add(courseSubjectLabel1);
        viewCourseInformationAdminPanel.add(courseDescriptionLabel1);
        viewCourseInformationAdminPanel.add(courseSeatsLabel1);
        viewCourseInformationAdminPanel.add(courseWaitlistLabel1);
        viewCourseInformationAdminPanel.add(deleteCourseButton);
        viewCourseInformationAdminPanel.add(backToAdminAllCoursesButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up viewing course information for student
    private void setUpViewCourseInformation() {
        viewCourseInformationMenuPanel = new JPanel();
        viewCourseInformationMenuPanel.setLayout(new BoxLayout(viewCourseInformationMenuPanel, BoxLayout.Y_AXIS));
        backToAllCoursesButton = new JButton("Back To All Course View");
        courseNameLabel = new JLabel("");
        courseDescriptionLabel = new JLabel("");
        courseSubjectLabel = new JLabel("");
        courseSeatsLabel = new JLabel("");
        courseWaitlistLabel = new JLabel("");
        registerCourseButton = new JButton("Register Course");
        registerWaitlistButton = new JButton("Register Waitlist");
        dropCourseButton = new JButton("Drop Course");
        dropWaitlistButton = new JButton("Drop Waitlist");
        studentAction(registerCourseButton);
        studentAction(registerWaitlistButton);
        studentAction(dropCourseButton);
        studentAction(dropWaitlistButton);
        addLabelsButtons();
    }

    // MODIFIES: this
    // EFFECTS: adds labels and buttons to viewing course information panel
    private void addLabelsButtons() {
        viewCourseInformationMenuPanel.add(courseNameLabel);
        viewCourseInformationMenuPanel.add(courseSubjectLabel);
        viewCourseInformationMenuPanel.add(courseDescriptionLabel);
        viewCourseInformationMenuPanel.add(courseSeatsLabel);
        viewCourseInformationMenuPanel.add(courseWaitlistLabel);
        viewCourseInformationMenuPanel.add(registerCourseButton);
        viewCourseInformationMenuPanel.add(registerWaitlistButton);
        viewCourseInformationMenuPanel.add(dropCourseButton);
        viewCourseInformationMenuPanel.add(dropWaitlistButton);
        viewCourseInformationMenuPanel.add(backToAllCoursesButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up viewing all courses for admin
    private void setUpAllCourseViewAdminMenu() {
        viewAllCoursesMenuAdminPanel = new JPanel();
        viewAllCoursesMenuAdminPanel.setLayout(new BoxLayout(viewAllCoursesMenuAdminPanel, BoxLayout.Y_AXIS));
        coursesStatusAdmin = new JLabel("All Available Courses: Click On Course To View");
        backToAdminMenuButton = new JButton("Back to admin menu");
        backToAdminMenuButton.setPreferredSize(new Dimension(200, 75));
        viewAllCoursesMenuAdminPanel.add(coursesStatusAdmin);
        viewAllCoursesMenuAdminPanel.add(backToAdminMenuButton);
        switchViewsAdmin(backToAdminMenuButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up viewing all courses for student
    private void setUpAllCourseViewStudentMenu() {
        viewAllCoursesMenuStudentPanel = new JPanel();
        viewAllCoursesMenuStudentPanel.setLayout(new BoxLayout(viewAllCoursesMenuStudentPanel, BoxLayout.Y_AXIS));
        backToStudentMenuButton = new JButton("Back to Student Menu");
        backToStudentMenuButton.setPreferredSize(new Dimension(200, 75));
        coursesStatus = new JLabel("All Available Courses: Click On Course To View");
        viewAllCoursesMenuStudentPanel.add(coursesStatus);
        viewAllCoursesMenuStudentPanel.add(backToStudentMenuButton);
    }

    // MODIFIES: this
    // EFFECTs: sets up student view menu
    private void setUpStudentViewMenu() {
        studentMenuPanel = new JPanel();
        studentMenuPanel.setLayout(new BoxLayout(studentMenuPanel, BoxLayout.Y_AXIS));
        viewRegisteredCoursesButton = new JButton("View Registered Courses");
        viewRegisteredWaitlistsButton = new JButton("View Registered Waitlists");
        viewAllCoursesButton = new JButton("View All Courses");
        backToHomeButton = new JButton("Back");
        viewAllCoursesButton.setPreferredSize(new Dimension(200, 75));
        backToHomeButton.setPreferredSize(new Dimension(200, 75));
        switchViewsStudent(viewRegisteredCoursesButton);
        switchViewsStudent(viewRegisteredWaitlistsButton);
        studentMenuPanel.add(new JLabel("Student Access"));
        studentMenuPanel.add(viewAllCoursesButton);
        studentMenuPanel.add(viewRegisteredCoursesButton);
        studentMenuPanel.add(viewRegisteredWaitlistsButton);
        studentMenuPanel.add(backToHomeButton);
    }

    // MODIFIES: this
    // EFFECTs: sets up home menu
    private void setUpHomeMenu() {
        homeMenuPanel = new JPanel();
        homeMenuPanel.setLayout(new BoxLayout(homeMenuPanel, BoxLayout.Y_AXIS));
        homeMenuPanel.add(new JLabel("Home"));
        studentAccessButton = new JButton("Student Access");
        studentAccessButton.setPreferredSize(new Dimension(200, 75));
        adminAccessButton = new JButton("Admin Access");
        adminAccessButton.setPreferredSize(new Dimension(200, 75));
        setUpSaveButton();
        setUpLoadButton();
        switchViewsStudent(studentAccessButton);
        switchViewsAdmin(adminAccessButton);
        homeMenuPanel.add(adminAccessButton);
        homeMenuPanel.add(studentAccessButton);
        homeMenuPanel.add(save);
        homeMenuPanel.add(load);

        homeMenu.setSize(WIDTH, HEIGHT);
        homeMenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTs: sets up load information button
    private void setUpLoadButton() {
        load = new JButton("Load");
        load.setPreferredSize(new Dimension(200, 75));
        load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showMessageDialog(null, "Information Loaded!");
                try {
                    // have to make new courses and student because app comes with default courses.
                    // if not then no need
                    courses = new Courses();
                    courses = jsonReaderCourses.readCourses(courses);
                    student = new Student("Jane Doe");
                    student = jsonReader.read(courses);
                    System.out.println("Loaded " + student.getName() + " from " + JSON_STORE);
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: sets up save information button
    private void setUpSaveButton() {
        save = new JButton("Save");
        save.setPreferredSize(new Dimension(200, 75));
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showMessageDialog(null, "Information Saved!");
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

        });
    }

    // MODIFIES: this
    // EFFECTS: gives buttons on student's viewing course information page action
    // listeners
    public void studentAction(JButton button) {
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.equals(registerCourseButton)) {
                    student.registerCourse(selectedCourse);
                    updateCourseInformation(selectedCourse);
                } else if (button.equals(registerWaitlistButton)) {
                    student.registerWaitlist(selectedCourse);
                    updateCourseInformation(selectedCourse);
                } else if (button.equals(dropCourseButton)) {
                    student.dropCourse(selectedCourse);
                    updateCourseInformation(selectedCourse);
                } else if (button.equals(dropWaitlistButton)) {
                    student.dropWaitlist(selectedCourse);
                    updateCourseInformation(selectedCourse);
                }
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: gives buttons acessed by student action listeners to swap screens
    public void switchViewsStudent(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (button.equals(studentAccessButton)) {
                    runStudentMenu();
                } else if (button.equals(viewAllCoursesButton)) {
                    runViewAllCoursesMenu();
                } else if (button.equals(backToHomeButton) || button.equals(backToHomeButton1)) {
                    runHomeMenu();
                } else if (button.equals(backToAllCoursesButton)) {
                    selectedCourse = null;
                    runViewAllCoursesMenu();
                } else if (button.equals(viewRegisteredCoursesButton)) {
                    runViewRegisteredCourses();
                } else if (button.equals(viewRegisteredWaitlistsButton)) {
                    runViewRegisteredWaitlists();
                } else if (button.equals(backToStudentMenuFromViewButton)
                        || button.equals(backToStudentMenuFromViewButton1)
                        || button.equals(backToStudentMenuButton)) {
                    runStudentMenu();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: gives buttons acessed by admin action listeners to swap screens
    public void switchViewsAdmin(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (button.equals(adminAccessButton) || button.equals(backToAdminMenuButton)
                        || button.equals(backToAdminFromCreateButton)) {
                    runAdminMenu();
                } else if (button.equals(viewAllCoursesAdminButton) || button.equals(backToAdminAllCoursesButton)) {
                    runViewAllCoursesAdminMenu();
                } else if (button.equals(addCourseButton)) {
                    runAddCourse();
                }

            }
        });
    }

    // EFFECTS: run home menu
    public void runHomeMenu() {
        cardLayout.show(parentPanel, "Home");
        switchViewsStudent(studentAccessButton);

    }

    // EFFECTS: run student view mennu
    public void runStudentMenu() {
        cardLayout.show(parentPanel, "Student");
        switchViewsStudent(backToHomeButton);
        switchViewsStudent(viewAllCoursesButton);

    }

    // EFFECTS: run admin menu
    public void runAdminMenu() {
        cardLayout.show(parentPanel, "Admin");

    }

    // MODIFIES: this
    // EFFECTS: run view all courses menu
    public void runViewAllCoursesMenu() {
        cardLayout.show(parentPanel, "View All Courses");
        deleteButtons(viewAllCoursesMenuStudentPanel);
        if (courses.getCourses().isEmpty()) {
            coursesStatus.setText("No Available Courses");
        } else {
            coursesStatus.setText("All Available Courses: Click On Course To View");
            for (Course c : courses.getCourses()) {
                JButton button = new JButton(c.getName());
                viewAllCoursesMenuStudentPanel.add(button);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedCourse = c;
                        runViewCourseInformation(c);
                    }

                });
            }
        }
        backToStudentMenuButton = new JButton("Back to Student Menu");
        backToStudentMenuButton.setPreferredSize(new Dimension(200, 75));
        viewAllCoursesMenuStudentPanel.add(backToStudentMenuButton);
        switchViewsStudent(backToStudentMenuButton);

    }

    // MODIFIES: this
    // EFFECTS: deletes all buttons from given panel
    private void deleteButtons(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                panel.remove(c);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: run viewing all courses for admin and updates page based on amount
    // of courses
    public void runViewAllCoursesAdminMenu() {
        cardLayout.show(parentPanel, "View All Courses Admin");
        deleteButtons(viewAllCoursesMenuAdminPanel);
        if (courses.getCourses().isEmpty()) {
            coursesStatusAdmin.setText("No Courses Available");
        } else {
            coursesStatusAdmin.setText("All Available Courses: Click On Course To View");
            for (Course c : courses.getCourses()) {
                JButton button = new JButton(c.getName());
                viewAllCoursesMenuAdminPanel.add(button);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedCourse = c;
                        runViewCourseInformationAdmin(c);
                    }

                });
            }
        }

        backToAdminMenuButton = new JButton("Back to Admin Menu");
        backToAdminMenuButton.setPreferredSize(new Dimension(200, 75));
        viewAllCoursesMenuAdminPanel.add(backToAdminMenuButton);
        switchViewsAdmin(backToAdminMenuButton);

    }

    // EFFECTS: run viewing course information for student
    // updates course information
    // gives back button action listener
    public void runViewCourseInformation(Course course) {
        cardLayout.show(parentPanel, "Course Information");

        updateCourseInformation(course);

        switchViewsStudent(backToAllCoursesButton);

    }

    // MODIFIES: this
    // EFFECTS: runs view course information page for admin user
    public void runViewCourseInformationAdmin(Course course) {
        cardLayout.show(parentPanel, "Course Information Admin");
        deleteCourseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                courses.getCourses().remove(course);
                if (student.getRegisteredCourses().contains(course)) {
                    student.getRegisteredCourses().remove(course);
                }
                if (student.getRegisteredWaitlists().contains(course)) {
                    student.getRegisteredWaitlists().remove(course);
                }
                runViewAllCoursesAdminMenu();
            }

        });
        updateCourseInformationAdmin(course);

    }

    // MODIFIES: this
    // EFFECTS: updates labels for course information for given course as student
    private void updateCourseInformation(Course course) {
        courseNameLabel.setText("Course Name: " + course.getName());
        courseDescriptionLabel.setText("Course Description: " + course.getDescription());
        courseSubjectLabel.setText("Course Subject: " + course.getSubject());
        courseSeatsLabel.setText("Seats Remaining: " + Integer.toString(course.getSeats()));
        courseWaitlistLabel.setText("Waitlist Seats Remaining: " + Integer.toString(course.getWaitlistSeats()));
    }

    // MODIFIES: this
    // EFFECTS: updates labels for course information for given course as admin
    private void updateCourseInformationAdmin(Course course) {
        courseNameLabel1.setText("Course Name: " + course.getName());
        courseDescriptionLabel1.setText("Course Description: " + course.getDescription());
        courseSubjectLabel1.setText("Course Subject: " + course.getSubject());
        courseSeatsLabel1.setText("Seats Remaining: " + Integer.toString(course.getSeats()));
        courseWaitlistLabel1.setText("Waitlist Seats Remaining: " + Integer.toString(course.getWaitlistSeats()));
    }

    // MODIFIES: this
    // EFFECTS: runs view registered courses
    public void runViewRegisteredCourses() {
        for (Component c : viewRegisteredCoursePanel.getComponents()) {
            if (c instanceof JLabel) {
                viewRegisteredCoursePanel.remove(c);
            }
        }
        if (student.getRegisteredCourses().isEmpty()) {
            viewRegisteredCoursePanel.add(new JLabel("No Registered Courses"));
        } else {
            for (Course c : student.getRegisteredCourses()) {
                viewRegisteredCoursePanel.add(new JLabel(c.getName()));
            }
        }
        switchViewsStudent(backToStudentMenuFromViewButton);
        cardLayout.show(parentPanel, "Registered Courses");

    }

    // MODIFIES: this
    // EFFECTS: runs view registered waitlists
    public void runViewRegisteredWaitlists() {
        for (Component c : viewRegisteredWaitlistPanel.getComponents()) {
            if (c instanceof JLabel) {
                viewRegisteredWaitlistPanel.remove(c);
            }
        }
        if (student.getRegisteredWaitlists().isEmpty()) {
            viewRegisteredWaitlistPanel.add(new JLabel("No Registered Courses"));
        } else {
            for (Course c : student.getRegisteredWaitlists()) {
                viewRegisteredWaitlistPanel.add(new JLabel(c.getName()));
            }
        }
        switchViewsStudent(backToStudentMenuFromViewButton1);
        cardLayout.show(parentPanel, "Registered Waitlists");

    }

    // MODIFIES: this
    // EFFECTS: runs page to allow admin user to create new course
    public void runAddCourse() {
        cardLayout.show(parentPanel, "Add Course");
        // createCourseButton.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // String name = textBoxName.getText();
        // String id = textBoxName.getText();
        // String subject = textBoxSubject.getText();
        // String description = textBoxDescription.getText();
        // String seats = textBoxSeats.getText();
        // String waitlists = textBoxWaitlist.getText();
        // Course course = new Course(name, id, subject, description,
        // Integer.parseInt(seats),
        // Integer.parseInt(waitlists));
        // courses.add(course);
        // showMessageDialog(null, "New Course Created!");
        // }

        // });
    }

    // EFFECTS: print log events
    public void printLog(Eventlog el) {
        for (Event next : el) {
            System.out.println("" + next.toString());
        }

    }

}
