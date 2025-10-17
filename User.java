import java.util.ArrayList;

/*Ang User class nagrepresentar sa usa ka tiggamit sa library system, 
nga nagmana sa Person class.Naa sab ni password alang sa login, 
role (admin o user), ug listahan sa borrowedBooks aron masubay ang mga gihulam nga libro.
 Ang displayInfo() nga methods mopakita sa user ID, ngalan, ug papel (role) sa system.
 
 */
public class User extends Person {
    private String password;
    private String role;
    private ArrayList<String> borrowedBooks = new ArrayList<>();

    public User(String id, String name, String password, String role) {
        super(id, name);
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public ArrayList<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void displayInfo() {
        System.out.println("User ID: " + id + ", Name: " + name + ", Role: " + role);
    }
}
