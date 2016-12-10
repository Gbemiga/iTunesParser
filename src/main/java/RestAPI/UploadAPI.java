package RestAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LibraryDAO;
import dao.PlaylistDAO;
import dao.TrackDAO;
import dao.UserDAO;
import entity.Library;
import entity.Playlist;
import entity.Track;
import entity.User;
import parser.XmlParser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.*;
import java.util.*;

@Path("/api")
public class UploadAPI {

    private LibraryDAO libraryDAO = new LibraryDAO();
    private TrackDAO trackDAO = new TrackDAO();
    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private UserDAO userDAO = new UserDAO();

    @POST
    @Path(value="/uploadFile")
    public String createLibrary(String params) {
        String userId = "";
        String fileObject = "";
        Map<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(params, new TypeReference<HashMap<String, String>>() {
            });
            fileObject = (String) map.get("file");
            userId = (String) map.get("userId");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File temp = File.createTempFile("tempIn", ".xml");

            temp.deleteOnExit();

            // Write to temp file
            BufferedWriter outWriter = new BufferedWriter(new FileWriter(temp));
            outWriter.write(fileObject);
            outWriter.close();

            User user = userDAO.findUserId(Integer.parseInt(userId));

            XmlParser xmlParser = new XmlParser();
            xmlParser.setUp(temp);
            Library library = xmlParser.getLibrary();
            System.out.println("From upload api" +library.toString());
            library.setUser(user);
            libraryDAO.createLibrary(library);

            Set<Track> tracks = xmlParser.getAllTracks();


            for(Track t : tracks) {
                t.setLibrary(library);
                trackDAO.createTrack(t);
            }

            List<Track> trackSet = trackDAO.findByLibrary(library);
            ArrayList<Playlist> playlists = xmlParser.getAllPlaylist(trackSet);

            for (Playlist p:playlists) {
                    p.setLibrary(library);
                Set<Track> temptracks = new HashSet<>();
                if(p.getTrackIdList()!=null) {
                    for (Track playt : p.getTrackIdList()) {
                        for (Track tempTrack : tracks) {
                            if (playt.getTrackId() == tempTrack.getTrackId()) {
                                temptracks.add(tempTrack);
                            }
                        }
                        playt.setLibrary(library);
                    }
                }
                tracks.removeAll(temptracks);
                playlistDAO.mergePlaylist(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "DONE";
    }


}