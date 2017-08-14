
/**
 * Created by Allan Wong on 2017/5/25.
 */
public class TestTileSet {
    public static void main(String[] args) {
        TileSet ts=new TileSet();
        ts.findTiles(-122.2833251953125,37.890027012440704,-122.23663330078125,37.85316192077866,0.000010728836059570312);
        //ts.printTileCollection();
        System.out.println("RasterImage_ullon:"+ts.getRasteredImageUlLon());
        System.out.println("RasterImage_ullat:"+ts.getRasteredImageUlLat());
        System.out.println("RasterImage_lrlon:"+ts.getRasteredImageLrLon());
        System.out.println("RasterImage_lrlat:"+ts.getRasteredImageLrLat());
        System.out.println("Depth: "+ts.getTcDepth());


        /*System.out.println("Test#1");
        System.out.println("Tile Collection Width: "+ts.getTcWidth());
        System.out.println("Tile Collection Height: "+ts.getTcHeight());
        System.out.println("Tile Collection size: "+ts.getTileCollection().size());
        System.out.println("RasterImage_ullon:"+ts.getRasteredImageUlLon());
        System.out.println("RasterImage_ullat:"+ts.getRasteredImageUlLat());
        System.out.println("RasterImage_lrlon:"+ts.getRasteredImageLrLon());
        System.out.println("RasterImage_lrlat:"+ts.getRasteredImageLrLat());
        TileSet ts1=new TileSet();
        ts1.findTiles(-122.27438109425786,37.875,-122.26881074007515,37.86218141553216,345,923);
        System.out.println("Test#2");
        System.out.println("Tile Collection Width: "+ts1.getTcWidth());
        System.out.println("Tile Collection Height: "+ts1.getTcHeight());
        System.out.println("Tile Collection size: "+ts1.getTileCollection().size());
        System.out.println("RasterImage_ullon:"+ts1.getRasteredImageUlLon());
        System.out.println("RasterImage_ullat:"+ts1.getRasteredImageUlLat());
        System.out.println("RasterImage_lrlon:"+ts1.getRasteredImageLrLon());
        System.out.println("RasterImage_lrlat:"+ts1.getRasteredImageLrLat());*/


    }
}
