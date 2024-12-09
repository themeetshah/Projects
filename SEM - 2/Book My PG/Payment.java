import java.util.*;

class Payment {
    int paymentId;
    String paymentMode;
    int landlordId;
    int tenantId;
    int roomId;
    double transactionamount;
    Date transactionDate;

    Payment(int paymentId, String paymentMode, int landlordId, int tenantId, int roomId, double transactionamount, Date transactionDate) {
        this.paymentId = paymentId;
        this.paymentMode = paymentMode;
        this.landlordId = landlordId;
        this.tenantId = tenantId;
        this.roomId = roomId;
        this.transactionamount = transactionamount;
        this.transactionDate = transactionDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public int getLandlordId() {
        return landlordId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public int getRoomId() {
        return roomId;
    }

    public double getTransactionamount() {
        return transactionamount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
