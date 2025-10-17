Cinema Ticket Management System
159.739 – Practical Software Engineering – Assignment 3

Massey University | Semester 2, 2025

1. Team Information
Name	Role	Student ID
Deepna John	Backend Development, Testing	[24011926]
Tasfia Mohtasim	GUI Design and Integration	[25021047]

GitHub Repository: https://github.com/Deepna-John/159739-A3-2025-Deepna-Tasfia

2. Project Overview

The Cinema Ticket Management System is a desktop-based software application developed using Java. The project demonstrates the application of object-oriented programming (OOP) concepts, the Model–View–Controller (MVC) design pattern, and good software engineering practices. It provides a structured solution for cinema ticket booking and movie management, incorporating authentication, data manipulation, and a graphical user interface built with Java Swing.

3. Responsibilities and Work Division
Team Member	Primary Responsibilities
Deepna	Developed model and controller classes, implemented file and factory utilities, created unit tests, and conducted code review.
Tasfia	Designed and implemented the graphical user interface (GUI), integrated the controller and model components, developed the authentication system, and implemented the manager and ticket seller functionalities.

4. System Requirements

Java Development Kit (JDK) version 11 or later

Apache Maven version 3.6 or later

A valid movies.txt file located in the project root directory

5. Installation and Execution

Step 1 – Clone the Repository

git clone https://github.com/yourusername/159739-A3-2025-Deepna-Tasfia.git
cd 159739-A3-2025-Deepna-Tasfia/cinemaapp


Step 2 – Ensure Data File Placement
The file movies.txt must be placed in the root directory of the project.

Step 3 – Build the Project

mvn clean compile


Step 4 – Execute Unit Tests

mvn test


Step 5 – Run the Application

mvn exec:java -Dexec.mainClass="edu.masseyuniversity.cinema.App"

6. User Credentials
Role	Username	Password
Ticket Seller	s1 / s2 / s3	identical to username
Manager	m1 / m2	identical to username
7. Key Features
General Features

Secure login system with role-based access

User-friendly interface designed in Java Swing

File export functionality for persistent data storage

Ticket Seller Features

View, search, and filter movies by category or title

Book tickets with automatic availability updates

Access restricted to non-management functions

Manager Features

Add, edit, or remove movies

Load movie data for modification

Full CRUD operations supported

8. Project Structure
cinemaapp/
├── src/
│   ├── main/java/edu/masseyuniversity/cinema/
│   │   ├── model/ (Movie and Staff hierarchies)
│   │   ├── controller/ (CinemaController, AuthenticationController)
│   │   ├── util/ (MovieFactory, MovieFileHandler)
│   │   ├── CinemaSystem.java (Main GUI)
│   │   └── App.java (Entry point)
│   └── test/java/... (JUnit tests)
├── movies.txt
├── pom.xml
└── README.md

9. Software Design and Methodology
9.1 Object-Oriented Programming Concepts

Encapsulation: All attributes are private with controlled access via getter and setter methods.

Inheritance: Abstract base classes Movie and Staff are extended by specific subclasses such as ActionMovie, ComedyMovie, Manager, and TicketSeller.

Polymorphism: Abstract methods are overridden in subclasses to provide specialized behavior.

Abstraction: Common attributes and methods are centralized within abstract classes to reduce redundancy.

9.2 Design Patterns

Factory Pattern: Implemented through MovieFactory to manage object creation for various movie categories.

Model–View–Controller (MVC):

Model: Represents movie and staff entities.

View: Developed using Swing for the GUI interface.

Controller: Handles user input and application logic integration.

10. Exception Handling

The application employs structured exception handling to ensure system robustness:

IOException for file operations

IllegalArgumentException for invalid or duplicate entries

IllegalStateException for unavailable tickets or missing data

Context-specific try–catch blocks with user-friendly error messages

11. Testing

Automated unit tests were developed using JUnit 5.

MovieTest.java: Validates functionality of movie subclasses

AuthenticationTest.java: Tests login and role authentication

CinemaControllerTest.java: Verifies CRUD operations

All tests passed successfully under the Maven build environment.

12. File Input Format

File: movies.txt
Each line represents a movie record in the following format:

Category, MovieID, Title, Director, Duration, Price, ShowTime, ExtraAttribute, AvailableTickets


Example:

Action,A001,Fast & Furious 9,Justin Lin,143,15.5,18:30,High,50
Romance,R001,Titanic,James Cameron,195,18.0,20:00,R13,50

13. Limitations

Data persistence is limited to text file storage.

Application supports single-user sessions only.

No database integration.

Must export manually to save session data permanently.

14. Future Work

Potential extensions include:

Database integration (e.g., MySQL, PostgreSQL)

Seat selection and payment functionality

Booking history and analytical reports

Email or notification features

Multi-user concurrency support

15. Compliance with Assignment Requirements

Demonstration of key OOP principles

Structured exception handling

File input and output management

Graphical User Interface using Java Swing

Automated testing with JUnit 5

Build and dependency management using Maven

GitHub version control with documented commits

Complete role-based functionality and export operations

16. Contact Information

Deepna John – deepnajohn4@gmail.com

Tasfia Mohtasim – mohtasimtoma@gmail.com

Last Updated: 17 October 2025
Course: 159.739 Practical Software Engineering
Institution: Massey University
