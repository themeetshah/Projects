import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Admin extends User {

    public Admin(int userId, String pass, String name, String mail, String contact) {
        super(userId, pass, name, mail, contact, "Admin");
    }

    public void showOptions() {
        
        while (true) {
            CourseEnrollmentSystem.refreshLists();
            System.out.println();
            System.out.println("---------Welcome Admin "+ getName() +"------");
            System.out.println();
            System.out.println("Press 1 for Student related options.");
            System.out.println("Press 2 for Instructor related options.");
            System.out.println("Press 3 for Course related options.");
            System.out.println("Press 4 for Transaction related options.");
            System.out.println("Press 5 to Logout.");
            System.out.println();
            System.out.print("Enter your choice: ");
            
            try {
                int option = sc.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("---------Student Related Options---------");
                        System.out.println();
                        System.out.println("Press 1 to View students details.");
                        System.out.println("Press 2 to Remove student.");
                        System.out.println("Press 3 to Return to main menu.");
                        System.out.println();
                        System.out.print("Enter your choice: ");
                        int studentOption = sc.nextInt();
                        switch (studentOption) {
                            case 1 -> viewStudents();

                            case 2 -> removeStudent();

                            case 3 -> {
                                return;
                            }

                            default -> System.out.println("Invalid option. Please choose a valid option.");
                        }
                    }

                    case 2 -> {
                        System.out.println();
                        System.out.println("---------Instructor Related Options---------");
                        System.out.println();
                        System.out.println("Press 1 to View instructors details.");
                        System.out.println("Press 2 to Add an instructor.");
                        System.out.println("Press 3 to Change or Assign course to an instructor.");
                        System.out.println("Press 4 to Remove an instructor");
                        System.out.println("Press 5 to Return to main menu.");
                        System.out.println();
                        System.out.print("Enter your choice: ");
                        int instructorOption = sc.nextInt();
                        switch (instructorOption) {
                            case 1 -> viewInstructors();

                            case 2 -> addInstructor();

                            case 3 -> assignCourseToInstructor();

                            case 4 -> removeInstructor();

                            case 5 -> {
                                return;
                            }

                            default -> System.out.println("Invalid option. Please choose a valid option.");
                        }
                    }

                    case 3 -> {
                        System.out.println();
                        System.out.println("---------Course Related Options---------");
                        System.out.println();
                        System.out.println("Press 1 to View all courses");
                        System.out.println("Press 2 to Add a new course");
                        System.out.println("Press 3 to Remove a course");
                        System.out.println("Press 4 to Update a course");
                        System.out.println("Press 5 to Generate Lists");
                        System.out.println("Press 6 to Display Lists");
                        System.out.println("Press 7 to Return to main menu.");
                        System.out.println();
                        System.out.print("Enter your choice: ");
                        int courseOption = sc.nextInt();
                        switch (courseOption) {
                            case 1 -> viewCourses();

                            case 2 -> addCourse();

                            case 3 -> removeCourse();

                            case 4 -> updateCourse();

                            case 5 -> listGenerate();

                            case 6 -> CourseEnrollmentSystem.listDisplay();
                            
                            case 7 -> {
                                return;
                            }

                            default -> System.out.println("Invalid option. Please choose a valid option.");
                        }
                    }

                    case 4 -> {
                        System.out.println();
                        System.out.println("---------Transaction Related Options---------");
                        System.out.println();
                        System.out.println("Press 1 to View all Transactions");
                        System.out.println("Press 2 to View Transactions in a range");
                        System.out.println("Press 3 to View Transactions of a particular date");
                        System.out.println("Press 4 to Return to main menu.");
                        System.out.println();
                        System.out.print("Enter your choice: ");
                        int transactionOption = sc.nextInt();
                        
                        switch (transactionOption) {
                            case 1 -> viewTransactions();
                            
                            case 2 -> viewTransactionsInRange();
                            
                            case 3 -> viewTransactionsOnDate();
                            
                            case 4 -> {
                                return;
                            }
                            
                            default -> System.out.println("Invalid option. Please choose a valid option.");
                        }
                    }

                    case 5 -> {
                        System.out.println("You have been logged out of the system.");
                        System.out.println();
                        return;
                    }

                    default -> System.out.println("Invalid option. Please choose a valid option.");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }
     
    static void viewStudents(){ 
        System.out.println();
        System.out.println("---------View Student Options---------");
        System.out.println();
        System.out.println("Press 1 to View all students");
        System.out.println("Press 2 to View a student by ID");
        System.out.println("Press 3 to View a student by Name");
        System.out.println("Press 4 to View a student by Course");
        System.out.println("Press 5 to Return back");
        System.out.println();
        System.out.print("Enter your choice: ");
        try {
            switch (sc.nextInt()) {
                case 1 -> {
                    displayStudentData(db.fetchStudentData());
                }

                case 2 -> {
                    System.out.print("Enter student ID: ");
                    int studentID = sc.nextInt();
                    displayStudentData(db.fetchStudentDataByStudentID(studentID));
                }

                case 3 -> {
                    System.out.print("Enter student Name: ");
                    sc.nextLine();
                    String studentName = sc.nextLine();
                    displayStudentData(db.fetchStudentDataByName(studentName));
                }

                case 4 -> {
                    System.out.print("Enter course name: ");
                    sc.nextLine();
                    String courseName = sc.nextLine();
                    displayStudentData(db.fetchStudentDataByCourseName(courseName));
                }
                
                case 5 -> {
                    return;
                }

                    
                default -> System.out.println("Invalid option. Please choose a valid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
    }
    
    static public void removeStudent() {
        try {
            
            System.out.println();
            System.out.println("---------Remove Student---------");
            System.out.println();
            System.out.print("Enter student id: ");
            int studentID = sc.nextInt();
            Student s = CourseEnrollmentSystem.getStudent(studentID);
            if (s.getEnrollStatus().equals("Confirmed")) {
                System.out.println("Student already has a confirmed seat. Cannot remove now.");
                return;
            }
            CourseEnrollmentSystem.students.remove(s);
            CourseEnrollmentSystem.users.remove(s);
            db.removeStudent(studentID);
            System.out.println("Student removed successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        } catch (NullPointerException e) {
            System.out.println("Student not found.");
        }
    }
    
    static void displayStudentData(ResultSet rs) {
        boolean b;
        try {
            b = rs.next();
            if (b) {
                System.out.println();
                System.out.println("---------Displaying Student Data---------");
                System.out.println();
                while (b) {
                    System.out.println("Student ID: " + rs.getInt("studentID"));
                    System.out.println("Student Name: " + rs.getString("name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Contact Number: " + rs.getString("contactNumber"));
                    System.out.println("Address: " + rs.getString("address"));
                    System.out.println("Marks: " + rs.getInt("marks"));
                    System.out.println("Enroll Status: " + rs.getString("enrollStatus"));
                    if (!rs.getString("enrollStatus").equals("Not Enrolled")) {
                        System.out.println("Enrolled Course Code: " + rs.getString("enrolledCourseCode"));
                        if (rs.getString("enrollStatus").equals("Confirm")) {
                            System.out.println("Payment Status: " + rs.getString("paymentStatus"));
                            System.out.println("Mentor ID: " + rs.getInt("mentorID"));
                            System.out.println("Transaction ID: "+rs.getInt("transactionID"));
                        }
                    }
                    System.out.println();
                    System.out.println("---------------------------------------------");
                    b = rs.next();
                }
            } else {
                System.out.println("No student data found!");
            }
        } catch (SQLException e) {
            System.out.println("Admin: displayStudentData: " + e.getMessage());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Admin: displayStudentData: " + e.getMessage());
            }
        }
    }
    
    static void viewInstructors() {
        System.out.println();
        System.out.println("---------View Instructors Option---------");
        System.out.println();
        System.out.println("Press 1 to View all instructors");
        System.out.println("Press 2 to View instructor by ID");
        System.out.println("Press 3 to View instructor by name");
        System.out.println("Press 4 to View instructor by teaching course name");
        System.out.println("Press 5 to View instructor by teaching experience same or more than it");
        System.out.println("Press 6 to Get mentorship list of all instructors");
        System.out.println("Press 7 to Get mentorship list of an instructor by id");
        System.out.println("Press 8 to Return back");
        System.out.println();
        System.out.print("Enter your choice: ");

        try {
            switch (sc.nextInt()) {
                case 1 -> {
                    displayInstructor(db.fetchInstructorData());
                }
                
                case 2 -> {
                    System.out.print("Enter instructor ID: ");
                    int insID = sc.nextInt();
                    displayInstructor(db.fetchInstructorDataByInstructorID(insID));
                }
                
                case 3 -> {
                    System.out.print("Enter instructor name: ");
                    sc.nextLine();
                    String insName = sc.nextLine();
                    displayInstructor(db.fetchInstructorDataByInstructorName(insName));
                }
                
                case 4 -> {
                    System.out.print("Enter teaching course name: ");
                    sc.nextLine();
                    String insCourseName = sc.nextLine();
                    System.out.println("\nInstructors teaching course " + insCourseName + ": ");
                    displayInstructor(db.fetchInstructorDataByTeachingCourseName(insCourseName));
                }
                
                case 5 -> {
                    System.out.print("Enter teaching experience: ");
                    int exp = sc.nextInt();    
                    System.out.println("\nInstructors having teaching experience greater than or equal to "+exp+" years:");
                    displayInstructor(db.fetchInstructorDataByTeachingExperience(exp));
                }
                
                case 6 -> {
                    System.out.println();
                    System.out.println("---------Mentorship Lists---------");
                    System.out.println();
                    LL<Instructor>.Node<Instructor> instructor = CourseEnrollmentSystem.instructors.Head;
                    while (instructor != null) {
                        System.out.println();
                        System.out.println("---------Mentorship List for Instructor ID: "+instructor.data.getID()+" ---------");
                        System.out.println();
                        if (instructor.data.mentorshipList.Head!=null) {
                            instructor.data.mentorshipList.display();
                        } else {
                            System.out.println("No mentorship data found for instructor id: " + instructor.data.getID() + " !");
                        }
                        System.out.println();
                        instructor = instructor.next;
                    }
                }
                
                case 7 -> {
                    System.out.print("Enter instructor id: ");
                    int instructorID = sc.nextInt();
                    Instructor i = CourseEnrollmentSystem.getInstructor(instructorID);
                    if (i != null) {
                        if (i.mentorshipList.Head!=null) {
                            i.mentorshipList.display();
                        } else {
                            System.out.println("No mentorship data found for instructor id: "+instructorID+" !");
                        }
                    } else {
                        System.out.println("No instructor data found for instructor id: "+instructorID+" !");
                    }
                }

                case 8 -> {
                    return;
                }
                    
                default -> System.out.println("Invalid option. Please choose a valid option.");
            } 
        } catch(InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
    }
    
    static void displayInstructor(ResultSet rs) {
        boolean b;
        try {
            b = rs.next();
            if (b) {
                
                System.out.println();
                System.out.println("---------Displaying Instructor Data---------");
                System.out.println();
                while (b) {
                    System.out.println("Instructor ID: " + rs.getInt("instructorId"));
                    System.out.println("Instructor Name: " + rs.getString("name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Contact Number: " + rs.getString("contactNumber"));
                    System.out.println("Teaching Course Code: " + rs.getString("teachingCourseCode"));
                    System.out.println("Teaching Experience: " + rs.getInt("teachingExperience"));
                    System.out.println("Specialization: " + rs.getString("specialization"));
                    System.out.println();
                    System.out.println("---------------------------------------------");
                    b = rs.next();
                }
            } else {
                System.out.println("No instructor data found!");
            }
        } catch (SQLException e) {  
            System.out.println("displayInstructor: "+e.getMessage());
        }
    }
    
    static void addInstructor() {
        System.out.println();
        System.out.println("---------Add Instructor---------");
        System.out.println();
        System.out.print("Enter name: ");
        sc.nextLine();
        String iname = sc.nextLine();
        String iemail;
        while (true) { 
            System.out.print("Enter email: ");
            iemail = sc.nextLine();
            if (CheckData.checkEmail(iemail)) {
                break;
            } else {
                System.out.println("Invalid email. Please enter a valid email.");
            }
        }
        String icontactNumber;
        while (true) {
            System.out.print("Enter contact number:");
            icontactNumber = sc.nextLine();
            if (CheckData.checkContactNumber(icontactNumber)) {
                break;
            }
        }
        int iexperience;
        while (true) {
            System.out.print("Enter experience (years): ");
            try {
                iexperience = sc.nextInt();
                if (iexperience > 0) {
                    break;
                } else {
                    System.out.println("Invalid experience!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
        System.out.print("Enter specialization: ");
        sc.nextLine();
        String spec = sc.nextLine();
        String icourseCode = null;
        while (true) { 
            System.out.println("Does this instructor teach any course: ");
            System.out.println("1. Yes");
            System.out.println("2. No");
            try {
                switch (sc.nextInt()) {
                    case 1 -> {
                        System.out.print("Enter course Code: ");
                        String code = sc.next();
                        if (CheckData.checkCourseCode(code)) {
                            icourseCode = code;
                            break;
                        } else {
                            System.out.println("Invalid course code!");
                        }
                    }

                    case 2 -> icourseCode = "Not Assigned";

                    default -> {
                        System.out.println("Invalid choice!");
                        continue;
                    }
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Enter integer input only");
                sc.nextLine();
            }
        }   
        String[] splitted = iname.split("\\s+");
        int iid = db.getUserID() + 1;
        Instructor i = new Instructor(iid, iid + "@" + splitted[0], iname, iemail, icontactNumber, icourseCode, iexperience, spec);
        db.addInstructor(i);
        db.addUser(i);
        CourseEnrollmentSystem.instructors.add(i);
        CourseEnrollmentSystem.users.add(i);
        System.out.println("Instructor added successfully!");    
        System.out.println("User credentials: userID:"+i.getID()+", Password: "+i.getPassword());
    }
    
    static void assignCourseToInstructor() {
        System.out.println();
        System.out.println("---------Change/Assign Course to Instructor---------");
        System.out.println();
        System.out.print("Enter instructor ID: ");
        try {
            int iid = sc.nextInt();
            Instructor i = CourseEnrollmentSystem.getInstructor(iid);
            if (i != null) {
                System.out.print("Enter course ID: ");
                String cid = sc.next();
                if (CheckData.checkCourseCode(cid)) {
                    i.setteachingCourseCode(cid);
                    db.updateInstructorDetails(i);
                    System.out.println("Course assigned to instructor successfully!");
                } else {
                    System.out.println("Course not found!");
                }
            } else {
                System.out.println("Instructor not found!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
        }
    }
    
    static void removeInstructor() {
        System.out.println();
        System.out.println("---------Remove Instructor---------");
        System.out.println();
        System.out.print("Enter instructor id: ");
        int id = sc.nextInt();
        Instructor i = CourseEnrollmentSystem.getInstructor(id);
        if (i != null) {
            CourseEnrollmentSystem.instructors.remove(i);
            LL<Student>.Node<Student> student = CourseEnrollmentSystem.students.Head;
            while (student != null) {
                if (student.data.getMentorID() == id) {
                    student.data.setMentorID(student.data.getAvailableMentor(student.data).getID());
                    db.updateStudentDetails(student.data);
                }
                student = student.next;
            }
            db.removeInstructor(i);
            System.out.println("Instructor removed successfully!");
        } else {
            System.out.println("Instructor not found!");
        }
    }
    
    static void viewCourses() {
        CourseEnrollmentSystem.viewCourses();
    }
    
    static void addCourse() {
        System.out.println();
        System.out.println("---------Add Course---------");
        System.out.println();
        System.out.print("Enter course name: ");
        sc.nextLine();
        String cname = sc.nextLine();
        System.out.print("Enter course code: ");
        String code = sc.next();
        double fee;
        while (true) {
            System.out.print("Enter course fee: ");
            try {
                fee = sc.nextDouble();
                if (fee > 0) {
                    break;
                } else {
                    System.out.println("Invalid capacity! Please enter a positive value.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid value.");
                sc.nextLine();
            }
        }
        int capacity;
        while (true) {
            System.out.print("Enter maximum capacity: ");
            try {
                capacity = sc.nextInt();
                if (capacity > 0) {
                    break;
                } else {
                    System.out.println("Invalid capacity! Please enter a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                sc.nextLine();
            }
        }
        Course c = new Course(code, cname, fee, capacity);
        db.addCourse(c);
        CourseEnrollmentSystem.courses.add(c);
        System.out.println("Course added successfully!");
    }
    
    static void removeCourse() {
        System.out.println();
        System.out.println("---------Remove Course---------");
        System.out.println();
        System.out.print("Enter course code: ");
        sc.nextLine();
        String code = sc.nextLine();
        Course c = CourseEnrollmentSystem.getCourse(code);
        if (c != null) {
            try {
                ResultSet rs = db.getConfirmedNumber(code);
                rs.next();
                if (rs.getInt(1) == 0) {
                    CourseEnrollmentSystem.courses.remove(c);
                    db.removeCourse(code);
                    System.out.println("Course removed successfully!");
                } else {
                    System.out.println("Students have enrolled in the course. Can't remove now!");
                }
            } catch (SQLException e) {
                System.out.println("removeCourse: " + e.getMessage());
            }
        } else {
            System.out.println("Course not found!");
        }
    }
    
    static void updateCourse() {
        System.out.println();
        System.out.println("---------Update Course---------");
        System.out.println();
        System.out.print("Enter course code: ");
        sc.nextLine();
        String code = sc.nextLine();
        Course c = CourseEnrollmentSystem.getCourse(code);
        if (c != null) {
            System.out.println("Press 1 to Update course fee");
            System.out.println("Press 2 to Update course max capacity");
            System.out.println("Press 3 to Return back");
            System.out.println();
            System.out.print("Enter your choice: ");
            try {
                switch (sc.nextInt()) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("---------Update Course Fee---------");
                        System.out.println();
                        System.out.print("Enter new course fee: ");
                        double fee = sc.nextDouble();
                        c.setCourseFee(fee);
                        db.updateCourse(c);
                        System.out.println("Course fee updated successfully!");
                    }
                    
                    case 2 -> {
                        System.out.println();
                        System.out.println("---------Update Course Capacity---------");
                        System.out.println();
                        int courseCap;
                        while (true) {
                            System.out.print("Enter new maximum capacity: ");
                            try {
                                courseCap = sc.nextInt();
                                if (courseCap > 0) {
                                    break;
                                } else {
                                    System.out.println("Invalid capacity! Please enter a positive integer.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter a valid integer.");
                                sc.nextLine();
                            }
                        }
                        c.setMaxCapacity(courseCap);
                        db.updateCourse(c);
                        System.out.println("Course capacity updated successfully!");
                    }
                    
                    case 3 -> {
                        return;
                    }
                        
                    default -> System.out.println("Choose valid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } else {
            System.out.println("Course not found!");
        }
    }
    
    static void viewTransactions() {
        if (CourseEnrollmentSystem.transactions.TOP != null) {
            System.out.println();
            System.out.println("---------Viewing Transaction History---------");
            System.out.println();
            CourseEnrollmentSystem.transactions.display();
        } else {
            System.out.println("No transactions found!");
        }
    }
    
    static void listGenerate() {
        System.out.println();
        System.out.println("---------Generating Lists---------");
        System.out.println();
        System.out.println("Press 1 to Generate lists for all courses");
        System.out.println("Press 2 to Generate lists for particular course");
        System.out.println("Press 3 to Return to Main Menu");
        System.out.println();
        System.out.print("Enter your choice: ");
        try {
            switch (sc.nextInt()) {
                case 1 -> {
                    LL<Course>.Node<Course> course = CourseEnrollmentSystem.courses.Head;
                    if (course != null) {
                        while (course != null) {
                            System.out.println();
                            System.out.println("---------Generating Lists for Course Code: "+ course.data.getCourseCode()+"---------");
                            System.out.println();
                            CourseEnrollmentSystem.generateLists(course.data);
                            course = course.next;
                        }
                    } else {
                        System.out.println("No courses found!");
                    }
                }

                case 2 -> {
                    System.out.print("Enter course Code:");
                    sc.nextLine();
                    String courseID = sc.nextLine();
                    LL<Course>.Node<Course> course = CourseEnrollmentSystem.courses.Head;
                    if (course != null) {
                        while (course != null) {
                            if (course.data.getCourseCode().equals(courseID)) {
                                System.out.println();
                                System.out.println("---------Generating Lists for Course Code: "+ course.data.getCourseCode()+"---------");
                                System.out.println();
                                CourseEnrollmentSystem.generateLists(course.data);
                                return; 
                            }
                            course = course.next;
                        }
                    } else {
                        System.out.println("No such course found!");
                    }
                }
                default -> System.out.println("Choose valid option!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void viewTransactionsInRange() {
        System.out.println();
        System.out.println("---------Viewing Transactions in a Range---------");
        System.out.println();
        System.out.print("Enter start date (yyyy-mm-dd): ");
        sc.nextLine();
        String startDate = sc.nextLine();
        System.out.print("Enter end date (yyyy-mm-dd): ");
        String endDate = sc.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date start = formatter.parse(startDate);
            java.util.Date end = formatter.parse(endDate);
            if (start.before(end)) {
                System.out.println();
                System.out.println("---------Transactions in the Range: " + startDate + " to " + endDate + "---------");
                System.out.println();
                ResultSet rs = db.printTransactionInRange(start, end);
                boolean b = rs.next();
                if (b) {
                    while (b) {
                        System.out.println("Transaction ID: "+rs.getInt("transactionID"));
                        System.out.println("Student ID: " + rs.getInt("studentID"));
                        System.out.println("Payment Method: " + rs.getString("paymentMethod"));
                        System.out.println("Amount: " + rs.getDouble("amount"));
                        System.out.println("Date: " + rs.getDate("date"));
                        System.out.println("Detail: "+ rs.getString("number"));
                        System.out.println();
                        System.out.println("---------------------------------------------");
                        b = rs.next();
                    }
                } else {
                    System.out.println("No transactions found in the range!");
                }
            } else {
                System.out.println("Invalid date range!");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
        }catch (SQLException ex) {
            System.out.println("viewTransactionsInRange: "+ex.getMessage());
        }
    }

    private void viewTransactionsOnDate() {
        System.out.println();
        System.out.println("---------Viewing Transactions On Date---------");
        System.out.println();
        System.out.print("Enter date (yyyy-mm-dd): ");
        String date = sc.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date1 = formatter.parse(date);
            System.out.println();
            System.out.println("---------Transactions on Date: " + date + "---------");
            System.out.println();
            ResultSet rs = db.printTransactionOnDate(date1);
            boolean b = rs.next();
            if (b) {
                while (b) {
                    System.out.println("Transaction ID: "+rs.getInt("transactionID"));
                    System.out.println("Student ID: " + rs.getInt("studentID"));
                    System.out.println("Payment Method: " + rs.getString("paymentMethod"));
                    System.out.println("Amount: " + rs.getDouble("amount"));
                    System.out.println("Date: " + rs.getDate("date"));
                    System.out.println("Detail: "+ rs.getString("number"));
                    System.out.println();
                    System.out.println("---------------------------------------------");
                    b = rs.next();
                }
            } else {
                System.out.println("No transactions found in the range!");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
        }catch (SQLException ex) {
            System.out.println("viewTransactionsInRange: "+ex.getMessage());
        }
    }
}