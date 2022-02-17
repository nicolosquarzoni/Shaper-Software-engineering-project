package demo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;

@Entity
public abstract class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    public String firstName;
    public String lastName;
    public String dateofb;
    public String userName;
    public String password;
    //private BufferedImage image;


    protected Person() {}

    public Person(String firstName, String lastName, String dateofb, String userName, String password) throws IOException {
        this.firstName = firstName;
        this.lastName = lastName;
        //this.image = ImageIO.read(new File(firstName + ".png"));
        this.dateofb = dateofb;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[firstName='%s', lastName='%s']",
                firstName, lastName);
    }

        public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

}
