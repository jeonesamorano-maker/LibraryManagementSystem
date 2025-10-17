/*Ang Person nga class nagsilbi nga base class o “parent class” alang sa ubang klase sama sa User. 
   Kini nagrepresentar sa usa ka tawo nga adunay duha ka common nga impormasyon: id,
   ug name. Sa constructor, gi-assign kini nga mga value sa dihang maghimo ug bag-ong
   object. Naa ni mga getter methods (getId() ug getName()) aron makuha ang ID ug ngalan,
   ug usa ka displayInfo() nga methods aron ipakita kini nga impormasyon sa console. 
   Sa kinatibuk-an, ang Person class gigamit aron mahimong batakang methods sa mga tawo sulod sa system,
   aron magamit usab sa mga klase nga nagmana niini. 
 */


public class Person {
    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name);
    }
}
