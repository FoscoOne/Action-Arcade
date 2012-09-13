package engine.level.objects.dynamics;

import engine.enums.ECollisionType;
import engine.enums.EFighter;
import engine.enums.ESide;
import engine.interfaces.ICollidableObject;
import engine.level.objects.abstracts.Animations;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class EnemyObject extends DynamicObject {

    // Animace
    protected Animations animations;

    /*
     * Konstruktor pro debuging
     */
    public EnemyObject(Shape collisionShape, String name, Vector2f position,
            float mass, float health, float bounce, Shape restrictionShape) {
        super(collisionShape, ECollisionType.ENEMY, name, position, new Vector2f(0, 0),
                mass, health, bounce, restrictionShape);
        init();
    }

    /*
     * Konstruktor s obrázkem
     */
    public EnemyObject(Image image, Shape collisionShape, String name,
            Vector2f position, float mass, float health, float bounce,
            Shape restrictionShape) {
        super(image, collisionShape, ECollisionType.ENEMY, name, position,
                new Vector2f(0, 0), mass, health, bounce, restrictionShape);
        init();
    }

    /*
     * Konstruktor s animaci
     */
    public EnemyObject(Animation animation, EFighter fighter,
            Shape collisionShape, String name, Vector2f position, float mass,
            float health, float bounce, Shape restrictionShape)
            throws SlickException {
        super(animation, collisionShape, ECollisionType.ENEMY, name, position, new Vector2f(0, 0),
                mass, health, bounce, restrictionShape);
        this.animations = new Animations(fighter);
        init();
    }

    /*
     * Inicializace objektu
     */
    private void init() {
        if (animation != null) {
            animation = animations.getWalkAnimation(getSide());
        }
        addCollisionTypeToList(ECollisionType.PLAYER);
        addCollisionTypeToList(ECollisionType.TERRAIN);
    }

    @Override
    public void notifyDeath() {
        super.notifyDeath();
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
    }
}
