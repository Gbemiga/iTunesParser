package dao;

import entity.Library;
import entity.Playlist;
import entity.Track;
import persistence.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class PlaylistDAO {

	public List<Playlist> findAll(){
        EntityManager em = PersistenceUtil.createEM();
        List<Playlist> playlists = (List<Playlist>)
                em.createNamedQuery("Playlist.findAll").getResultList();
        em.close();
       return playlists;
	}

	public List<Playlist> findByLibrary(Library library){
        EntityManager em = PersistenceUtil.createEM();
        List<Playlist> playlists = (List<Playlist>)
                em.createNamedQuery("Playlist.findByLibrary").
                    setParameter("library", library).getResultList();
        em.close();
       return playlists;
	}
    public void createPlaylist(Playlist playlist){
        PersistenceUtil.persist(playlist);
    }

    public void mergePlaylist(Playlist playlist){
        PersistenceUtil.merge(playlist);
    }

}