/* Kaning Book class nagrepresentar sa usa ka libro sulod sa Library Management System.
 naa ni upat ka main nga data ang: bookId, title, author,
  ug kung ang libro kay libre pa bang mahulaman o 
 nahulaman na. Sa constructor, gisulod ang mga impormasyon sa dihang maghimo ug bag-ong libro.
  Adunay kini mga getter methods aron makuha angmga data, ug setter methods aron mausab ang title, 
  author, o status sa libro. Ang displayBookDetails() nga methods gigamit aron ipakita ang impormasyon 
  sa libro sa klaro ug maayong format sulod sa console, uban sa status kung “Available” pa o 
  “Borrowed” na. Sa kinatibuk-an, kini nga klase mao ang modelo sa usa ka libro nga gigamit sa tibuok library system.
 */

public class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean available;

    public Book(String bookId, String title, String author, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = available;
    }


    public String getBookId() { return bookId; }
    public String getTitle()  { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setAvailable(boolean available) { this.available = available; }

    public void displayBookDetails() {
        System.out.printf("%-5s | %-30s | %-20s | %s%n",
                bookId,
                title.length() > 30 ? title.substring(0, 27) + "..." : title,
                author.length() > 20 ? author.substring(0, 17) + "..." : author,
                (available ? "Available" : "Borrowed"));
    }
}
