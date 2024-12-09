import java.util.Date;

public class Transaction {
    int transactionID;
    int studentID;
    String paymentMethod;
    double amount;
    Date date;
    String detail;
    
    public Transaction(int transactionID, int studentID, double amount, Date date, String paymentMethod, String detail) {
        this.transactionID = transactionID;
        this.studentID = studentID;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.date = date;
        this.detail = detail;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return "Transaction ID: "+transactionID + "\nStudent ID: " + studentID + "\nPayment Method: " + paymentMethod + "\nAmount: " + amount + "\nDate: " + date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()-100) + "\nDetail: " + detail + "\n---------------------------------------------";
    }
}