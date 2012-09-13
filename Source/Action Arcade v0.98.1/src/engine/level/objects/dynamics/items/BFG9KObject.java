package engine.level.objects.dynamics.items;

import engine.enums.EAmmo;
import engine.enums.EItem;
import engine.enums.EWeapon;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public class BFG9KObject extends WeaponObject {
    /*
     * Konstruktor
     */
    public BFG9KObject(Vector2f position) throws SlickException {
        super(EWeapon.BFG9000, 1000, EAmmo.PLASMA, EItem.WEAPON,
                new Image("data/images/icons/bfg9k.png"), 1,
                new Image("data/images/items/bfg9k.png"), "BFG9000",
                position, 10, new Sound("data/sounds/bfg9k_fire.wav"));
    }
}
