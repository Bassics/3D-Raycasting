import org.jsfml.audio.SoundBuffer;
import java.io.IOException;

public class SoundHandler {
    public static void setSound(SoundBuffer sb, String s) {
        try {
            sb.loadFromStream(SoundHandler.class.getClassLoader().getResourceAsStream(s));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
