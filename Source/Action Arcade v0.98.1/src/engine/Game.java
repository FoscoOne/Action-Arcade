package engine;

import engine.level.Level;
import org.newdawn.slick.*;

public class Game extends BasicGame {

    // Herni svet
    private static Level level;

    /*
     * Konstruktor hry
     */
    public Game(String title) {
        super(title);
    }

    /*
     * Inicializace hry
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        // inicializace sveta
        long start = System.currentTimeMillis();
        gc.getInput().addKeyListener(Settings.getKeyListener());
        level = new Level(2000, 1000, 0, 0, gc);
        level.initLevel();
        long end = System.currentTimeMillis();
        System.out.println("Inicializace trvala " + (end - start) + "ms");
    }

    /*
     * Aktualizace hry
     */
    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        level.update(i);
    }

    /*
     * Vykresleni hry
     */
    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        level.draw(grphcs);
    }
}
