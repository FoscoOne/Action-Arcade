package engine.level.objects.dynamics.items;

import engine.enums.EAmmo;
import engine.enums.EItem;
import engine.enums.EWeapon;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public class ShotgunObject extends WeaponObject {
    
    /*
     * Konstruktor
     */
    public ShotgunObject(Vector2f position) throws SlickException {
        super(EWeapon.SHOTGUN, 650, EAmmo.SHELLS, EItem.WEAPON,
                new Image("data/images/icons/shotgun.png"), 1,
                new Image("data/images/items/shotgun.png"), "Shotgun",
                position, 2, new Sound("data/sounds/shotgun_fire.wav"));
    }
}
