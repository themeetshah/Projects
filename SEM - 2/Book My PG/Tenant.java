import java.io.*;
import java.util.Date;
import java.util.InputMismatchException;

public class Tenant extends User {
    
    File biodata;
    int paymentId;
    boolean verifiedStatus; //(if true then only pg can be booked)
    String preferredLocality;
    Payment payment;
    
    public Tenant(int userID, String password, String role, String name, String phoneNumber, String emailID, String address, int age, String gender, File biodata, int paymentId, boolean verifiedStatus, String locality) {
        super(userID, password, role, name, phoneNumber, emailID, address, age, gender);
        this.biodata = biodata;
        this.paymentId = paymentId;
        this.verifiedStatus = verifiedStatus;
        this.preferredLocality = locality;
    }

    public File getBiodata() {
        return biodata;
    }

    public void setBiodata(File biodata) {
        this.biodata = biodata;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public boolean getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(boolean verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public String getPreferredLocality() {
        return preferredLocality;
    }

    public void setPreferredLocality(String preferredLocality) {
        this.preferredLocality = preferredLocality;
    }
    
    public void TenantMenu() {
        while (true) {
            BookMyPG.getUserData();
            BookMyPG.getLandlordData();
            BookMyPG.getTenantData();
            BookMyPG.getRoomData();
            BookMyPG.getPaymentData();
            BookMyPG.getFeedbackData();
            BookMyPG.db.affectFeedback();
            System.out.println("\nChoose an action");
            System.out.println("1. View Room Details");
            System.out.println("2. Book a Room");
            System.out.println("3. View Profile");
            System.out.println("4. Edit Profile");
            System.out.println("5. Give Feedback");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            try {
                switch (sc.nextInt()) {
                    case 1 -> viewRoomDetails();
                    
                    case 2 -> bookRoom();
                    
                   case 3 -> viewProfile();
                
                   case 4 -> editProfile();
                    
                   case 5 -> feedback();
                   
                    case 6 -> {
                        return;
                    }
                
                    default -> {
                        System.out.println("Please enter valid option");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter integer input.");
              sc.nextLine();
            }
        }
    }

    public void viewRoomDetails() {
        System.out.println("\n1. View Room by room id");
        System.out.println("2. View Room by room amenities rating");
        System.out.println("3. View Room by room feedback rating");
        System.out.println("4. View Room by price");
        System.out.print("Enter your choice: ");
        try {
            switch (sc.nextInt()) {
                case 1 -> viewRoomById();
                
                case 2 -> viewRoomByAmenitiesRating();
                
                case 3 -> viewRoomByFeedbackRating();
                
                case 4 -> viewRoomByPrice();
                
                default -> System.out.println("Enter valid option");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter integer input.");
            sc.nextLine();
        }
    }
    
    public void viewRoomById() {
        System.out.print("Enter room id: ");
        try {
            int roomId = sc.nextInt();
            LL<Room>.Node<Room> temp = BookMyPG.rooms.Head;
            while (temp != null) {
                if (temp.data.getRoomId() == roomId) {
                    System.out.println(temp.data.toString());
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Room not found");
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter integer input.");
        }
    }
    
    public void viewRoomByAmenitiesRating() {
        System.out.println("\n1. 1-star\n2. 2-Star\n3. 3-Star\n4. 4-Star\n5. 5-Star");
        System.out.print("Enter your choice: ");
        try {
            int choice = sc.nextInt();
            if (choice >= 1 && choice <= 5) {
                LL<Room>.Node<Room> temp = BookMyPG.rooms.Head;
                if (temp != null) {
                    System.out.println("Amenities Rating: " + (choice) + "-star");
                    System.out.println("RoomID\tLocality\tFeedbackRating\tRent\ttype\tAmenities");
                    while (temp != null) {
                        if (temp.data.getAmenitiesRating() == (choice) && temp.data.getLocality().equals(getPreferredLocality())) {
                            System.out.println(temp.data.getRoomId() + "\t" + temp.data.getLocality() + "\t"
                                    + temp.data.getFeedbackRating() + "\t" + temp.data.getRent() + "\t"
                                    + temp.data.getType() + "\t" + temp.data.getAmenities());
                        }
                        temp = temp.next;
                    }
                } else {
                    System.out.println("No rooms available");
                }
            } else {
                System.out.println("Enter valid option");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter integer input.");
        }
    }
    
    public void viewRoomByFeedbackRating() {
        try {
            System.out.print("Enter upper rating(from 1 to 5): ");
            double uchoice = sc.nextInt();
            System.out.print("Enter lower rating(from 1 to 5): ");
            double lchoice = sc.nextInt();
            if ((lchoice >= 1 && lchoice <= 5) && (uchoice >= 1 && uchoice <= 5)) {
                LL<Room>.Node<Room> temp = BookMyPG.rooms.Head;
                if (temp != null) {
                    System.out.println("Feedback Rating: " + (lchoice) + " to "+(uchoice));
                    System.out.println("RoomID\tLocality\tAmenitiesRating\\tFeedbackRating\tRent\ttype\tAmenities");
                    while (temp != null) {
                        if (temp.data.getFeedbackRating()>=(lchoice) && temp.data.getFeedbackRating()<=(uchoice)) {
                            System.out.println(temp.data.getRoomId() + "\t" + temp.data.getLocality() + "\t" + temp.data.getAmenitiesRating() + "\t" + temp.data.getFeedbackRating() + "\t" + temp.data.getRent() + "\t" + temp.data.getType() + "\t" + temp.data.getAmenities());
                        }
                        temp = temp.next;
                    }
                } else {
                    System.out.println("No rooms available");
                }
            } else {
                System.out.println("Enter valid option");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter integer input.");
        }
    }
    
    public void viewRoomByPrice() {
        try {
            System.out.println("Enter lowest price");
            double lp = sc.nextDouble();
            System.out.print("Enter highest price: ");
            double hp = sc.nextDouble();
            LL<Room>.Node<Room> temp = BookMyPG.rooms.Head;
            if (temp != null) {
                System.out.println("Price: " + lp + " to " + hp);
                System.out.println("RoomID\tLocality\tAmenitiesRating\tFeedbackRating\tRent\ttype\tAmenities");
                while (temp != null) {
                    if (temp.data.getRent()>=lp && temp.data.getRent()<=hp && temp.data.getLocality().equals(getPreferredLocality())) {
                        System.out.println(temp.data.getRoomId() + "\t" + temp.data.getLocality() + "\t"
                                + temp.data.getAmenitiesRating() + "\t" + temp.data.getRent() + "\t"
                                + temp.data.getType() + "\t" + temp.data.getAmenities());
                    }
                    temp = temp.next;
                }
            } else {
                System.out.println("No rooms available");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter integer input.");
        }
    }

    public void bookRoom() {
        if (this.getVerifiedStatus()) {
            if (this.getPaymentId() == 0) {
                System.out.print("Enter room id: ");
                int roomId = sc.nextInt();
                LL<Room>.Node<Room> temp = BookMyPG.rooms.Head;
                while (temp != null) {
                    if (temp.data.getRoomId() == roomId) {
                        if (temp.data.getAvailability() == true) {
                            if (getPayment(this, temp.data)) {
                                temp.data.setSize(temp.data.getSize() + 1);
                                if (temp.data.getCapacity() == temp.data.getSize()) {
                                    temp.data.setAvailability(false);
                                }
                                System.out.println("Room booking successfully");
                                db.updateRoomBookingDetails(this, temp.data, payment);
                                db.updateRoom(temp.data);
                                db.updateTenant(this);
                                break;
                            } else {
                                System.out.println("Payment failed");
                                System.out.println("Room booking unsuccessfully");
                            }
                        } else {
                            System.out.println("Room is full");
                        }
                    }
                    temp = temp.next;
                }
                if (temp == null) {
                    System.out.println("Room not found");
                }
            } else {
                System.out.println("You already have a booking");
            }
        } else {
            System.out.println("You are not verified");
        }
    }
    
    void feedback() {
        if (this.getPaymentId() != 0) {
            System.out.print("Enter room id: ");
            int roomId = sc.nextInt();
            LL<Room>.Node<Room> temp = BookMyPG.rooms.Head;
            while (temp != null) {
                if (temp.data.getRoomId() == roomId) {
                    if (db.getTenantRoom(this) == roomId) {
                        System.out.println("Enter your feedback: ");
                        System.out.println("1. 1-star");
                        System.out.println("2. 2-star");
                        System.out.println("3. 3-star");
                        System.out.println("4. 4-star");
                        System.out.println("5. 5-star");
                        int rating = sc.nextInt();
                        if (rating >= 1 && rating <= 5) {
                            Feedback f = new Feedback(db.getFeedbackID(), roomId, getID(), rating);
                            db.addFeedback(f);
                            BookMyPG.feedback_queue.enqueue(f);
                            System.out.println("Feedback added successfully");
                            break;
                        } else {
                            System.out.println("Invalid rating");
                        }
                    } else {
                        System.out.println("You are not a tenant of this room");
                    }
                }
                temp = temp.next;
            }
        }
    }
    
    boolean getPayment(Tenant tenant, Room room){
        System.out.println("Select Payment Mode");
        System.out.println("1. UPI");
        System.out.println("2. Card");
        int paymentModeChoice = sc.nextInt();
        switch (paymentModeChoice) {
            case 1 -> {
                String paymentMode = "UPI";
                if (validateUPIID()) {
                    if (validatePIN()) {
                        paymentId = db.getPaymentID()+1;
                        payment = new Payment(paymentId, paymentMode, room.getLandlordId(), tenant.getID(),
                                room.getRoomId(), room.getRent(), new Date());
                                this.setPaymentId(paymentId);
                                BookMyPG.payment.addLast(payment);
                                System.out.println("Payment successfully");
                                return true;
                            } else {
                                System.out.println("Invalid PIN");
                                return false;
                            }
                        } else {
                            System.out.println("Invalid UPI ID");
                            return false;
                        }
                    }
                    
                    case 2 -> {
                        String paymentMode = "Card";
                        if (validateCardno()) {
                            if (validatePIN()) {
                                payment = new Payment(db.getPaymentID()+1, paymentMode, room.getLandlordId(), tenant.getID(), room.getRoomId(), room.getRent(), new Date());
                                BookMyPG.payment.addLast(payment);
                                this.setPaymentId(paymentId);
                        System.out.println("Payment successfully");
                        return true;
                    } else {
                        System.out.println("Invalid PIN");
                        return false;
                    }
                } else {
                    System.out.println("Invalid Card Number");
                    return false;
                }
            }

            default -> {
                System.out.println("Invalid payment method.");
                return false;
            }
        }
    }
    
    
    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateUPIID() {
        while (true) {
            System.out.println("Enter UPI ID : ");
            sc.nextLine();
            String UPINO = sc.nextLine();
            if (UPINO.length() == 10 && isNumeric(UPINO)) {
                return true;
            } else {
                System.out.println("Invalid UPI number.UPI number must be exactly 10 digits long.");
                return false;
            }
        }
    }

    private boolean validatePIN() {
        while (true) {
            System.out.println("Enter UPI PIN: ");
            String UPIPIN = sc.nextLine();
            if (UPIPIN.length() == 6 && isNumeric(UPIPIN)) {
                return true;
            } else {
                System.out.println("Invalid UPI PIN.UPI PIN must be exactly 6 digits long.");
                return false;
            }
        }
    }

    private boolean validateCardno() {
        while (true) {
            System.out.println("Enter Credit Card NO : ");
            sc.nextLine();
            String Creditno = sc.nextLine();
            if (Creditno.length() == 16 && isNumeric(Creditno)) {
                return true;
            } else {
                System.out.println("Invalid Credit Card number.Credit Card number must be exactly 16 digits long.");
                return false;
            }
        }
    }

    private void viewProfile() {
        System.out.println("\nYour profile: ");
        System.out.println("ID: " + getID());
        System.out.println("Name: " + getName());
        System.out.println("Password: "+ getPassword());
        System.out.println("Phone Number: "+ getPhoneNumber());
        System.out.println("Email: "+ getEmailID());
        System.out.println("Address: "+ getAddress());
        System.out.println("Age: "+ getAge());
        System.out.println("Gender: " + getAge());
        System.out.println("Preffered Locality: "+ getPreferredLocality());
        System.out.println("Verified Status: "+ getVerifiedStatus());
    }
    
    private void editProfile() {
        System.out.println("\nEdit Profile: ");
        System.out.println("1. Name");
        System.out.println("2. Phone Number");
        System.out.println("3. Email ID");
        System.out.println("4. Address");
        System.out.println("5. Preferred Locality");
        System.out.println("6. Password");
        System.out.println("7. BioData");
        System.out.println("8. Back");
        System.out.println("Enter your choice: ");
        try {
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter new name: ");
                    String uname = sc.next();
                    setName(uname);
                    db.updateUser(this);
                }
                case 2 -> {
                    System.out.println("Enter new phone number: ");
                    String uphoneNumber = sc.next();
                    setPhoneNumber(uphoneNumber);
                    db.updateUser(this);
                }
                case 3 -> {
                    System.out.println("Enter new email ID: ");
                    String uemailID = sc.next();
                    setEmailID(uemailID);
                    db.updateUser(this);
                }
                case 4 -> {
                    System.out.println("Enter new address: ");
                    String uaddress = sc.next();
                    setAddress(uaddress);
                    db.updateUser(this);
                }
                case 5 -> {
                    System.out.println("Enter new preferred locality: ");
                    String upreferredLocality = sc.next();
                    setPreferredLocality(upreferredLocality);
                    db.updateTenant(this);
                }
                case 6 -> {
                    System.out.println("Enter new password: ");
                    String upassword = sc.next();
                    setPassword(upassword);
                    db.updateUser(this);
                }
                case 7 -> {
                    System.out.println("Enter new bio data file path: ");
                    String bioFileName = sc.next();
                    File f = new File(bioFileName);
                    if (f.exists()) {
                        setBiodata(f);
                        setVerifiedStatus(true);
                        db.updateTenant(this);
                    } else {
                        System.out.println("File not found");
                    }
                }
                case 8 -> System.out.println("Back to main menu");
                default -> System.out.println("Invalid choice");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! enter integer input only");
        }
    }
}