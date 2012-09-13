package engine.level.objects.dynamics.enemies;

import engine.enums.EFighter;
import engine.interfaces.ICollidableObject;
import engine.level.objects.dynamics.AIEnemyObject;
import engine.level.objects.statics.StaticObject;
import library.Collider;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class ERobot extends AIEnemyObject {

    private static int nextHealth = 10;

    public ERobot(Animation animation, Vector2f position)
            throws SlickException {
        super(animation, new Rectangle(0, 0, 36, 30), position, 70, nextHealth++,
                EFighter.EROBOT, 15);
        (new Sound("data/sounds/new.ogg")).play();
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        super.doAfterCollision(entity);
        if ((collider.willBeCollisionLeft() && velocity.x < 0)
                || (collider.willBeCollisionRight() && velocity.x > 0)) {
            if (entity instanceof StaticObject) {
                Collider c = new Collider();
                c.calculateCollision(getCollisionShape(), entity.getCollisionShape());
                if ((c.willBeCollisionRight() && velocity.x > 0)
                        || (c.willBeCollisionLeft() && velocity.x < 0)) {
                    if (isOnGround()) {
                        int y_s = (int) ((StaticObject) entity).getCollisionShape().getMinY();
                        int y_me = (int) getCollisionShape().getMinY();
                        int delta = y_s - y_me;
                        if (delta < 30 && delta > -30) {
                            setVelocityY(-25);
                        }
                    }
                }
            }
        }
    }
}
