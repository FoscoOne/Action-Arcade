package engine.level.objects;

import engine.enums.ECollisionType;
import engine.interfaces.ICollidableObject;
import engine.level.objects.statics.StaticObject;
import java.util.ArrayList;
import library.Collider;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class CollidableObject extends LevelObject implements ICollidableObject {

    // Kolizni shape
    private Shape collisionShape;
    // Kolizni typ
    private ECollisionType collisionType;
    // Restrikce v pohybu
    private Shape restrictionShape;
    // Zaznam kolizi
    protected Collider collider = new Collider();
    // Zapnuty tvrde kolize
    private boolean hardCollisions = true;
    // Kolize zapnuty
    private boolean collisionsOn = true;
    // Kolizni objekty
    private ArrayList<ECollisionType> collisionContainer = new ArrayList<ECollisionType>();

    /*
     * Konstruktor
     */
    public CollidableObject(Shape collisionShape, ECollisionType collisionType,
            String name, Vector2f position, Vector2f velocity, float mass, 
            float health, float bounce, Shape restrictionShape) {
        super(name, position, velocity, mass, health, bounce);
        this.collisionShape = collisionShape;
        this.collisionType = collisionType;
        this.restrictionShape = restrictionShape;
    }

    /*
     * Vykresleni objektu
     */
    @Override
    public abstract void render(Graphics graphics);

    /*
     * Aktualizace objektu
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        collider.reset();
    }

    /*
     * Vrati zakladni kolizni shape
     */
    @Override
    public Shape getNormalCollisionShape() {
        return collisionShape;
    }

    /*
     * Vrati relativni kolizni shape
     */
    @Override
    public Shape getCollisionShape() {
        return collisionShape.transform(
                Transform.createTranslateTransform(position.x, position.y));
    }

    /*
     * Vrati typ kolize
     */
    @Override
    public int getCollisionType() {
        return collisionType.ordinal();
    }

    /*
     * Vrati hodnotu true, pokud byla kolize
     */
    @Override
    public boolean isCollidingWith(ICollidableObject collidable) {
        if (!collisionsOn) {
            return false;
        }
        if (!collidable.isCollisionsOn()) {
            return false;
        }
        boolean c = false;
        for (int i = 0; i < collisionContainer.size(); i++) {
            if (collisionContainer.get(i).ordinal() == collidable.getCollisionType()) {
                c = true;
                break;
            }
        }
        if (!c) {
            return false;
        }
        return collider.willBeCollision(getCollisionShape(),
                collidable.getCollisionShape());
    }

    /*
     * Reakce na kolizi
     */
    @Override
    public void collidedWith(ICollidableObject entity) {
        collider.calculateCollision(getCollisionShape(),
                entity.getCollisionShape());
        if (hardCollisions) {
            if (!collider.isCheckDown()) {
                if (collider.willBeCollisionDown()) {
                    if ((getCollisionShape().getMaxY()
                            + velocity.y) > collider.getCollisionDown()) {
                        position.y = collider.getCollisionDown()
                                - getCollisionShape().getHeight();
                        setIsOnGround(true);
                        if (entity instanceof StaticObject) {
                            velocity.y = -velocity.y * getBounce();
                        }
                        if (entity instanceof PhysicalObject) {
                            ((PhysicalObject) entity).setVelocityY(
                                    ((PhysicalObject) entity).getVelocity().y
                                    + velocity.y);
                        }
                    }
                }
                if (collider.wasCollisionDown()) {
                    position.y = entity.getCollisionShape().getMinY()
                            - getCollisionShape().getHeight();
                    collider.setCheckedDown();
                    setIsOnGround(true);
                    velocity.y = 0;
                }
            }

            if (!collider.isCheckUp()) {
                if (collider.willBeCollisionUp()) {
                    if ((getCollisionShape().getMinY()
                            + velocity.y) < collider.getCollisionUp()) {
                        position.y = collider.getCollisionUp();
                        if (entity instanceof StaticObject) {
                            velocity.y = -velocity.y * getBounce();
                        }
                        if (entity instanceof PhysicalObject) {
                            ((PhysicalObject) entity).setVelocityY(
                                    ((PhysicalObject) entity).getVelocity().y
                                    + velocity.y);
                        }
                    }
                }
                if (collider.wasCollisionUp()) {
                    position.y = entity.getCollisionShape().getMaxY() + 1;
                    collider.setCheckedUp();
                }
            }

            if (!collider.isCheckLeft()) {
                if (collider.willBeCollisionLeft()) {
                    if ((getCollisionShape().getMinX()
                            + velocity.x) < collider.getCollisionLeft()) {
                        position.x = collider.getCollisionLeft();
                        if (entity instanceof StaticObject) {
                            velocity.x = -velocity.x * getBounce();
                        }
                        if (entity instanceof PhysicalObject) {
                            ((PhysicalObject) entity).setVelocityX(
                                    ((PhysicalObject) entity).getVelocity().x
                                    + velocity.x);
                        }
                    }
                }
                if (collider.wasCollisionLeft()) {
                    position.x = entity.getCollisionShape().getMaxX() + 1;
                    collider.setCheckedLeft();
                }
            }

            if (!collider.isCheckRight()) {
                if (collider.willBeCollisionRight()) {
                    if ((getCollisionShape().getMaxX()
                            + velocity.x) > collider.getCollisionRight()) {
                        position.x = collider.getCollisionRight()
                                - getCollisionShape().getWidth();
                        if (entity instanceof StaticObject) {
                            velocity.x = -velocity.x * getBounce();
                        }
                        if (entity instanceof PhysicalObject) {
                            ((PhysicalObject) entity).setVelocityX(
                                    ((PhysicalObject) entity).getVelocity().x
                                    + velocity.x);
                        }
                    }
                }

                if (collider.wasCollisionRight()) {
                    position.x = entity.getCollisionShape().getMinX()
                            - getCollisionShape().getWidth() - 1;
                    collider.setCheckedRight();
                }
            }
        }
        doAfterCollision(entity);
    }

    /*
     * Co delat v pripade kolize
     */
    protected abstract void doAfterCollision(ICollidableObject entity);

    /*
     * Kontrola restrikci v pohybu
     */
    protected void checkRestrictions() {
        if (restrictionShape == null) {
            return;
        }

        if (position.x < restrictionShape.getMinX()) {
            position.x = restrictionShape.getMinX();
        }
        if (getCollisionShape().getMaxX() > restrictionShape.getMaxX()) {
            position.x = restrictionShape.getMaxX() - getCollisionShape().getWidth();
        }
        if (position.y < restrictionShape.getMinY()) {
            position.y = restrictionShape.getMinY();
        }
        if (position.y > restrictionShape.getMaxY()) {
            this.deactivate();
        }
    }

    /*
     * Aktualizace restrikce
     */
    public void updateRestrictionShape(Shape restrictionShape) {
        this.restrictionShape = restrictionShape;
    }

    /*
     * Nastaveni mekkych kolizi
     */
    public void hardCollisionsOff() {
        this.hardCollisions = false;
    }

    /*
     * Jsou zapnuty tvrde kolize
     */
    public boolean isHardCollisionsOn() {
        return hardCollisions;
    }
    
    /*
     * Vypnout kolize
     */
    public void setCollisionsOff() {
        collisionsOn = false;
    }
    
    /*
     * Zapnout kolize
     */
    public void setCollisionsOn() {
        collisionsOn = true;
    }

    /*
     * Jsou zapnuty kolize
     */
    @Override
    public boolean isCollisionsOn() {
        return collisionsOn;
    }
    
    /*
     * Vlozeni kolizniho typu do kontejneru
     */
    public void addCollisionTypeToList(ECollisionType ect) {
        collisionContainer.add(ect);
    }
    
    public void clearCollisionList() {
        collisionContainer.clear();
    }
}
