package engine.level.objects;

import engine.enums.ESide;
import engine.interfaces.IPhysicalObject;
import org.newdawn.slick.geom.Vector2f;

public abstract class PhysicalObject implements IPhysicalObject {

    // Vektor rychlosti
    protected Vector2f position;
    // Vektor rychlosti
    protected Vector2f velocity;
    // Vaha
    private float mass;
    // Zdravi
    private float health;
    // Tvrdost objektu
    private float bounce;
    // Treni na zemi
    private float GROUND_FRICTION = 0.5f;
    // Treni ve vzduchu
    private float AIR_FRICTION = 0.9f;
    // Je predmet na zemi
    private boolean isOnGround = false;
    // Rychlost na ose X
    private float speed = 0;
    // Osa Y povolena
    private boolean yAxisOn = true;
    // Osa X povolena
    private boolean xAxisOn = true;
    // Strana
    private ESide side = ESide.RIGHT;
    // Zivot
    protected boolean isAlive = true;

    /*
     * Konstruktor fyzickeho objektu
     */
    public PhysicalObject(Vector2f position, Vector2f velocity, float mass,
            float health, float bounce) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.health = health;
        this.bounce = bounce;
    }

    /*
     * Aplikace akcelerace
     */
    @Override
    public void applyAcceleration(Vector2f gravity, float delta) {
        if (speed != 0 && xAxisOn) {
            velocity.x = speed;
        }

        // Gravitace
        velocity.x += gravity.x;
        velocity.y += gravity.y;

        // aplikace tření
        if (isOnGround) {
            velocity.x *= 1.0f - ((1.0f - GROUND_FRICTION) * delta);
            velocity.y *= 1.0f - ((1.0f - GROUND_FRICTION) * delta);
        } else {
            velocity.x *= 1.0f - ((1.0f - AIR_FRICTION) * delta);
            velocity.y *= 1.0f - ((1.0f - AIR_FRICTION) * delta);
        }

        if (xAxisOn) {
            position.x += velocity.x * delta;
        }
        if (yAxisOn) {
            position.y += velocity.y * delta;
        }
        isOnGround = false;
    }

    /*
     * Vrati hodnotu true, pokud je objekt na zemi
     */
    @Override
    public boolean isOnGround() {
        return isOnGround;
    }

    /*
     * Nastaveni pozice na ose X a Y
     */
    @Override
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    /*
     * Nastaveni offsetu objektu
     */
    @Override
    public void setPositionOffset(float offsetx, float offsety) {
        this.position.x += offsetx;
        this.position.y += offsety;
    }

    /*
     * Vrati vektor pozice
     */
    @Override
    public Vector2f getPosition() {
        return position;
    }

    /*
     * Nastaveni jestli je objekt na zemi
     */
    @Override
    public void setIsOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    /*
     * Nastaveni konstanti rychlosti po ose X
     */
    @Override
    public void setConstantSpeed(float speed) {
        if (speed > 0) {
            side = ESide.RIGHT;
        }
        if (speed < 0) {
            side = ESide.LEFT;
        }
        this.speed = speed;
    }

    /*
     * Vrati vektor rychlosti
     */
    @Override
    public Vector2f getVelocity() {
        return velocity;
    }

    /*
     * Nastaveni vektoru rychlosti
     */
    @Override
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity.copy();
    }

    /*
     * Nastaveni zdravi
     */
    @Override
    public void setHealth(float health) {
        if (health > 0) {
            this.health = health;
        } else {
            this.health = 0;
        }
    }

    /*
     * Vrati zdravi
     */
    @Override
    public float getHealth() {
        return health;
    }

    /*
     * Zmenseni zdravi o
     */
    public void decreaseHealth(float decrease) {
        this.health -= decrease;
        if (health < 0) {
            this.health = 0;
        }
    }

    /*
     * Zvetseni zdravi o
     */
    public void increaseHealth(float increase) {
        this.health += increase;
    }

    /*
     * Vrati vahu
     */
    @Override
    public float getMass() {
        return mass;
    }

    /*
     * Dej stranu
     */
    @Override
    public ESide getSide() {
        return side;
    }

    /*
     * Nastaveni rychlosti na ose X
     */
    @Override
    public void setVelocityX(float x) {
        velocity.x = x;
    }

    /*
     * Nastaveni rychlosti na ose Y
     */
    @Override
    public void setVelocityY(float y) {
        velocity.y = y;
    }

    /*
     * Nastaveni treni ve vzduchu
     */
    @Override
    public void setAIR_FRICTION(float AIR_FRICTION) {
        this.AIR_FRICTION = AIR_FRICTION;
    }

    /*
     * Nastaveni treni na zemi
     */
    @Override
    public void setGROUND_FRICTION(float GROUND_FRICTION) {
        this.GROUND_FRICTION = GROUND_FRICTION;
    }

    /*
     * Nastav Y konstantni
     */
    protected void setYAxisOff() {
        this.yAxisOn = false;
    }

    /*
     * Vypni Y konstantni
     */
    protected void setYAxisOn() {
        this.yAxisOn = true;
    }

    /*
     * Nastav X konstantni
     */
    protected void setXAxisOff() {
        this.xAxisOn = false;
    }

    /*
     * Vypni X konstantni
     */
    protected void setXAxisOn() {
        this.xAxisOn = true;
    }

    /*
     * Kouka se doprava
     */
    public boolean isObjectLooksLeft() {
        return (side == ESide.LEFT ? true : false);
    }

    /*
     * Kouka se doleva
     */
    public boolean isObjectLooksRight() {
        return (side == ESide.RIGHT ? true : false);
    }

    public float getBounce() {
        return bounce;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
}
