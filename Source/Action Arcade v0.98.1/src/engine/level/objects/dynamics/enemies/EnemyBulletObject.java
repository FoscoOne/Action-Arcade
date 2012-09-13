package engine.level.objects.dynamics.enemies;

import engine.enums.ECollisionType;
import engine.enums.ESide;
import engine.interfaces.ICollidableObject;
import engine.level.objects.dynamics.BulletObject;
import engine.level.objects.dynamics.PlayerObject;
import engine.level.objects.statics.StaticObject;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class EnemyBulletObject extends BulletObject {

    public EnemyBulletObject(String name, Vector2f position, ESide side,
            boolean hardCollisions, Image image, Shape shape, Float mass,
            Vector2f velocity, int damage, boolean isHorizontaly, float speed,
            int ttl) {
        super(name, position, side, hardCollisions, image, shape, mass,
                velocity, damage, isHorizontaly, speed, ttl);
        init();
    }

    private void init() {
        clearCollisionList();
        addCollisionTypeToList(ECollisionType.PLAYER);
        addCollisionTypeToList(ECollisionType.TERRAIN);
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        if (collider.wasCollision()) {
            if (entity instanceof PlayerObject) {
                ((PlayerObject) entity).decreaseHealth(getDamage());
                deactivate();
            }
            if (entity instanceof StaticObject) {
                deactivate();
            }
        }
    }
}
