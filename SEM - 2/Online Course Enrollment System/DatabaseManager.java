
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/courseenrollmentsystem";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private final Scanner sc = new Scanner(System.in);

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    //user table:
    public int addUser(User user) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Insert into users(userID, password, name, email, contactNumber, role) values(?,?,?,?,?,?);");
            pst.setInt(1, user.getID());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getName());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getContactNumber());
            pst.setString(6, user.getRole());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addUser: " + e.getMessage());
            return 0;
        }
    }

    public int getUserID() {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Select userID from users order by userID DESC limit 1;");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("getUserID: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet checkAdmin() {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from users where role='Admin';");
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("checkAdmin: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchUsers() {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from users order by userID ASC;");
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchUsers: " + e.getMessage());
            return null;
        }
    }

    public int updateUserDetails(User user) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Update users set name=?, password=?, email=?, contactNumber=? where userID=?;");
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getContactNumber());
            pst.setInt(5, user.getID());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateUserDetails: " + e.getMessage());
            return 0;
        }
    }

    public void removeUser(int userID) {
        try {
            Connection con = getConnection();
            con.setAutoCommit(false);
             
            PreparedStatement pst = con.prepareStatement("Delete from users where userID=?;");
            pst.setInt(1, userID);
            System.out.println("Do you really want to continue?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");
            int n = sc.nextInt();
            pst.executeUpdate();
            if (n == 1) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            System.out.println("removeUser: " + e.getMessage());
        }
    }

    //transaction table:
    public int getTransactionID() {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Select transactionID from transactions order by transactionID DESC limit 1;");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("getTransactionID: " + e.getMessage());
            return 0;
        }
    }

    public int addTransaction(Transaction transaction) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Insert into transactions(studentID, amount,date, paymentMethod, number) values (?,?,?,?,?);");
            pst.setInt(1, transaction.getStudentID());
            pst.setDouble(2, transaction.getAmount());
            pst.setDate(3, new java.sql.Date(transaction.getDate().getTime()));
            pst.setString(4, transaction.getPaymentMethod());
            pst.setString(5, transaction.getDetail());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addTransaction: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet fetchTransactions() {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from transactions order by transactionID ASC;");
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchTransactions: " + e.getMessage());
            return null;
        }
    }
    
    public ResultSet printTransactionInRange(java.util.Date start, java.util.Date end) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from transactions where date between ? and ? order by transactionID ASC;");
            pst.setDate(1, new java.sql.Date(start.getTime()));
            pst.setDate(2, new java.sql.Date(end.getTime()));
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("printTransactionInRange: " + e.getMessage());
            return null;
        }
    }
    
    public ResultSet printTransactionOnDate(java.util.Date date) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from transactions where date=? order by transactionID ASC;");
            pst.setDate(1, new java.sql.Date(date.getTime()));
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("printTransactionOnDate: " + e.getMessage());
            return null;
        }
    }

    //Courses table:
    public int addCourse(Course course) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Insert into courses(courseCode,courseName,courseFee,maxCapacity) values (?,?,?,?);");
            pst.setString(1, course.getCourseCode());
            pst.setString(2, course.getCourseName());
            pst.setDouble(3, course.getCourseFee());
            pst.setInt(4, course.getMaxCapacity());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addCourse: " + e.getMessage());
            return 0;
        }
    }

    public void removeCourse(String courseCode) {
        try {
            Connection con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement pst = con.prepareStatement("Delete from courses where courseCode=?");
            pst.setString(1, courseCode);
            pst.executeUpdate();
            System.out.println("Are you sure you want remove this course?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");
            try {
                try (sc) {
                    switch (sc.nextInt()) {
                        case 1 -> {
                            con.commit();
                        }
                        
                        case 2 -> {
                            con.rollback();
                        }
                        
                        default ->
                            System.out.println("Enter valid option");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Failed Removing course");
                System.out.println("Invalid input");
                sc.next();
            }
        } catch (SQLException e) {
            System.out.println("removeCourse: " + e.getMessage());
        }
    }

    public int updateCourse(Course course) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Update courses set courseFee=?,maxCapacity=? where courseCode=?");
            pst.setDouble(1, course.getCourseFee());
            pst.setInt(2, course.getMaxCapacity());
            pst.setString(3, course.getCourseCode());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateCourse: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet fetchCourses() {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from courses;");
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchCourses: " + e.getMessage());
            return null;
        }
    }

    public int insertLists(Course course) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Insert into lists values (?,?,?,?,?);");
            FileReader enrolled = new FileReader(course.getEnrolledStudentsFile());
            FileReader confirmed = new FileReader(course.getEnrolledStudentsFile());
            FileReader waiting = new FileReader(course.getEnrolledStudentsFile());
            FileReader rejected = new FileReader(course.getEnrolledStudentsFile());
            pst.setString(1, course.getCourseCode());
            pst.setClob(2, enrolled);
            pst.setClob(3, confirmed);
            pst.setClob(4, waiting);
            pst.setClob(5, rejected);
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertLists: SQL: " + e.getMessage());
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("insertLists: IO: " + e.getMessage());
            return 0;
        }
    }

    public int updateLists(Course course) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Update lists set enrolledStudents=?, confirmedList=?, waitingList=?, rejectedList=? where courseCode=?;");
            FileReader enrolled = new FileReader(course.getEnrolledStudentsFile());
            FileReader confirmed = new FileReader(course.getConfirmedListFile());
            FileReader waiting = new FileReader(course.getWaitingListFile());
            FileReader rejected = new FileReader(course.getRejectedListFile());
            pst.setClob(1, enrolled);
            pst.setClob(2, confirmed);
            pst.setClob(3, waiting);
            pst.setClob(4, rejected);
            pst.setString(5, course.getCourseCode());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateLists: SQL: " + e.getMessage());
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("updateLists: IO: " + e.getMessage());
            return 0;
        }
    }

    public int deleteList(Course course) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Delete from lists where courseCode=?;");
            pst.setString(1, course.getCourseCode());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteList: SQL: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet fetchLists(String courseCode) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement(
                    "Select * from lists where courseCode=?;");
            pst.setString(1, courseCode);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchLists: " + e.getMessage());
            return null;
        }
    }

    public ResultSet checkList(String courseCode) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement(
                    "Select COUNT(*) from lists where courseCode=?;");
            pst.setString(1, courseCode);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("checkList: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchStudentsInCourse(Course course) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Select * from students where enrolledCourseCode=?;");
            pst.setString(1, course.getCourseCode());
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchStudentsInCourse: " + e.getMessage());
            return null;
        }
    }

    public ResultSet getConfirmedNumber(String courseCode) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select Count(*) from students where enrolledCourseCode=? and studentID IN (Select studentID from students where enrollStatus='Confirm' or enrollStatus='Pending');");
            pst.setString(1, courseCode);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("getConfirmedNumber: " + e.getMessage());
            return null;
        }
    }

    public ResultSet getConfirmedNumberInstructor(String courseCode) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select Count(*) from students where enrolledCourseCode=? and studentID IN (Select studentID from students where enrollStatus='Confirm');");
            pst.setString(1, courseCode);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("getConfirmedNumberInstructor: " + e.getMessage());
            return null;
        }
    }

    //Instructor Table:
    public int addInstructor(Instructor instructor) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Insert into instructors(instructorID, teachingCourseCode, teachingExperience, specialization) values(?,?,?,?);");
            pst.setInt(1, instructor.getID());
            pst.setString(2, instructor.getTeachingCourseCode());
            pst.setInt(3, instructor.getTeachingExperience());
            pst.setString(4, instructor.getSpecialization());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addInstructor: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet fetchInstructorData() {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from instructors inner join users on users.userID=instructors.instructorID;");
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchInstructorData: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchInstructorDataByInstructorID(int insID) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from instructors inner join users on users.userID=instructors.instructorID where instructors.instructorID=?;");
            pst.setInt(1, insID);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchInstructorDatabyInstructorID: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchInstructorDataByInstructorName(String insName) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from instructors inner join users on users.userID=instructors.instructorID where users.name=?;");
            pst.setString(1, insName);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchInstructorDatabyInstructorName: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchInstructorDataByTeachingCourseName(String courseName) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from instructors inner join users on users.userID=instructors.instructorID where instructors.teachingCourseCode=(Select courseCode from courses where courseName=?);");
            pst.setString(1, courseName);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchInstructorDatabyTeachingCourseName: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchInstructorDataByTeachingExperience(int exp) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from instructors inner join users on users.userID=instructors.instructorID where instructors.teachingExperience>=?;");
            pst.setInt(1, exp);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchInstructorDatabyTeachingExperience: " + e.getMessage());
            return null;
        }
    }

    public int updateInstructorDetails(Instructor instructor) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Update instructors set teachingCourseCode=? where instructorID=?;");
            pst.setString(1, instructor.getTeachingCourseCode());
            pst.setInt(2, instructor.getID());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateInstructorDetails: " + e.getMessage());
            return 0;
        }
    }

    public void removeInstructor(Instructor instructor) {
        try {
            Connection con = getConnection();
            con.setAutoCommit(false);
            CallableStatement cst = con.prepareCall("{call remove_instructor(?)}");
            cst.setInt(1, instructor.getID());
            cst.executeUpdate();
            System.out.println("Are you sure you want remove this course?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");
            try {
                try (sc) {
                    switch (sc.nextInt()) {
                        case 1 -> {
                            con.commit();
                        }
                        
                        case 2 -> {
                            con.rollback();
                        }
                        
                        default ->
                            System.out.println("Enter valid option");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Failed Removing course");
                System.out.println("Invalid input");
                sc.next();
            }
        } catch (SQLException e) {
            System.out.println("removeInstructor: " + e.getMessage());
            //return 0;
        }
    }

    public ResultSet fetchMentorshipData(int instructorID) {
        try {
            String query = "SELECT studentID FROM students WHERE mentorID = ?";
            PreparedStatement pstmt = getConnection().prepareStatement(query);
            pstmt.setInt(1, instructorID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchMentorshipData: SQL: " + e.getMessage());
            return null;
        }
    }

    //Student Table:
    public int addStudent(Student student) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Insert into students(studentID, address, marks) values(?,?,?);");
            pst.setInt(1, student.getID());
            pst.setString(2, student.getAddress());
            pst.setDouble(3, student.getMarks());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addStudent: " + e.getMessage());
            return 0;
        }
    }

    public int removeStudent(int studentID) {
        try {
            Connection con = getConnection();
            CallableStatement cst = con.prepareCall("{call remove_student(?)}");
            cst.setInt(1, studentID);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("removeStudent: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet fetchStudentData() {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from students inner join users on users.userID=students.studentID;");
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchStudentData: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchConfirmStudentData(String courseCode) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from confirm_student where enrolledCourseCode=? ;");
            pst.setString(1, courseCode);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchConfirmStudentData: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchEnrolledStudentData(String courseCode) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from students where enrolledCourseCode=? and studentID IN (Select studentID from students where enrollStatus='Registered' or enrollStatus='Confirm' or enrollStatus='Pending' or enrollStatus='Waiting') order by marks DESC;");
            pst.setString(1, courseCode);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchEnrolledStudentData: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchStudentDataByStudentID(int sid) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from students inner join users on users.userID=students.studentID where students.studentID=?;");
            pst.setInt(1, sid);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchStudentDatabyStudentID: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchStudentDataByName(String sname) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from students inner join users on users.userID=students.studentID where name=?;");
            pst.setString(1, sname);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchStudentDatabyStudentName: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchStudentDataByCourseName(String sname) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement("Select * from students inner join users on users.userID=students.studentID where enrolledCourseCode=(Select courseCode from courses where courseName=?);");
            pst.setString(1, sname);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("fetchStudentDatabyStudentName: " + e.getMessage());
            return null;
        }
    }

    public int updateStudentDetails(Student student) {
        try {
            Connection con = getConnection();
             
            PreparedStatement pst = con.prepareStatement("Update students set enrollStatus=?, enrolledCourseCode=?, address=?, marks=?, mentorID=?, transactionID=?, paymentStatus=? where studentID=?;");
            pst.setString(1, student.getEnrollStatus());
            pst.setString(2, student.getEnrolledCourseCode());
            pst.setString(3, student.getAddress());
            pst.setDouble(4, student.getMarks());
            pst.setInt(5, student.getMentorID());
            pst.setInt(6, student.getTransactionID());
            pst.setBoolean(7, student.getPaymentStatus());
            pst.setInt(8, student.getID());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateStudentDetails: " + e.getMessage());
            return 0;
        }
    }
}
