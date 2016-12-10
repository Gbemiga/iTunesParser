package entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@NamedQueries( {
        @NamedQuery(name = "Track.findAll", query = "select t from Track t"),
        @NamedQuery(name = "Track.findByLibrary", query = "select t from Track t where t.library=:library"),
})

@XmlRootElement
@Entity
public class Track {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int trackId;
    private String name;
    private String artist;
    private String composer;
    private String album;
    private String genre;
    private String persistenceId;
    private int year;

    @ManyToOne
    private Library library;

    public Track() {
    }

    public Track(int trackId, String name, String artist, String composer, String album, String genre, String persistenceId, int year, Library library) {
        this.trackId = trackId;
        this.name = name;
        this.artist = artist;
        this.composer = composer;
        this.album = album;
        this.genre = genre;
        this.persistenceId = persistenceId;
        this.year = year;
        this.library = library;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @XmlElement
    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    @XmlElement
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @XmlElement
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @XmlElement
    public String getPersistenceId() {
        return persistenceId;
    }

    public void setPersistenceId(String persistenceId) {
        this.persistenceId = persistenceId;
    }

    @XmlElement
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @XmlElement
    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }



    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", trackId=" + trackId +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", composer='" + composer + '\'' +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", persistenceId='" + persistenceId + '\'' +
                ", year=" + year +
                ", library=" + library +
                '}';
    }
}
