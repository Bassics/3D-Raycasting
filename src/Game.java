import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

public class Game {

    private final RenderWindow window = new RenderWindow();
    private final String windowTitle = "Game";
    private final Vector2i windowDimensions = new Vector2i(640, 480);
    private final int antiAliasingLevel = 0;
    private boolean windowFocus = true;
    private Player player;
    private Renderer renderer;

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

    public void doInitialize() {
        VideoMode videoMode = new VideoMode(windowDimensions.x, windowDimensions.y);
        int windowStyle = WindowStyle.CLOSE | WindowStyle.TITLEBAR;
        window.create(videoMode, windowTitle, windowStyle, new ContextSettings(antiAliasingLevel));
        Map.loadMap(
                new int[][] {
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,2,2,2,2,2,0,0,0,0,3,0,3,0,3,0,0,0,1},
                        {1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,3,0,0,0,3,0,0,0,1},
                        {1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,2,2,0,2,2,0,0,0,0,3,0,3,0,3,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,4,0,4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,4,0,0,0,0,5,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,4,0,4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,4,0,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                }
        );
        player = new Player();
        player.setPosition(new Vector2f(22, 12));
        player.setDirection(new Vector2f(-1, 0));
        player.setPlane(new Vector2f(0, 0.66f));
        player.setMoveSpeed(0.01f);
        player.setRotSpeed(0.01f);
        renderer = new Renderer(window, player);
    }

    public void doInput() {
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case GAINED_FOCUS:
                    windowFocus = true;
                    break;
                case LOST_FOCUS:
                    windowFocus = false;
                    break;
                case KEY_PRESSED:
                    switch (event.asKeyEvent().key) {
                        case W:
                            break;
                        case A:
                            break;
                        case S:
                            break;
                        case D:
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        if (windowFocus) {
            if (Keyboard.isKeyPressed(Key.W)) {
                player.movePlayer(1);
            }
            if (Keyboard.isKeyPressed(Key.A)) {
                player.rotateCamera(1);
            }
            if (Keyboard.isKeyPressed(Key.S)) {
                player.movePlayer(-1);
            }
            if (Keyboard.isKeyPressed(Key.D)) {
                player.rotateCamera(-1);
            }
        }
    }

    public void doLogic() {
    }

    public void doRender() {
        window.clear();

        window.draw(renderer);

        window.display();
    }

    public void run() {
        doInitialize();
        Clock updateClock = new Clock();
        float lagTime = 0;
        while (window.isOpen()) {
            float elapsedTime = updateClock.restart().asSeconds();
            lagTime += elapsedTime;

            doInput();

            while (lagTime >= 0.01) {
                doLogic();
                lagTime -= 0.01;
            }

            doRender();
        }
    }

}
