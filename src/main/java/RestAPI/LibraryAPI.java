package RestAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LibraryDAO;
import entity.Library;
import parser.XmlParser;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api")
public class LibraryAPI {

    private LibraryDAO libraryDAO = new LibraryDAO();

    @GET
    @Path(value="/getAllLibraries")
    @Produces(value={"application/json"})
    public List<Library> getAllLibrary() {
        List<Library> libraries = new ArrayList<>(libraryDAO.findAll());
        return libraries;
    }

    @POST
    @Path(value="/getUsersLibrary")
    @Produces(value={"application/json"})
    public List<Library> getUsersLibrary(String userIdJson) {
        List<Library> libraries = new ArrayList<>(libraryDAO.findAll());
        List<Library> userLibraries = new ArrayList<>();

        int userId= Integer.parseInt(userIdJson);

        for(Library l : libraries){
            if(l.getUser().getId()==userId){
                userLibraries.add(l);
            }
        }
        return userLibraries;
    }

    @POST
    @Path(value="/createLibrary")
    public String createLibrary(String LibraryXml) {

        System.out.println(LibraryXml);

        return "DONE";
    }

    @GET
    @Path(value="/createLibraryTemp")
    public String createLibrary() {

        return "DONE";
    }


}