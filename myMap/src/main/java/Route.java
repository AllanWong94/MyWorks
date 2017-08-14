
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Allan Wong on 7/27/2017.
 */
public class Route {
    private double start_lon;
    private double start_lat;
    private double end_lon;
    private double end_lat;
    private Node startNode;
    private Node endNode;
    private MinPQ<Node> q;
    private LinkedList<Long> route;
    private GraphDB g;

    public Route(GraphDB G, double startlon, double startlat, double endlon, double endlat) {
        System.out.println("Route initialized...");
        g = G;
        start_lon = startlon;
        start_lat = startlat;
        end_lon = endlon;
        end_lat = endlat;
        startNode = g.findClosestConnectedNode(startlon, startlat);
        endNode = g.findClosestConnectedNode(endlon, endlat);
        q = new MinPQ<>();
        route = new LinkedList<>();
        findRoute();
    }

    public void findRoute() {
        startNode.setRouteParams(null, endNode);
        q.insert(startNode);
        Node cur = null;
        while (!q.isEmpty()) {
            cur = q.delMin();
            if (cur.equals(endNode)) {
//                System.out.println("Route found!");
                break;
            }
            List<Node> temp = cur.getConnectedNodes();
            for (Node n : temp) {
                if (!n.isChecked() && !n.inserted) {
                    if (n.inserted&&n.setPre(cur)) {
                        q.insert(n);
                    }else{
                        n.setRouteParams(cur, endNode);
                        n.inserted = true;
                        q.insert(n);
                    }
                }
            }
            cur.setChecked(true);
        }
        while (cur != null) {
//            System.out.println(cur.getId());
            route.addFirst(Long.parseLong(cur.getId()));
            cur = cur.getPre();
        }
        route.addFirst(Long.parseLong(startNode.getId()));
    }

    public LinkedList<Long> getRoute() {
        return route;
    }


}
