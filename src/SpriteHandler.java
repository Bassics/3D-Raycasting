import org.jsfml.graphics.Sprite;

import java.util.ArrayList;

public class SpriteHandler {
    private static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    public static void loadSprites(int n) {
        for (int i = 0; i < n; i++) {
            sprites.add(new Sprite());
        }
    }
    public static Sprite getSprite(int i) {
        return sprites.get(i);
    }
}
