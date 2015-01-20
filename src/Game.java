import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.*;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;

/**
 * @author: Kyle Lee
 */

public class Game {
    /* The main RenderWindow of the game */
    private final RenderWindow window = new RenderWindow();
    /* String used to name the title bar */
    private final String windowTitle = "Game";
    /* We don't necessarily need this, but I am keeping it for good measures */
    private final Vector2i windowDimensions = new Vector2i(640, 480);
    /* Boolean used to keep track of whether the window is focused */
    private boolean windowFocus = true;
    /* Instantiate the Player */
    private Player player;
    /* Instantiate the Renderer */
    private Renderer renderer;
    /* These two are currently used for testing */
    private final float playerSpeed = 0.05f;
    private final float cameraSpeed = 0.02f;
    /* Keep track of the previous position of the mouse */
    private Vector2i lastMousePosition = new Vector2i(0, 0);

    public static void main(String[] args) {
        /* Start the new game */
        Game g = new Game();
        g.run();
    }

    public void doInitialize() {
        /* Create the window */
        window.create(new VideoMode(windowDimensions.x,windowDimensions.y), windowTitle, WindowStyle.FULLSCREEN);
        /* We don't want an annoying mouse cursor now, do we? */
        window.setMouseCursorVisible(false);
        window.setFramerateLimit(60);
        window.setVerticalSyncEnabled(true);
        /* TODO: I'll eventually use a text parser for this =3= */
        Map.loadMap(
                new int[][]{
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
                        {41,41,37,41,35,41,37,41,35,41,37,41,35,41,37,41,35,41,37,41,35,41,41}
                }
        );
        /* Let me instantiate that player for you. */
        player = new Player();
        /* TODO: Again, I'll use a text parser for this */
        player.setPosition(new Vector2f(22, 12));
        /* TODO: And this */
        player.setDirection(new Vector2f(-1, 0));
        /* This sets the plane for the camera rendering */
        player.setPlane(new Vector2f(0, 0.66f));
        /* Set the player's move speed */
        player.setMoveSpeed(playerSpeed);
        /* Set the camera's rotation speed */
        player.setRotSpeed(cameraSpeed);
        /* Load all of the needed textures */
        TextureHandler.loadTextures();
        Map.setFloorTexture(TextureHandler.getWallTextures()[1][1]);
        WeaponHandler.setWindowDimensions(windowDimensions);
        WeaponHandler.createWeapon("m1911", player);
        /* Instantiate the renderer */
        renderer = new Renderer(window, player);
        /* Set the last mouse position to the current mouse position */
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
                case MOUSE_BUTTON_PRESSED:
                    switch (event.asMouseButtonEvent().button) {
                        case LEFT:
                            WeaponHandler.getCurrentWeapon().animateShooting();
                            break;
                    }
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
                default:
                    break;
            }
        }
        if (windowFocus) {
            /* Move the player in the desired direction */
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
        if (windowFocus) {
            /* Get the current mouse position */
            Vector2i currentMousePosition = Mouse.getPosition();
            /* Get the difference between the last and the current mouse position */
            Vector2i mouseMovement = Vector2i.sub(lastMousePosition, currentMousePosition);
            /* Set the last mouse position to the center (explanation below) */
            lastMousePosition = new Vector2i(windowDimensions.x / 2, windowDimensions.y / 2);
            /* Rotate the camera by the buffered mouse movement */
            player.rotateCamera((float) mouseMovement.x / 10);
            player.tiltCamera((float) -mouseMovement.y / 10);
            /* Set the current mouse position to the center so the mouse doesn't go off the edge */
            Mouse.setPosition(lastMousePosition);
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
        float SECONDS_PER_UPDATE = 1 / 60f;
        Clock updateClock = new Clock();
        Clock testClock = new Clock();
        float nextTime = updateClock.getElapsedTime().asSeconds();
        float lagTime = 0;
        while (window.isOpen()) {
            float elapsedTime = updateClock.restart().asSeconds();
            lagTime += elapsedTime;

            doInput();

            while (lagTime >= SECONDS_PER_UPDATE) {
                doLogic();
                lagTime -= SECONDS_PER_UPDATE;
            }
            testClock.restart();
            doRender();
            //System.out.println(testClock.getElapsedTime().asSeconds());
            float extrapolation = lagTime / SECONDS_PER_UPDATE;
            //player.setMoveSpeed(playerSpeed + (playerSpeed * extrapolation));
            //player.setRotSpeed(cameraSpeed + (cameraSpeed * extrapolation));
        }
    }

}
