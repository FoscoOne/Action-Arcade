package engine.level.objects.dynamics;

import engine.enums.ECollisionType;
import engine.enums.EItem;
import engine.interfaces.ICollidableObject;
import engine.interfaces.IItemObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class ItemObject extends DynamicObject implements IItemObject {

    private final Image icon;
    protected int quantity;
    private final EItem type;

    /*
     * Konstruktor s obrazkem
     */
    public ItemObject(EItem type, Image icon, int quantity, Image image,
            String name, Vector2f position, float mass) {
        super(image, new Rectangle(0, 0, image.getWidth(), image.getHeight()),
                ECollisionType.ITEM, name, position, new Vector2f(0, 0), mass, 50,
                0.01f, null);
        this.icon = icon;
        this.type = type;
        this.quantity = quantity;
        init();
    }

    /*
     * Konstruktor s animaci
     */
    public ItemObject(EItem type, Image icon, int quantity, Animation animation,
            String name, Vector2f position, float mass) {
        super(animation, new Rectangle(0, 0,
                animation.getCurrentFrame().getWidth(),
                animation.getCurrentFrame().getHeight()), ECollisionType.ITEM, name,
                position, new Vector2f(0, 0), mass, 50, 0.01f, null);
        this.icon = icon;
        this.type = type;
        this.quantity = quantity;
        init();
    }

    /*
     * Kolize s objektem
     */
    @Override
    public void collidedWith(ICollidableObject entity) {
        super.collidedWith(entity);
        if (entity instanceof PlayerObject) {
            ((PlayerObject) entity).putItemToInventory(this);
        }
    }

    /*
     * Vrati ikonu
     */
    @Override
    public Image getIcon() {
        return icon;
    }

    /*
     * Vrati typ predmetu
     */
    @Override
    public EItem getType() {
        return type;
    }

    /*
     * Vrati pocet
     */
    @Override
    public int getQuantity() {
        return quantity;
    }
    
    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        if (collider.wasCollision()) {
            if (entity instanceof PlayerObject) {
                ((PlayerObject)entity).putItemToInventory(this);
                deactivate();
            }
        }
    }

    private void init() {
        addCollisionTypeToList(ECollisionType.PLAYER);
        addCollisionTypeToList(ECollisionType.TERRAIN);
    }
}
