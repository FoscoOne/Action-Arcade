package engine.level.objects.abstracts;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Bullet {

    private final float damage;
    private final float speed;
    private final boolean isHorizontaly;
    private int TTL;
    private float mass;
    private float bounce;
    private Animation animation;
    private Image image;
    private Shape shape;
    private Vector2f velocity;

    public Bullet(float damage, float speed, boolean isHorizontaly, int TTL,
            float mass, float bound, Image image, Shape shape,
            Vector2f velocity) {
        this.damage = damage;
        this.speed = speed;
        this.isHorizontaly = isHorizontaly;
        this.TTL = TTL;
        this.mass = mass;
        this.bounce = bound;
        this.image = image;
        this.shape = shape;
        this.velocity = velocity;
    }

    public Bullet(float damage, float speed, boolean isHorizontaly, int TTL,
            float mass, float bound, Animation animation, Shape shape,
            Vector2f velocity) {
        this.damage = damage;
        this.speed = speed;
        this.isHorizontaly = isHorizontaly;
        this.TTL = TTL;
        this.mass = mass;
        this.bounce = bound;
        this.animation = animation;
        this.shape = shape;
        this.velocity = velocity;
    }

    public int getTTL() {
        return TTL;
    }

    public Animation getAnimation() {
        return animation;
    }

    public float getBounce() {
        return bounce;
    }

    public float getDamage() {
        return damage;
    }

    public Image getImage() {
        return image;
    }

    public boolean isIsHorizontaly() {
        return isHorizontaly;
    }

    public float getMass() {
        return mass;
    }

    public Shape getShape() {
        return shape;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2f getVelocity() {
        return velocity;
    }
}
