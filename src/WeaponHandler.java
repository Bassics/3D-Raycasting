import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class WeaponHandler {
    private static Weapon currentWeapon;
    private static Vector2i windowDimensions = new Vector2i(0,0);
    public static void createWeapon(String name, Player p) {
        currentWeapon = new Weapon(name, p);
    }
    public static Weapon getCurrentWeapon() {
        return currentWeapon;
    }
    public static void setWindowDimensions(Vector2i wd) {
        windowDimensions = wd;
    }
    public static Vector2i getWindowDimensions() {
        return windowDimensions;
    }
}
