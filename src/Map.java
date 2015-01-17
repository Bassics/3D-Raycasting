import org.jsfml.graphics.Texture;

public class Map {
    private static int[][] currentMap;
    private static Texture floorTexture;
    public static void loadMap(int[][] m) {
        currentMap = m;
    }
    public static int[][] getCurrentMap() {
        return currentMap;
    }
    public static int getInt(int x, int y) {
        return currentMap[x][y];
    }
    public static void setFloorTexture(Texture t) {
        floorTexture = t;
    }
    public static Texture getFloorTexture() {
        return floorTexture;
    }
}
