
import java.io.File;

class Course {

    private String courseCode;
    private String courseName;
    private double courseFee;
    private int maxCapacity;
    private File enrolledStudentsFile;
    private File confirmedListFile;
    private File waitingListFile;
    private File rejectedListFile;

    public Course(String courseCode, String courseName, double courseFee, int maxCapacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.maxCapacity = maxCapacity;
        enrolledStudentsFile = new File(courseCode + "_enrolledStudents.txt");
        confirmedListFile = new File(courseCode + "_confirmedList.txt");
        waitingListFile = new File(courseCode + "_waitingList.txt");
        rejectedListFile = new File(courseCode + "_rejectedList.txt");
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public File getEnrolledStudentsFile() {
        return enrolledStudentsFile;
    }

    public void setEnrolledStudentsFile(File enrolledStudentsFile) {
        this.enrolledStudentsFile = enrolledStudentsFile;
    }

    public File getConfirmedListFile() {
        return confirmedListFile;
    }

    public void setConfirmedListFile(File confirmedListFile) {
        this.confirmedListFile = confirmedListFile;
    }

    public File getWaitingListFile() {
        return waitingListFile;
    }

    public void setWaitingListFile(File waitingListFile) {
        this.waitingListFile = waitingListFile;
    }

    public File getRejectedListFile() {
        return rejectedListFile;
    }

    public void setRejectedListFile(File rejectedListFile) {
        this.rejectedListFile = rejectedListFile;
    }

    @Override
    public String toString() {
        return "\nCourse Code: " + courseCode + "\nCourse Name: " + courseName + "\nCourse Fee: " + courseFee + "\nCapacity: "+ maxCapacity + "\n\n------------------------------";
    }
}
