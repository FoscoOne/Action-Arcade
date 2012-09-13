package engine.interfaces;

import org.newdawn.slick.geom.Vector2f;

public interface Moveable {

    void move(float delta, Vector2f gravity);
    
    void setVelocity(Vector2f velocity); 
}
