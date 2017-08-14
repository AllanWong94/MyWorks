/**
 * Created by Allan Wong on 2017/5/25.
 */
public class Tile {
    private double ullon;
    private double ullat;
    private double lrlon;
    private double lrlat;
    private int depth;
    private int index;
    private double lonDPP;
    private double latDPP;
    private double centerlon;
    private double centerlat;
    private Tile[] child;
    private String filename;
    private static final int IMAGE_WIDTH=256;
    private static final int IMAGE_HEIGHT=256;

    public double getUllon() {
        return ullon;
    }

    public double getUllat() {
        return ullat;
    }

    public double getLrlon() {
        return lrlon;
    }

    public double getLrlat() {
        return lrlat;
    }

    public int getDepth() {
        return depth;
    }

    public int getIndex() {
        return index;
    }

    public double getLonDPP() {
        return lonDPP;
    }

    public double getLatDPP() {
        return latDPP;
    }

    public double getCenterlon() {
        return centerlon;
    }

    public double getCenterlat() {
        return centerlat;
    }

    public Tile getChild(int index) {
        return child[index];
    }

    public String getFilename() {
        return filename;
    }

    public Tile(double ullo, double ulla, double lrlo, double lrla, int dep, int parentIndx, int ind) {
        ullat = ulla;
        ullon = ullo;
        lrlat = lrla;
        lrlon = lrlo;
        depth = dep;
        child = new Tile[4];
        lonDPP = (lrlon - ullon) / IMAGE_WIDTH;
        latDPP = (ullat - lrlon) / IMAGE_HEIGHT;
        centerlon = (lrlon + ullon) / 2;
        centerlat = (lrlat + ullat) / 2;
        if (depth == 0) {
            index = 0;
        } else if (depth == 1) {
            index = ind;
        } else {
            index = parentIndx * 10 + ind;
        }
        filename = Integer.toString(index) + ".png";
        if (depth < 7) {
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        child[i] = new Tile(ullon, ullat, centerlon, centerlat, depth + 1, index, i + 1);
                        break;
                    case 1:
                        child[i] = new Tile(centerlon, ullat, lrlon, centerlat, depth + 1, index, i + 1);
                        break;
                    case 2:
                        child[i] = new Tile(ullon, centerlat, centerlon, lrlat, depth + 1, index, i + 1);
                        break;
                    case 3:
                        child[i] = new Tile(centerlon, centerlat, lrlon, lrlat, depth + 1, index, i + 1);
                        break;
                }
            }
        } else {
            child = null;
        }
    }

    public int Right(){
        char[] c=Integer.toString(index).toCharArray();
        boolean carry=true;
        for (int i=c.length-1;i>=0;i--){
            if(carry){
                switch (c[i]){
                    case '1':
                        c[i]='2';
                        carry=false;
                        break;
                    case '2':
                        c[i]='1';
                        carry=true;
                        break;
                    case '3':
                        c[i]='4';
                        carry=false;
                        break;
                    case '4':
                        c[i]='3';
                        carry=true;
                        break;
                }
            }
        }
        int res=Integer.parseInt(String.valueOf(c));
        return res;
    }

    public int Down(){
        char[] c=Integer.toString(index).toCharArray();
        boolean carry=true;
        for (int i=c.length-1;i>=0;i--){
            if(carry){
                switch (c[i]){
                    case '1':
                        c[i]='3';
                        carry=false;
                        break;
                    case '2':
                        c[i]='4';
                        carry=false;
                        break;
                    case '3':
                        c[i]='1';
                        carry=true;
                        break;
                    case '4':
                        c[i]='2';
                        carry=true;
                        break;
                }
            }
        }
        int res=Integer.parseInt(String.valueOf(c));
        return res;
    }

    public int Up(){
        char[] c=Integer.toString(index).toCharArray();
        boolean carry=true;
        for (int i=c.length-1;i>=0;i--){
            if(carry){
                switch (c[i]){
                    case '1':
                        c[i]='3';
                        carry=true;
                        break;
                    case '2':
                        c[i]='4';
                        carry=true;
                        break;
                    case '3':
                        c[i]='1';
                        carry=false;
                        break;
                    case '4':
                        c[i]='2';
                        carry=false;
                        break;
                }
            }
        }
        int res=Integer.parseInt(String.valueOf(c));
        return res;
    }

    public int Left(){
        char[] c=Integer.toString(index).toCharArray();
        boolean carry=true;
        for (int i=c.length-1;i>=0;i--){
            if(carry){
                switch (c[i]){
                    case '1':
                        c[i]='2';
                        carry=true;
                        break;
                    case '2':
                        c[i]='1';
                        carry=false;
                        break;
                    case '3':
                        c[i]='4';
                        carry=true;
                        break;
                    case '4':
                        c[i]='3';
                        carry=false;
                        break;
                }
            }
        }
        int res=Integer.parseInt(String.valueOf(c));
        return res;
    }
}
