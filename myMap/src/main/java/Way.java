import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Allan Wong on 7/27/2017.
 */
public class Way {

    private String id;
    private LinkedList<Node> nodes;
    private HashMap<String, String> attributes;

    public Way(String ID, LinkedList<Node> Nodes, HashMap<String, String> attb) {
        id = ID;
        nodes = Nodes;
        attributes = attb;
        for (Node n : nodes) {
            n.setConnected(true);
            for (Node node : nodes) {
                if (n != node)
                    n.addConnectedNode(node);
            }
        }
    }


    public String getId() {
        return id;
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }


}
