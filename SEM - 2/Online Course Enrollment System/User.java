import java.util.Scanner;

class User {
    int userID;
    String password;
    String name;
    String email;
    String contactNumber;
    String role;

    static DatabaseManager db = new DatabaseManager();
    static Scanner sc = new Scanner(System.in);

    public User(int userID, String password, String name, String email, String phone, String role) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactNumber = phone;
        this.role = role;
    }

    public int getID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    @Override
    public String toString() {
        return "User ID: " + userID + "\tName: " + name + "\tPassword: " + password;
    }
}