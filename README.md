# Course Registration App

A Java-based desktop application that simulates a university course registration system. This application provides an interactive Graphical User Interface (GUI) and a Console-based UI for managing student course registrations, built to streamline the class enrollment process.

## Features

- **Student Dashboard:** View all available courses, register for open classes, or join a waitlist if a class is full.
- **Course Management:** Students can drop courses they are currently registered for or waitlisted in.
- **Administrative Functions:** Administrators can create new course offerings, modify existing course information, and delete courses.
- **Data Persistence:** The application uses JSON for saving and loading the state of courses and student registrations, allowing users to pick up right where they left off.
- **Event Logging:** Tracks important system events (such as when a student registers or drops a course).

## Architecture

The application is structured logically and is divided into three main packages:

- **Model (`src/main/model/`)**: Contains the core business logic and entities, including `Course`, `Student`, `Courses` (collection manager), and event logging components (`Event`, `Eventlog`).
- **Persistence (`src/main/persistence/`)**: Handles data saving and loading using `JsonReader` and `JsonWriter` to serialize/deserialize objects to and from JSON files.
- **UI (`src/main/ui/`)**: Contains the user interfaces. `CourseRegistrationAppUI` provides the primary graphical interface (Java Swing), while `CourseRegistrationApp` provides a console-based alternative.

## Technologies Used

- **Java**: Core programming language.
- **Swing / AWT**: Used for building the Graphical User Interface (GUI).
- **JSON**: Used for data persistence.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher.

### Running the Application

1. Open the `course registration app` directory in your preferred Java IDE (like IntelliJ IDEA or Eclipse).
2. Locate the `Main.java` file in the `src/main/ui/` directory.
3. Run the `Main.main()` method to start the application.
4. A splash screen will display for a few seconds before the main home screen appears.

### Using the App

- **Saving & Loading:** On the home screen, you can use the "Save" and "Load" buttons to persist your data or retrieve a previous session.
- **Student Actions:** Navigate to "Student Access" to view all courses, register for a course, or drop a course.

## Project Background

This project was developed to address common frustrations with existing course registration systems by providing a streamlined, easy-to-use alternative. It is also designed to be adaptable for smaller educational organizations, such as tutoring centers, to help manage student registrations electronically instead of on paper.
