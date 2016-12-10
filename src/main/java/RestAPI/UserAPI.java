package RestAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDAO;
import entity.User;
import persistence.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api")
public class UserAPI {

    private UserDAO userDAO = new UserDAO();

    @GET
    @Path(value="/getAllUser")
    @Produces(value={"application/json"})
    public List<User> getAllUsers() {
        EntityManager em = PersistenceUtil.createEM();
        UserDAO userDAO = new UserDAO();
        List<User> customers = new ArrayList<>(userDAO.findAll());
        return customers;
    }

    @POST
    @Path(value="/createUser")
    public String createUser(String customerJson) {

        System.out.println("Ive been hit");
        System.out.println(customerJson);
        System.out.println("After printing report");

        Map<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(customerJson, new TypeReference<HashMap<String, String>>() {
            });
            createUserMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "DONE";
    }

    @POST
    @Path(value="/getUser")
    @Produces(value={"application/json"})
    public User getUser(String userJson){

        System.out.println("Ive been hit");
        System.out.println(userJson);
        System.out.println("After printing report");
        User user = new User();
        Map<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(userJson, new TypeReference<HashMap<String, String>>() {
            });
            String email = (String) map.get("email");
            String password = (String) map.get("password");
            user = userDAO.findUserByEmailAndPassword(email, password);
            if(user != null){
                System.out.println(user.toString());
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void createUserMap(Map<String, String> map) {

        String username = (String) map.get("username");
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        if (newUser != null) {
            userDAO.createCustomer(newUser);
        }
    }
}