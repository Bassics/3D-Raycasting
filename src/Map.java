import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector3f;

import java.util.ArrayList;

public class Map {
    /* The current map grid */
    private static int[][] currentMap;
    private static ArrayList<Vector3f> hitPositions;
    private static ArrayList<Texture> hitTextures;
    private static ArrayList<int[]> hitMaps;
    /* The current floor texture */
    private static Texture floorTexture;
    /* Load the grid map */
    public static void loadMap(int[][] m) {
        currentMap = m;
        hitPositions = new ArrayList<Vector3f>();
        hitTextures = new ArrayList<Texture>();
        hitMaps = new ArrayList<int[]>();
    }
    public static void addHitPosition(Vector3f data, Texture t, int[] pos) {
        if (hitPositions.size() > 30) {
            hitPositions.remove(0);
            hitTextures.remove(0);
            hitMaps.remove(0);
        }
        hitPositions.add(data);
        hitTextures.add(t);
        hitMaps.add(pos);
    }
    public static Vector3f getHitPosition(int i) {
        return hitPositions.get(i);
    }
    public static Texture getHitTexture(int i) {
        return hitTextures.get(i);
    }
    public static int[] getHitMap(int i) {
        return hitMaps.get(i);
    }
    public static ArrayList<Vector3f> getHitPositions() {
        return hitPositions;
    }
    public static ArrayList<Texture> getHitTextures() {
        return hitTextures;
    }
    public static ArrayList<int[]> getHitMaps() { return hitMaps; }
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
