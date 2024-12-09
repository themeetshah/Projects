import java.io.File;

class Room {
    int roomId;
    File image;
    String locality;
    double rent;
    double feedbackRating;
    int amenitiesRating;
    boolean availability;
    String type;
    String amenities;
    int capacity;
    int size;
    int landlordId;
    
    public Room(int roomId, File image, String locality, double rent, int amenitiesRating, double feedbackRating, boolean availability, String type, String amenities, int size, int landlordId) {
        this.roomId = roomId;
        this.image = image;
        this.locality = locality;
        this.rent = rent;
        this.amenitiesRating = amenitiesRating;
        this.feedbackRating = feedbackRating;
        this.availability = availability;
        this.type = type;
        this.amenities = amenities;
        this.size = size;
        this.capacity = Integer.parseInt((type.split("-"))[0]);
        this.landlordId = landlordId;
    }
    
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(double feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public int getAmenitiesRating() {
        return amenitiesRating;
    }

    public void setAmenitiesRating(int amenitiesRating) {
        this.amenitiesRating = amenitiesRating;
    }

    public boolean getAvailability() {
        return availability;
    }
    
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public int getLandlordId() {
        return landlordId;
    }
    
    @Override
    public String toString() {
        return "Room ID: " + roomId + "\n" + "Locality: " + locality + "\n" + "Rent: " + rent + "\n" + "AmenitiesRating: " + amenitiesRating + "\n" + "FeedbackRating: " + feedbackRating + "\n" + "Availability: " + availability + "\n" + "Type: " + type + "\n" + "Members Left: " + size + "\n" + "Amenities: " + amenities;
    }
}