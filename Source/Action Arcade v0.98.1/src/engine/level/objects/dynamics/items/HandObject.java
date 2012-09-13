package engine.level.objects.dynamics.items;

import engine.enums.EAmmo;
import engine.enums.EItem;
import engine.enums.ESide;
import engine.level.objects.abstracts.Bullet;
import engine.level.objects.dynamics.DynamicObject;
import engine.level.objects.dynamics.ItemObject;
import engine.level.objects.dynamics.ThrowableBulletObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public class HandObject extends ItemObject {

    private AmmoObject ammo;
    private final int fireInterval = 1000;
    private long lastFire = System.currentTimeMillis();

    public HandObject() throws SlickException {
        super(EItem.THROWABLE, new Image("data/images/icons/hand.png"), 1, new Image("data/images/icons/hand.png"), "Hand", new Vector2f(0, 0), 1);
        deactivate();
    }

    public boolean tryToThrow(DynamicObject entity) {
        if (isLoaded()) {
            if (System.currentTimeMillis() - lastFire > fireInterval) {
                Bullet bullet = ammo.getAmmo();
                if (bullet != null) {
                    Image image = bullet.getImage();
                    if (entity.getSide() == ESide.LEFT) {
                        image = image.getFlippedCopy(true, false);
                    }
                    float posx;
                    float posy = entity.getPosition().y + 23;
                    if (entity.isObjectLooksLeft()) {
                        posx = entity.getPosition().x - 20;
                    } else {
                        posx = entity.getPosition().x
                                + entity.getCollisionShape().getWidth() + 20;
                    }

                    switch (ammo.getAmmoType()) {
                        case BOMBS:
                            engine.level.Level.addDynamicObject(new ThrowableBulletObject(bullet,
                                    ammo.getName(), new Vector2f(posx, posy),
                                    entity.getSide(), EAmmo.BOMBS));
                            break;
                        case GRENADES:
                            engine.level.Level.addDynamicObject(new ThrowableBulletObject(bullet,
                                    ammo.getName(), new Vector2f(posx, posy),
                                    entity.getSide(), EAmmo.GRENADES));
                            break;
                    }
                    try {
                        (new Sound("data/sounds/hhh.wav")).play();
                    } catch (SlickException ex) {
                    }
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
        if (ammo.getAmmoType() == EAmmo.GRENADES
                || ammo.getAmmoType() == EAmmo.BOMBS) {
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
