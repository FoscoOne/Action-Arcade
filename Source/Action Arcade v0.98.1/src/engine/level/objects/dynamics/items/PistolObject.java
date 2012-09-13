package engine.level.objects.dynamics.items;

import engine.enums.EAmmo;
import engine.enums.EItem;
import engine.enums.EWeapon;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public class PistolObject extends WeaponObject {

    /*
     * Konstruktor
     */
    public PistolObject(Vector2f position) throws SlickException {
        super(EWeapon.PISTOL, 200, EAmmo.BULLETS, EItem.WEAPON,
                new Image("data/images/icons/pistol.png"), 1,
                new Image("data/images/items/pistol.png"), "Pistol",
                position, 2, new Sound("data/sounds/pistol_fire.wav"));
    }
}
