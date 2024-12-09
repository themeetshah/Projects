
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

class Database {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/pg", "root", "");
    }

    public int getUserID() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(userID) FROM users;");
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int addUser(User user) {
        try {
            String q = "Insert into users(userID, password,role,name,phoneNo,address,age,gender,emailId) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = getConnection().prepareStatement(q);
            pst.setInt(1,user.getID());
            pst.setString(2,user.getPassword());
            pst.setString(3,user.getRole());
            pst.setString(4,user.getName());
            pst.setString(5,user.getPhoneNumber());
            pst.setString(6,user.getAddress());
            pst.setInt(7,user.getAge());
            pst.setString(8,user.getGender());
            pst.setString(9,user.getEmailID());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addUser: "+e.getMessage());
            return 0;
        }
    }
    
    public int updateUser(User user) {
        try {
            String q = "Update users set password=?, name=?, phoneNo=?, address=?, emailId=? where userID=?";
            PreparedStatement pst = getConnection().prepareStatement(q);
            pst.setString(1,user.getPassword());
            pst.setString(2,user.getName());
            pst.setString(3,user.getPhoneNumber());
            pst.setString(4,user.getAddress());
            pst.setString(5,user.getEmailID());
            pst.setInt(6,user.getID());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addUser: "+e.getMessage());
            return 0;
        }
    }
    
    public boolean checkEmailExist(String email) {
        try {
            String q = "SELECT COUNT(*) FROM users WHERE emailId = ?";
            PreparedStatement pst = getConnection().prepareStatement(q);
            pst.setString(1,email);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return rs.getInt(1)>0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("checkEmailExist: "+e.getMessage());
            return false;
        }     
    }
    
    public int addTenant(Tenant tenant) {
        try {
            String q2 = "Insert into tenants(tenantID,biodata,locality,paymentId,verifiedStatus) values (?,?,?,?,?)";
            PreparedStatement pst = getConnection().prepareStatement(q2);
            pst.setInt(1, tenant.getID());
            pst.setClob(2, (tenant.getBiodata()!=null)?(new FileReader(tenant.getBiodata())):(null));
            pst.setString(3, tenant.getPreferredLocality());
            pst.setInt(4, tenant.getPaymentId());
            pst.setBoolean(5, tenant.getVerifiedStatus());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addTenant: SQL: "+e.getMessage());
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("addTenant: IO: "+e.getMessage());
            return 0;
        }
    }
    
    public int updateTenant(Tenant tenant) {
        try {
            String q2 = "Update tenants set locality=?, biodata=?, verifiedStatus=?, paymentId=? where tenantID=?";
            PreparedStatement pst = getConnection().prepareStatement(q2);
            pst.setString(1, tenant.getPreferredLocality());
            pst.setClob(2, (tenant.getBiodata()!=null)?(new FileReader(tenant.getBiodata())):(null));
            pst.setBoolean(3, tenant.getVerifiedStatus());
            pst.setInt(4, tenant.getPaymentId());
            pst.setInt(5, tenant.getID());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateTenant: SQL: "+e.getMessage());
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("updateTenant: IO: "+e.getMessage());
            return 0;
        }
    }
    
    public int removeUser(int userID) {
        try {
            String q2 = "Delete from User where userID=?";
            PreparedStatement pst = getConnection().prepareStatement(q2);
            pst.setInt(1, userID);
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("removeUser: SQL: "+e.getMessage());
            return 0;
        }
    }

    public int deletePaymentTenant(int ID) {
        try {
            CallableStatement  cst = getConnection().prepareCall("call delete_from_payment_tenant(?)");
            cst.setInt(1, ID);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deletePaymentTenant: SQL: "+e.getMessage());
            return 0;
        }
    }

    public int deletePaymentLandlord(int ID) {
        try {
            CallableStatement  cst = getConnection().prepareCall("call delete_from_payment_landlord(?)");
            cst.setInt(1, ID);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deletePaymentLandlord: SQL: "+e.getMessage());
            return 0;
        }
    }

    public int deletePaymentRoom(int ID) {
        try {
            CallableStatement  cst = getConnection().prepareCall("call delete_from_payment_room(?)");
            cst.setInt(1, ID);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deletePaymentRoom: SQL: "+e.getMessage());
            return 0;
        }
    }

    public int setPaymentId(int ID) {
        try {
            CallableStatement  cst = getConnection().prepareCall("call set_paymentId_room(?)");
            cst.setInt(1, ID);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("setPaymentId: SQL: "+e.getMessage());
            return 0;
        }
    }
    
    public int addLandlord(Landlord landlord) {
        try {
            String q2 = "Insert into landlords(landlordID,rating,negotiation,landlordAcctNo) values (?,?,?,?)";
            PreparedStatement pst = getConnection().prepareStatement(q2);
            pst.setInt(1, landlord.getID());
            pst.setInt(2, landlord.getRating());
            pst.setBoolean(3, landlord.getNegotiation());
            pst.setString(4, landlord.getLandlordAcctNo());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addLandlord: SQL: "+e.getMessage());
            return 0;
        }
    }

    public int getPaymentID() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(paymentid) FROM payments");
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public ResultSet getTenantDetailsByRoom(int roomID) {
        try {
            String q2 = "Select * from Tenants where tenantID IN (Select tenantID from payments where roomID = ?)";
            PreparedStatement pst = getConnection().prepareStatement(q2);
            pst.setInt(1, roomID);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("getTenantDetailsByRoom: SQL: " + e.getMessage());
            return null;
        }
    }
    
    public int getRoomID() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(roomID) FROM rooms");
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int addRoom(Room room) {
        try {
            String q2 = "Insert into rooms(roomID,locality,feedbackRating, amenitiesRating ,type, rent, availability, vacant_space,image, amenities, landlordId) values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = getConnection().prepareStatement(q2);
            pst.setInt(1, room.getRoomId());
            pst.setString(2, room.getLocality());
            pst.setDouble(3, room.getFeedbackRating());
            pst.setInt(4, room.getAmenitiesRating());
            pst.setString(5, room.getType());
            pst.setDouble(6, room.getRent());
            pst.setBoolean(7, room.getAvailability());
            pst.setInt(8, room.getCapacity() - room.getSize());
            pst.setBlob(9, new FileInputStream(room.getImage()));
            pst.setString(10, room.getAmenities());
            pst.setInt(11, room.getLandlordId());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addRoom: SQL:"+e.getMessage());
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("addRoom: IO: " + e.getMessage());
            return 0;
        }
    }
    
    public void updateRoomBookingDetails(Tenant tenant, Room data, Payment payment) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("Insert into payments values (?,?,?,?,?,?,?)");
            pstmt.setInt(1, payment.getPaymentId());
            pstmt.setString(2, payment.getPaymentMode());
            pstmt.setInt(3, data.getLandlordId());
            pstmt.setInt(4, tenant.getID());
            pstmt.setInt(5, data.getRoomId());
            pstmt.setDouble(6, payment.getTransactionamount());
            pstmt.setDate(7, new java.sql.Date(payment.getTransactionDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRoom(Room data) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE rooms SET vacant_space = ?, rent = ?, availability = ?, amenities=? WHERE roomid = ?");
            pstmt.setInt(1, data.getCapacity() - data.getSize());
            pstmt.setDouble(2, data.getRent());
            pstmt.setBoolean(3, data.getAvailability());
            pstmt.setString(4, data.getAmenities());
            pstmt.setInt(5, data.getRoomId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet getRooms(int landlordID) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM rooms WHERE landlordid = ?");
            pstmt.setInt(1, landlordID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("getRooms: " + e.getMessage());
            return null;
        }
    }
    
    public ResultSet getFeedbacksByRoom(int roomID) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM feedbacks WHERE roomid = ?");
            pstmt.setInt(1, roomID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("getFeedbacksByRoom: " + e.getMessage());
            return null;
        }
    }

    public ResultSet fetchUserData()
    {
        try{
            Connection conn = getConnection();
           PreparedStatement pst = conn.prepareStatement("SELECT * FROM users");
            return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("fetchUserData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet fetchLandlordData()
    {
        try{
            Connection conn = getConnection();
           PreparedStatement pst = conn.prepareStatement("SELECT * FROM landlords INNER JOIN USERS ON LANDLORDS.landlordID = USERS.USERID");
            return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("fetchLandlordData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet fetchTenantData()
    {
        try{
            Connection conn = getConnection();
           PreparedStatement pst = conn.prepareStatement("SELECT * FROM TENANTS INNER JOIN USERS ON TENANTS.tenantID = USERS.USERID");
            return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("fetchTenantData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet fetchRoomData()
    {
        try{
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM ROOMS");
             return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("fetchRoomData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet fetchFeedbackData()
    {
        try{
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM FEEDBACKS");
             return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("fetchRoomData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet fetchPaymentData()
    {
        try{
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM PAYMENTS");
             return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("fetchPaymentData: "+e.getMessage());
            return null;
        }
    }
    // ---------------------------------------


    public ResultSet viewTenantByTenanatId(int Tenant_id)
        {
            try{
            PreparedStatement pst= getConnection().prepareStatement("Select * from Tenant inner join Users on Users.userId = Tenant.userId where Tenant.tenantId=?");
            pst.setInt(1,Tenant_id);
            return pst.executeQuery();    
        }
        catch(SQLException e)
        {
            System.out.println("viewTenantByTenantId: "+e.getMessage());
            return null;
        }
    }
    
    public ResultSet viewTenantByTenantName(String Tenant_name)
    {
        try{
        PreparedStatement pst= getConnection().prepareStatement("Select * from Tenant inner join Users on Users.userId = Tenant.userId where Users.name=?");
        pst.setString(1,Tenant_name);
        return pst.executeQuery();    
        }
        catch(SQLException e)
        {
            System.out.println("viewTenantByTenantName: "+e.getMessage());
            return null;
        }
    }
    public ResultSet viewTenantByTenantVerifiedStatus(boolean Tenant_verifiedStatus)
    {
        try{
        PreparedStatement pst= getConnection().prepareStatement("Select * from Tenant inner join Users on Users.userId = Tenant.userId where Tenant.verifiedStatus=?");
        pst.setBoolean(1,Tenant_verifiedStatus);
        return pst.executeQuery();    
        }
        catch(SQLException e)
        {
        System.out.println("viewTenantByTenantVerifiedStatus: "+e.getMessage());
        return null;
        }
    }
    public ResultSet viewTenantByTenantRoomId(int Tenant_roomId)
    {
        try{
        PreparedStatement pst= getConnection().prepareStatement("Select * from Tenant inner join Users on Users.userId = Tenant.userId where Tenant.paymentId In (select payementId from payment where roomId=?)");
        pst.setInt(1,Tenant_roomId);
        return pst.executeQuery();    
        }
        catch(SQLException e)
        {
        System.out.println("viewTenantByTenantRoomId: "+e.getMessage());
        return null;
        }
    }
    public ResultSet viewAllTenant()
    {
        try{
        PreparedStatement pst= getConnection().prepareStatement("Select * from Tenant inner join Users on Users.userId = Tenant.userId ");
        return pst.executeQuery();    
        }
        catch(SQLException e)
        {
        System.out.println("viewAllTenant: "+e.getMessage());
        return null;
        }
    }
    public ResultSet viewRoomsByRoomId(int room_id)
    {
        try {
            String q = "Select * from rooms where roomId = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setInt(1,room_id);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewRoomsByRoomId: " + e.getMessage());
            return null;
        }
    }
    public ResultSet viewRoomsByLocality(String locality)
    {
        try {
            String q = "Select * from rooms where locality = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setString(1,locality);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewRoomsByLocality: " + e.getMessage());
            return null;
        }
    }

    public ResultSet viewRoomsByRoomType(String type)
    {
        try {
            String q = "Select * from rooms where type = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setString(1,type);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewRoomsByRoomType: " + e.getMessage());
            return null;
        }
    }

    public ResultSet viewRoomsByRoomsRent(double rent)
    {
        try {
            String q = "Select * from rooms where rent = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setDouble(1,rent);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewRoomsByRoomsRent: " + e.getMessage());
            return null;
        }
    }

    public ResultSet viewRoomsByRating(int rating)
    {
        try {
            String q = "Select * from rooms where rating = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setInt(1,rating);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewRoomsByRating: " + e.getMessage());
            return null;
        }
    }

    public ResultSet viewAllRooms()
    {
        try {
            String q = "Select * from rooms";
        PreparedStatement pst = getConnection().prepareStatement(q);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewAllRooms: " + e.getMessage());
            return null;
        }
    }
    public ResultSet viewLandlordsById(int landlordId)
    {
        try {
            String q = "Select * from Landlord inner join users on Landlord.userid = Users.userid where Landlord.landlordId = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setInt(1,landlordId);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewLandlordsById: " + e.getMessage());
            return null;
        }
    }

    public ResultSet viewLandlordsByLandlordName(String landlordName)
    {
        try {
            String q = "Select * from Landlord inner join users on Landlord.userid = Users.userid where Users.name = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setString(1,landlordName);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewLandlordsByLandlordName: " + e.getMessage());
            return null;
        }
    }

    public ResultSet viewLandlordsByNoOfProperties(int noOfProperties)
    {
        try {
            String q = "Select * from Landlord inner join users on Landlord.userid = Users.userid where landLordId in (Select landLordId from rooms group by landLordId having count(roomId) = ?)";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setInt(1,noOfProperties);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewLandlordsByNoOfProperties: " + e.getMessage());
            return null;
        }
    }


    public ResultSet viewLandlordsByRating(int rating)
    {
        try {
            String q = "Select * from Landlord inner join users on Landlord.userid = Users.userid where Landlord.rating = ?";
        PreparedStatement pst = getConnection().prepareStatement(q);
        pst.setInt(1,rating);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("viewLandlordsByRating: " + e.getMessage());
            return null;
        }
    }

    public ResultSet viewAllLandlords()
    {
        try {
            String q = "Select * from Landlord inner join users on Landlord.userid = Users.userid";
        PreparedStatement pst = getConnection().prepareStatement(q);

        return pst.executeQuery();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("vviewAllLandlords: " + e.getMessage());
            return null;
        }
    }
    public ResultSet getLandlordData()
    {
        try{
            Connection conn = getConnection();
           PreparedStatement pst = conn.prepareStatement("SELECT * FROM lANDLORDS INNER JOIN USERS ON LANDLORDS.USERID = USERS.USERID");
            return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("getLandlordData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet getTenantData()
    {
        try{
            Connection conn = getConnection();
           PreparedStatement pst = conn.prepareStatement("SELECT * FROM TENANTS INNER JOIN USERS ON TENANTS.USERID = USERS.USERID");
            return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("getTenantData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet getRoomData()
    {
        try{
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM ROOMS");
             return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("getRoomData: "+e.getMessage());
            return null;
        }
    }
    public ResultSet getfeedbackData()
    {
        try{
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM FEEDBACKS");
             return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("getRoomData: "+e.getMessage());
            return null;
        }
    }

    public ResultSet getPaymentData()
    {
        try{
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM PAYMENTS");
             return pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("getRoomData: "+e.getMessage());
            return null;
        }
    }

    public int removeLandlord(int landlordId)
    {
        try {
            CallableStatement cst = getConnection().prepareCall("{call delete_landlord(?)}");
            cst.setInt(1, landlordId);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("removeLandlord: SQL: "+e.getMessage());
            return 0;
        }
    }

    public int removeTenant(int tenantId)
    {
        try {
            CallableStatement cst = getConnection().prepareCall("{call delete_tenant(?)}");
            cst.setInt(1, tenantId);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("removeTenant: SQL: "+e.getMessage());
            return 0;
        }
    }

    public int removeRoom(int roomID)
    {
        try {
            CallableStatement cst = getConnection().prepareCall("{call delete_room(?)}");
            cst.setInt(1, roomID);
            return cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("removeRoom: SQL: " + e.getMessage());
            return 0;
        }
    }
    
    public int getTenantRoom(Tenant tenant) {
        try {
            PreparedStatement pst = getConnection().prepareStatement("Select roomId from payments where tenantId = ?");
            pst.setInt(1, tenant.getID());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("roomId");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("getTenantRoom: SQL: " + e.getMessage());
            return 0;
        }
    }
    
    public int addFeedback(Feedback f) {
        try {
            PreparedStatement pst = getConnection()
                    .prepareStatement("Insert into feedbacks(feedbackId, roomId, tenantId, rating) values(?,?,?,?)");
            pst.setInt(1, f.getFeedbackID());
            pst.setInt(2, f.getRoomID());
            pst.setInt(3, f.getTenantID());
            pst.setInt(4, f.getRating());
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addFeedback: SQL: " + e.getMessage());
            return 0;
        }
    }
    
    
    public int getFeedbackID() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(feedbackId) FROM feedbacks");
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public void affectFeedback() {
        try {
            Statement st = getConnection().createStatement();
            st.executeUpdate("UPDATE rooms SET feedbackRating = (SELECT AVG(rating) FROM feedbacks WHERE roomId = rooms.roomId) WHERE roomId IN (SELECT roomId FROM feedbacks)");
        } catch (SQLException e) {
            System.out.println("affectFeedback: SQL: " + e.getMessage());
        }
    }
}
