import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class WeaponHandler {
    private static Weapon currentWeapon;
    public static void createWeapon(String name, Player p, RenderWindow w) {
        currentWeapon = new Weapon(name, p, w);
    }
    public static Weapon getCurrentWeapon() {
        return currentWeapon;
    }
}
