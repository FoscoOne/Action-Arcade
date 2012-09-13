package engine.level.objects.dynamics;

import engine.enums.ECollisionType;
import engine.interfaces.ICollidableObject;
import engine.interfaces.Moveable;
import engine.level.objects.CollidableObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class DynamicObject extends CollidableObject implements Moveable {

    // Animace
    protected Animation animation;
    // Obrazek
    protected Image image;

    /*
     * Konstruktor s animaci
     */
    public DynamicObject(Animation animation, Shape collisionShape,
            ECollisionType collisionType, String name, Vector2f position,
            Vector2f velocity, float mass, float health, float bounce,
            Shape restrictionShape) {
        super(collisionShape, collisionType, name, position, velocity, mass,
                health, bounce, restrictionShape);
        this.animation = animation;
        this.image = null;
    }

    /*
     * Konstruktor s obrazkem
     */
    public DynamicObject(Image image, Shape collisionShape, ECollisionType collisionType,
            String name, Vector2f position, Vector2f velocity, float mass,
            float health, float bounce, Shape restrictionShape) {
        super(collisionShape, collisionType, name, position, velocity, mass,
                health, bounce, restrictionShape);
        this.animation = null;
        this.image = image;
    }

    /*
     * Konstruktor
     */
    public DynamicObject(Shape collisionShape, ECollisionType collisionType, String name,
            Vector2f position, Vector2f velocity, float mass, float health,
            float bounce, Shape restrictionShape) {
        super(collisionShape, collisionType, name, position, velocity, mass,
                health, bounce, restrictionShape);
        this.animation = null;
        this.image = null;
    }

    /*
     * Vykresleni objektu
     */
    @Override
    public void render(Graphics graphics) {
        if (animation != null) {
            graphics.drawAnimation(animation, position.x, position.y);
        } else if (image != null) {
            graphics.drawImage(image, position.x, position.y);
        } else {
            graphics.setColor(Color.red);
            graphics.draw(getCollisionShape());
        }
    }

    /*
     * Aktualizace objektu
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        super.update(gc, sbg, delta);
        if (isAlive) {
            if (getHealth() <= 0) {
                notifyDeath();
            }
        }
    }

    /*
     * Pohyb objektu
     */
    @Override
    public void move(float delta, Vector2f gravity) {
        if (isAlive) {
            applyAcceleration(gravity, delta);
            checkRestrictions();
        }
    }

    /*
     * Co se stane po smrti
     */
    public void notifyDeath() {
        isAlive = false;
    }

    @Override
    protected abstract void doAfterCollision(ICollidableObject entity);
}
