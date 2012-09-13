package engine.level.objects.dynamics;

import engine.enums.ECollisionType;
import engine.enums.ESide;
import engine.interfaces.ICollidableObject;
import engine.level.objects.abstracts.Bullet;
import engine.level.objects.abstracts.GameStats;
import engine.level.objects.statics.StaticObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BulletObject extends DynamicObject {

    private final float damage;
    private int ttl;
    protected boolean timeout = false;

    /*
     * Konstruktor
     */
    public BulletObject(Bullet bullet, String name, Vector2f position,
            ESide side, boolean hardCollisions) {
        super(bullet.getImage(), bullet.getShape(), ECollisionType.BULLET, name, position,
                bullet.getVelocity(), bullet.getMass(), 20, bullet.getBounce(),
                null);
        this.damage = bullet.getDamage();
        if (bullet.isIsHorizontaly()) {
            setYAxisOff();
        }
        if (side == ESide.RIGHT) {
            setConstantSpeed(bullet.getSpeed());
        } else {
            setConstantSpeed(-bullet.getSpeed());
            velocity.x = -velocity.x;
        }
        this.ttl = bullet.getTTL();
        if (!hardCollisions) {
            hardCollisionsOff();
        }
        init();
    }

    /*
     * Konstruktor 2
     */
    public BulletObject(String name, Vector2f position, ESide side,
            boolean hardCollisions, Image image, Shape shape, Float mass,
            Vector2f velocity, int damage, boolean isHorizontaly, float speed,
            int ttl) {
        super(image, shape, ECollisionType.BULLET, name, position, velocity, mass, 20, 0.2f, null);
        this.damage = damage;
        if (isHorizontaly) {
            setYAxisOff();
        }
        if (side == ESide.RIGHT) {
            setConstantSpeed(speed);
        } else {
            setConstantSpeed(-speed);
            velocity.x = -velocity.x;
        }
        this.ttl = ttl;
        if (!hardCollisions) {
            hardCollisionsOff();
        }
        init();
    }

    /*
     * Inicializace
     */
    private void init() {
        addCollisionTypeToList(ECollisionType.ENEMY);
        addCollisionTypeToList(ECollisionType.TERRAIN);
        addCollisionTypeToList(ECollisionType.PLAYER);
    }

    /*
     * Vrati poskozeni
     */
    public float getDamage() {
        return damage;
    }

    /*
     * Aktualizace objektu
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        super.update(gc, sbg, delta);
        ttl--;
        if (ttl < 0) {
            timeout = true;
        }
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        if (collider.wasCollision()) {
            if (entity instanceof EnemyObject) {
                ((EnemyObject) entity).decreaseHealth(damage);
                GameStats.increaseScore((int) damage);
                deactivate();
            }
            if (entity instanceof StaticObject) {
                deactivate();
            }
        }
    }

    @Override
    protected void checkRestrictions() {
        return;
    }
}
