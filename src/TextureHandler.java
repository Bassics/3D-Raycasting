import org.jsfml.graphics.Texture;
import java.io.IOException;

public class TextureHandler {
    private final static int NUM_TEXTURES = 58;
    private static Texture[][] wallTextures = new Texture[NUM_TEXTURES + 1][2];
    private static Texture[] hitTextures = new Texture[5];
    public static void loadTextures() {
        for (int i = 1; i <= NUM_TEXTURES; i++) {
            wallTextures[i] = new Texture[]{new Texture(), new Texture()};
            System.out.println("wolfenstein" + i + "_" + 1 + ".png");
            try {
                wallTextures[i][0].loadFromStream(
                        TextureHandler.class.getClassLoader().getResourceAsStream("tex/wolfenstein" + i + "_" + 1 + ".png")
                );
                wallTextures[i][1].loadFromStream(
                        TextureHandler.class.getClassLoader().getResourceAsStream("tex/wolfenstein" + i + "_" + 2 + ".png")
                );
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        for (int i = 1; i <= 4; i++) {
            hitTextures[i] = new Texture();
            try {
                hitTextures[i].loadFromStream(
                        TextureHandler.class.getClassLoader().getResourceAsStream("tex/hit"+i+".png")
                );
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static Texture[][] getWallTextures() {
        return wallTextures;
    }

    public static Texture[] getHitTextures() {
        return hitTextures;
    }
}
