package engine.level.objects;

import engine.interfaces.ILevelObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class LevelObject extends PhysicalObject
        implements ILevelObject {

    // Jmeno objektu
    protected String name;
    // Je objekt aktivni
    protected boolean isActive = true;

    /*
     * Konstruktor
     */
    public LevelObject(String name, Vector2f position,
            Vector2f velocity, float mass, float health, float bounce) {
        super(position, velocity, mass, health, bounce);
        this.name = name;
    }

    /*
     * Vrati jmeno
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * Renderovani grafiky
     */
    @Override
    public abstract void render(Graphics graphics);

    /*
     * Update objektu
     */
    @Override
    public abstract void update(GameContainer gc, StateBasedGame sbg,
            int delta);

    /*
     * Vrati hodnotu true, pokud je objekt aktivni
     */
    @Override
    public boolean isActive() {
        return isActive;
    }

    /*
     * Deaktivace objektu
     */
    @Override
    public void deactivate() {
        this.isActive = false;
    }
}
