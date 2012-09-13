package engine.level.objects.dynamics.items;

import engine.enums.EAmmo;
import engine.enums.EItem;
import engine.level.objects.abstracts.Bullet;
import engine.level.objects.dynamics.ItemObject;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class AmmoObject extends ItemObject {

    private final EAmmo eAmmo;
    private ArrayList<Bullet> ammo = new ArrayList<Bullet>();

    /*
     * Konstruktor s animaci
     */
    public AmmoObject(EAmmo eAmmo, Image icon, int quantity,
            Animation animation, String name, Vector2f position)
            throws SlickException {
        super(EItem.AMMO, icon, quantity, animation, name, position, 10);
        this.eAmmo = eAmmo;
        init();
    }

    /*
     * Konstruktor s obrazkem
     */
    public AmmoObject(EAmmo eAmmo, Image icon, int quantity, Image image,
            String name, Vector2f position) throws SlickException {
        super(EItem.AMMO, icon, quantity, image, name, position, 10);
        this.eAmmo = eAmmo;
        init();
    }

    /*
     * Inicializace
     */
    private void init() throws SlickException {
        switch (eAmmo) {
            case BULLETS:
                for (int i = 0; i < getQuantity(); i++) {
                    ammo.add(new Bullet(3, 10, true, 200, 0.05f, 0.01f,
                            new Image("data/images/bullet.gif"),
                            new Rectangle(0, 0, 10, 3), new Vector2f(0, 0)));
                }
                break;
            case SHELLS:
                for (int i = 0; i < getQuantity(); i++) {
                    ammo.add(new Bullet(6, 12, true, 200, 0.05f, 0.05f,
                            new Image("data/images/bullet.gif"),
                            new Rectangle(0, 0, 10, 3), new Vector2f(0, 0)));
                }
                break;
            case GRENADES:
                for (int i = 0; i < getQuantity(); i++) {
                    ammo.add(new Bullet(8, 0, false, 100, 0.05f, 1.2f,
                            new Image("data/images/items/grenade.png"),
                            new Circle(0, 0, 6f), new Vector2f(20, -20)));
                }
                break;
            case BOMBS:
                for (int i = 0; i < getQuantity(); i++) {
                    ammo.add(new Bullet(20, 5, false, 100, 0.05f, 2.1f,
                            new Image("data/images/items/bomb.png"),
                            new Circle(0, 0, 7.5f), new Vector2f(0, 0)));
                }
                break;
            case PLASMA:
                for (int i = 0; i < getQuantity(); i++) {
                    ammo.add(new Bullet(30, 7, true, 200, 0.05f, 0.01f,
                            new Image("data/images/items/plasma.png"),
                            new Circle(0, 0, 10f), new Vector2f(0, 0)));
                }
                break;
            default:
                return;
        }
    }

    /*
     * Vrati naboj
     */
    public Bullet getAmmo() {
        if (quantity >= 1) {
            quantity--;
            return ammo.remove(ammo.size() - 1);
        }
        return null;
    }

    /*
     * Vrati typ naboju
     */
    public EAmmo getAmmoType() {
        return eAmmo;
    }

    /*
     * Dosla munice
     */
    public boolean isEmpty() {
        return ammo.isEmpty();
    }
}
