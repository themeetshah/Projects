import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Landlord extends User {

    int rating;
    boolean negotiation;
    String landlordAcctNo;

    public Landlord(int userID, String password, String role, String name, String phoneNumber, String emailID, String address, int age, String gender, int rating, boolean negotiation, String landlordAcctNo) {
        super(userID, password, role, name, phoneNumber, emailID, address, age, gender);
        this.rating = rating;
        this.negotiation = negotiation;
        this.landlordAcctNo = landlordAcctNo;
    }

    public int getRating() {
        return rating;
    }

    public boolean getNegotiation() {
        return negotiation;
    }

    public String getLandlordAcctNo() {
        return landlordAcctNo;
    }
    
    
    public void LandlordMenu() {
        while (true) {
            BookMyPG.getUserData();
            BookMyPG.getLandlordData();
            BookMyPG.getTenantData();
            BookMyPG.getRoomData();
            BookMyPG.getPaymentData();
            BookMyPG.getFeedbackData();
            BookMyPG.db.affectFeedback();
            System.out.println("\nChoose an action");
            System.out.println("1. Add room");
            System.out.println("2. Remove Room");
            System.out.println("3. View All Properties");
            System.out.println("4. Edit Room");
            System.out.println("5. View Tenant Details");
            System.out.println("6. View Feedback");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            try {
                switch (sc.nextInt()) {
                    case 1 -> addRoom();
                    
                    case 2 -> removeRoom();
                    
                    case 3 -> viewAllProperties();
                    
                    case 4 -> editRoom();
                    
                    case 5 -> viewTenantDetails();
                    
                case 6 -> viewFeedbacks();

                case 7 -> {
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
    
    public void addRoom() {
        System.out.print("Enter room locality: ");
        String roomLocality = sc.next();
        int amenitiesRating = 0;
        double feedbackRating = 0;
        double roomRent = 0;
        String roomType = "";
        String amenities =  "";

        while (true) {
            System.out.println("Choose Amenities Rating of your room:");
            System.out.println("1. 1 star");
            System.out.println("2. 2 star");
            System.out.println("3. 3 star");
            System.out.println("4. 4 star");
            System.out.println("5. 5 star");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        amenitiesRating = 1;
                        amenities = "No Cupboard";
                        System.out.println("Amenities: " + amenities);
                        roomType = "4-sharing";
                        roomRent = 5000;
                    }
                    case 2 -> {
                        amenitiesRating = 2;
                        amenities = "Common Washroom";
                        System.out.println("Amenities: " + amenities);
                        System.out.println("Choose type: ");
                        System.out.println("1. 3-sharing");
                        System.out.println("2. 4-sharing");
                        System.out.print("Enter choice: ");
                        int typeChoice = sc.nextInt();
                        switch (typeChoice) {
                            case 1 -> {
                                roomType = "3-sharing";
                                roomRent = 14000;
                            }
                            case 2 -> {
                                roomType = "4-sharing";
                                roomRent = 10000;
                            }
                            default -> {
                                System.out.println("Invalid choice");
                                continue; 
                            }
                        }
                    }
                    case 3 -> {
                        amenitiesRating = 3;
                        amenities = "Study-table, Common Washroom";
                        System.out.println("Amenities: " + amenities);
                        System.out.println("Choose type: ");
                        System.out.println("1. 3-sharing");
                        System.out.println("2. 4-sharing");
                        System.out.print("Enter choice: ");
                        int typeChoice = sc.nextInt();
                        switch (typeChoice) {
                            case 1 -> {
                                roomType = "3-sharing";
                                roomRent = 18000;
                            }
                            case 2 -> {
                                roomType = "4-sharing";
                                roomRent = 12000;
                            }
                            default -> {
                                System.out.println("Invalid choice");
                                continue; 
                            }
                        }
                    }
                    case 4 -> {
                        amenitiesRating = 4;
                        amenities = "Attached Washroom, Study-table, Common Washroom";
                        System.out.println("Amenities: " + amenities);
                        System.out.print("Do you want to add other amenities: ");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Enter your choice: ");
                        if (sc.nextInt() == 1) {
                            amenities = addAmenities(amenities);
                        }
                        System.out.println("Choose type: ");
                        System.out.println("1. 2-sharing");
                        System.out.println("2. 3-sharing");
                        System.out.println("3. 4-sharing");
                        System.out.print("Enter choice: ");
                        int typeChoice = sc.nextInt();
                        switch (typeChoice) {
                            case 1 -> {
                                roomType = "2-sharing";
                                roomRent = 22000;
                            }
                            case 2 -> {
                                roomType = "3-sharing";
                                roomRent = 19000;
                            }
                            case 3 -> {
                                roomType = "4-sharing";
                                roomRent = 15000;
                            }
                            default -> {
                                System.out.println("Invalid choice");
                                continue; 
                            }
                        }
                    }
                    case 5 -> {
                        amenitiesRating = 5;
                        amenities = "Geyser, Attached Washroom, Study-table, Common Washroom";
                        System.out.println("Amenities: " + amenities);
                        System.out.print("Do you want to add other amenities: ");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Enter your choice: ");
                        if (sc.nextInt() == 1) {
                            amenities = addAmenities(amenities);
                        }
                        System.out.println("Choose type: ");
                        System.out.println("1. 1-sharing");
                        System.out.println("2. 2-sharing");
                        System.out.println("3. 3-sharing");
                        System.out.println("4. 4-sharing");
                        System.out.print("Enter choice: ");
                        int typeChoice = sc.nextInt();
                        switch (typeChoice) {
                            case 1 -> {
                                roomType = "1-sharing";
                                roomRent = 31000;
                            }
                            case 2 -> {
                                roomType = "2-sharing";
                                roomRent = 25000;
                            }
                            case 3 -> {
                                roomType = "3-sharing";
                                roomRent = 21000;
                            }
                            case 4 -> {
                                roomType = "4-sharing";
                                roomRent = 18000;
                            }
                            default -> {
                                System.out.println("Invalid choice");
                                continue; 
                            }
                        }
                    }
                    default -> {
                        System.out.println("Invalid choice");
                        continue; 
                    }
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter integer input.");
                sc.nextLine(); 
            }
        }
    
        File f;
        while (true) {
            System.out.print("Enter image file path for room: ");
            String imagePath = sc.nextLine();
            f = new File(imagePath);
            if (f.exists()) {
                break;
            } else {
                System.out.println("File does not exist. Please try again.");
            }
        }
        Room room = new Room(db.getRoomID(), f, roomLocality, roomRent, amenitiesRating, feedbackRating, true, roomType,
                amenities, 0, getID());
        System.out.println(room.getCapacity()+" "+room.getSize());
        db.addRoom(room);
        BookMyPG.rooms.add(room);
    }

    
    public String addAmenities(String amenities) {
        System.out.println("enter number of extra amenities: ");
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter extra amenities: ");
            String addon = sc.next();
            String[] arr = amenities.split(", ");
            try{
                for (int j = 0; j < amenities.length(); j++) {
                    if (!arr[j].equals(addon)) {
                        amenities += ", " + addon;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("addAmenities: "+e.getMessage());
            }
        }
        return amenities;
    }
    
    public void removeRoom() {
        System.out.println("Enter roomID: ");
        int roomID = sc.nextInt();
        Room room = BookMyPG.getRoom(roomID);
        if (room != null) {
            if (getID() == room.getLandlordId()) {
                BookMyPG.removeRoom(roomID);
            } else {
                System.out.println("You don't have permission to remove this room.");
            }
        }
    }
    
    public void viewAllProperties() {
        ResultSet rs = db.getRooms(getID());
        try {
            boolean b = rs.next();
            if (b) {
                System.out.println("RoomID\tRoomType\tRoomRent\tRoomStatus\tAmenitiesRating\tFeedbackRating\tAmenities");
                while (b) {
                    System.out.println(rs.getInt("roomID") + "\t" + rs.getString("type") + "\t" + rs.getInt("rent") + "\t" + rs.getBoolean("availability") + "\t" + rs.getInt("amenitiesRating") + "\t" + rs.getInt("feedbackRating") + "\t" + rs.getString("amenities"));
                    b = rs.next();
                }
            } else {
                System.out.println("No rooms found.");
            }
        } catch (SQLException e) {
            System.out.println("viewAllProperties: " + e.getMessage());
        }
    }
    
    public void editRoom() {
        System.out.print("Enter roomID: ");
        int roomID = sc.nextInt();
        Room room = BookMyPG.getRoom(roomID);
        if (room != null) {
            if (getID() == room.getLandlordId()) {
                System.out.println("\nChoose an option: ");
                System.out.println("1. Edit Room Rent");
                System.out.println("2. Add Amenities");
                System.out.print("Enter your choice: ");
                try {
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1 -> {
                            System.out.print("Enter new room rent: ");
                            double newRent = sc.nextDouble();
                            room.setRent(newRent);
                            db.updateRoom(room);
                        }
                        case 2 -> { 
                            room.setAmenities(addAmenities(room.getAmenities()));
                            db.updateRoom(room);
                        }

                        default -> System.out.println("Invalid choice.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("enter valid option");
                    e.printStackTrace();
                }
            } else {
                System.out.println("You don't have permission to edit this room.");
            }
        }
    }
    
    public void viewTenantDetails() {
        System.out.print("Enter roomID: ");
        int roomID = sc.nextInt();
        Room room = BookMyPG.getRoom(roomID);
        if (room != null) {
            if (room.getLandlordId() == getID()) {
                ResultSet rs = db.getTenantDetailsByRoom(roomID);
                boolean b;
                try {
                    b = rs.next();
                    if (b) {
                        System.out.println("\nTenant Details:");
                        System.out.println("Tenant ID: " + rs.getInt("tenant_id"));
                        System.out.println("Name: " + rs.getString("name"));
                        System.out.println("Phone Number: " + rs.getString("phone_number"));
                        System.out.println("Email: " + rs.getString("email"));
                        System.out.println("Address: " + rs.getString("address"));
                    } else {
                        System.out.println("No tenant found for this room.");
                    }
                } catch (SQLException e) {
                    System.out.println("viewTenantDetails: " + e.getMessage());
                }
            } else {
                System.out.println("You don't have permission to view this room.");
            }
        } else {
            System.out.println("Room not found.");
        }
    }
    
    public void viewFeedbacks() {
        System.out.print("Enter roomID: ");
        int roomID = sc.nextInt();
        Room room = BookMyPG.getRoom(roomID);
        if (room != null) {
            if (room.getLandlordId() == getID()) {
                ResultSet rs = db.getFeedbacksByRoom(roomID);
                boolean b;
                try {
                    b = rs.next();
                    if (b) {
                        System.out.println("\nFeedbacks:");
                        while (b) {
                            System.out.print("Feedback ID: " + rs.getInt("feedbackID") + ", ");
                            System.out.println("Rating: " + rs.getInt("rating"));
                            b = rs.next();
                        }
                    } else {
                        System.out.println("No feedbacks found for this room.");
                    }
                } catch (SQLException e) {
                    System.out.println("viewFeedbacks: " + e.getMessage());
                }
            } else {
                System.out.println("You don't have permission to view this room.");
            }
        } else {
            System.out.println("Room not found.");
        }                                   
    }
}