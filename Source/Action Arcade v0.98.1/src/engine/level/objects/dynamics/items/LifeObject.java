package engine.level.objects.dynamics.items;

import engine.enums.EItem;
import engine.interfaces.ICollidableObject;
import engine.level.objects.dynamics.ItemObject;
import engine.level.objects.dynamics.PlayerObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class LifeObject extends ItemObject {

    public LifeObject(EItem type, Image icon, Animation animation, Vector2f position) {
        super(type, icon, 1, animation, "Life", position, 2);
    }

    public LifeObject(Vector2f position) throws SlickException {
        super(EItem.USEABLE, new Image("data/images/items/heart.png"), 1,
                new Image("data/images/items/heart.png"), "Life", position, 2);
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        super.doAfterCollision(entity);
        if (entity instanceof PlayerObject) {
            ((PlayerObject) entity).increaseHealth(25);
        }
    }
}
