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
    ArrayList<Texture> renderQueue = new ArrayList<Texture>();
    ArrayList<Texture> fireTextures = new ArrayList<Texture>();
    private int xOffset = 0;
    private int yOffset = 0;
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
    }
    public void animateShooting() {
        renderQueue = new ArrayList<Texture>(fireTextures);
        int w = WeaponHandler.getWindowDimensions().x;
        float camDirection = 2f * (w/2) / w - 1;
        Vector2f rayDirection = new Vector2f(
                player.getDirection().x + player.getPlane().x * camDirection,
                player.getDirection().y + player.getPlane().y * camDirection
        );
        Ray cast = new Ray(player.getPosition(), rayDirection);
        boolean hitObject = cast.calculateRay();
        if (hitObject) {
            Vector3f hitPosition = new Vector3f(cast.getExactHit().x, cast.getExactHit().y, player.getYaw());
            Map.addHitPosition(hitPosition, TextureHandler.getHitTextures()[1+(int)(Math.random()*4)], cast.getSide());
        }
    }
    public void draw(RenderTarget target, RenderStates states) {
        int o = Math.max((int)((player.getDirection().x + 0.5) * 1000f), 100);
        Color weaponColor = new Color(o,o,o);
        if (renderQueue.size() > 0) {
            weaponSprite.setTexture(renderQueue.get(0));
            //renderQueue.remove(0);
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
        System.out.println(sizeRatio);
        weaponSprite.setScale(new Vector2f(sizeRatio, sizeRatio));
        weaponSprite.setPosition(
                xOffset + windowDim.x/2 - (sizeRatio * spriteSize.x)/3,
                yOffset + windowDim.y/2 - (sizeRatio * spriteSize.y)/4
        );
        weaponSprite.draw(target, states);
    }
    public void update() {
        if (renderQueue.size() > 0) {
            renderQueue.remove(0);
        }
    }

    public void setPitchOffset(int x) {
        this.xOffset = (int)(Arithmetic.lerp(xOffset, x, 0.5f));
    }

    public void setYawOffset(int y) {
        this.yOffset = (int)(Arithmetic.lerp(yOffset, y, 0.5f));
    }
}
