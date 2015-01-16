import org.jsfml.graphics.Texture;

import javax.xml.soap.Text;
import java.io.IOException;
import java.nio.file.Paths;

public class TextureHolder {
    private final static int NUM_TEXTURES = 58;
    private static Texture[][] textures = new Texture[NUM_TEXTURES + 1][2];
    public static void loadTextures() {
        for (int i = 1; i <= NUM_TEXTURES; i++) {
            Texture texture1 = new Texture();
            Texture texture2 = new Texture();
            System.out.println("wolfenstein" + i + "_" + 1 + ".png");
            try {
                texture1.loadFromStream(TextureHolder.class.getClassLoader().getResourceAsStream("tex/wolfenstein" + i + "_" + 1 + ".png"));
                texture2.loadFromStream(TextureHolder.class.getClassLoader().getResourceAsStream("tex/wolfenstein" + i + "_" + 2 + ".png"));
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
