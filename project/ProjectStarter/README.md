# My CPSC 210 Project: Course Registration App


## Project Proposal
This application will be a course registering app. This application will have the following main features:
- allow students to view course information and register for courses if available
- allow students to view, all courese that they have registered for
- allow staff to put out new courses
- allow staff to modify course information/add or remove students from courses/view amount of students registered

This application will be used by students looking to register for specific classes, as well as administrative staff looking to put out new class offerings, modify current ones, or perform additional administrative duties. I chose to create this project because I, like many other ubc students, have had negatives experiences using Workday regarding course selection. Thus, I wanted to try and create my own course registration system to experience what it is like to build one. Additionally, I know family members who run/work with tutoring organizations overseas, and they often have trouble oganizing courses and registration by taking calls and noting registration down on paper. Such an app could be helpful as it would allow students to remotely register for courses without needing to call/email, as well as allow multiple students to register for courses simultaneously.


## User Stories
- As a user, I want to be able to add 1 or more courses to a list of registered courses
- As a user, I want to be able to add 1 or more courses to a list of waitlisted courses
- As a user, I want to be able to view individual course information
- As a user, I want to be able to mark a course as a favourited course(not implemented as designed for gui)
- As a user, I want to be able to drop courses and delete them from my list of registered courses
- As a user, I want to be able to drop courses and delete them from my list of waitlisted courses
- As a user, I want to be able to view the list of all registered courses
- As a user, I want to be able to view the list of all waitlisted courses
- As a user, I want to be able to put out new coures offererings 
- As a user, I want to be able to modify existing course information
- As a user, I want to be able to view all offered courses

## Phase 2 User Stories
- As a user, I want to be able to choose to save my registered and waitlisted courses
- As a user, I want to be able to choose to load my registered and waitlisted courses
- As a user, I want to be able to choose/load my new/modified courses that I've changed

## Phase 3 User Stories
- As a user, I want to be able to delete an existing course

## Instructions for Grader
- To generate the first required action related to the user story "add multiple x to y", you select student access -> click on view all courses button -> click on a specific course -> click on button labelled "register course" to register student for the course and add this course to student's registered courses

- To generate the second required action related to the user story "add multiple x to y", you select student access -> click on the button labelled "view all courses" -> click on a perticular course -> click on button labelled "drop course" to delete the course from student's registered courses.

- You can locate my visual component by running it the application. When it starts, it will display a loading screen splash art image for 5 seconds before running the home screen of the application

- You can save the state of my application by pressing the "save" button on the home screen(first screen when opening the application)

- You can reload the state of my application by pressing the "load" button on the home screen(first screen when opening the application)

## Phase 4 task 2
Mon Aug 05 22:51:51 PDT 2024
student registered for course: CPSC 110
Mon Aug 05 22:51:52 PDT 2024
student registered for waitlist: CPSC 210
MON Aug 05 22:51:53 PDT 2024
student dropped from course: CPSC 110
Mon Aug 05 22:51:52 PDT 2024
student dropped from waitlist: CPSC 210

## Phase 4 task 3
If I were to refactor my project, I would consider refactoring the saving and loading function for my console based application ui and gui application into it's own class, since they do contain degrees of coupling since the functions are identical between the two applications, except in the console based application, saving/loading is done through keyboard action and in the gui, saving/loading is down through buttons. Additionally, I would consider refactoring methods from the gui and console based application into it's own classes. For example, setting up and running each panel could be it's own class. This is because currently, everything happens inside of one class for my gui, which does not adhere well to the single responsibility principle. I could improve the design if I made the home menu a class, the student menu a class, etc. There also may still be code that is identical across multiple methods, which I could refactor out into one common method in order to reduce duplication of code. This would also reduce coupling in my program.