package dao;

import entity.User;
import persistence.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDAO {

	public List<User> findAll(){
        EntityManager em = PersistenceUtil.createEM();
        List<User> users = (List<User>)
                em.createNamedQuery("User.findAll").getResultList();
        em.close();
       return users;
	}

	public void createCustomer(User user){
		PersistenceUtil.persist(user);
	}

    public User findUserByUsername(String username){
        EntityManager em = PersistenceUtil.createEM();
        List<User> users = (List<User>)
                em.createNamedQuery("User.findByUsername").
                        setParameter("username", username).getResultList();
        em.close();

        if (users.size() == 0)
            return null;
        else
            return users.get(0);
    }

    public User findUserByUsernameAndPassword(String username, String password){
        EntityManager em = PersistenceUtil.createEM();
        List<User> users = (List<User>)
                em.createNamedQuery("User.findByUsernameAndPassword").
                        setParameter("username", username).
                        setParameter("password", password).getResultList();
        em.close();

        if (users.size() == 0)
            return null;
        else
            return users.get(0);
    }

    public User findUserByEmailAndPassword(String email, String password){
        EntityManager em = PersistenceUtil.createEM();
        List<User> users = (List<User>)
                em.createNamedQuery("User.findUserByEmailAndPassword").
                        setParameter("email", email).
                        setParameter("password", password).getResultList();
        em.close();

        if (users.size() == 0)
            return null;
        else
            return users.get(0);
    }

    public User findUserId(int id){
            EntityManager em = PersistenceUtil.createEM();
            List<User> users = (List<User>)
                    em.createNamedQuery("User.findUserById").
                            setParameter("id", id).getResultList();
            em.close();

            if (users.size() == 0)
                return null;
            else
                return users.get(0);
        }

}