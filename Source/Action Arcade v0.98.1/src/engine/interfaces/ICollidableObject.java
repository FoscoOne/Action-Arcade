package engine.interfaces;

import org.newdawn.slick.geom.Shape;

public interface ICollidableObject {

    Shape getNormalCollisionShape();

    Shape getCollisionShape();

    int getCollisionType();

    boolean isCollidingWith(ICollidableObject collidable);

    boolean isActive();

    void deactivate();

    void collidedWith(ICollidableObject entity);
    
    boolean isCollisionsOn();
}
