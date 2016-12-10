package parser;

import dao.LibraryDAO;
import dao.TrackDAO;
import entity.Library;
import entity.Playlist;
import entity.Track;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XmlParser {

    private static XPath xPath;
    private static Document document;


    public void setUp(File file) {
        try {

//            File file = new File("/Users/gbemigaadeosun/Documents/College/Distributed System/Final Assignment/JPARelationshipAndRestDS/src/main/resources/META-INF/songs.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);

            document.getDocumentElement().normalize();
            xPath =  XPathFactory.newInstance().newXPath();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Set<Track> getAllTracks() throws Exception{
        String trackExpression = "//dict/key[. = 'Tracks']/following-sibling::*[1]/child::*";
        NodeList trackNodeList = (NodeList) xPath.compile(trackExpression).evaluate(document, XPathConstants.NODESET);
        NodeList eachTrack = null;
        ArrayList<Track> tracks = new ArrayList<>();
        for (int i = 0; i < trackNodeList.getLength(); i++) {
            if(trackNodeList.item(i).getNodeName().equals("key")) {
                Track track = new Track();
                eachTrack = trackNodeList.item(i).getNextSibling().getNextSibling().getChildNodes();

                for (int j = 0; j < eachTrack.getLength(); j++) {
                    if (eachTrack.item(j).getTextContent().equals("Track ID"))
                        track.setTrackId(Integer.parseInt(eachTrack.item(j).getNextSibling().getTextContent()));
                    if (eachTrack.item(j).getTextContent().equals("Name"))
                        track.setName(eachTrack.item(j).getNextSibling().getTextContent());
                    if (eachTrack.item(j).getTextContent().equals("Artist"))
                        track.setArtist(eachTrack.item(j).getNextSibling().getTextContent());
                    if (eachTrack.item(j).getTextContent().equals("Composer"))
                        track.setComposer(eachTrack.item(j).getNextSibling().getTextContent());
                    if (eachTrack.item(j).getTextContent().equals("Album"))
                        track.setAlbum(eachTrack.item(j).getNextSibling().getTextContent());
                    if (eachTrack.item(j).getTextContent().equals("Genre"))
                        track.setGenre(eachTrack.item(j).getNextSibling().getTextContent());
                    if (eachTrack.item(j).getTextContent().equals("Persistent ID"))
                        track.setPersistenceId(eachTrack.item(j).getNextSibling().getTextContent());
                    if (eachTrack.item(j).getTextContent().equals("Year"))
                        track.setYear(Integer.parseInt(eachTrack.item(j).getNextSibling().getTextContent()));
                }
                tracks.add(track);
            }
        }
        System.out.println(tracks.size());
        Set set = new HashSet(tracks);
        return set;
    }

    public ArrayList<Playlist> getAllPlaylist(List<Track> trackSet) throws Exception{
        String nameExpression = "//dict/key[. = 'Playlists']/following-sibling::*[1]/child::*";
        NodeList nodeList = (NodeList) xPath.compile(nameExpression).evaluate(document, XPathConstants.NODESET);
        NodeList playListNodeList = null;
        ArrayList<Playlist> playlists = new ArrayList<>();


        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList newNodeList = nodeList.item(i).getChildNodes();
            Playlist playlist = new Playlist();
            ArrayList<Track> trackIdList = new ArrayList<>();
            for (int j = 0; j < newNodeList.getLength(); j++) {
                if (newNodeList.item(j).getTextContent().equals("Name"))
                    playlist.setName(newNodeList.item(j).getNextSibling().getTextContent());
                if (newNodeList.item(j).getTextContent().equals("Playlist ID"))
                    playlist.setPlaylistId(Integer.parseInt(newNodeList.item(j).getNextSibling().getTextContent()));
                if (newNodeList.item(j).getTextContent().equals("Playlist Persistent ID"))
                    playlist.setPlaylistPersistentId(newNodeList.item(j).getNextSibling().getTextContent());
                if (newNodeList.item(j).getTextContent().equals("Playlist Items")) {
                    playListNodeList = newNodeList.item(j).getNextSibling().getNextSibling().getChildNodes();
                    for (int x = 0; x < playListNodeList.getLength(); x++) {
                        if (playListNodeList.item(x).getNodeName().equals("dict")){
                            System.out.println(Integer.parseInt(playListNodeList.item(x).getChildNodes().item(0).getNextSibling().getNextSibling().getTextContent()));
                            int playlistTrackId = Integer.parseInt(playListNodeList.item(x).getChildNodes().item(0).getNextSibling().getNextSibling().getTextContent());
                            for(Track t: trackSet) {
                                if (t.getTrackId() == playlistTrackId) {
                                    trackIdList.add(t);
                                    System.out.println(t.toString()+"From XMLParser");
                                }
                            }
                        }

                        }
                    Set set = new HashSet(trackIdList);
                    playlist.setTrackIdList(set);
                }
            }
            playlists.add(playlist);
        }
        return playlists;
    }

    public Library getLibrary() throws Exception{
        String libraryExpression = "//dict/key[. = 'Library Persistent ID']";
        NodeList libraryNodeList = (NodeList) xPath.compile(libraryExpression).evaluate(document, XPathConstants.NODESET);
        Library library = new Library();
        for (int i = 0; i < libraryNodeList.getLength(); i++) {
            if(libraryNodeList.item(i).getTextContent().equals("Library Persistent ID")) {
                library.setLibraryPersistentId(libraryNodeList.item(i).getNextSibling().getTextContent());
            }
        }
        return library;
    }
}