import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector3f;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Weapon implements Drawable {
    Player player;
    String weaponName;
    Sprite weaponSprite = new Sprite();
    Texture baseTexture = new Texture();
    RectangleShape[] crossHairs;
    ArrayList<Texture> renderQueue = new ArrayList<Texture>();
    ArrayList<Texture> fireTextures = new ArrayList<Texture>();
    private int xOffset = 0;
    private int yOffset = 0;
    private SoundBuffer soundBuffer = new SoundBuffer();
    private Sound sound = new Sound();

    private final int crosshairOffset = 20;
    private int currentOffset = 0;

    public Weapon(String name, Player p) {
        weaponName = name;
        player = p;
        loadTextures();
    }
    public void loadTextures() {
        //baseTexture.setSmooth(true);
        try {
            baseTexture.loadFromStream(
                    getClass().getClassLoader().getResourceAsStream("wep/" + weaponName + "_base.png")
            );
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        try{
            soundBuffer.loadFromStream(getClass().getClassLoader().getResourceAsStream("salmon.wav"));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 1; i < 15; i++) {
            Texture currentTexture = new Texture();
            //currentTexture.setSmooth(true);
            try {
                currentTexture.loadFromStream(
                        getClass().getClassLoader().getResourceAsStream("wep/" + weaponName + "_fire" + i + ".png")
                );
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            fireTextures.add(currentTexture);
        }

        sound.setBuffer(soundBuffer);

        crossHairs = new RectangleShape[]{
                new RectangleShape(new Vector2f(2, 10)),
                new RectangleShape(new Vector2f(2, 10)),
                new RectangleShape(new Vector2f(10, 2)),
                new RectangleShape(new Vector2f(10, 2))
        };

    }
    public void animateShooting() {
        sound.play();
        renderQueue = new ArrayList<Texture>(fireTextures);
        int w = WeaponHandler.getWindowDimensions().x;
        float camDirection = 2f * (w/2f) / w - 1;
        Vector2f rayDirection = new Vector2f(
                player.getDirection().x + player.getPlane().x * camDirection,
                player.getDirection().y + player.getPlane().y * camDirection
        );
        Ray cast = new Ray(player.getPosition(), rayDirection);
        boolean hitObject = cast.calculateRay();
        int h = WeaponHandler.getWindowDimensions().y;
        int lineHeight = Math.abs((int)(h/cast.getLength()));
        int yaw = (int) (player.getYaw() * (h / 2));
        int wallTop = -lineHeight / 2 + h / 2 - yaw;
        int wallBottom = lineHeight / 2 + h / 2 - yaw;
        if (hitObject) {
            if (h/2 > wallTop && h/2 < wallBottom - lineHeight / 10) {
                Vector3f hitPosition = new Vector3f(
                        cast.getExactHit().x,
                        cast.getExactHit().y,
                        (h / 2 - wallTop) / (float) lineHeight
                );
                Map.addHitPosition(hitPosition, TextureHandler.getHitTextures()[1 + (int) (Math.random() * 4)], cast.getSide());
            }
        }
    }
    public void draw(RenderTarget target, RenderStates states) {
        int o = Math.max((int)((player.getDirection().x + 0.5) * 1000f), 100);
        Color weaponColor = new Color(o,o,o);
        if (renderQueue.size() > 0) {
            weaponSprite.setTexture(renderQueue.get(0));
        } else {
            weaponSprite.setTexture(baseTexture);
        }
        Vector2i spriteSize = weaponSprite.getTexture().getSize();
        Vector2i windowDim = WeaponHandler.getWindowDimensions();
        weaponSprite.setTextureRect(
                new IntRect(0, 0, spriteSize.x, spriteSize.y)
        );
        weaponSprite.setColor(weaponColor);
        float sizeRatio = windowDim.y/(float)spriteSize.y*2f;
        weaponSprite.setScale(new Vector2f(sizeRatio, sizeRatio));
        weaponSprite.setPosition(
                xOffset + windowDim.x/2 - (sizeRatio * spriteSize.x)/3,
                yOffset + windowDim.y/2 - (sizeRatio * spriteSize.y)/4
        );
        weaponSprite.draw(target, states);
        for (int i = 0; i < crossHairs.length; i++) {
            crossHairs[i].draw(target, states);
        }
    }
    public void update() {
        if (renderQueue.size() > 0) {
            renderQueue.remove(0);
        }
        float dir = Math.abs(player.getForwardDir()) == 1 || Math.abs(player.getSidewaysDir()) == 1 ? 1f : 0f;
        currentOffset = (int)Arithmetic.lerp(currentOffset, dir * crosshairOffset, 0.5f);
        int h = WeaponHandler.getWindowDimensions().y;
        int w = WeaponHandler.getWindowDimensions().x;
        crossHairs[0].setPosition(w/2 - crossHairs[0].getSize().x/2, h/2 - crossHairs[0].getSize().y*1.5f - currentOffset);
        crossHairs[1].setPosition(w/2 - crossHairs[1].getSize().x/2, h/2 + crossHairs[1].getSize().y*.5f + currentOffset);
        crossHairs[2].setPosition(w/2 - crossHairs[2].getSize().x*1.5f - currentOffset, h/2 - crossHairs[2].getSize().y/2);
        crossHairs[3].setPosition(w/2 + crossHairs[3].getSize().x*.5f + currentOffset, h/2 - crossHairs[3].getSize().y/2);
    }

    public void setPitchOffset(int x) {
        this.xOffset = (int)(Arithmetic.lerp(xOffset, x, 0.5f));
    }

    public void setYawOffset(int y) {
        this.yOffset = (int)(Arithmetic.lerp(yOffset, y, 0.5f));
    }
}
