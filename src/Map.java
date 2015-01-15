public class Map {
    private static int[][] currentMap;
    public static void loadMap(int[][] m) {
        currentMap = m;
    }
    public static int[][] getCurrentMap() {
        return currentMap;
    }
}
