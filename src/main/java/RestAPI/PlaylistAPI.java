package RestAPI;

import dao.LibraryDAO;
import dao.PlaylistDAO;
import entity.Library;
import entity.Playlist;
import entity.Track;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/api/track")
public class PlaylistAPI {

    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private LibraryDAO libraryDAO = new LibraryDAO();


    @POST
    @Path(value="/getLibraryPlaylists")
    @Produces(value={"application/json"})
    public List<Playlist> getLibraryPlaylists(String libraryIJson) {
        Library library = libraryDAO.findById(Integer.parseInt(libraryIJson));
        List<Playlist> playlists = new ArrayList<>(playlistDAO.findByLibrary(library));

        return playlists;
    }



}