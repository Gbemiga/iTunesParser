package entity;


import javax.persistence.*;
import javax.xml.bind.annotation.*;


@NamedQueries( {
        @NamedQuery(name = "User.findAll", query = "select u from User u"),
        @NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username=:username"),
        @NamedQuery(name = "User.findUserById", query = "select u from User u where u.id=:id"),
        @NamedQuery(name = "User.findByUsernameAndPassword", query = "select u from User u where u.username=:username and u.password=:password"),
        @NamedQuery(name = "User.findUserByEmailAndPassword", query = "select u from User u where u.email=:email and u.password=:password"),
})

@XmlRootElement
@Entity
public class User {

    //every entity requires an id, and we can make it auto generated
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String username;
    private String password;
    private String email;

    public User(){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}