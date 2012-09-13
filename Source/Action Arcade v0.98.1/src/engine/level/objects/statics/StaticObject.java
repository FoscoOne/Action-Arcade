package engine.level.objects.statics;

import engine.enums.ECollisionType;
import engine.interfaces.ICollidableObject;
import engine.interfaces.ILevelObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class StaticObject implements ICollidableObject, ILevelObject {

    // Animace
    protected Animation animation;
    // Obrazek
    protected Image image;
    // Kolizni shape
    private Shape collisionShape;
    // Pozice
    Vector2f position;
    // Kolizni typ
    private ECollisionType collisionType;
    // Je aktivni
    private boolean isActive = true;
    // Nazev
    private String name;

    public StaticObject(Image image, Shape collisionShape, Vector2f position,
            ECollisionType collisionType, String name) {
        this.image = image;
        this.collisionShape = collisionShape;
        this.position = position;
        this.collisionType = collisionType;
        this.name = name;
    }

    public StaticObject(Animation animation, Shape collisionShape,
            Vector2f position, ECollisionType collisionType, String name) {
        this.animation = animation;
        this.collisionShape = collisionShape;
        this.position = position;
        this.collisionType = collisionType;
        this.name = name;
    }

    @Override
    public Shape getNormalCollisionShape() {
        return collisionShape;
    }

    @Override
    public Shape getCollisionShape() {
        return collisionShape.transform(Transform.createTranslateTransform(position.x, position.y));
    }

    @Override
    public int getCollisionType() {
        return collisionType.ordinal();
    }

    @Override
    public boolean isCollidingWith(ICollidableObject collidable) {
        return getCollisionShape().intersects(collidable.getCollisionShape());
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public void collidedWith(ICollidableObject entity) {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract void render(Graphics graphics);

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
    }

    public void setPositionOffset(float offsetx, float offsety) {
        this.position.x += offsetx;
        this.position.y += offsety;
    }
    
    public Vector2f getPosition() {
        return position;
    }
}
