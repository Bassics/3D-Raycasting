import org.jsfml.graphics.Texture;

public class Map {
    /* The current map grid */
    private static int[][] currentMap;
    /* The current floor texture */
    private static Texture floorTexture;
    /* Load the grid map */
    public static void loadMap(int[][] m) {
        currentMap = m;
    }
    /* Get the grid map */
    public static int[][] getCurrentMap() {
        return currentMap;
    }
    /* Get what type of object is in the space */
    public static int getInt(int x, int y) {
        return currentMap[x][y];
    }
    /* Set the floor's texture */
    public static void setFloorTexture(Texture t) {
        floorTexture = t;
    }
    /* Get the floor's texture */
    public static Texture getFloorTexture() {
        return floorTexture;
    }
}
