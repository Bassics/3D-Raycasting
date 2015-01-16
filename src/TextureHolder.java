import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

public class TextureHolder {
    private static int numTextures = 58;
    private static Texture[][] textures = new Texture[numTextures + 1][2];
    public static void loadTextures() {
        for (int i = 1; i <= numTextures; i++) {
            Texture texture1 = new Texture();
            Texture texture2 = new Texture();
            System.out.println("wolfenstein" + i + "_" + 1 + ".png");
            try {
                texture1.loadFromFile(Paths.get("res/tex/wolfenstein" + i + "_" + 1 + ".png"));
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            try {
                texture2.loadFromFile(Paths.get("res/tex/wolfenstein" + i + "_" + 2 + ".png"));
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            textures[i] = new Texture[]{texture1, texture2};
        }
    }
    public static Texture[][] getTextures() {
        return textures;
    }
}
