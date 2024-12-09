public class Feedback {
    int feedbackID;
    int roomID;
    int tenantID;
    int rating;

    public int getFeedbackID() {
        return feedbackID;
    }
    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }
    public int getRoomID() {
        return roomID;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public int getTenantID() {
        return tenantID;
    }
    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public Feedback(int feedbackID, int roomID, int tenantID, int rating) {
        this.feedbackID = feedbackID;
        this.roomID = roomID;
        this.tenantID = tenantID;
        this.rating = rating;
    }
}
