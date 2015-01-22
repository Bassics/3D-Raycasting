import org.jsfml.graphics.Sprite;

import java.util.ArrayList;

public class SpriteHandler {
    private static ArrayList<Sprite> wallSprites = new ArrayList<Sprite>();
    private static ArrayList<Sprite> bulletSprites = new ArrayList<Sprite>();
    public static void loadSprites(int n) {
        for (int i = 0; i < n; i++) {
            wallSprites.add(new Sprite());
            bulletSprites.add(new Sprite());
        }
    }
    public static Sprite getWallSprite(int i) {
        return wallSprites.get(i);
    }
    public static Sprite getBulletSprite(int i) {
        return bulletSprites.get(i);
    }
}
