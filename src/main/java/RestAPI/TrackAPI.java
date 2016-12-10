package RestAPI;

import dao.LibraryDAO;
import dao.TrackDAO;
import entity.Library;
import entity.Track;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/api/track")
public class TrackAPI {

    private TrackDAO trackDAO = new TrackDAO();
    private LibraryDAO libraryDAO = new LibraryDAO();

    @GET
    @Path(value="/getAllTracks")
    @Produces(value={"application/json"})
    public List<Track> getAllTracks() {
        List<Track> tracks = new ArrayList<>(trackDAO.findAll());
        return tracks;
    }

    @POST
    @Path(value="/getUserTracks")
    @Produces(value={"application/json"})
    public List<Track> getUserTracks(String userIdJson) {
        List<Track> tempTracks = new ArrayList<>(trackDAO.findAll());
        List<Track> tracks = new ArrayList<>();
        List<Library> libraries = new ArrayList<>(libraryDAO.findAll());
        int userId= Integer.parseInt(userIdJson);

        for(Library l : libraries){
            if(l.getUser().getId()==userId){
                for (Track t: tempTracks){
                    if(t.getLibrary().getId()==l.getId()) {
                        tracks.add(t);

                    }
                }
            }
        }
        System.out.println(tracks.size()+ "SIZING UP ");
        return tracks;
    }

    @POST
    @Path(value="/getLibraryTracks")
    @Produces(value={"application/json"})
    public List<Track> getLibraryTracks(String libraryIJson) {
        Library library = libraryDAO.findById(Integer.parseInt(libraryIJson));
        List<Track> tracks = new ArrayList<>(trackDAO.findByLibrary(library));

        return tracks;
    }

    @POST
    @Path(value="/deleteTrack")
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