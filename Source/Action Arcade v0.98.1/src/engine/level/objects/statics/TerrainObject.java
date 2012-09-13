package engine.level.objects.statics;

import engine.enums.ECollisionType;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class TerrainObject extends StaticObject {

    public TerrainObject(Image image, Shape collisionShape, Vector2f position, 
            String name) {
        super(image, collisionShape, position, ECollisionType.TERRAIN, name);
    }

    /*
     * Vykresleni objektu
     */
    @Override
    public void render(Graphics graphics) {
        if (image != null) {
            graphics.setColor(new Color(255, 255, 255, 1.0f));
            graphics.texture(getCollisionShape(), image, true);
        } else {
            graphics.setColor(Color.green);
            graphics.draw(getCollisionShape());
        }
    }

    @Override
    public boolean isCollisionsOn() {
        return true;
    }
}
