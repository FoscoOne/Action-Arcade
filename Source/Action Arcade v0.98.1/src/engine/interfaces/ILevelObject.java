package engine.interfaces;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface ILevelObject {

    String getName();

    void render(Graphics graphics);

    void update(GameContainer gc, StateBasedGame sbg, int delta);

    boolean isActive();

    void deactivate();
}
