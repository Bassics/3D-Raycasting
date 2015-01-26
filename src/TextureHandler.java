import org.jsfml.graphics.Texture;
import java.io.IOException;

public class TextureHandler {
    private static Texture[][] wallTextures = new Texture[59][2];
    private static Texture[] hitTextures = new Texture[5];
    public static void setTexture(Texture t, String s) {
        try {
            t.loadFromStream(TextureHandler.class.getClassLoader().getResourceAsStream(s));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void loadTextures() {
        for (int i = 1; i <= 58; i++) {
            wallTextures[i] = new Texture[]{new Texture(), new Texture()};
            setTexture(wallTextures[i][0], "tex/wolfenstein" + i + "_" + 1 + ".png");
            setTexture(wallTextures[i][1], "tex/wolfenstein" + i + "_" + 2 + ".png");
        }
        for (int i = 1; i <= 4; i++) {
            hitTextures[i] = new Texture();
            setTexture(hitTextures[i], "tex/hit" + i + ".png");
        }
    }
    public static Texture[][] getWallTextures() {
        return wallTextures;
    }
    public static Texture[] getHitTextures() {
        return hitTextures;
    }
}
