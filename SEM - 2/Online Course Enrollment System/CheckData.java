public class CheckData {
    
    public static boolean checkUserID(int userID) {
        LL<User>.Node<User> user = CourseEnrollmentSystem.users.Head;
        while (user != null) {
            if (user.data.getID() == userID) {
                return true;
            }
            user = user.next;
        }
        return false;
    }

    public static boolean checkCourseCode(String code) {
        LL<Course>.Node<Course> course = CourseEnrollmentSystem.courses.Head;
        while (course != null) {
            if (course.data.getCourseCode().equals(code)) {
                return true;
            }
            course = course.next;
        }
        return false;
    }

    public static boolean checkUserPass(int userID, String password) {
        LL<User>.Node<User> user = CourseEnrollmentSystem.users.Head;
        while (user != null) {
            if (user.data.getID() == userID) {
                return user.data.getPassword().trim().equals(password.trim());
            }
            user = user.next;
        }
        return false;
    }

    public static String checkUserRole(int userID) {
        LL<User>.Node<User> user = CourseEnrollmentSystem.users.Head;
        while (user != null) {
            if (user.data.getID() == userID) {
                return user.data.getRole();
            }
            user = user.next;
        }
        return null;
    }

    public static boolean checkContactNumber(String contactNumber) {
        if (contactNumber.length() == 10) {
            boolean b = false;
            for (int i = 0; i < contactNumber.length(); i++) {
                if (contactNumber.charAt(i) >= '0' && contactNumber.charAt(i) <= '9') {
                    b = true;
                } else {
                    b = false;
                    System.out.println("Enter digits only!");
                    return b;
                }
            }
            return b;
        } else {
            System.out.println("Enter a 10 digit number!");
            return false;
        }
    }

    public static boolean checkEmail(String email) {
        if (email.contains("@gmail.com") && email.length()>10) {
            return true;
        }
        System.out.println("Enter a valid Gmail address!");
        return false;
    }

    public static boolean checkMarks(double marks) {
        if (marks >= 0 && marks <= 100) {
            return true;
        }
        System.out.println("Invalid marks! Please enter between 0 and 100.");
        return false;
    }
}
