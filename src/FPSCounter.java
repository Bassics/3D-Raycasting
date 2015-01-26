import org.jsfml.graphics.*;

import java.io.IOException;
import java.nio.file.Paths;

public class FPSCounter implements Drawable {
    private Font font;
    private Text text;
    public FPSCounter() {
        font = new Font();
        try {
            font.loadFromFile(Paths.get("res/font/Sebaldus-Gotisch.ttf"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        text = new Text("N/A", font, 24);
        text.setColor(Color.YELLOW);
    }
    public void updateFPS(int x) {
        text.setString(x+"");
    }
    public void draw(RenderTarget target, RenderStates states) {
        text.draw(target, states);
    }
}
