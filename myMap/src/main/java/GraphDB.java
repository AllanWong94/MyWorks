import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Wraps the parsing functionality of the MapDBHandler as an example.
 * You may choose to add to the functionality of this class if you wish.
 * @author Alan Yao
 */
public class GraphDB {
    HashMap<String, Node> allNodes,connectedNodes;
    HashMap<String, Way> ways;
    Trie<Node> trie;
    /**
     * Example constructor shows how to create and start an XML parser.
     * @param db_path Path to the XML file to be parsed.
     */
    public GraphDB(String db_path) {
        allNodes=new HashMap<>();
        ways=new HashMap<>();
        connectedNodes=new HashMap<>();
        trie=new Trie<>();
        try {
            File inputFile = new File(db_path);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapDBHandler maphandler = new MapDBHandler(this);
            saxParser.parse(inputFile, maphandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        int count=0;
        for(Node n:allNodes.values()){
            if(n.isConnected()){
                connectedNodes.put(n.getId(),n);
//                count++;
            }
        }
//        System.out.println("Connected node count: "+count);
    }

    public void putNode(Node node){
        allNodes.put(node.getId(), node);
        if(node.hasAttb("name")) trie.put(cleanString(node.getAttb("name")),node);
    }

    public Node getNode(String id){
        return allNodes.get(id);
    }

    public void putWay(Way way){
        ways.put(way.getId(), way);
    }

    public Node findClosestConnectedNode(double lon,double lat){
        Node res=null;
        double dist=Double.MAX_VALUE;
        for(Node n:connectedNodes.values()){
            double temp=calDist(lon,lat,n);
            if(temp<dist){
                res=n;
                dist=temp;
            }
        }
        return res;
    }

    public double calDist(double lon,double lat, Node n){
        double lond=n.getLon()-lon, latd=n.getLat()-lat;
        return Math.sqrt(lond*lond+latd*latd);
    }

    public void resetNodes(){
        for(Node n:connectedNodes.values()){
            n.inserted=false;
            n.setChecked(false);
        }
    }

    public Stack<String> getNamesByPrefix(String prefix){
        return trie.keysWithPrefix(cleanString(prefix));
    }

    public List<Map<String, Object>> getLocations(String location){
        LinkedList<Map<String, Object>> maps=new LinkedList<>();
        LinkedList<Node> nodes=trie.get(cleanString(location));
        for(Node n:nodes){
            HashMap<String,Object> map=new HashMap<>();
            map.put("lat", n.getLat());
            map.put("lon", n.getLon());
            map.put("name", n.getAttb("name"));
            map.put("id",n.getId());
            maps.add(map);
        }
        return maps;
    }
}
