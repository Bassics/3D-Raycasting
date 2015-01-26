import org.jsfml.graphics.Sprite;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SpriteHandler {
    private static ArrayList<Sprite> wallSprites = new ArrayList<Sprite>();
    private static ArrayList<Sprite> bulletSprites = new ArrayList<Sprite>();
    private static ArrayList<Sprite> floorSprites = new ArrayList<Sprite>();
    public static void loadSprites(int w, int h) {
        for (int i = 0; i < w; i++) {
            wallSprites.add(new Sprite());
            bulletSprites.add(new Sprite());
            for (int y = h/2; y < h; y++) {
                floorSprites.add(new Sprite());
            }
        }
    }
    public static Sprite getWallSprite(int i) {
        return wallSprites.get(i);
    }
    public static Sprite getBulletSprite(int i) {
        return bulletSprites.get(i);
    }
    public static Sprite getFloorSprite(int i) {
        return floorSprites.get(i);
    }
}
