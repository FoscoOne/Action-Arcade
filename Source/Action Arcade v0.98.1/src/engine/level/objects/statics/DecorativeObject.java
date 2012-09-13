package engine.level.objects.statics;

import engine.enums.ECollisionType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class DecorativeObject extends StaticObject {

    /*
     * Konsruktor s animaci
     */
    public DecorativeObject(Animation animation, Vector2f position) {
        super(animation, new Rectangle(0, 0,
                animation.getCurrentFrame().getWidth(),
                animation.getCurrentFrame().getHeight()), position,
                ECollisionType.WORLD_DECORATION, "Decoration");
    }

    /*
     * Konstruktor s obrazkem
     */
    public DecorativeObject(Image image, Vector2f position) {
        super(image, new Rectangle(0, 0, image.getWidth(), image.getHeight()),
                position, ECollisionType.WORLD_DECORATION, "Decoration");
    }

    /*
     * Vykresleni
     */
    @Override
    public void render(Graphics graphics) {
        if (image != null) {
            graphics.drawImage(image, position.x, position.y);
        }
        if (animation != null) {
            graphics.drawAnimation(animation, position.x, position.y);
        }
    }

    @Override
    public boolean isCollisionsOn() {
        return false;
    }
}
