import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector3f;

import java.util.ArrayList;

public class Weapon implements Drawable {
    private final int crosshairOffset = 20;
    Player player;
    String weaponName;
    Sprite weaponSprite = new Sprite();
    Texture baseTexture = new Texture();
    RectangleShape[] crossHairs;
    ArrayList<Texture> renderQueue = new ArrayList<Texture>();
    ArrayList<Texture> fireTextures = new ArrayList<Texture>();
    ArrayList<Float> cameraQueue = new ArrayList<Float>();
    private RenderWindow window;
    private int xOffset = 0;
    private int yOffset = 0;
    private int bobXOffset = 0;
    private int bobYOffset = 0;
    private float currentRotation = 0;
    private SoundBuffer soundBuffer = new SoundBuffer();
    private Sound sound = new Sound();
    private int numUpdates = 0;
    private int currentOffset = 0;

    public Weapon(String name, Player p, RenderWindow w) {
        weaponName = name;
        player = p;
        window = w;
        loadTextures();
    }

    public void loadTextures() {
        TextureHandler.setTexture(baseTexture, "wep/" + weaponName + "_base.png");
        SoundHandler.setSound(soundBuffer, "salmon.wav");
        sound.setBuffer(soundBuffer);
        for (int i = 1; i < 15; i++) {
            Texture currentTexture = new Texture();
            TextureHandler.setTexture(currentTexture, "wep/" + weaponName + "_fire" + i + ".png");
            fireTextures.add(currentTexture);
        }
        crossHairs = new RectangleShape[]{
                new RectangleShape(new Vector2f(2, 10)),
                new RectangleShape(new Vector2f(2, 10)),
                new RectangleShape(new Vector2f(10, 2)),
                new RectangleShape(new Vector2f(10, 2))
        };
    }

    public void doShooting() {
        cameraQueue = new ArrayList<Float>();
        for (int i = 0; i < 5; i++) {
            cameraQueue.add(-0.5f);
        }
        for (int i = 0; i < 5; i++) {
            cameraQueue.add(0.5f);
        }
        sound.play();
        renderQueue = new ArrayList<Texture>(fireTextures);
        int w = window.getSize().x;
        float camDirection = 2f * (w / 2f) / w - 1;
        Vector2f rayDirection = new Vector2f(
                player.getDirection().x + player.getPlane().x * camDirection,
                player.getDirection().y + player.getPlane().y * camDirection
        );
        Ray cast = new Ray(player.getPosition(), rayDirection);
        boolean hitObject = cast.calculateRay();
        int h = window.getSize().y;
        int lineHeight = Math.abs((int) (h / cast.getLength()));
        int yaw = (int) (player.getYaw() * (h / 2));
        int wallTop = -lineHeight / 2 + h / 2 - yaw;
        int wallBottom = lineHeight / 2 + h / 2 - yaw;
        if (hitObject) {
            if (h / 2 > wallTop && h / 2 < wallBottom) {
                Vector3f hitPosition = new Vector3f(
                        cast.getExactHit().x,
                        cast.getExactHit().y,
                        (h / 2 - wallTop) / (float) lineHeight
                );
                Map.addHitPosition(hitPosition, TextureHandler.getHitTextures()[1 + (int) (Math.random() * 4)], cast.getMapPos());
            }
        }
    }

    public void resetNumUpdates() {
        numUpdates = 0;
    }

    public void draw(RenderTarget target, RenderStates states) {
        int o = Math.max((int) ((player.getDirection().x + 0.5) * 1000f), 100);
        Color weaponColor = new Color(o, o, o);
        if (renderQueue.size() > 0) {
            weaponSprite.setTexture(renderQueue.get(0));
        } else {
            weaponSprite.setTexture(baseTexture);
        }
        if (cameraQueue.size() > 0) {
            player.tiltCamera(cameraQueue.get(0));
        }
        Vector2i spriteSize = weaponSprite.getTexture().getSize();
        weaponSprite.setTextureRect(
                new IntRect(0, 0, spriteSize.x, spriteSize.y)
        );
        weaponSprite.setColor(weaponColor);
        float sizeRatio = window.getSize().y / (float) spriteSize.y;
        weaponSprite.setScale(new Vector2f(sizeRatio, sizeRatio));
        weaponSprite.setRotation(currentRotation);
        weaponSprite.setPosition(
                bobXOffset + xOffset + window.getSize().x / 2 - (sizeRatio * spriteSize.x) / 1.25f,
                bobYOffset + yOffset + window.getSize().y / 2 - (sizeRatio * spriteSize.y) / 4
        );
        weaponSprite.draw(target, states);
        for (int i = 0; i < crossHairs.length; i++) {
            crossHairs[i].draw(target, states);
        }
    }

    public void update() {
        numUpdates++;
        numUpdates = Math.max(0, numUpdates);
        if (player.getSidewaysDir() != 0 || player.getForwardDir() != 0) {
            currentRotation = Arithmetic.lerp(currentRotation, (numUpdates / 15 % 2) * 12, 0.05f);
            bobXOffset = (int)Arithmetic.lerp(bobXOffset, (numUpdates / 15 % 4) * 14, 0.05f);
            bobYOffset = (int)Arithmetic.lerp(bobYOffset, (numUpdates / 15 % 2) * 14, 0.05f);
        } else {
            currentRotation = Arithmetic.lerp(currentRotation, (numUpdates / 60 % 2), 0.05f);
            bobXOffset = (int)Arithmetic.lerp(bobXOffset, 0, 0.05f);
            bobYOffset = (int)Arithmetic.lerp(bobYOffset, 0, 0.05f);
        }
        if (cameraQueue.size() > 0) {
            cameraQueue.remove(0);
        }
        if (renderQueue.size() > 0) {
            renderQueue.remove(0);
        }
        float dir = Math.abs(player.getForwardDir()) == 1 || Math.abs(player.getSidewaysDir()) == 1 ? 1f : 0f;
        currentOffset = (int) Arithmetic.lerp(currentOffset, dir * crosshairOffset, 0.25f);
        int h = window.getSize().y;
        int w = window.getSize().x;
        crossHairs[0].setPosition(w / 2 - crossHairs[0].getSize().x / 2, h / 2 - crossHairs[0].getSize().y * 1.5f - currentOffset);
        crossHairs[1].setPosition(w / 2 - crossHairs[1].getSize().x / 2, h / 2 + crossHairs[1].getSize().y * .5f + currentOffset);
        crossHairs[2].setPosition(w / 2 - crossHairs[2].getSize().x * 1.5f - currentOffset, h / 2 - crossHairs[2].getSize().y / 2);
        crossHairs[3].setPosition(w / 2 + crossHairs[3].getSize().x * .5f + currentOffset, h / 2 - crossHairs[3].getSize().y / 2);
    }

    public void setPitchOffset(int x) {
        this.xOffset = (int) (Arithmetic.lerp(xOffset, x, 0.25f));
    }

    public void setYawOffset(int y) {
        this.yOffset = (int) (Arithmetic.lerp(yOffset, y, 0.25f));
    }
}
