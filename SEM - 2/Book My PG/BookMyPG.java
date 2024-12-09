import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class BookMyPG {

    static LL<User> users = new LL<>();
    static LL<Landlord> landlords = new LL<>();
    static LL<Tenant> tenants = new LL<>();
    static LL<Room> rooms = new LL<>();
    static DoublyLinkedListJay payment = new DoublyLinkedListJay();
    static Database db = new Database();
    static Admin admin;
    static Queue<Feedback> feedback_queue = new Queue<>();
    static Queue<Payment> payment_queue = new Queue<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        admin = new Admin(1, "D1_AD", "Admin", "Meet", "9876543210", "meet@mail.com", "abc city", 19, "Male");
        start();
    }

    static void start() {
        while (true) {
            db.affectFeedback();
            getUserData();
            getLandlordData();
            getTenantData();
            getRoomData();
            getPaymentData();
            getFeedbackData();
            try {
                System.out.println("\nChoose an action: ");
                System.out.println("1. Login");
                System.out.println("2. Sign up");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                switch (sc.nextInt()) {
                    case 1 -> {
                        System.out.print("Enter your userID: ");
                        int userID = sc.nextInt();
                        if (checkUser(userID)) {
                            User user = getUser(userID);
                            System.out.print("Enter Pass:");
                            String pass = sc.next();
                            if(user.getPassword().equals(pass)){
                                switch (getUser(userID).getRole()) {
                                    case "Admin" -> {
                                        admin.AdminMenu();
                                    }
                                    case "Landlord" -> {
                                        getLandlord(userID).LandlordMenu();
                                    }
                                    case "Tenant" -> {
                                        getTenant(userID).TenantMenu();
                                    }
                                }
                            } else {
                                System.out.println("Invalid Password");
                            }
                        } else {
                            System.out.println("No such user found! Try signing up");
                        }
                    }
                
                    case 2 -> {
                        System.out.println("\nChoose your role: ");
                        System.out.println("1. Tenant");
                        System.out.println("2. Landlord");
                        System.out.println("3. Return");
                        System.out.print("Enter your choice: ");
                        int choice = sc.nextInt();
                        switch (choice) {
                            case 1 -> {
                                addTenant();
                            }
                            case 2 -> {
                                addLandlord();
                            }
                            case 3 -> {
                                return;
                            }
                            
                            default -> System.out.println("Choose valid option!");
                        }            
                    }

                    case 3 -> {
                        System.out.println("Exiting the system. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option! Please enter 1, 2, or 3.");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!! Enter integer value only.");
                sc.nextLine();
            }
        }
    }

    public static void getUserData() {
        ResultSet rs = db.fetchUserData();
        try {
            while (rs.next()) {
                users.add(new User(rs.getInt("userId"), rs.getString("password"), rs.getString("role"), rs.getString("name"), rs.getString("phoneNo"), rs.getString("emailId"), rs.getString("address"), rs.getInt("age"), rs.getString("gender")));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("getUserData: "+e.getMessage());
        }
    }

    public static void getTenantData() {
        ResultSet rs = db.fetchTenantData();
        try {
            while (rs.next()) {
                Clob c = rs.getClob("biodata");
                File f = null;
                if (c != null) {
                    
                    Reader r = rs.getClob("biodata").getCharacterStream();
                    f = new File(rs.getString("tenantID")+"_biodata");
                    FileWriter fw = new FileWriter(f);
                    int i;
                    while((i=r.read())!=-1){
                        fw.write((char)i);
                    }
                    fw.flush();
                    fw.close();
                }
                tenants.add(new Tenant(rs.getInt("tenantID"), rs.getString("password"), "Tenant", rs.getString("name"), rs.getString("phoneNo"), rs.getString("emailId"), rs.getString("address"), rs.getInt("age"), rs.getString("gender"), f, rs.getInt("paymentID"), rs.getBoolean("verifiedStatus"), rs.getString("locality")));
            }
        } catch (SQLException e) {
            System.out.println("getTenantData: SQL: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("getTenantdata: IO: "+e.getMessage());
        }
    }

    public static void getLandlordData() {
        ResultSet rs = db.fetchLandlordData();
        try {
            while(rs.next()) {
                landlords.add(new Landlord(rs.getInt("landlordID"), rs.getString("password"), "Landlord", rs.getString("name"), rs.getString("phoneNo"), rs.getString("emailId"), rs.getString("address"), rs.getInt("age"), rs.getString("gender"),  rs.getInt("rating"), rs.getBoolean("negotiation"), rs.getString("landlordAcctNo")));
            }
        } catch (SQLException e) {
            System.out.println("getLandlordData: SQL: "+e.getMessage());
        }
    }

    public static void getRoomData() {
        ResultSet rs = db.fetchRoomData();
        try {
            while(rs.next()) {
                File f = new File(rs.getInt("roomId")+"_Image.jpg");
                FileOutputStream fos = new FileOutputStream(f);
                Blob b = rs.getBlob("image");
                if(b!=null){
                    byte[] image = b.getBytes(1, (int)b.length());
                    fos.write(image);
                    fos.close();
                }

                rooms.add(new Room(rs.getInt("roomId"), f, rs.getString("locality"), rs.getDouble("rent"), rs.getInt("amenitiesRating"), rs.getDouble("feedbackRating"), rs.getBoolean("availability"), rs.getString("type"), rs.getString("amenities"), Integer.parseInt(rs.getString("type").split("-")[0])-rs.getInt("vacant_space"),  rs.getInt("landlordId")));
            }
        } catch (SQLException e) {
            System.out.println("getRoomData: SQL: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("getRoomData: IO: "+e.getMessage());
        }
    }

    public static void getFeedbackData() {
        ResultSet rs = db.fetchFeedbackData();
        try {
            while(rs.next()) {
                feedback_queue.enqueue(new Feedback(rs.getInt("feedbackID"), rs.getInt("roomID"),rs.getInt("tenantID"), rs.getInt("rating")));
            }
        }  catch (SQLException e) {
            System.out.println("getFeedbackData: SQL: "+e.getMessage());
        }
    }

    public static void getPaymentData() {
        ResultSet rs = db.fetchPaymentData();
        try {
            while(rs.next()) {
                payment_queue.enqueue(new Payment(rs.getInt("paymentID"), rs.getString("paymentMode"),rs.getInt("landlordID"),rs.getInt("tenantID"), rs.getInt("roomID"), rs.getDouble("transactionamount"), rs.getDate("transactionDate")));
            }
        }  catch (SQLException e) {
            System.out.println("getPaymentData: SQL: "+e.getMessage());
        }
    }

    public static boolean checkUser(int userID) {

        LL<User>.Node<User> temp = users.Head;
        if (temp != null) {
            while (temp != null) {
                if (temp.data.getID() == userID ) {
                    return true;
                }
                temp = temp.next;
            }
        }
        return false;
    }    
    
    public static boolean checkPhoneNumber(String contactNumber) {
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
    
    public static boolean checkAccNumber(String accNumber) {
        if (accNumber.length() == 10) {
            boolean b = false;
            if (accNumber.charAt(0) >= 'A' && accNumber.charAt(0) <= 'Z') {
                for (int i = 1; i < accNumber.length(); i++) {
                    if (accNumber.charAt(i) >= '0' && accNumber.charAt(i) <= '9') {
                        b = true;
                    } else {
                        b = false;
                        System.out.println("Enter digits only after 1st alphabet!");
                        return b;
                    }
                }
            } else {
                System.out.println("Enter account number starting with alphabet!");
            }
            return b;
        } else {
            System.out.println("Enter a acc number of length 10!");
            return false;
        }
    }
    
    public static Room getRoom(int roomID) {
        LL<Room>.Node<Room> room = rooms.Head;
        if(room!=null) {
            while (room!=null) {
                if (room.data.getRoomId() == roomID) {
                    return room.data;
                }
                room = room.next;
            }
            System.out.println("Room not found");
            return null;
        } else {
            System.out.println("No rooms available");
            return null;
        }
    }

    public static User getUser(int userID) {
        LL<User>.Node<User> temp = users.Head;
        if (temp != null) {
            while (temp != null) {
                if (temp.data.getID() == userID) {
                    return temp.data;
                }
                temp = temp.next;
            }
        }
        return null;
    }
    
    public static Tenant getTenant(int tenantID) {
        LL<Tenant>.Node<Tenant> tenant = tenants.Head;
        while(tenant!=null){
            if (tenant.data.getID() == tenantID) {
                return tenant.data;
            }
            tenant = tenant.next;
        }
        return null;
    }

    public static Landlord getLandlord(int landlordID) {
        LL<Landlord>.Node<Landlord> landlord = landlords.Head;
        while(landlord!=null){
            if (landlord.data.getID() == landlordID) {
                return landlord.data;
            }
            landlord = landlord.next;
        }
        return null;
    }

    public static void addTenant()
    {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();
            String phone;
            while(true){
                System.out.print("Enter Phone Number: ");
                phone = sc.next();
                if (checkPhoneNumber(phone)) {
                    break;
                }
            }
            String emailid = null;
            while(true) {
                System.out.println("enter email_ID: ");
                emailid = sc.next();
                if(db.checkEmailExist(emailid)){
                    System.out.println("Given emailID already exists. Please enter valid emailID"); 
                } else {
                    break;
                }
            }

            System.out.println("Enter address: ");
            sc.nextLine();
            String address = sc.nextLine();          
            System.out.println("Enter age: ");  
            int age = sc.nextInt();
            System.out.println("Enter gender: ");
            String gender = sc.next();
            System.out.print("Do you want to add your bio-data(YES/NO): ");
            String choice = sc.next();
            File file = null;
            if (choice.equalsIgnoreCase("yes")) {
                while (true) {
                    System.out.println("Enter file Path: ");
                    String file_path = sc.next();
                    file = new File(file_path);
                    if (file.exists()) {
                        if ((int) file.length() > 0) {
                            break;
                        } else {
                            System.out.println("File Properties not matched! No File added");
                            break;
                        }
                    } else {
                        System.out.println("file does not exist");
                    }
                }
            } else if(!choice.equalsIgnoreCase("no")) {
                System.out.println("Not a valid option! File not added");
            }
            System.out.println("Enter locality: ");
            String locality =sc.next();
            Tenant tenant = new Tenant(db.getUserID(), password, "Tenant", name, phone, emailid, address, age, gender, file, 0, (file != null), locality);
            tenants.add(tenant);
            db.addUser(tenant);
            db.addTenant(tenant);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addLandlord()
    {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();
            String phone;
            while (true) {
                System.out.print("Enter Phone Number: ");
                phone = sc.next();
                if (checkPhoneNumber(phone)) {
                    break;
                }
            }
            String emailid = null;
            while (true) {
                System.out.println("enter email_ID: ");
                emailid = sc.next();
                if (db.checkEmailExist(emailid)) {
                    System.out.println("Given emailID already exists. Please enter valid emailID");
                } else {
                    break;
                }
            }

            System.out.println("Enter address: ");
            sc.nextLine();
            String address = sc.nextLine();
            System.out.println("Enter age: ");
            int age = sc.nextInt();
            System.out.println("Enter gender: ");
            String gender = sc.next();
            boolean negotiation;
            System.out.print("Enter if negotiation possible (1.Yes, 2.No): ");
            int choice = sc.nextInt();
            if (choice == 1) {
                negotiation = true;
            } else if (choice != 2) {
                System.out.println("automatically set to false");
                negotiation = false;
            } else {
                System.out.println("automatically set to false");
                negotiation = false;
            }
            String accountno;
            while (true) {
                System.out.print("Enter landlord's account no: ");
                accountno = sc.next();
                if (checkAccNumber(accountno)) {
                    break;
                }
            }
            Landlord landlord = new Landlord(db.getUserID(), password, "Landlord", name, phone, emailid, address, age, gender, 0, negotiation, accountno);
            landlords.add(landlord);
            db.addUser(landlord);
            db.addLandlord(landlord);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static void removeRoom(int roomID) {
        LL<Room>.Node<Room> room = rooms.Head;
        if (room != null) {
            while (room != null) {
                if (room.data.getRoomId() == roomID) {
                    db.removeRoom(roomID);
                    rooms.remove(room.data);
                    System.out.println("Room removed successfully");
                    return;
                }
                room = room.next;
            }
            System.out.println("Room not found");
        } else {
            System.out.println("No rooms available");
        }
    }

    public static void removeLandlord(int landlordId) {
        LL<Landlord>.Node<Landlord> landlord = landlords.Head;
        if (landlord != null) {
            while (landlord != null) {
                if (landlord.data.getID() == landlordId) {
                        db.removeLandlord(landlordId);
                        users.remove(landlord.data);
                        landlords.remove(landlord.data);
                        System.out.println("Landlord removed successfully");
                    return;
                }
                landlord = landlord.next;
            }
        }
    }
        
    public static void removeTenant(int tenantId)
    {
        try {
            LL<Tenant>.Node<Tenant> temp = tenants.Head;
            while(temp != null)
            {
                if(temp.data.getID() == tenantId)
                {
                    db.removeTenant(tenantId);
                    users.remove(temp.data);
                    tenants.remove(temp.data);
                    System.out.println("Tenant removed successfully");
                    return;
                }
                temp = temp.next;
            }
            System.out.println("No such tenant found!");
        } catch (InputMismatchException e) {
            System.out.println("removeTenants:" + e.getMessage());
        }
    }
}