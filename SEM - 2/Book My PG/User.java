import java.util.Scanner;

public class User {
    int userID;
    String password;
    String role;
    String name;
    String phoneNumber;
    String emailID;
    String address;
    int age;
    String gender;
    
    Database db = new Database();
    Scanner sc = new Scanner(System.in);
    BookMyPG object = new BookMyPG();
    
    public User(int userID, String password, String role, String name, String phoneNumber, String emailID, String address, int age, String gender) {
        this.userID = userID;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.address = address;
        this.age = age;
        this.gender = gender;
    }
    
    public int getID() {
        return userID;
    }

    public void setID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}