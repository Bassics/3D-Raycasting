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
        int w = window.getSize().x;
        int h = window.getSize().y;
        int yaw = (int) (player.getYaw() * (h / 2));
        for (int x = 0; x < w; x++) {
            float camDirection = 2f * x / w - 1;
            Vector2f rayDirection = new Vector2f(
                    player.getDirection().x + player.getPlane().x * camDirection,
                    player.getDirection().y + player.getPlane().y * camDirection
            );
            Ray cast = new Ray(player.getPosition(), rayDirection);
            boolean hitObject = cast.calculateRay();
            if (hitObject) {
                int lineHeight = Math.abs((int) (h / cast.getLength()));
                Texture wallTexture = TextureHolder.getTextures()[cast.getInt()][cast.getSide()];
                int texturePosition = (int) (cast.getHitPosition() * (double) wallTexture.getSize().x);
                if (cast.getSide() == 0 && cast.getDirection().x > 0 || cast.getSide() == 1 && cast.getDirection().y < 0)
                    texturePosition = wallTexture.getSize().x - texturePosition - 1;
                int wallTop = -lineHeight / 2 + h / 2 - yaw;
                int wallBottom = lineHeight / 2 + h / 2 - yaw;
                float colorOff = (float) Math.pow(cast.getLength(), 1.25);
                Color wallColor = new Color((int) (255 / colorOff), (int) (255 / colorOff), (int) (255 / colorOff));
                Sprite wallSprite = new Sprite();
                wallSprite.setTexture(wallTexture);
                wallSprite.setTextureRect(new IntRect(texturePosition, 0, 1, wallTexture.getSize().y));
                wallSprite.setScale(new Vector2f(1, lineHeight / (float) wallTexture.getSize().y));
                wallSprite.setPosition(x, wallTop);
                wallSprite.setColor(wallColor);
                wallSprite.draw(target, states);
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
