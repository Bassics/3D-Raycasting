import org.jsfml.graphics.Texture;
import java.io.IOException;

public class TextureHandler {
    private final static int NUM_TEXTURES = 58;
    private static Texture[][] wallTextures = new Texture[NUM_TEXTURES + 1][2];
    private static Texture[] hitTextures = new Texture[5];
    public static void loadTextures() {
        for (int i = 1; i <= NUM_TEXTURES; i++) {
            Texture texture1 = new Texture();
            Texture texture2 = new Texture();
            System.out.println("wolfenstein" + i + "_" + 1 + ".png");
            try {
                texture1.loadFromStream(
                        TextureHandler.class.getClassLoader().getResourceAsStream("tex/wolfenstein" + i + "_" + 1 + ".png")
                );
                texture2.loadFromStream(
                        TextureHandler.class.getClassLoader().getResourceAsStream("tex/wolfenstein" + i + "_" + 2 + ".png")
                );
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            wallTextures[i] = new Texture[]{texture1, texture2};
        }
        for (int i = 1; i <= 4; i++) {
            Texture texture = new Texture();
            try {
                texture.loadFromStream(
                        TextureHandler.class.getClassLoader().getResourceAsStream("tex/hit"+i+".png")
                );
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            hitTextures[i] = texture;
        }
    }
    public static Texture[][] getWallTextures() {
        return wallTextures;
    }

    public static Texture[] getHitTextures() {
        return hitTextures;
    }
}
