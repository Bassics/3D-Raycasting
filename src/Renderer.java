import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Renderer implements Drawable {
    private RenderWindow window;
    private Player player;

    public Renderer(RenderWindow w, Player p) {
        window = w;
        player = p;
    }

    public void draw(RenderTarget target, RenderStates states) {
        /* Get the width and height for later */
        int w = window.getSize().x;
        int h = window.getSize().y;
        /* This is the amount that the camera is tilted on the y-axis */
        int yaw = (int) (player.getYaw() * (h / 2));
        /* Loop through every x pixel */
        for (int x = 0; x < w; x++) {
            /* The pixel in camera space */
            float camDirection = 2f * x / w - 1;
            /* The unit vector direction of the ray */
            Vector2f rayDirection = new Vector2f(
                    player.getDirection().x + player.getPlane().x * camDirection,
                    player.getDirection().y + player.getPlane().y * camDirection
            );
            /* Cast a new ray and calculate it */
            Ray cast = new Ray(player.getPosition(), rayDirection);
            boolean hitObject = cast.calculateRay();
            if (hitObject) {
                /* This is the height of the current pixel's line */
                int lineHeight = Math.abs((int) (h / cast.getLength()));
                /* Grab the texture from the stored ones */
                Texture wallTexture = TextureHolder.getTextures()[cast.getInt()][cast.getSide()];
                /* Calculate what x pixel on the texture we want to render */
                int texturePosition = (int) (cast.getHitPosition() * (double) wallTexture.getSize().x);
                /* Depending on the direction of the wall, get the inverse texture position */
                if (cast.getSide() == 0 && cast.getDirection().x > 0 || cast.getSide() == 1 && cast.getDirection().y < 0)
                    texturePosition = wallTexture.getSize().x - texturePosition - 1;
                /* Where the line starts on the screen */
                int wallTop = -lineHeight / 2 + h / 2 - yaw;
                /* Where the line ends on the screen */
                int wallBottom = lineHeight / 2 + h / 2 - yaw;
                /* The offset of color depending on how far the player is */
                float colorOff = (float) Math.pow(cast.getLength(), 1.25);
                /* The current ambient color of the line */
                Color wallColor = new Color((int) (255 / colorOff), (int) (255 / colorOff), (int) (255 / colorOff));
                /* Create a new sprite for the line */
                Sprite wallSprite = new Sprite();
                /* Set the texture */
                wallSprite.setTexture(wallTexture);
                /* Correct the scaling of the sprite and texture */
                wallSprite.setTextureRect(new IntRect(texturePosition, 0, 1, wallTexture.getSize().y));
                wallSprite.setScale(new Vector2f(1, lineHeight / (float) wallTexture.getSize().y));
                /* Position the line */
                wallSprite.setPosition(x, wallTop);
                /* Set the ambient color of the line */
                wallSprite.setColor(wallColor);
                wallSprite.draw(target, states);
                /* Get correct directions for floor rendering */
                Vector2f floorPosition = new Vector2f(0, 0);
                if (cast.getSide() == 0 && cast.getDirection().x > 0)
                    floorPosition =
                            new Vector2f(cast.getMapPos()[0], cast.getMapPos()[1] + cast.getHitPosition());
                else if (cast.getSide() == 0 && cast.getDirection().x < 0)
                    floorPosition =
                            new Vector2f(cast.getMapPos()[0] + 1, cast.getMapPos()[1] + +cast.getHitPosition());
                else if (cast.getSide() == 1 && cast.getDirection().y > 0)
                    floorPosition =
                            new Vector2f(cast.getMapPos()[0] + cast.getHitPosition(), cast.getMapPos()[1]);
                else
                    floorPosition =
                            new Vector2f(cast.getMapPos()[0] + cast.getHitPosition(), cast.getMapPos()[1] + 1);
                /*for (int y = wallBottom + 1; y < h; y++) {
                    float currentDist = h / (2f * y - h);
                    float asfasf = (currentDist - 0f) / (cast.getLength() - 0f);
                    Vector2f floorDistance = new Vector2f(
                            asfasf * floorPosition.x + (1 - asfasf) * player.getPosition().x,
                            asfasf * floorPosition.y + (1 - asfasf) * player.getPosition().y
                    );
                    Vector2i floorTexPos = new Vector2i(
                            (int)(floorDistance.x * )
                    );
                }*/
            }
        }
    }
}
