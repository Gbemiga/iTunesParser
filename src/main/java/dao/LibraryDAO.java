package dao;

import entity.Library;
import entity.User;
import persistence.PersistenceUtil;
import javax.persistence.EntityManager;
import java.util.List;

public class LibraryDAO {

	public List<Library> findAll(){
        EntityManager em = PersistenceUtil.createEM();
        List<Library> libraries = (List<Library>)
                em.createNamedQuery("Library.findAll").getResultList();
        em.close();
       return libraries;
	}
	public Library findById(int id){
        EntityManager em = PersistenceUtil.createEM();
        List<Library> libraries = (List<Library>)
                em.createNamedQuery("Library.findById")
                        .setParameter("id",id).getResultList();
        em.close();
       return libraries.get(0);
	}

	public List<Library> findByCustomer(User user){
        EntityManager em = PersistenceUtil.createEM();
        List<Library> libraries = (List<Library>)
                em.createNamedQuery("Library.findByCustomer").
                    setParameter("user", user).getResultList();
        em.close();
       return libraries;
	}
    public void createLibrary(Library library){
        PersistenceUtil.persist(library);
    }

}