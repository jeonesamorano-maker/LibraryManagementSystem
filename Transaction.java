/*Ang Transaction class nagrekord sa paghulam ug pagbalik sa libro.
naa sab ni transactionId, userId, bookId, dateBorrowed, ug dateReturned aron masubay 
 kinsa ang nanghulam ug kanus-a gibalik ang libro. Ang displayTransaction() mopakita sa detalye 
 sa transaksiyon, ug kung wala pa nabalik, ipakita nga “Not Returned.”
 */


public class Transaction {
    private String transactionId;
    private String userId;
    private String bookId;
    private String dateBorrowed;
    private String dateReturned;

    public Transaction(String transactionId, String userId, String bookId, String dateBorrowed, String dateReturned) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
    }

    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }
    public String getDateBorrowed() { return dateBorrowed; }
    public String getDateReturned() { return dateReturned; }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    public void displayTransaction() {
        System.out.printf("%s | User: %s | Book: %s | Borrowed: %s | Returned: %s\n",
                transactionId, userId, bookId, dateBorrowed, (dateReturned == null ? "Not Returned" : dateReturned));
    }
}
