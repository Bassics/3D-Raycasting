import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector3f;

import java.util.Arrays;

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
                Texture wallTexture = TextureHandler.getWallTextures()[cast.getInt()][cast.getSide()];
                /* Calculate what x pixel on the texture we want to render */
                int texturePosition = (int) (cast.getHitPosition() * (float) wallTexture.getSize().x);
                /* Depending on the direction of the wall, get the inverse texture position */
                if (cast.getSide() == 0 && cast.getDirection().x > 0 || cast.getSide() == 1 && cast.getDirection().y < 0)
                    texturePosition = wallTexture.getSize().x - texturePosition - 1;
                /* Where the line starts on the screen */
                int wallTop = -lineHeight / 2 + h / 2 - yaw;
                /* Where the line ends on the screen */
                int wallBottom = lineHeight / 2 + h / 2 - yaw;
                /* The offset of color depending on how far the player is */
                float colorOff = (float) Math.pow(cast.getLength()/3, 0.5);
                /* The current ambient color of the line */
                Color wallColor = new Color((int) (255 / colorOff), (int) (255 / colorOff), (int) (255 / colorOff));
                /* Create a new sprite for the line */
                Sprite wallSprite = SpriteHandler.getWallSprite(x);
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
                for (int i = 0; i < Map.getHitPositions().size(); i++) {
                    Vector3f hitPosition = Map.getHitPosition(i);
                    float hitSide = cast.getSide() == 1 ? cast.getExactHit().x : cast.getExactHit().y;
                    float dataSide = cast.getSide() == 1 ? hitPosition.x : hitPosition.y;
                    if ((int)hitSide == (int)dataSide && Map.getHitSide(i) == cast.getSide()) {
                        float hitOffset = (hitSide - dataSide) * 10;
                        if (hitSide > dataSide && hitOffset > 0 && hitOffset < 1) {
                            Texture hitTexture = Map.getHitTexture(i);
                            Sprite hitSprite = SpriteHandler.getBulletSprite(x);
                            hitSprite.setTexture(hitTexture);
                            hitSprite.setTextureRect(
                                    new IntRect((int) (hitOffset * (float) hitTexture.getSize().x), 0, 1, 4)
                            );
                            hitSprite.setScale(new Vector2f(1, lineHeight / 10 / (float) hitTexture.getSize().y));
                            hitSprite.setPosition(x, wallTop + (int) ((hitPosition.z) * lineHeight));
                            hitSprite.draw(target, states);
                        }
                    }
                }
                /* Get correct directions for floor rendering */
                /*Vector2f floorPosition = new Vector2f(0, 0);
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
                Texture floorTexture = Map.getFloorTexture();
                for (int y = wallBottom + 1; y < h; y++) {
                    float currentDist = h / (2f * y - h);
                    float weight = (currentDist - 0f) / (cast.getLength() - 0f);
                    Vector2f floorDistance = new Vector2f(
                            weight * floorPosition.x + (1 - weight) * player.getPosition().x,
                            weight * floorPosition.y + (1 - weight) * player.getPosition().y
                    );
                    Vector2i floorTexPos = new Vector2i(
                            (int)(floorDistance.x * floorTexture.getSize().x) % floorTexture.getSize().x,
                            (int)(floorDistance.y * floorTexture.getSize().y) % floorTexture.getSize().y
                    );
                    Sprite floorSprite = new Sprite();
                    floorSprite.setTexture(floorTexture);
                    floorSprite.setTextureRect(new IntRect(floorTexPos.x, floorTexPos.y, 1, 1));
                    floorSprite.setScale(new Vector2f(1, 1));
                    floorSprite.setPosition(x, y);
                    floorSprite.setColor(wallColor);
                    floorSprite.draw(target, states);
                }*/
            }
        }
        WeaponHandler.getCurrentWeapon().draw(target, states);
    }
}
