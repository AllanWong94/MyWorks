import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Allan Wong on 2017/5/25.
 */
public class TileSet {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625,
            Lv3WDDP = 0.00004291534423828125, Lv3HDDP = 0.00003388335630702399;

    private Tile root;
    private LinkedList<Tile> TileCollection;
    private int tcWidth;
    private int tcHeight;
    private double rasteredImageWidth;
    private double rasteredImageHeight;
    private double rasteredImageUlLon;
    private double rasteredImageUlLat;
    private double rasteredImageLrLon;
    private double rasteredImageLrLat;
    private boolean tcInitialized;
    private double tcDepth;
    private double lonDPP;
    private BufferedImage bi;
    private static final int IMAGE_SIZE = 256;
    private LinkedList<Long> route;
    private GraphDB g;


    public TileSet() {
        root = new Tile(ROOT_ULLON, ROOT_ULLAT, ROOT_LRLON, ROOT_LRLAT, 0, 0, 0);
        tcInitialized = false;
        TileCollection = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            TileCollection.add(root.getChild(i));
        }
    }

    public LinkedList<Tile> findTiles(double ullonQ, double ullatQ, double lrlonQ, double lrlatQ, double width, double height) {
        double lonDPPQ = (lrlonQ - ullonQ) / width;
        return findTiles(ullonQ, ullatQ, lrlonQ, lrlatQ, lonDPPQ);
    }

    public LinkedList<Tile> findTiles(double ullonQ, double ullatQ, double lrlonQ, double lrlatQ, double lonDPPQ) {
        TileCollection = new LinkedList<>();
        tcDepth = 0;
        Tile TopLeft = find(ullonQ, ullatQ, lonDPPQ, 0);
        Tile TopRight = find(lrlonQ, ullatQ, lonDPPQ, 1);
        Tile BottomLeft = find(ullonQ, lrlatQ, lonDPPQ, 2);
        Tile BottomRight = find(lrlonQ, lrlatQ, lonDPPQ, 3);
        tcDepth = TopLeft.getDepth();
        //System.out.println(tcDepth);
        rasteredImageUlLon = TopLeft.getUllon();
        rasteredImageUlLat = TopLeft.getUllat();
        rasteredImageLrLon = BottomRight.getLrlon();
        rasteredImageLrLat = BottomRight.getLrlat();
        Tile Beginning = TopLeft;
        Tile End = TopRight;
        Tile next;
        tcHeight = 0;
        tcWidth = 0;
        while (Beginning.getIndex() != BottomLeft.Down()) {
            tcHeight++;
            next = Beginning;
            while (next.getIndex() != End.Right()) {
                TileCollection.add(next);
                next = get(next.Right());
            }
            Beginning = get(Beginning.Down());
            End = get(End.Down());
        }
        tcInitialized = true;
        if (TileCollection.size() > 0) {
            tcWidth = TileCollection.size() / tcHeight;
            lonDPP = TileCollection.get(0).getLonDPP();
        } else {
            for (int i = 0; i < 4; i++)
                TileCollection.add(root.getChild(i));
            tcDepth = 1;
            lonDPP = TileCollection.get(0).getLonDPP();
            tcWidth = 2;
            tcHeight = 2;
        }
        return TileCollection;
    }

    public void printTileCollection() {
        if (TileCollection.size() == 0)
            return;
        System.out.println("TileCollection contents:");
        for (Tile t : TileCollection) {
            System.out.println(t.getIndex());
        }
    }

    public Tile get(int index) {
        LinkedList<Integer> l = new LinkedList<>();
        char[] c = Integer.toString(index).toCharArray();
        for (int i = c.length - 1; i >= 0; i--) {
            int num = Integer.parseInt(String.valueOf(c[i]));
            l.addFirst(num - 1);
        }
        Tile t = root;
        int size = l.size();
        for (int j = 0; j < size; j++) {
            t = t.getChild(l.remove());
        }
        return t;
    }

    //Find the suitable tile that is at the corner of the raster. Index indicates which corner,
    //i.e. upper left=>0, upper right=>1, lower left=>2, lower right=>3
    public Tile find(double ullonQ, double ullatQ, double lonDPPQ, int index) {
        Tile t = root.getChild(childIndex(ullonQ, ullatQ, root.getCenterlon(), root.getCenterlat(), index));
        while (t.getLonDPP() > lonDPPQ && t.getDepth() < 7) {
            t = t.getChild(childIndex(ullonQ, ullatQ, t.getCenterlon(), t.getCenterlat(), index));
        }
        return t;
    }

    //Given a center coordinate, return the index of the child tile that the location lies in.
    public int childIndex(double ullon, double ullat, double centerLon, double centerLat, int index) {
        boolean UP;
        boolean LEFT;
        LEFT = ullon < centerLon;
        UP = ullat > centerLat;
        if (ullon == centerLon) {
            if (index == 1 || index == 3) {
                LEFT = true;
            }
        }
        if (ullat == centerLat) {
            if (index == 2 || index == 3) {
                UP = true;
            }
        }
        int res = 0;
        if (!UP) {
            res += 2;
        }
        if (!LEFT) {
            res += 1;
        }
        return res;
    }

    public void drawJoinedBufferedImage(OutputStream os) {
        if (!tcInitialized) {
            System.out.println("Error! TileCollection not initialized!");
            return;
        }
        rasteredImageWidth = IMAGE_SIZE * tcWidth;
        rasteredImageHeight = IMAGE_SIZE * tcHeight;
        int TopLeftX;
        int TopLeftY;
        BufferedImage img;
        String imageName;
        String inputImagePath = ".\\img\\";
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage((int) rasteredImageWidth, (int) rasteredImageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, (int) rasteredImageWidth, (int) rasteredImageHeight);

        //draw image
        g2.setColor(oldColor);
        try {
            for (int i = 0; i < tcHeight; i++) {
                for (int j = 0; j < tcWidth; j++) {
                    TopLeftX = j * IMAGE_SIZE;
                    TopLeftY = i * IMAGE_SIZE;
                    imageName = TileCollection.get(i * tcWidth + j).getFilename();

                    img = ImageIO.read(new File(inputImagePath + imageName));
                    g2.drawImage(img, null, TopLeftX, TopLeftY);

                }
            }
            if (route != null) {
                g2.setColor(MapServer.ROUTE_STROKE_COLOR);
                g2.setStroke(new BasicStroke(MapServer.ROUTE_STROKE_WIDTH_PX, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
               // g2.drawLine(0,0,255,255);
                System.out.println("Route size: "+route.size());
                for(int i=1;i<route.size();i++){
                    Long start=route.get(i-1),end=route.get(i);
                    int[] startCoord=convertCoordinates(start),endCoord=convertCoordinates(end);
                    g2.drawLine(startCoord[0],startCoord[1],endCoord[0],endCoord[1]);
                }
            }
            g2.dispose();
            bi = newImage;
            ImageIO.write(newImage, "png", os);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static double getRootUllat() {
        return ROOT_ULLAT;
    }

    public static double getRootUllon() {
        return ROOT_ULLON;
    }

    public static double getRootLrlat() {
        return ROOT_LRLAT;
    }

    public static double getRootLrlon() {
        return ROOT_LRLON;
    }

    public Tile getRoot() {
        return root;
    }

    public LinkedList<Tile> getTileCollection() {
        return TileCollection;
    }

    public int getTcWidth() {
        return tcWidth;
    }

    public int getTcHeight() {
        return tcHeight;
    }

    public double getRasteredImageWidth() {
        return rasteredImageWidth;
    }

    public double getRasteredImageHeight() {
        return rasteredImageHeight;
    }

    public double getRasteredImageUlLon() {
        return rasteredImageUlLon;
    }

    public double getRasteredImageUlLat() {
        return rasteredImageUlLat;
    }

    public double getRasteredImageLrLon() {
        return rasteredImageLrLon;
    }

    public double getRasteredImageLrLat() {
        return rasteredImageLrLat;
    }

    public boolean isTcInitialized() {
        return tcInitialized;
    }

    public double getTcDepth() {
        return tcDepth;
    }

    public double getLonDPP() {
        return lonDPP;
    }

    public BufferedImage getBi() {
        return bi;
    }

    public void setRoute(LinkedList<Long> r) {
        route = r;
    }

    public void clearRoute() {
        route = null;
    }

    public void setGraphDB(GraphDB G){
        g=G;
    }

    public int[] convertCoordinates(Long id){
        Node node=g.getNode(String.valueOf(id));
        double lon=node.getLon(),lat=node.getLat();
        double x,y;
        y=(rasteredImageUlLat-lat)*rasteredImageHeight/(rasteredImageUlLat-rasteredImageLrLat);
        x=(lon-rasteredImageUlLon)*rasteredImageWidth/(rasteredImageLrLon-rasteredImageUlLon);
        return new int[]{(int)x,(int)y};
    }

}
