package engine.level.objects.dynamics;

import engine.enums.EFighter;
import engine.interfaces.ICollidableObject;
import engine.level.objects.abstracts.GameStats;
import engine.level.objects.statics.StaticObject;
import library.Clock;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class AIEnemyObject extends EnemyObject {

    private int randomSpeed;
    private float damage;
    private Clock damageClock = new Clock(500);
    private float hStep;
    private Clock movement = new Clock(100);

    public AIEnemyObject(Animation animation, Shape collisionShape,
            Vector2f position, float mass, float health, EFighter efighter, float damage) throws SlickException {
        super(animation, efighter, collisionShape, "AIEnemy", position,
                mass, health, 0.01f, engine.level.Level.getLevelShape());
        this.damage = damage;
        init();
    }

    public AIEnemyObject(Image image, Shape collisionShape, Vector2f position,
            float mass, float health, float damage) {
        super(image, collisionShape, "AIEnemy", position, mass, health,
                0.01f, engine.level.Level.getLevelShape());
        this.damage = damage;
        init();
    }

    public AIEnemyObject(Shape collisionShape, Vector2f position, float mass,
            float health, Shape restrictionShape, float damage) {
        super(collisionShape, "AIEnemy", position, mass, health,
                0.01f, engine.level.Level.getLevelShape());
        this.damage = damage;
        init();
    }

    private void init() {
        hStep = (float) (getCollisionShape().getWidth() / getHealth());
        int random = 0;
        while (random < 2 && random > -2) {
            random = (int) (Math.round(-5 + Math.random() * 10));
        }
        this.randomSpeed = random;
        setConstantSpeed(randomSpeed);
    }

    @Override
    public void notifyDeath() {
        super.notifyDeath();
        try {
            (new Sound("data/sounds/die.ogg")).play();
        } catch (SlickException ex) {
        }
        GameStats.nextFrag();
        animation = animations.getDiedFigure(getSide());
        setCollisionsOff();
        hardCollisionsOff();
        setIsOnGround(false);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setColor(Color.red);
        graphics.fillRect(position.x, position.y - 10, (float) (hStep * getHealth()), 2);
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        super.doAfterCollision(entity);
        if (isAlive) {
            if (entity instanceof StaticObject) {
                if (collider.wasCollisionLeft() || collider.wasCollisionRight()) {
                    if (movement.isNextTick()) {
                        setInvertRandomSpeed();
                    }
                }
            }
            if (entity instanceof PlayerObject) {
                if (collider.wasCollision()) {
                    if (damageClock.isNextTick()) {
                        ((PlayerObject) entity).decreaseHealth(damage);
                        ((PlayerObject) entity).setDamagedAnimation(this);
                    }
                }
            }
        }
    }

    @Override
    protected void checkRestrictions() {
        super.checkRestrictions();
        if (position.x <= engine.level.Level.getLevelShape().getMinX()) {
            setInvertRandomSpeed();
        }
        if (position.x + getCollisionShape().getWidth() >= engine.level.Level.getLevelShape().getMaxX()) {
            setInvertRandomSpeed();
        }
    }

    public void setInvertRandomSpeed() {
        this.randomSpeed = -randomSpeed;
        setConstantSpeed(randomSpeed);
        if (animation != null) {
            animation = animations.getWalkAnimation(getSide());
        }
    }
}
