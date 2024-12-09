
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

class CourseEnrollmentSystem {

    static Scanner sc = new Scanner(System.in);
    static LL<User> users = new LL<>();
    static LL<Student> students = new LL<>();
    static LL<Course> courses = new LL<>();
    static LL<Instructor> instructors = new LL<>();
    static Stack1<Transaction> transactions = new Stack1<>();
    static DatabaseManager db = new DatabaseManager();
    static Admin admin = new Admin(001, "001@MeetAD", "Meet", "meet@mail.com", "9876543210");

    public static void main(String[] args) {
        try {
            users.add(admin);
            ResultSet rs = db.checkAdmin();
            boolean b = rs.next();
            if (!b) {
                db.addUser(admin);
            }
        } catch (SQLException ex) {
            System.out.println("Main: " + ex.getMessage());
        }
        start();
    }

    public static void start() {
        while (true) {
            refreshLists();
            System.out.println();
            System.out.println("--------Welcome to Course Enrollment System------------");
            System.out.println();
            System.out.println("Press 1 for Login");
            System.out.println("Press 2 for Sign Up (Only for students)");
            System.out.println("Press 3 for Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("--------Welcome to Login Interface------------");
                        System.out.println();
                        System.out.print("Enter your userID: ");
                        int userID = sc.nextInt();
                        if (CheckData.checkUserID(userID)) {
                            System.out.print("Enter your password: ");
                            sc.nextLine();
                            String pass = sc.nextLine();
                            if (CheckData.checkUserPass(userID, pass)) {
                                System.out.println();
                                System.out.println("--------SucessFully Login-----------");
                                switch (CheckData.checkUserRole(userID)) {
                                    case "Student" -> {
                                        Student s = getStudent(userID);
                                        System.out.println("Welcome " + s.getName());
                                        s.showOptions();
                                    }
                                    case "Instructor" -> {
                                        Instructor i = getInstructor(userID);
                                        i.showOptions();
                                    }
                                    case "Admin" -> {
                                        admin.showOptions();
                                    }
                                }
                            } else {
                                System.out.println("Incorrect Password! Try again.");
                            }
                        } else {
                            System.out.println("No such user! Try Signing up.");
                        }
                    }

                    case 2 -> {
                        System.out.println();
                        System.out.println("--------Welcome to Sign Up Interface------------");
                        System.out.println();
                        System.out.print("Enter name: ");
                        sc.nextLine();
                        String name = sc.nextLine();
                        System.out.print("Enter address: ");
                        String address = sc.nextLine();
                        String email;
                        while (true) {
                            System.out.print("Enter email: ");
                            email = sc.nextLine();
                            if (CheckData.checkEmail(email)) {
                                break;
                            }
                        }
                        String contactNumber;
                        while (true) {
                            System.out.print("Enter contact number:");
                            contactNumber = sc.nextLine();
                            if (CheckData.checkContactNumber(contactNumber)) {
                                break;
                            }
                        }
                        double marks;
                        while (true) {
                            System.out.print("Enter marks: ");
                            try {
                                marks = sc.nextDouble();
                                if (CheckData.checkMarks(marks)) {
                                    break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter a number.");
                                sc.nextLine();
                            }
                        }
                        String[] splitted = name.split("\\s+");
                        int id = db.getUserID() + 1;
                        Student s = new Student(id, id + "@" + splitted[0], name, email, contactNumber, address, marks,
                                "Not Enrolled", null, false, 0, 0);
                        db.addStudent(s);
                        db.addUser(s);
                        users.add(s);
                        students.add(s);
                        System.out.println("User added successfully!");
                        System.out.println("User credentials: userID:" + s.getID() + ", Password: " + s.getPassword());
                    }

                    case 3 -> {
                        System.out.println("Exiting the Online Course Enrollment System. Thank you for using the system!");
                        return;
                    }
                    default ->
                        System.out.println("Invalid option! Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input! Enter integer value only.");
                sc.nextLine();
            }
        }
    }
    
    public static void refreshLists() {
        getUserData();
        getCourseData();
        getStudentData();
        getInstructorData();
        getTransactionData();
        getMentorshipData();
    }

    public static void getUserData() {
        try {
            users.clear();
            ResultSet rs = db.fetchUsers();
            boolean b = rs.next();
            if (b) {
                while (b) {
                    users.add(new User(rs.getInt("userID"), rs.getString("password"), rs.getString("name"), rs.getString("email"), rs.getString("contactNumber"), rs.getString("role")));
                    b = rs.next();
                }
            } else {
                System.out.println("No users found!");
            }
        } catch (SQLException e) {
            System.out.println("getUserData: " + e.getMessage());
        } catch (NullPointerException e) {

        }
    }

    public static void getStudentData() {
        try {
            students.clear();
            ResultSet rs = db.fetchStudentData();
            while (rs.next()) {
                Student s = (new Student(rs.getInt("userID"), rs.getString("password"), rs.getString("name"), rs.getString("email"), rs.getString("contactNumber"), rs.getString("address"), rs.getDouble("marks"), rs.getString("enrollStatus"), rs.getString("enrolledCourseCode"), rs.getBoolean("paymentStatus"), rs.getInt("mentorID"), rs.getInt("transactionID")));
                students.add(s);
            }
        } catch (SQLException e) {
            System.out.println("getStudentData: " + e.getMessage());
        } catch (NullPointerException e) {

        }
    }

    public static void getInstructorData() {
        try {
            instructors.clear();
            ResultSet rs = db.fetchInstructorData();
            while (rs.next()) {
                Instructor i = (new Instructor(rs.getInt("userID"), rs.getString("password"), rs.getString("name"), rs.getString("email"), rs.getString("contactNumber"), rs.getString("teachingCourseCode"), rs.getInt("teachingExperience"), rs.getString("specialization")));
                instructors.add(i);
            }
        } catch (SQLException e) {
            System.out.println("getInstructorData: " + e.getMessage());
        } catch (NullPointerException e) {

        }
    }

    public static void getCourseData() {
        try {
            courses.clear();
            ResultSet rs = db.fetchCourses();
            while (rs.next()) {
                courses.add(new Course(rs.getString("courseCode"), rs.getString("courseName"), rs.getDouble("courseFee"), rs.getInt("maxCapacity")));
            }
        } catch (SQLException e) {
            System.out.println("getCourseData: " + e.getMessage());
        } catch (NullPointerException e) {

        }
    }

    public static void getTransactionData() {
        try {
            transactions.empty();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ResultSet rs = db.fetchTransactions();
            while (rs.next()) {
                transactions.PUSH(new Transaction(rs.getInt("transactionID"), rs.getInt("studentID"), rs.getDouble("amount"), formatter.parse(rs.getString("date")), rs.getString("paymentMethod"), rs.getString("number")));
            }
        } catch (SQLException e) {
            System.out.println("getTransactionData: " + e.getMessage());
        } catch (NullPointerException e) {

        } catch (ParseException e) {
            System.out.println("Parsing: " + e.getMessage());
        }
    }

    public static void getMentorshipData() {
        try {
            LL<Instructor>.Node<Instructor> instructor = instructors.Head;
            while (instructor != null) {
                instructor.data.mentorshipList.clear();
                ResultSet rs = db.fetchMentorshipData(instructor.data.getID());
                while (rs.next()) {
                    instructor.data.mentorshipList.add(getStudent(rs.getInt("studentID")));
                }
                instructor = instructor.next;
            }
        } catch (SQLException e) {
            System.out.println("getMentorshipData: " + e.getMessage());
        } catch (NullPointerException e) {

        }
    }

    public static Course getCourse(String courseCode) {
        LL<Course>.Node<Course> course = courses.Head;
        while (course != null) {
            if (course.data.getCourseCode().equals(courseCode)) {
                return course.data;
            }
            course = course.next;
        }
        return null;
    }

    public static Course getCourseByCourseName(String cName) {
        LL<Course>.Node<Course> course = courses.Head;
        while (course != null) {
            if (course.data.getCourseCode().equalsIgnoreCase(cName)) {
                return course.data;
            }
            course = course.next;
        }
        return null;
    }

    public static Student getStudent(int studentID) {
        LL<Student>.Node<Student> student = students.Head;
        while (student != null) {
            if (student.data.getID() == studentID) {
                return student.data;
            }
            student = student.next;
        }
        return null;
    }

    public static User getUser(int userID) {
        LL<User>.Node<User> user = users.Head;
        while (user != null) {
            if (user.data.getID() == userID) {
                return user.data;
            }
            user = user.next;
        }
        return null;
    }

    public static Instructor getInstructor(int instructorID) {
        LL<Instructor>.Node<Instructor> instructor = instructors.Head;
        while (instructor != null) {
            if (instructor.data.getID() == instructorID) {
                return instructor.data;
            }
            instructor = instructor.next;
        }
        return null;
    }

    public static void viewCourses() {
        if (courses.Head != null) {
            System.out.println();
            System.out.println("---------View Courses---------");
            System.out.println();
            LL<Course>.Node<Course> course = courses.Head;
            while (course != null) {
                System.out.println("Course Code: "+course.data.getCourseCode());
                System.out.println("Course Name: "+ course.data.getCourseName());
                System.out.println("Coourse Fee: "+ course.data.getCourseFee()); 
                System.out.println("Max Capacity: " + course.data.getMaxCapacity());
                System.out.println();
                System.out.println("------------------------------");
                course = course.next;
            }
        } else {
            System.out.println("No courses available");
        }
    }

    public static void generateLists(Course course) {
        BufferedWriter bwEnrolled;
        try {
            File enroll = course.getEnrolledStudentsFile();
            File confirm = course.getConfirmedListFile();
            File waiting = course.getWaitingListFile();
            File rejected = course.getRejectedListFile();
            bwEnrolled = new BufferedWriter(new FileWriter(enroll));
            LL<Student>.Node<Student> student = students.Head;
            bwEnrolled.write("Course ID: " + course.getCourseCode());
            bwEnrolled.newLine();
            bwEnrolled.newLine();
            bwEnrolled.write("---------------------------------------------");
            while (student != null) {
                if (student.data.getEnrolledCourseCode() != null && student.data.getEnrolledCourseCode().equals(course.getCourseCode())) {
                    bwEnrolled.newLine();
                    bwEnrolled.write("Student ID: "+ student.data.getID());
                    bwEnrolled.newLine();
                    bwEnrolled.write("Student Name: "+ student.data.getName());
                    bwEnrolled.newLine();
                    bwEnrolled.write("Student Marks: "+ student.data.getMarks());
                    bwEnrolled.newLine();
                    bwEnrolled.write("---------------------------------------------");
                    bwEnrolled.newLine();
                    bwEnrolled.flush();
                }
                student = student.next;
            }
            bwEnrolled.close();
            ResultSet rs = db.checkList(course.getCourseCode());
            rs.next();
            if (rs.getInt(1) == 0 || rs.getInt(1) < course.getMaxCapacity()) {
                if (db.fetchStudentsInCourse(course) != null) {
                    int i = 1;
                    BufferedWriter bwWaiting;
                    BufferedWriter bwRejected;
                    try (BufferedWriter bwConfirmed = new BufferedWriter(new FileWriter(confirm))) {
                        bwConfirmed.write("Course ID: " + course.getCourseCode());
                        bwConfirmed.newLine();
                        bwConfirmed.newLine();
                        bwConfirmed.write("---------------------------------------------");
                        bwConfirmed.flush();
                        bwWaiting = new BufferedWriter(new FileWriter(waiting));
                        bwWaiting.write("Course ID: " + course.getCourseCode());
                        bwWaiting.newLine();
                        bwWaiting.newLine();
                        bwWaiting.write("---------------------------------------------");
                        bwWaiting.flush();
                        bwRejected = new BufferedWriter(new FileWriter(rejected));
                        bwRejected.write("Course ID: " + course.getCourseCode());
                        bwRejected.newLine();
                        bwRejected.newLine();
                        bwRejected.write("---------------------------------------------");
                        bwRejected.flush();
                        ResultSet rs3 = db.fetchEnrolledStudentData(course.getCourseCode());
                        while (rs3.next()) {
                            Student s = getStudent(rs3.getInt("studentID"));
                            if (i <= course.getMaxCapacity()) {
                                bwConfirmed.newLine();
                                bwConfirmed.write("Rank: "+ i);
                                bwConfirmed.newLine();
                                bwConfirmed.write("Student ID: "+ rs3.getInt("studentID"));
                                bwConfirmed.newLine();
                                bwConfirmed.write("Student Marks: "+ rs3.getDouble("marks"));
                                bwConfirmed.newLine();
                                bwConfirmed.write("---------------------------------------------");
                                bwConfirmed.newLine();
                                bwConfirmed.flush();
                                s.setEnrollStatus("Pending");
                            } else if (i > course.getMaxCapacity() && i <= course.getMaxCapacity() + 7) {
                                bwWaiting.newLine();
                                bwWaiting.write("Rank: "+ i);
                                bwWaiting.newLine();
                                bwWaiting.write("Student ID: "+ rs3.getInt("studentID"));
                                bwWaiting.newLine();
                                bwWaiting.write("Student Marks: "+ rs3.getDouble("marks"));
                                bwWaiting.newLine();
                                bwWaiting.write("---------------------------------------------");
                                bwWaiting.newLine();
                                bwWaiting.flush();
                                s.setEnrollStatus("Waiting");
                            } else {
                                bwRejected.newLine();
                                bwRejected.write("Rank: "+ i);
                                bwRejected.newLine();
                                bwRejected.write("Student ID: "+ rs3.getInt("studentID"));
                                bwRejected.newLine();
                                bwRejected.write("Student Marks: "+ rs3.getDouble("marks"));
                                bwRejected.newLine();
                                bwRejected.write("---------------------------------------------");
                                bwRejected.newLine();
                                bwRejected.flush();
                                s.setEnrollStatus("Rejected");
                            }
                            db.updateStudentDetails(s);
                            i++;
                        }
                        System.out.println("Lists generated for Course Code: " + course.getCourseCode());
                        System.out.println("Lists location:");
                        System.out.println(course.getConfirmedListFile().getAbsolutePath());
                        System.out.println(course.getWaitingListFile().getAbsolutePath());
                        System.out.println(course.getRejectedListFile().getAbsolutePath());
                        course.setEnrolledStudentsFile(enroll);
                        course.setConfirmedListFile(confirm);
                        course.setWaitingListFile(waiting);
                        course.setRejectedListFile(rejected);
                    }
                    bwWaiting.close();
                    bwRejected.close();
                    if (rs.getInt(1) == 0) {
                        db.insertLists(course);
                    } else {
                        db.updateLists(course);
                    }
                } else {
                    System.out.println("No students to enroll for course code: " + course.getCourseCode());
                }
            } else {
                System.out.println("Confirm List Full");
            }
        } catch (IOException e) {
            System.out.println("generateLists(): " + e.getMessage());
        } catch (SQLException ex) {
        }
    }

    public static void listDisplay() {
        try {
            System.out.println();
            
            System.out.print("Enter course Code:");
            String courseID = sc.next();
            LL<Course>.Node<Course> course = courses.Head;
            if (course != null) {
                while (course != null) {
                    if (course.data.getCourseCode().equals(courseID)) {
                        System.out.println("\nChoose which list to display:");
                        System.out.println("1. Enrolled Students");
                        System.out.println("2. Confirmed Students");
                        System.out.println("3. Waiting Students");
                        System.out.println("4. Rejected Students");
                        System.out.print("Enter your choice:");
                        switch (sc.nextInt()) {
                            case 1 -> {
                                ResultSet rs = db.fetchLists(courseID);
                                while (rs.next()) {
                                    Clob enrolledClob = rs.getClob("enrolledStudents");
                                    BufferedReader enrolledReader = new BufferedReader(
                                            enrolledClob.getCharacterStream());
                                    String s;
                                    while ((s = enrolledReader.readLine()) != null) {
                                        System.out.println(s);
                                    }
                                }
                            }

                            case 2 -> {
                                ResultSet rs = db.fetchLists(courseID);
                                while (rs.next()) {
                                    Clob confirmClob = rs.getClob("confirmedList");
                                    BufferedReader confirmReader = new BufferedReader(confirmClob.getCharacterStream());
                                    String s;
                                    while ((s = confirmReader.readLine()) != null) {
                                        System.out.println(s);
                                    }
                                }
                            }

                            case 3 -> {
                                ResultSet rs = db.fetchLists(courseID);
                                while (rs.next()) {
                                    Clob waitingClob = rs.getClob("waitingList");
                                    BufferedReader waitingReader = new BufferedReader(waitingClob.getCharacterStream());
                                    String s;
                                    while ((s = waitingReader.readLine()) != null) {
                                        System.out.println(s);
                                    }
                                }
                            }

                            case 4 -> {
                                ResultSet rs = db.fetchLists(courseID);
                                while (rs.next()) {
                                    Clob rejectedClob = rs.getClob("rejectedList");
                                    BufferedReader rejectedReader = new BufferedReader(rejectedClob.getCharacterStream());
                                    String s;
                                    while ((s = rejectedReader.readLine()) != null) {
                                        System.out.println(s);
                                    }
                                }
                            }

                            default ->
                                System.out.println("Enter valid input!");
                        }
                        return;
                    }
                    course = course.next;
                }
                System.out.println("No such course found!");
            } else {
                System.out.println("No courses available.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter integer input only!");
        } catch (IOException e) {
            System.out.println("displayLists(): IO: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("displayLists(): SQL: " + e.getMessage());
        }
    }
}
