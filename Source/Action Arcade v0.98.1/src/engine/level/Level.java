package engine.level;

import engine.Settings;
import engine.enums.EAmmo;
import engine.enums.EGameStatus;
import engine.interfaces.ILevelObject;
import engine.interfaces.Moveable;
import engine.level.objects.dynamics.DynamicObject;
import engine.level.objects.PhysicalObject;
import engine.level.objects.abstracts.GameStats;
import engine.level.objects.abstracts.Message;
import engine.level.objects.dynamics.PlayerObject;
import engine.level.objects.dynamics.items.AmmoObject;
import engine.level.objects.statics.StaticObject;
import engine.level.objects.statics.TerrainObject;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import library.TextFileReader;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public final class Level {

    // Rozmer sveta v px
    private static Dimension dimension;
    // Poloha rohu viditelneho kusu sveta
    private static Point worldCoordinates;
    // Vektor gravitace
    public static final Vector2f GRAVITY = new Vector2f(0.0f, 1.8f);
    // Staticke pozadi sveta
    private static Image backgroundImage;
    private static Image staticBackground;
    private static Image movingBackground;
    // Aktivni prvky
    private static ArrayList<DynamicObject> dynamicObjects;
    // Staticke prvky
    private static ArrayList<StaticObject> staticObjects;
    // Game container
    private static GameContainer gameContainer;
    // Game generator
    private Generator generator = new Generator(20000, 7500, 80000);
    // Hlavni hrac
    private static PlayerObject player;
    // Hudba v pozadi
    private static Music music;

    /*
     * Vytvori svet 
     */
    public Level(int width, int height, int x, int y, GameContainer gc)
            throws SlickException {
        dimension = new Dimension(width, height);
        worldCoordinates = new Point(x, y);
        dynamicObjects = new ArrayList();
        staticObjects = new ArrayList();
        gameContainer = gc;
        backgroundImage = new Image("data/images/world.png");
        staticBackground = new Image("data/images/static_background.jpg");
        movingBackground = new Image("data/images/moving_background.png");
    }

    /*
     * Inicializace levelu
     */
    public void initLevel() throws SlickException {
        player = null;
        GameStats.initLives();
        loadTerrain("data/maps/map1.txt");
        initPlayer();
        loadDefaultItem();
        music = new Music("data/music/music1.ogg");
        music.setVolume(65);
        music.play();
        music.loop();
        GameStats.eGameStatus = EGameStatus.GAME_PAUSED;
        try {
            (new Sound("data/sounds/new_game.ogg")).play();
        } catch (SlickException ex) {
        }
    }

    /*
     * Reinicializace levelu
     */
    public static void reinitLevel() throws SlickException {
        dynamicObjects.clear();
        staticObjects.clear();
        worldCoordinates.x = 0;
        worldCoordinates.y = 0;
        player = null;
        GameStats.initLives();
        loadTerrain("data/maps/map1.txt");
        initPlayer();
        loadDefaultItem();
        music = new Music("data/music/music1.ogg");
        music.setVolume(65);
        music.play();
        music.loop();
        GameStats.eGameStatus = EGameStatus.GAME_RUNNING;
        try {
            (new Sound("data/sounds/new_game.ogg")).play();
        } catch (SlickException ex) {
        }
    }

    /*
     * Inicializace hráče
     */
    private static void initPlayer() throws SlickException {
        Image frames[] = new Image[4];
        frames[0] = new Image("data/images/entities/sentinel/stay_1.png");
        frames[1] = new Image("data/images/entities/sentinel/stay_2.png");
        frames[2] = new Image("data/images/entities/sentinel/stay_3.png");
        frames[3] = new Image("data/images/entities/sentinel/stay_4.png");
        Animation initAnimation = new Animation(frames, 100);
        player = new PlayerObject(initAnimation,
                new Rectangle(0, 0, 25, 50), new Vector2f(50, 700), 70,
                gameContainer, 1);
        dynamicObjects.add(player);
    }

    /*
     * Aktualizace sveta
     */
    public void update(int delta) throws SlickException {
        if (GameStats.eGameStatus == EGameStatus.GAME_RUNNING) {
            movement(delta);
            collision();
            updateObject(delta);
            visibility();
            generator.tryGenerateAmmo();
            generator.tryGenerateEnemy();
            generator.tryGenerateWeapon();
        }
    }

    /*
     * Vykresleni sveta
     */
    public void draw(Graphics g) {
        g.drawImage(staticBackground, 0, 0);
        g.drawImage(movingBackground, worldCoordinates.x, -100);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, worldCoordinates.x, worldCoordinates.y);
        }
        if (GameStats.eGameStatus == EGameStatus.GAME_RUNNING) {
            for (int i = 0; i < staticObjects.size(); i++) {
                if (staticObjects.get(i).isActive()) {
                    ((ILevelObject) staticObjects.get(i)).render(g);
                }
            }

            for (int i = 0; i < dynamicObjects.size(); i++) {
                if (dynamicObjects.get(i).isActive()) {
                    ((ILevelObject) dynamicObjects.get(i)).render(g);
                }
            }
            Message.draw(g);
        }
        GameStats.draw(g);
    }


    /*
     * Kontrola kolizi
     */
    private void collision() {
        if (GameStats.eGameStatus == EGameStatus.GAME_RUNNING) {
            for (int i = 0; i < dynamicObjects.size(); i++) {
                if (dynamicObjects.get(i).isActive()) {
                    for (int j = 0; j < dynamicObjects.size(); j++) {
                        if (i != j && dynamicObjects.get(j).isActive()) {
                            if (dynamicObjects.get(i).isCollidingWith(
                                    dynamicObjects.get(j))) {
                                dynamicObjects.get(i).collidedWith(
                                        dynamicObjects.get(j));
                                dynamicObjects.get(j).collidedWith(
                                        dynamicObjects.get(i));
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < dynamicObjects.size(); i++) {
                if (dynamicObjects.get(i).isActive()) {
                    for (int j = 0; j < staticObjects.size(); j++) {
                        if (staticObjects.get(j).isActive()) {
                            if (dynamicObjects.get(i).isCollidingWith(
                                    staticObjects.get(j))) {
                                dynamicObjects.get(i).collidedWith(
                                        staticObjects.get(j));
                                staticObjects.get(j).collidedWith(
                                        dynamicObjects.get(i));
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * Pohyb aktivnich objektu
     */
    private void movement(int d) {
        if (GameStats.eGameStatus == EGameStatus.GAME_RUNNING) {
            float delta = (float) d / (float) Settings.getFpsLimit();
            Vector2f frameGravity = new Vector2f(GRAVITY.x * delta, GRAVITY.y * delta);
            for (int i = 0; i < dynamicObjects.size(); i++) {
                if (dynamicObjects.get(i).isActive()) {
                    ((Moveable) dynamicObjects.get(i)).move(delta, frameGravity);
                }
            }
        }
    }

    /*
     * Kontrola viditelnych objektu
     */
    private void visibility() {
        if (GameStats.eGameStatus == EGameStatus.GAME_RUNNING) {
            int border = 250;
            for (int i = 0; i < dynamicObjects.size(); i++) {
                if (dynamicObjects.get(i).isActive()) {
                    if (dynamicObjects.get(i).getCollisionShape().getMinX()
                            < getLevelShape().getMinX() - border) {
                        dynamicObjects.get(i).deactivate();
                    }
                    if (dynamicObjects.get(i).getCollisionShape().getMaxX()
                            > getLevelShape().getMaxX() + border) {
                        dynamicObjects.get(i).deactivate();
                    }
                    if (dynamicObjects.get(i).getCollisionShape().getMinY()
                            < getLevelShape().getMinY() - border) {
                        dynamicObjects.get(i).deactivate();
                    }
                    if (dynamicObjects.get(i).getCollisionShape().getMaxY()
                            > getLevelShape().getMaxY() + border) {
                        dynamicObjects.get(i).deactivate();
                    }
                }
            }

            for (int i = 0; i < staticObjects.size(); i++) {
                if (staticObjects.get(i).isActive()) {
                    if (staticObjects.get(i).getCollisionShape().getMinX()
                            < getLevelShape().getMinX() - border) {
                        staticObjects.get(i).deactivate();
                    }
                    if (staticObjects.get(i).getCollisionShape().getMaxX()
                            > getLevelShape().getMaxX() + border) {
                        staticObjects.get(i).deactivate();
                    }
                    if (staticObjects.get(i).getCollisionShape().getMinY()
                            < getLevelShape().getMinY() - border) {
                        staticObjects.get(i).deactivate();
                    }
                    if (staticObjects.get(i).getCollisionShape().getMaxY()
                            > getLevelShape().getMaxY() + border) {
                        staticObjects.get(i).deactivate();
                    }
                }
            }
        }
    }

    /*
     * Prida dynamicky objekt do sveta
     */
    public static void addDynamicObject(DynamicObject object) {
        dynamicObjects.add(object);
    }

    /*
     * Pridat staticky objekt do sveta
     */
    public static void addStaticObject(StaticObject object) {
        staticObjects.add(object);
    }

    /*
     * Nahraje mapu
     */
    private static void loadTerrain(String path) throws SlickException {
        TextFileReader tfr = new TextFileReader(path);
        Iterator it = tfr.getIterator();
        while (it.hasNext()) {
            String params[] = ((String) it.next()).split(",");
            float x = Float.valueOf(params[0]);
            float y = Float.valueOf(params[1]);
            float width = Float.valueOf(params[2]);
            float height = Float.valueOf(params[3]);
            String imagePath = params[4];
            addStaticObject(new TerrainObject(new Image(imagePath),
                    new Rectangle(0, 0, width, height), new Vector2f(x, y),
                    "Terrain"));
        }
    }

    /*
     * Pohyb s obrazovkou
     */
    public static void moveScreen(float offsetx, float offsety) {
        worldCoordinates.x += offsetx;
        worldCoordinates.y += offsety;

        for (int i = 0; i < staticObjects.size(); i++) {
            if (staticObjects.get(i).isActive()) {
                staticObjects.get(i).setPositionOffset(offsetx, offsety);
            }
        }
        for (int i = 0; i < dynamicObjects.size(); i++) {
            if (dynamicObjects.get(i).isActive()) {
                ((PhysicalObject) dynamicObjects.get(i)).setPositionOffset(offsetx, offsety);
                if (dynamicObjects.get(i).isHardCollisionsOn()) {
                    dynamicObjects.get(i).updateRestrictionShape(getLevelShape());
                }
            }
        }
    }

    private static void updateObject(int delta) {
        for (int i = 0; i < staticObjects.size(); i++) {
            if (staticObjects.get(i).isActive()) {
                staticObjects.get(i).update(gameContainer, null, delta);
            }
        }
        for (int i = 0; i < dynamicObjects.size(); i++) {
            if (dynamicObjects.get(i).isActive()) {
                dynamicObjects.get(i).update(gameContainer, null, delta);
            }
        }
    }

    private static void loadDefaultItem() throws SlickException {
        addDynamicObject(new AmmoObject(EAmmo.BULLETS,
                new Image("data/images/icons/bullets.png"),
                50, new Image("data/images/items/ammobox.png"),
                "Bullets", new Vector2f(250, 100)));
    }

    public static void notifyGameOver() throws SlickException {
        GameStats.eGameStatus = EGameStatus.GAME_OVER;
        System.out.println("Game Over");
        try {
            (new Sound("data/sounds/looser.ogg")).play();
            (new Sound("data/sounds/end.wav")).play();
        } catch (SlickException ex) {
        }
    }

    public static void notifyGameWin() {
        GameStats.eGameStatus = EGameStatus.GAME_WIN;
        System.out.println("Game Win");
        try {
            (new Sound("data/sounds/winner.ogg")).play();
        } catch (SlickException ex) {
        }
    }

    public static ArrayList<DynamicObject> getDynamicObjects() {
        return dynamicObjects;
    }

    public static ArrayList<StaticObject> getStaticObjects() {
        return staticObjects;
    }

    public static float getLevelMinX() {
        return worldCoordinates.x;
    }

    public static float getLevelMinY() {
        return worldCoordinates.y;
    }

    public static float getLevelMaxX() {
        return worldCoordinates.x + dimension.width;
    }

    public static float getLevelMaxY() {
        return worldCoordinates.y + dimension.height;
    }

    public static float getScreenXToLevelX(float x) {
        return worldCoordinates.x + x;
    }

    public static float getScreenYToLevelY(float y) {
        return worldCoordinates.y + y;
    }

    public static Shape getLevelShape() {
        return new Rectangle(worldCoordinates.x, worldCoordinates.y,
                dimension.width, dimension.height);
    }

    public static Shape getScreenShape() {
        return new Rectangle(0, 0, Settings.getWidth(), Settings.getHeight());
    }

    public static void waitingForPressAnyKey() {
        if (GameStats.eGameStatus == EGameStatus.GAME_OVER
                || GameStats.eGameStatus == EGameStatus.GAME_WIN) {
            GameStats.reset();
            player = null;
            try {
                reinitLevel();
            } catch (SlickException ex) {
            }
        }
        if (GameStats.eGameStatus == EGameStatus.GAME_PAUSED) {
            GameStats.eGameStatus = EGameStatus.GAME_RUNNING;
        }

    }
}