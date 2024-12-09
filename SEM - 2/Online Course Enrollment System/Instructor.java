import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

class Instructor extends User {

    String teachingCourseCode;
    String specialization;
    int teachingExperience;
    LL<Student> mentorshipList = new LL<>();

    public Instructor(int userId, String pass, String name, String mail, String contact, String teachingCourseCode,
            int teachingExperience, String specialization) {
        super(userId, pass, name, mail, contact, "Instructor");
        this.teachingCourseCode = teachingCourseCode;
        this.teachingExperience = teachingExperience;
        this.specialization = specialization;
    }

    public int getTeachingExperience() {
        return teachingExperience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getTeachingCourseCode() {
        return teachingCourseCode;
    }

    public void setTeachingCourseCode(String teachingCourseCode) {
        this.teachingCourseCode = teachingCourseCode;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setTeachingExperience(int teachingExperience) {
        this.teachingExperience = teachingExperience;
    }

    public void setteachingCourseCode(String teachingCourseCode) {
        this.teachingCourseCode = teachingCourseCode;
    }

    public LL<Student> getMentorshipList() {
        return mentorshipList;
    }

    public void setMentorshipList(LL<Student> mentorshipList) {
        this.mentorshipList = mentorshipList;
    }
    
    @Override
    public String toString() {
        return "\nInstructor ID: " + getID() + "\nName: " + getName() + "\nEmail: " + getEmail() + "\nContact: "+ getContactNumber() + "\nTeaching Experience: " + getTeachingExperience() + "\nSpecialization: " + getSpecialization() + "\nInstructor Teaching Course Code: " + getTeachingCourseCode() + "\n\n------------------------------";
    }

    public void showOptions() {
        while (true) {
            CourseEnrollmentSystem.refreshLists();
            System.out.println();
            System.out.println("--------Welcome "+getName()+"------------");
            System.out.println();
            System.out.println("Press 1 to View Courses");
            System.out.println("Press 2 to View Confirm Students");
            System.out.println("Press 3 to View Students under Mentorship");
            System.out.println("Press 4 to View Profile");
            System.out.println("Press 5 to Edit Profile");
            System.out.println("Press 6 to Logout");
            System.out.println();
            System.out.print("Enter your choice: ");
            try {
                switch (sc.nextInt()) {
                    case 1 ->
                        CourseEnrollmentSystem.courses.display();

                    case 2 -> {
                        if (this.getTeachingCourseCode() != null) {
                            System.out.println();
                            System.out.println("--------Students having Confirm Seat in Course Code: "+getTeachingCourseCode()+"------------");
                            System.out.println();
                            try {
                                ResultSet rs = db.getConfirmedNumberInstructor(this.getTeachingCourseCode());
                                rs.next();
                                if (rs.getInt(1) != 0) {
                                    ResultSet rs1 = db.fetchConfirmStudentData(this.getTeachingCourseCode());
                                    boolean b = rs1.next();
                                    if (b) {
                                        while (b) {
                                            System.out.println("Student ID: " + rs1.getInt("studentID"));
                                            System.out.println("Marks: " + rs1.getInt("marks"));
                                            System.out.println("Enrolled Course Code: " + rs1.getString("enrolledCourseCode"));
                                            System.out.println("Mentor ID: " + rs1.getInt("mentorID"));
                                            System.out.println();
                                            System.out.println("--------------------------------");
                                            b = rs1.next();
                                        }
                                    } else {
                                        System.out.println("No students are confirmed in the course code: " + getTeachingCourseCode());
                                    }
                                } else {
                                    System.out.println("No confirm student data found!");
                                }
                            } catch (SQLException e) {
                                System.out.println("confirmdisplay: " + e.getMessage());
                            }
                        } else {
                            System.out.println("You are not assigned to any course.");
                        }
                    }

                    case 3 -> {
                        System.out.println();
                        System.out.println("--------Students under your Mentorship------------");
                        System.out.println();
                        mentorshipList.display();
                    }
                    
                    case 4 -> {
                        System.out.println();
                        System.out.println("--------Your Profile------------");
                        System.out.println();
                        System.out.println(this.toString());
                    }

                    case 5 ->
                        showInstructorUpdateOptions();

                    case 6 -> {
                        System.out.println("You have been logged out of the system");
                        System.out.println();
                        return;
                    }

                    default ->
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!! Enter integer value only.");
                sc.next();
            }
        }
    }

    void showInstructorUpdateOptions() {
        System.out.println();
        System.out.println("--------Update Your Profile------------");
        System.out.println();
        System.out.println("Press 1 to Update Name");
        System.out.println("Press 2 to Update Email");
        System.out.println("Press 3 to Update Phone Number");
        System.out.println("Press 4 to Update Teaching Course");
        System.out.println("Press 5 to Update Password");
        System.out.println("Press 6 to Return Back");
        System.out.print("Enter your choice: ");
        try {
            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.println();
                    System.out.println("--------Update Name------------");
                    System.out.println();
                    System.out.println("Enter new name:");
                    String nname = sc.next();
                    this.setName(nname);
                    System.out.println((db.updateUserDetails(this) > 0) ? "Name updated successfully!" : "Name updation Failed");
                }

                case 2 -> {
                    System.out.println();
                    System.out.println("--------Update Email------------");
                    System.out.println();
                    String nemail;
                    while (true) {
                        System.out.println("Enter new email:");
                        nemail = sc.next();
                        if (CheckData.checkEmail(nemail)) {
                            this.setEmail(nemail);
                            break;
                        }
                    }
                    System.out.println((db.updateUserDetails(this) > 0) ? "Email updated successfully!" : "Email updation Failed");
                }

                case 3 -> {
                    System.out.println();
                    System.out.println("--------Update Phone Number------------");
                    System.out.println();
                    System.out.println("Enter new phone number:");
                    String phoneNumber;
                    while (true) {
                        System.out.print("Enter contact number:");
                        phoneNumber = sc.next();
                        if (CheckData.checkContactNumber(phoneNumber)) {
                            this.setContactNumber(phoneNumber);
                            break;
                        }
                    }
                    System.out.println((db.updateUserDetails(this) > 0) ? "Phone number updated successfully!" : "Phone number updation Failed");
                }

                case 4 -> {
                    System.out.println();
                    System.out.println("--------Update Teaching Course Code------------");
                    System.out.println();
                    System.out.println("Enter new teaching course code:");
                    String courseCode = sc.next();
                    if (CheckData.checkCourseCode(courseCode)) {
                        this.setteachingCourseCode(courseCode);
                        System.out.println(
                                (db.updateInstructorDetails(this) > 0) ? "Teaching course updated successfully!"
                                : "Teaching Course Updation Failed");
                    } else {
                        System.out.println("Invalid course code");
                    }
                }

                case 5 -> {
                    System.out.println();
                    System.out.println("--------Update Password------------");
                    System.out.println();
                    System.out.println("Enter new password:");
                    String npassword = sc.next();
                    if (npassword.length() >= 5) {
                        this.setPassword(npassword);
                        System.out.println((db.updateUserDetails(this) > 0) ? "Password updated successfully!"
                                : "Password updation Failed");
                    } else {
                        System.out.println("Password must be at least 5 characters long");
                    }
                }

                case 6 -> {
                    return;
                }

                default ->
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input!! Enter integer value only.");
        }
    }
}
