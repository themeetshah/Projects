import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;  
import java.sql.SQLException;

public class Admin extends User {
    public Admin(int userID, String password, String role, String name, String phoneNumber, String emailID,
            String address, int age, String gender) {
        super(userID, password, role, name, phoneNumber, emailID, address, age, gender);
    }
    
    public void AdminMenu() {
        while (true) {
            BookMyPG.getUserData();
            BookMyPG.getLandlordData();
            BookMyPG.getTenantData();
            BookMyPG.getRoomData();
            BookMyPG.getPaymentData();
            BookMyPG.getFeedbackData();
            BookMyPG.db.affectFeedback();
            System.out.println("\nSelect an action: ");
            System.out.println(
                    "1. View Rooms\n 2. View Landlords\n3. View Tenants\n4. Add Tenants\n5. Remove Tenants\n6. Add Landlords\n7. Remove Landlords\n8. Remove Rooms\n9. View Feedback & Complaints\n10. View payment history \n11. exit");
            switch (sc.nextInt()) {
                case 1: 
                    viewRooms();
                    break;                 
                case 2:
                    viewLandlords();
                    break;
                case 3: 
                      viewTenants();
                      break;
                case 4:
                      addTenants(); 
                    break;
                case 5: 
                    System.out.println("Enter ID of the Tenant you want to remove: ");
                    int id = sc.nextInt();
                     removeTenant(id);
                     break;
                case 6:
                      addLandlords();
                      break;
                 case 7:System.out.println("Enter ID of the LandLord you want to remove: ");
                        int landlord_id = sc.nextInt();
                        removeLandlords(landlord_id); 
                        break;
                case 8:
                       removeRooms();
                       break;
                case 9:
                        viewFeedBackComplaints();
                        break;
                case 10: 
                        viewPaymentHistory();
                         break;
                case 11:  return;
                default: System.out.println("Invalid Input. Please enter valid Input");
                    break;
            }
        }
    }
    public void viewRooms()
    {
       System.out.println("1. by room id \n2. by room locality \n3. by room's type \n4. by room's rent \n5. by room rating \n6. all rooms");
       switch(sc.nextInt())
       {
          case 1: System.out.print("Enter id: ");
                   displayRoomsData(db.viewRoomsByRoomId(sc.nextInt()));
                 break;
         case 2: System.out.print("Enter room locality: ");
                  displayRoomsData(db.viewRoomsByLocality(sc.next()));
                 break;
        case 3:  System.out.print("Enter room type: ");
                 displayRoomsData(db.viewRoomsByRoomType(sc.next()));
                 break;
        case 4: System.out.print("Enter room's rent: ");
                displayRoomsData(db.viewRoomsByRoomsRent(sc.nextDouble()));
                 break;
        case 5: System.out.print("Enter room rating: ");
               displayRoomsData(db.viewRoomsByRating(sc.nextInt()));
               break;
        case 6:  displayRoomsData(db.viewAllRooms());
               break;
       }
    }
    public void viewLandlords()
    {
        System.out.println("1. by landlord id \n2. by landlord name \n3. by landlord's number of properties  \n4. by landlord rating \n5. all landlord ");
        switch(sc.nextInt())
        {
           case 1: System.out.print("Enter id: ");
                    displayLandlordData(db.viewLandlordsById(sc.nextInt()));
                  break;
           case 2: System.out.print("Enter Landlord name: ");
                  displayLandlordData(db.viewLandlordsByLandlordName(sc.nextLine()));
                  break;
           case 3:  System.out.print("Enter no. of properties of Landlord: ");
                  displayLandlordData(db.viewLandlordsByNoOfProperties(sc.nextInt()));
                  break;
            
        }
    }
    public void viewTenants()
    {
        System.out.println("1. by tenant id \n2. by tenant name \n3. by tenant's room id \n4. display tenant whose verified status is true \n5. All tenants ");
        switch(sc.nextInt())
        {
           case 1: System.out.print("Enter id: ");
                    displayTenantsData(db.viewTenantByTenanatId(sc.nextInt()));
                  break;
           case 2: System.out.print("Enter Tenant name: ");
           displayTenantsData(db.viewTenantByTenantName(sc.nextLine()));
                  break;
           case 3:  System.out.print("Enter tenant's room id: ");
           displayTenantsData(db.viewTenantByTenantRoomId(sc.nextInt()));
                  break;
           case 4: 
           displayTenantsData(db.viewTenantByTenantVerifiedStatus(true));
                  break;
           case 5:  displayTenantsData(db.viewAllTenant());
                break;
        }
    }
    public void addTenants()
    {
        BookMyPG.addTenant();
    }
    
    public void removeTenant(int tenantId)
    {
       BookMyPG.removeTenant(tenantId);
    }
    public void addLandlords()
    {
        BookMyPG.addLandlord();
    }
    public void removeLandlords(int landlordId)
    {
        BookMyPG.removeLandlord(landlordId);
    }
    public void removeRooms()
    {
        System.out.println("enter id of room: ");
        BookMyPG.removeRoom(sc.nextInt());
    }
    public void viewFeedBackComplaints()
    {
        BookMyPG.payment_queue.display();
    }
    public void viewPaymentHistory()
    {
        BookMyPG.feedback_queue.display();
    }
    public void displayRoomsData(ResultSet rs)
    {
      boolean b ;
      try {
            b = rs.next();
            if (b) {
                while (b) {
                    System.out.println("Room id: "+rs.getInt("roomId"));
                    System.out.println("Room's locality : "+rs.getString("locality"));
                    System.out.println("Room's feedback rating: "+rs.getDouble("feedbackRating"));
                    System.out.println("Room's amenities rating: "+rs.getInt("amenitiesRating"));
                    System.out.println("Room's Type: "+rs.getString("type"));
                    System.out.println("Room's Rent: "+rs.getDouble("rent"));
                    System.out.println("Room's availability: "+rs.getBoolean("availability"));
                    System.out.println("Room's vacant space: "+rs.getInt("vacant_space"));
                    File f = new File("D://"+rs.getInt(1)+"_Photo.png");
                    FileOutputStream fos = new FileOutputStream(f);
                    InputStream is = rs.getBinaryStream("image");
                    int j = is.read();
                    while(j!=-1)
                    {
                        fos.write(j);
                        j=is.read();
                    }
                    fos.close();
                    System.out.println("Image of the room saved in d drive: "+f.getAbsolutePath());
                    System.out.println("Room's Amenities: "+rs.getString("Amenities"));
                    System.out.println("Room's LandLord id: "+rs.getInt("landlordId"));
                    System.out.println();
                    b = rs.next();
                }
            } else {
                System.out.println("Room not found.");
            }
            rs.close();
        } catch (IOException | SQLException e) {
            System.out.println("displayRoomData: "+e.getMessage());
        }

    }
    public void displayLandlordData(ResultSet rs)
    {
        boolean b ;
        try {
              b = rs.next();
              if (b) {
                  while (b) {
                    System.out.println("User id : "+rs.getInt("userId"));
                    System.out.println(" Landord's password: "+rs.getString("password"));
                    System.out.println("Role: "+rs.getString("role"));
                    System.out.println("Name: "+rs.getString("name"));
                    System.out.println("Phone no: "+rs.getString("phoneno"));
                    System.out.println("Address: "+rs.getString("address"));
                    System.out.println("Age: "+rs.getInt("age"));
                    System.out.println("Gender: "+rs.getString("gender"));
                    System.out.println("Email id: "+rs.getString("emailId"));
                    System.out.println("Landlord id: "+rs.getInt("landlordId"));
                    System.out.println("Rating: "+rs.getString("rating"));
                    System.out.println("Negotiation: "+rs.getBoolean("negotiation"));
                    System.out.println("Landloard account no: "+rs.getString("landlordAcctNo"));
                  }   
              } else {
                  System.out.println("Landlord not found.");
              }
              rs.close();
          } catch (SQLException e) {
              System.out.println("displayLanlordData: "+e.getMessage());
          }
    }
    public void displayTenantsData(ResultSet rs)
    {
        boolean b ;
        try {
              b = rs.next();
              if (b) {
                  while (b) {
                    System.out.println("User id : "+rs.getInt("userId"));
                    System.out.println(" Tenant's password: "+rs.getString("password"));
                    System.out.println("Role: "+rs.getString("role"));
                    System.out.println("Name: "+rs.getString("name"));
                    System.out.println("Phone no: "+rs.getString("phoneno"));
                    System.out.println("Address: "+rs.getString("address"));
                    System.out.println("Age: "+rs.getInt("age"));
                    System.out.println("Gender: "+rs.getString("gender"));
                    System.out.println("Email id: "+rs.getString("emailId"));
                    System.out.println("Tenant's id: "+rs.getInt("tenantid"));
                    System.out.println("Tenant's Biodata");
                    File f = new File("D://"+rs.getInt("tenantId")+"_Tenant.txt");
                    FileReader fr = new FileReader(f);
                    int i = fr.read();
                    while(i!=-1)
                    {
                        System.out.println((char)i);
                        i=fr.read();
                    }
                    fr.close();
                    System.out.println("Locality: "+rs.getString("locality"));
                    System.out.println("Payment id: "+rs.getInt("paymentid"));
                    System.out.println("Tenant's verified status: "+rs.getBoolean("verifiedStatus"));
                  }   
              } else {
                  System.out.println("Landlord not found.");
              }
              rs.close();
          } catch (IOException | SQLException e) {
              System.out.println("displayTenantsData: "+e.getMessage());
          }
    }
}