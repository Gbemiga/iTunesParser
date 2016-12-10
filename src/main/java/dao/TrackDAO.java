package dao;

import entity.Library;
import entity.Track;
import persistence.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class TrackDAO {

	public List<Track> findAll(){
        EntityManager em = PersistenceUtil.createEM();
        List<Track> tracks = (List<Track>)
                em.createNamedQuery("Track.findAll").getResultList();
        em.close();
       return tracks;
	}

	public List<Track> findByLibrary(Library library){
        EntityManager em = PersistenceUtil.createEM();
        List<Track> tracks = (List<Track>)
                em.createNamedQuery("Track.findByLibrary").
                    setParameter("library", library).getResultList();
        em.close();
       return tracks;
	}

    public void createTrack(Track track){
        PersistenceUtil.persist(track);
    }
    public void mergeTrack(Track track){
        PersistenceUtil.persist(track);
    }

}