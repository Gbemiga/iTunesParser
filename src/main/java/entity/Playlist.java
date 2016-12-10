package entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@NamedQueries( {
        @NamedQuery(name = "Playlist.findAll", query = "select p from Playlist p"),
        @NamedQuery(name = "Playlist.findByLibrary", query = "select p from Playlist p where p.library=:library"),
})

@XmlRootElement
@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;
    private int playlistId;
    private String playlistPersistentId;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Track> trackIdList;

    @ManyToOne
    private Library library;

    public Playlist() {
    }

    public Playlist(String name, int playlistId, String playlistPersistentId, Set<Track> trackIdList, Library library) {
        this.name = name;
        this.playlistId = playlistId;
        this.playlistPersistentId = playlistPersistentId;
        this.trackIdList = trackIdList;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @XmlElement
    public String getPlaylistPersistentId() {
        return playlistPersistentId;
    }

    public void setPlaylistPersistentId(String playlistPersistentId) {
        this.playlistPersistentId = playlistPersistentId;
    }

    @XmlElement
    public Set<Track> getTrackIdList() {
        return trackIdList;
    }

    public void setTrackIdList(Set<Track> trackIdList) {
        this.trackIdList = trackIdList;
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
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playlistId=" + playlistId +
                ", playlistPersistentId='" + playlistPersistentId + '\'' +
                ", trackIdList=" + trackIdList +
                ", library=" + library +
                '}';
    }
}
