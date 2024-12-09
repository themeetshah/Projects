import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.InputMismatchException;

class Student extends User {

    private double marks;
    private String address;
    private String enrollStatus;
    private String enrolledCourseCode;
    private boolean paymentStatus;
    private int mentorID;
    private String acnumber;
    private int transactionID;
    
    public Student(int userId, String pass, String name, String mail, String contact, String add, double marks, String enrollStatus, String enrolledCourseCode, boolean paymentStatus, int mentorID, int transactionID) {
        super(userId, pass, name, mail, contact, "Student");
        this.marks = marks;
        this.address = add;
        this.enrollStatus = enrollStatus;
        this.enrolledCourseCode = enrolledCourseCode;
        this.paymentStatus = paymentStatus;
        this.mentorID = mentorID;
        this.transactionID = transactionID;
    }

    public double getMarks() {
        return marks;
    }

    public String getAddress() {
        return address;
    }

    public String getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(String enrollStatus) {
        this.enrollStatus = enrollStatus;
    }

    public String getEnrolledCourseCode() {
        return enrolledCourseCode;
    }

    public void setEnrolledCourseCode(String enrolledCourseID) {
        this.enrolledCourseCode = enrolledCourseID;
    }

    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    
    public String getAcnumber() {
        return acnumber;
    }

    public void setAcnumber(String acnumber) {
        this.acnumber = acnumber;
    }
    
    public void setMarks(double marks) {
        this.marks = marks;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "\nStudent ID: " + getID() + "\nName: " + getName() + "\nEmail: " + getEmail() + "\nContact: "+ getContactNumber() + "\nAddress: " + getAddress() + "\nMarks: " + getMarks() + "\nEnroll Status: " + getEnrollStatus() + ((!getEnrollStatus().equals("Not Enrolled"))? ("\nEnrolled Course Code: " +getEnrolledCourseCode()) : ("")) + "\n\n------------------------------";
    }
    
    public void showOptions() {
        while (true) {
            CourseEnrollmentSystem.refreshLists();
            System.out.println();
            System.out.println("--------Welcome "+getName()+"------------");
            System.out.println();
            System.out.println("Press 1 to View Courses");
            System.out.println("Press 2 to Register in a Course");
            System.out.println("Press 3 to Check Seat Allotment");
            System.out.println("Press 4 to Cancel Seat");
            System.out.println("Press 5 to Make Payment");
            System.out.println("Press 6 to View Profile");
            System.out.println("Press 7 to Edit Profile");
            System.out.println("Press 8 to Logout");
            System.out.println();
            System.out.print("Enter your choice: ");
            try {
                switch (sc.nextInt()) {
                    case 1 -> CourseEnrollmentSystem.viewCourses();
                    
                    case 2 -> this.register();
                    
                    case 3 -> {
                        System.out.println();
                        System.out.println("--------Check Seat Allotment------------");
                        System.out.println();
                        if (enrolledCourseCode != null) {
                            if (CourseEnrollmentSystem.getCourse(this.getEnrolledCourseCode()) != null) {
                                System.out.println(
                                        "Course: " + CourseEnrollmentSystem.getCourse(this.getEnrolledCourseCode()).getCourseName()
                                                + ", Allotment Status: " + this.getEnrollStatus());
                            } else {
                                System.out.println("Course not found.");
                            }
                        } else {
                            System.out.println("You are not enrolled in any course.");
                        }
                    }
                    case 4 -> {
                        System.out.println();
                        System.out.println("--------Cancel Seat Allotment------------");
                        System.out.println();
                        this.cancelSeat();    
                    }
                    
                    case 5 -> {
                        System.out.println();
                        System.out.println("--------Make Payment------------");
                        System.out.println();
                        if (this.makePayment()) {
                            System.out.println("Payment Successful");
                            String paymentMethod;
                            if(this.getAcnumber().length()==16){
                                paymentMethod="Card";
                            } else {
                                paymentMethod = "Net-banking";
                            }
                            double amt = CourseEnrollmentSystem.getCourse(this.getEnrolledCourseCode()).getCourseFee();
                            int transID = db.getTransactionID() + 1;
                            Transaction t = new Transaction(transID, this.getID(), amt, new Date(System.currentTimeMillis()), paymentMethod, this.getAcnumber());
                            CourseEnrollmentSystem.transactions.PUSH(t);
                            this.setTransactionID(transID);
                            db.addTransaction(t);
                            db.updateStudentDetails(this);
                            System.out.println("Payment successful");
                        }
                    }
                    
                    case 6 -> {
                        System.out.println();
                        System.out.println("--------Your Profile------------");
                        System.out.println();
                        System.out.println(toString());
                    }
                    
                    case 7 -> {
                        System.out.println();
                        System.out.println("--------Edit Profile------------");
                        System.out.println();
                        showUpdateOptions();
                    }
                    
                    case 8 -> {
                        System.out.println("You have been logged out of the system.");
                        System.out.println();
                        return;
                    }
                        
                    default -> System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!! Enter integer value only.");
                sc.next();
            }
        }
    }
    
    void showUpdateOptions() {
        while (true) {
            System.out.println(
                    "Press 1 to Update Name\nPress 2 to Update Email\nPress 3 to Update Phone Number\nPress 4 to Update Enrolled Course\nPress 5 to Update Password\nPress 6 to Update Address\nPress 7 to Update Marks\nPress 8 to Return");
            System.out.print("Enter your choice: ");
            try {
                switch (sc.nextInt()) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("--------Update Name------------");
                        System.out.println();
                        System.out.print("Enter new name:");
                        sc.nextLine();
                        String nname = sc.nextLine();
                        this.setName(nname);
                        System.out.println((db.updateUserDetails(this)>0)?"Name updated successfully!":"Name updation failed");
                    }
                    
                    case 2 -> {
                        System.out.println();
                        System.out.println("--------Update Email------------");
                        System.out.println();
                        System.out.print("Enter new email:");
                        String nemail;
                        while (true) {
                            nemail = sc.nextLine();
                            if (CheckData.checkEmail(nemail)) {
                                this.setEmail(nemail);
                                break;
                            }
                        }
                        System.out.println((db.updateUserDetails(this)>0)?"Email updated successfully!":"Email Updation Failed");
                    }
                    
                    case 3 -> {
                        System.out.println();
                        System.out.println("--------Update Phone Number------------");
                        System.out.println();
                        String phoneNumber;
                        while (true) {
                            System.out.print("Enter new phone number:");
                            sc.nextLine();
                            phoneNumber = sc.nextLine();
                            if (CheckData.checkContactNumber(phoneNumber)) {
                                this.setContactNumber(phoneNumber);
                                break;
                            }
                        }
                        System.out.println((db.updateUserDetails(this)>0)?"Phone number updated successfully!":"Phone number updattion failed");
                    }
                    
                    case 4 -> {
                        System.out.println();
                        System.out.println("--------Update Enrolled Course Code------------");
                        System.out.println();
                        if (this.getEnrolledCourseCode() != null) {
                            ResultSet rs = db.getConfirmedNumber(this.getEnrolledCourseCode());
                            rs.next();
                            if (rs.getInt(1) == 0) {
                                System.out.println(CourseEnrollmentSystem.courses.toString());
                                System.out.print("Enter new course code:");
                                sc.nextLine();
                                String courseCode = sc.nextLine();
                                if (CheckData.checkCourseCode(courseCode)) {
                                    this.setEnrolledCourseCode(courseCode);
                                    System.out.println((db.updateStudentDetails(this)>0)?"Enrolled course updated successfully!":"Enrolled course change failed");
                                } else {
                                    System.out.println("Invalid course code");
                                }
                            } else {
                                System.out.println("Lists are out! Can't change course");
                            }
                        } else {
                            System.out.println("First Register in a course!");
                        }
                    }
                    
                    
                    case 5 -> {
                        System.out.println();
                        System.out.println("--------Update Password------------");
                        System.out.println();
                        System.out.print("Enter new password:");
                        sc.nextLine();
                        String npassword = sc.nextLine();
                        if (npassword.length() >= 5) {
                            this.setPassword(npassword);
                            System.out.println((db.updateUserDetails(this)>0)?"Password updated successfully!":"Password updation failed");
                        } else {
                            System.out.println("Password must be at least 5 characters long");
                        }
                    }
                    
                    case 6 -> {
                        System.out.println();
                        System.out.println("--------Update Address------------");
                        System.out.println();
                        System.out.print("Enter new address:");
                        sc.nextLine();
                        String naddress = sc.nextLine();
                        this.setAddress(naddress);
                        System.out.println((db.updateStudentDetails(this)>0)?"Address updated successfully!":"Address Updation Failed");
                    }
                    
                    case 7 -> {
                        System.out.println();
                        System.out.println("--------Update Marks------------");
                        System.out.println();
                        if (this.getEnrolledCourseCode() != null) {
                            ResultSet rs = db.getConfirmedNumber(this.getEnrolledCourseCode());
                            rs.next();
                            if (rs.getInt(1)==0) {
                                System.out.print("Enter new marks:");
                                double nmarks;
                                while (true) {
                                    System.out.print("Enter marks: ");
                                    try {
                                        nmarks = sc.nextDouble();
                                        break;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input! Please enter a number.");
                                        sc.next();
                                    }
                                }
                                this.setMarks(nmarks);
                                System.out.println((db.updateStudentDetails(this) > 0) ? "Marks updated successfully!"
                                        : "Marks updation failed");
                            } else {
                                System.out.println("Lists are out! Can't change marks");
                            }
                        } else {
                            System.out.print("Enter new marks:");
                                double nmarks;
                                while (true) {
                                    System.out.print("Enter marks: ");
                                    try {
                                        nmarks = sc.nextDouble();
                                        break;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input! Please enter a number.");
                                        sc.next();
                                    }
                                }
                                this.setMarks(nmarks);
                                System.out.println((db.updateStudentDetails(this) > 0) ? "Marks updated successfully!" : "Marks updation failed");
                        }
                    }
                    
                    case 8 -> {
                        return;
                    }
                    
                    default -> System.out.println("Invalid option. Please choose a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!! Enter integer value only.");
                sc.next();
            } catch (SQLException e) {
                System.out.println("Error in database operation!!!");
            }
        }
    }

    public void register() {
        if (this.getEnrollStatus().equals("Not Enrolled") || this.getEnrollStatus().equals("Cancelled") || this.getEnrollStatus().equals("Rejected")) {
            CourseEnrollmentSystem.viewCourses();
            if (CourseEnrollmentSystem.courses.Head!=null) {                
                System.out.print("Enter course code:");
                String courseCode = sc.next();
                if (CheckData.checkCourseCode(courseCode)) {
                    Course c = CourseEnrollmentSystem.getCourse(courseCode);
                    ResultSet rs = db.getConfirmedNumber(this.getEnrolledCourseCode());
                    try {
                    rs.next();
                        if (rs.getInt(1)==0 || (rs.getInt(1)!=0 && rs.getInt(1)<c.getMaxCapacity())) {
                            this.setEnrollStatus("Registered");
                            this.setEnrolledCourseCode(courseCode);
                            db.updateStudentDetails(this);
                            System.out.println("Registration successful!");
                        } else {
                            System.out.println("This course is full.\nTry enrolling in another one!");
                        }
                    } catch (SQLException e) {
                        System.out.println("register(): "+e.getMessage());
                    }
                } else {
                    System.out.println("Invalid course code");
                }
            }
        } else {
            System.out.println("You have already enrolled in a course");
        }
    }
    
    public void cancelSeat() {
        switch (this.getEnrollStatus()) {
            case "Confirm" -> System.out.println("Can't cancel seat!");
            case "Not Enrolled" -> System.out.println("You are not enrolled in any course.");
            case "Rejected" -> System.out.println("You have not been alloted any seat");
            case "Pending", "Waiting", "Registered" -> {
                this.setEnrollStatus("Cancelled");
                db.updateStudentDetails(this);
                System.out.println("Your seat has been cancelled!");
            }
            case "Cancel" -> System.out.println("Seat already cancelled");
            default -> {
            }
        }
    }
    
    public boolean makePayment() {
        if (this.getEnrollStatus().equals("Pending") || this.getEnrollStatus().equals("Confirm")) {
            if (!this.getPaymentStatus()) {
                System.out.println("Choose payment method: ");
                System.out.println("1. Card\n2. Net-banking");
                System.out.print("Enter your choice: ");
                int choice;
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice! Please enter 1 or 2.");
                    sc.next();
                    return false;
                }

                switch (choice) {
                    case 1 -> {
                        if (processCardPayment()) {
                            this.setEnrollStatus("Confirm");
                            this.setPaymentStatus(true);
                            this.setMentorID(getAvailableMentor(this).getID());
                            return true;
                        }
                    }
                    case 2 -> {
                        if (processNetBankingPayment()) {
                            this.setEnrollStatus("Confirm");
                            this.setPaymentStatus(true);
                            this.setMentorID(getAvailableMentor(this).getID());
                            return true;
                        }
                    }
                    default -> System.out.println("Invalid choice!");
                }
                System.out.println("Payment unsuccessful.");
                return false;
            } else {
                System.out.println("Payment already done!");
                return false;
            }
        } else {
            System.out.println("You don't have any confirmed seat!");
            return false;
        }
    }

    private boolean processCardPayment() {
        System.out.print("Enter card number (16 digits): ");
        String cardNumber = sc.next();
        if (cardNumber.length() == 16 && cardNumber.chars().allMatch(Character::isDigit)) {
            System.out.print("Enter password (6 digits): ");
            String cardPass = sc.next();
            if (cardPass.length() == 6 && cardPass.chars().allMatch(Character::isDigit)) {
                this.setAcnumber(cardNumber);
                return true;
            } else {
                System.out.println("Invalid password. It must be 6 digits.");
            }
        } else {
            System.out.println("Invalid card number. It must be 16 digits.");
        }
        return false;
    }

    private boolean processNetBankingPayment() {
        System.out.print("Enter account number (12 digits): ");
        String accNumber = sc.next();
        if (accNumber.length() == 12 && accNumber.chars().allMatch(Character::isDigit)) {
            System.out.print("Enter password (4 digits): ");
            String accPass = sc.next();
            if (accPass.length() == 4 && accPass.chars().allMatch(Character::isDigit)) {
                this.setAcnumber(accNumber);
                return true;
            } else {
                System.out.println("Invalid password. It must be 4 digits.");
            }
        } else {
            System.out.println("Invalid account number. It must be 12 digits.");
        }
        return false;
    }
    
    public Instructor getAvailableMentor(Student student) {
        LL<Instructor>.Node<Instructor> instructor = CourseEnrollmentSystem.instructors.Head;
        while(instructor!=null) {
            if (instructor.data.mentorshipList.size() < 10) {
                return instructor.data;
            }
            instructor = instructor.next;
        }
        return null;
    }
}