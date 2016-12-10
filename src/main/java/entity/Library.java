package entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries( {
        @NamedQuery(name = "Library.findAll", query = "select l from Library l"),
        @NamedQuery(name = "Library.findById", query = "select l from Library l where l.id=:id"),
        @NamedQuery(name = "Library.findByCustomer", query = "select l from Library l where l.user=:user"),
})

@Entity
@XmlRootElement
public class Library {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String LibraryPersistentId;

    @ManyToOne
    private User user;

    public Library() {
    }

    public Library(String libraryPersistentId, User user) {
        this.LibraryPersistentId = libraryPersistentId;
        this.user = user;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getLibraryPersistentId() {
        return LibraryPersistentId;
    }

    public void setLibraryPersistentId(String libraryPersistentId) {
        LibraryPersistentId = libraryPersistentId;
    }

    @XmlElement
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", LibraryPersistentId='" + LibraryPersistentId + '\'' +
                ", user=" + user +
                '}';
    }
}
