package engine.interfaces;

import engine.enums.ESide;
import org.newdawn.slick.geom.Vector2f;

public interface IPhysicalObject {

    void setVelocity(Vector2f velocity);

    void setPosition(Vector2f position);

    void setPositionOffset(float offsetx, float offsety);

    Vector2f getPosition();

    void setVelocityX(float x);

    void setVelocityY(float y);

    void setHealth(float health);

    float getHealth();

    float getMass();

    void applyAcceleration(Vector2f gravity, float delta);

    boolean isOnGround();

    void setIsOnGround(boolean isOnGround);

    void setAIR_FRICTION(float AIR_FRICTION);

    void setGROUND_FRICTION(float GROUND_FRICTION);

    Vector2f getVelocity();
    
    ESide getSide();
    
    void setConstantSpeed(float speed);
}
