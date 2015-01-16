import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.*;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;

public class Game {

    private final RenderWindow window = new RenderWindow();
    private final String windowTitle = "Game";
    private final Vector2i windowDimensions = new Vector2i(640, 480);
    private final int antiAliasingLevel = 0;
    private boolean windowFocus = true;
    private Player player;
    private Renderer renderer;
    private final float playerSpeed = 0.01f;
    private final float cameraSpeed = 0.01f;
    private Vector2i lastMousePosition = new Vector2i(0,0);

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

    public void doInitialize() {
        VideoMode videoMode = new VideoMode(windowDimensions.x, windowDimensions.y);
        int windowStyle = WindowStyle.CLOSE | WindowStyle.TITLEBAR;
        window.create(VideoMode.getDesktopMode(), windowTitle, WindowStyle.FULLSCREEN, new ContextSettings(antiAliasingLevel));
        window.setMouseCursorVisible(false);
        Map.loadMap(
                new int[][] {
                        {41,41,37,41,35,41,37,41,35,41,37,41,35,41,37,41,35,41,37,41,35,41,41},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {37, 0, 5, 8, 7, 9, 5, 8, 5, 9, 5, 8, 5, 9, 5, 8, 5, 9, 5, 8, 5, 9,37},
                        {41, 0,42,12,24,12,12,11,12, 2, 1, 2,28, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {35, 0,17,12, 0, 0, 0, 0, 0, 3, 0, 0, 0,28, 0, 0, 0, 0, 0, 0, 0, 0,35},
                        {41, 0,18,24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {37, 0,17,12, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0,37},
                        {41, 0,17,12,24,12,12,11,12,28, 0, 0, 0, 0, 0,28, 0, 0, 0, 0, 0, 0,41},
                        {35, 0,20,17,17,18,17, 1, 2, 6, 1, 2,28,29, 0,29, 0, 0, 0, 0, 0, 0,35},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {37, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,37},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,35},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {37, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,37},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,35},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {37, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,37},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,35},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {37, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,37},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,35},
                        {41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,41},
                        {41,41,37,41,35,41,37,41,35,41,37,41,35,41,37,41,35,41,37,41,35,41,41},
                }
        );
        player = new Player();
        player.setPosition(new Vector2f(22, 12));
        player.setDirection(new Vector2f(-1, 0));
        player.setPlane(new Vector2f(0, 0.66f));
        player.setMoveSpeed(0.01f);
        player.setRotSpeed(0.01f);
        TextureHolder.loadTextures();
        renderer = new Renderer(window, player);
        renderer.setFloorColor(new Color(148, 57, 0));
        renderer.setRoofColor(new Color(100, 100, 100));
        lastMousePosition = Mouse.getPosition();
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
                        case ESCAPE:
                            window.close();
                            break;
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
                case MOUSE_MOVED:
                    break;
                default:
                    break;
            }
        }
        if (windowFocus) {
            if (Keyboard.isKeyPressed(Key.W)) {
                player.moveForward(1);
            }
            if (Keyboard.isKeyPressed(Key.A)) {
                player.moveSideways(1);
            }
            if (Keyboard.isKeyPressed(Key.S)) {
                player.moveForward(-1);
            }
            if (Keyboard.isKeyPressed(Key.D)) {
                player.moveSideways(-1);
            }
        }
        Vector2i currentMousePosition = Mouse.getPosition();
        Vector2i mouseMovement = Vector2i.sub(lastMousePosition, currentMousePosition);
        lastMousePosition = new Vector2i(windowDimensions.x/2, windowDimensions.y/2);
        player.rotateCamera((float)mouseMovement.x/10);
        player.tiltCamera((float) -mouseMovement.y / 10);
        Mouse.setPosition(lastMousePosition);
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
        float SECONDS_PER_UPDATE = 1/60f;
        Clock updateClock = new Clock();
        float lagTime = 0;
        while (window.isOpen()) {
            float elapsedTime = updateClock.restart().asSeconds();
            lagTime += elapsedTime;

            doInput();

            while (lagTime >= SECONDS_PER_UPDATE) {
                doLogic();
                lagTime -= SECONDS_PER_UPDATE;
            }

            doRender();

            float extrapolation = lagTime / SECONDS_PER_UPDATE;
            player.setMoveSpeed(playerSpeed + (playerSpeed * extrapolation));
            player.setRotSpeed(cameraSpeed + (cameraSpeed * extrapolation));
        }
    }

}
