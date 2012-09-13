package engine.level.objects.dynamics;

import engine.enums.EAmmo;
import engine.enums.ESide;
import engine.interfaces.ICollidableObject;
import engine.level.Level;
import engine.level.objects.abstracts.Bullet;
import engine.level.objects.statics.StaticObject;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ThrowableBulletObject extends BulletObject {

    private final EAmmo eAmmo;

    public ThrowableBulletObject(Bullet bullet, String name, Vector2f position, ESide side, EAmmo eAmmo) {
        super(bullet, name, position, side, true);
        this.eAmmo = eAmmo;
        if (eAmmo == EAmmo.GRENADES) {
            hardCollisionsOff();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        super.update(gc, sbg, delta);
        if (timeout) {
            if (eAmmo == EAmmo.GRENADES) {
                try {
                    (new Sound("data/sounds/grenade_exp.wav")).play();
                    for (int i = 0; i < 10; i++) {
                        Level.addDynamicObject(new BulletObject("Grenade cluster",
                                new Vector2f(position.x + (new Random().nextInt(5)) - 3, position.y + (new Random().nextInt(6)) - 4), ESide.LEFT, true,
                                new Image("data/images/items/grenade_cluster.png"),
                                new Rectangle(0, 0, 3, 4), 0.01f, new Vector2f(5 + (new Random().nextInt(10)), -5 - (new Random().nextInt(10))), 3, false, 0, 1000));
                        Level.addDynamicObject(new BulletObject("Grenade cluster",
                                new Vector2f(position.x + (new Random().nextInt(5)) - 3, position.y + (new Random().nextInt(6)) - 4), ESide.LEFT, true,
                                new Image("data/images/items/grenade_cluster.png"),
                                new Rectangle(0, 0, 3, 4), 0.01f, new Vector2f(-5 - (new Random().nextInt(10)), -5 - (new Random().nextInt(10))), 3, false, 0, 1000));
                    }
                } catch (SlickException ex) {
                }
            }
            if (eAmmo == EAmmo.BOMBS) {
                try {
                    (new Sound("data/sounds/bomb_exp.wav")).play();
                    for (int i = 0; i < 5; i++) {
                        Level.addDynamicObject(new BulletObject("Grenade cluster",
                                new Vector2f(position.x + (new Random().nextInt(5)) - 3, position.y + (new Random().nextInt(6)) - 4), ESide.LEFT, true,
                                new Image("data/images/items/bomb_cluster.png"),
                                new Rectangle(0, 0, 5, 5), 0.01f, new Vector2f(5 + (new Random().nextInt(20)), -5 - (new Random().nextInt(20))), 10, false, 0, 1000));
                        Level.addDynamicObject(new BulletObject("Grenade cluster",
                                new Vector2f(position.x + (new Random().nextInt(5)) - 3, position.y + (new Random().nextInt(6)) - 4), ESide.LEFT, true,
                                new Image("data/images/items/bomb_cluster.png"),
                                new Rectangle(0, 0, 5, 5), 0.01f, new Vector2f(-5 - (new Random().nextInt(20)), -5 - (new Random().nextInt(20))), 10, false, 0, 1000));
                    }
                } catch (SlickException ex) {
                }

            }
            deactivate();
        }
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        if (collider.wasCollision()) {
            if (entity instanceof EnemyObject) {
                if (eAmmo == EAmmo.BOMBS) {
                    timeout = true;
                }
            }
            if (entity instanceof StaticObject || entity instanceof EnemyObject) {
                if (eAmmo == EAmmo.GRENADES) {
                    timeout = true;
                }
            }
        }
    }
}
