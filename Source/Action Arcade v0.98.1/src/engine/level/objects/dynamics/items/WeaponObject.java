package engine.level.objects.dynamics.items;

import engine.enums.EAmmo;
import engine.enums.EItem;
import engine.enums.ESide;
import engine.enums.EWeapon;
import engine.level.objects.abstracts.Bullet;
import engine.level.objects.dynamics.BulletObject;
import engine.level.objects.dynamics.DynamicObject;
import engine.level.objects.dynamics.ItemObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public abstract class WeaponObject extends ItemObject {

    private final int fireInterval;
    private long lastFire = System.currentTimeMillis();
    private final EAmmo eAmmo;
    private AmmoObject ammo;
    private final EWeapon eWeapon;
    private final Sound shot;

    public WeaponObject(EWeapon eWeapon, int fireInterval, EAmmo eAmmo, EItem type,
            Image icon, int quantity, Animation animation, String name,
            Vector2f position, float mass, Sound shot) {
        super(type, icon, quantity, animation, name, position, mass);
        this.fireInterval = fireInterval;
        this.eAmmo = eAmmo;
        this.eWeapon = eWeapon;
        this.shot = shot;
    }

    public WeaponObject(EWeapon eWeapon, int fireInterval, EAmmo eAmmo, EItem type,
            Image icon, int quantity, Image image, String name,
            Vector2f position, float mass, Sound shot) {
        super(type, icon, quantity, image, name, position, mass);
        this.fireInterval = fireInterval;
        this.eAmmo = eAmmo;
        this.eWeapon = eWeapon;
        this.shot = shot;
    }

    public boolean tryToFire(DynamicObject entity) {
        if (isLoaded()) {
            if (System.currentTimeMillis() - lastFire > fireInterval) {
                Bullet bullet = ammo.getAmmo();
                if (bullet != null) {
                    float posx;
                    float posy = entity.getPosition().y + 23;
                    if (entity.isObjectLooksLeft()) {
                        posx = entity.getPosition().x - 20;
                    } else {
                        posx = entity.getPosition().x
                                + entity.getCollisionShape().getWidth() + 20;
                    }

                    switch (eWeapon) {
                        case PISTOL:
                            engine.level.Level.addDynamicObject(new BulletObject(bullet,
                                    ammo.getName(), new Vector2f(posx, posy),
                                    entity.getSide(), false));
                            break;
                        case SHOTGUN:
                            ammo.getAmmo();
                            engine.level.Level.addDynamicObject(new BulletObject(bullet,
                                    ammo.getName(), new Vector2f(posx, posy - 2),
                                    entity.getSide(), false));
                            engine.level.Level.addDynamicObject(new BulletObject(bullet,
                                    ammo.getName(), new Vector2f(posx, posy + 2),
                                    entity.getSide(), false));
                            break;
                        case BFG9000:
                            engine.level.Level.addDynamicObject(new BulletObject(bullet,
                                    ammo.getName(), new Vector2f(posx, posy),
                                    entity.getSide(), false));
                            break;
                    }
                    shot.play();
                    lastFire = System.currentTimeMillis();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean reload(AmmoObject ammo) {
        if (ammo == null) {
            return false;
        }
        if (ammo.getAmmoType() == eAmmo) {
            this.ammo = ammo;
            return true;
        }
        return false;
    }

    public boolean isLoaded() {
        if (ammo == null) {
            return false;
        }
        if (ammo.isEmpty()) {
            return false;
        }
        return true;
    }
}
