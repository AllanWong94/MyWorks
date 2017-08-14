import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Allan Wong on 7/27/2017.
 */
public class Node implements Comparable<Node> {
    private String id;
    private double lon, lat;
    private HashMap<String, String> attb;
    private boolean connected, checked;
    private LinkedList<Node> connectedNodes;
    private double distance, heuristic, priority;
    private Node pre, target;
    public boolean inserted;

    public Node(String ID, double Lon, double Lat, HashMap<String, String> attb) {
        id = ID;
        lon = Lon;
        lat = Lat;
        this.attb = attb;
        connected = false;
        checked = false;
        connectedNodes = new LinkedList<>();
        distance = 0;
        inserted=false;
    }


    public boolean equals(Node n) {
        return id.equals(n.getId());
    }

    public double getPriority() {
        return priority;
    }

    public String getId() {
        return id;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public void setConnected(boolean b) {
        connected = b;
    }

    public HashMap<String, String> getAttributes() {
        return attb;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setChecked(boolean b) {
        checked = b;
    }

    public boolean isChecked() {
        return checked;
    }

    public void addConnectedNode(Node n) {
        if (n != this)
            connectedNodes.add(n);
    }

    public double calDist(Node n) {
        return Math.sqrt((lon - n.getLon()) * (lon - n.getLon()) + (lat - n.getLat()) * (lat - n.getLat()));
    }

    @Override
    public int compareTo(Node that) {
        double that_priority = that.getPriority();
        if (that_priority > priority) return -1;
        if (that_priority < priority) return 1;
        return 0;
    }

    public double getDistance() {
        return distance;
    }

    public LinkedList<Node> getConnectedNodes() {
        return connectedNodes;
    }

    public boolean setPre(Node Pre) {
        if (Pre.getDistance() + calDist(Pre) < distance) {
            pre = Pre;
            distance = pre.getDistance() + calDist(pre);
            priority = distance + heuristic;
            return true;
        }
        return false;
    }

    public Node getPre() {
        return pre;
    }

    public void setRouteParams(Node Pre, Node targ) {
        pre = Pre;
        if (pre != null)
            distance = pre.getDistance() + calDist(pre);
        target = targ;
        heuristic = calDist(target);
        priority = distance + heuristic;
    }

    public String getAttb(String key){
        return attb.get(key);
    }

    public boolean hasAttb(String key){
        return attb.containsKey(key);
    }
}
