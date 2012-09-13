package engine.level.objects.dynamics;

import engine.enums.EAmmo;
import engine.enums.ECollisionType;
import engine.enums.EFighter;
import engine.enums.ESide;
import engine.interfaces.ICollidableObject;
import engine.level.Level;
import engine.level.objects.PhysicalObject;
import engine.level.objects.abstracts.Animations;
import engine.level.objects.abstracts.Cheats;
import engine.level.objects.abstracts.GameStats;
import engine.level.objects.abstracts.Inventory;
import engine.level.objects.dynamics.items.AmmoObject;
import engine.level.objects.dynamics.items.PistolObject;
import library.Clock;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerObject extends DynamicObject implements KeyListener {

    // Maximalni skok
    private static final int MAX_JUMP = 35;
    // Rychlost pohybu po ose X
    private final int SPEED = 7;
    // Ovladani
    private int controls[] = new int[10];
    // Krok na vykreslovani zivota
    private float hStep;
    // Inventar
    private Inventory inventory;
    // Animace
    protected Animations animations;
    // Interval na spawnuti
    private final int spawnInterval = 2000;
    // Cas smrti
    private long timeOfDeath = 0;
    // Budik akce
    private Clock action = new Clock(100);

    /*
     * Konstruktor
     */
    public PlayerObject(Shape collisionShape, Vector2f position, float mass,
            GameContainer gc, int player) throws SlickException {
        super(collisionShape, ECollisionType.PLAYER, "Player", position, new Vector2f(0, 0),
                mass, 100, 0.01f, engine.level.Level.getLevelShape());
        gc.getInput().addKeyListener(this);
        setControls(player);
        init();
    }

    /*
     * Konstruktor s obrazkem
     */
    public PlayerObject(Image image, Shape collisionShape, Vector2f position,
            float mass, GameContainer gc, int player)
            throws SlickException {
        super(image, collisionShape, ECollisionType.PLAYER, "Player", position, new Vector2f(0, 0),
                mass, 100, 0.01f, engine.level.Level.getLevelShape());
        gc.getInput().addKeyListener(this);
        setControls(player);
        init();
    }

    /*
     * Konstruktor s animaci
     */
    public PlayerObject(Animation animation, Shape collisionShape,
            Vector2f position, float mass, GameContainer gc, int player)
            throws SlickException {
        super(animation, collisionShape, ECollisionType.PLAYER, "Player", position,
                new Vector2f(0, 0), mass, 100, 0.01f,
                engine.level.Level.getLevelShape());
        gc.getInput().addKeyListener(this);
        setControls(player);
        init();
        this.animations = new Animations(EFighter.SENTINEL);
        if (animation == null) {
            animation = animations.getStayAnimation(getSide());
        }
    }

    /*
     * Inicializace
     */
    private void init() throws SlickException {
        hStep = (float) (100 / getHealth());
        inventory = new Inventory(530, 20, 100, 200);
        inventory.addItem(new PistolObject(new Vector2f(200, 100)));
        inventory.addItem(new AmmoObject(EAmmo.BULLETS,
                new Image("data/images/icons/bullets.png"),
                50, new Image("data/images/items/ammobox.png"),
                "Bullets", new Vector2f(250, 100)));
        addCollisionTypeToList(ECollisionType.ENEMY);
        addCollisionTypeToList(ECollisionType.ITEM);
        addCollisionTypeToList(ECollisionType.TERRAIN);
    }

    /*
     * Aktualizace objektu
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        super.update(gc, sbg, delta);

        if (!isAlive) {
            if ((System.currentTimeMillis() - timeOfDeath) > spawnInterval) {
                position = new Vector2f(Level.getScreenXToLevelX(100),
                        Level.getScreenYToLevelY(100));
                isAlive = true;
                animation = animations.getStayAnimation(getSide());
                setHealth(100);
            }
        }

        int border = 250;
        float screen_speed = 3f;
        if (position.x < engine.level.Level.getScreenShape().getMinX() + border) {
            if (position.x > engine.level.Level.getLevelShape().getMinX()
                    && engine.level.Level.getLevelShape().getMinX() < engine.level.Level.getScreenShape().getMinX()) {
                engine.level.Level.moveScreen(screen_speed, 0);
            }
        }
        if (getCollisionShape().getMaxX() > engine.level.Level.getScreenShape().getMaxX() - border) {
            if (position.x < engine.level.Level.getLevelShape().getMaxX()
                    && engine.level.Level.getLevelShape().getMaxX() > engine.level.Level.getScreenShape().getMaxX()) {
                engine.level.Level.moveScreen(-screen_speed, 0);
            }
        }
        if (position.y < engine.level.Level.getScreenShape().getMinY() + border) {
            if (position.y > engine.level.Level.getLevelShape().getMinY()
                    && engine.level.Level.getLevelShape().getMinY() < engine.level.Level.getScreenShape().getMinY()) {
                engine.level.Level.moveScreen(0, screen_speed);
            }
        }
        if (getCollisionShape().getMaxY() > engine.level.Level.getScreenShape().getMaxY() - border) {
            if (getCollisionShape().getMaxY() < engine.level.Level.getLevelShape().getMaxY()
                    && engine.level.Level.getLevelShape().getMaxY() > engine.level.Level.getScreenShape().getMaxY()) {
                engine.level.Level.moveScreen(0, -screen_speed);
            }
        }

        if (position.x < engine.level.Level.getScreenShape().getMinX()) {
            position.x = engine.level.Level.getScreenShape().getMinX();
        }
        if (getCollisionShape().getMaxX() > Level.getScreenShape().getMaxX()) {
            position.x = Level.getScreenShape().getMaxX() - getCollisionShape().getWidth();
        }
        if (position.y < Level.getScreenShape().getMinY()) {
            position.y = Level.getScreenShape().getMinY();
        }
        if (position.y > Level.getLevelShape().getMaxY()) {
            this.deactivate();
        }
    }

    /*
     * Vykresleni
     */
    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.setColor(Color.black);
        graphics.fillRect(530, 10, 102, 7);
        graphics.setColor(Color.red);
        graphics.fillRect(531, 11, (float) (hStep * getHealth()), 5);
        inventory.draw(graphics);
    }

    /*
     * Co se stane po smrti
     */
    @Override
    public void notifyDeath() {
        super.notifyDeath();
        try {
            (new Sound("data/sounds/death.wav")).play();
        } catch (SlickException ex) {
        }
        animation = animations.getDiedFigure(getSide());
        timeOfDeath = System.currentTimeMillis();
        GameStats.nextDeath();
        try {
            GameStats.nextLive();
        } catch (SlickException ex) {
        }
    }

    /*
     * Pridani predmetu do inventare
     */
    public void putItemToInventory(ItemObject item) {
        inventory.addItem(item);
        try {
            (new Sound("data/sounds/p.wav")).play();
        } catch (SlickException ex) {
        }
    }

    /*
     * Nastaveni ovladani
     */
    private void setControls(int player) {
        switch (player) {
            case 1:
                controls[0] = 200; // UP
                controls[1] = 205; // RIGHT
                controls[2] = 208; // DOWN
                controls[3] = 203; // LEFT
                controls[4] = 22; // ACTION KEY 1 (U)
                controls[5] = 23; // ACTION KEY 2 (I)
                controls[6] = 24; // ACTION KEY 3 (O)
                controls[7] = 36; // ACTION KEY 4 (J)
                controls[8] = 37; // ACTION KEY 5 (K)
                controls[9] = 38; // ACTION KEY 6 (L)
                break;
            case 2:
                controls[0] = 20; // UP (T)
                controls[1] = 35; // RIGHT (H)
                controls[2] = 34; // DOWN (G)
                controls[3] = 33; // LEFT (F)
                controls[4] = 16; // ACTION KEY 1 (Q)
                controls[5] = 17; // ACTION KEY 2 (W)
                controls[6] = 18; // ACTION KEY 3 (E)
                controls[7] = 30; // ACTION KEY 4 (A)
                controls[8] = 31; // ACTION KEY 5 (S)
                controls[9] = 32; // ACTION KEY 6 (D)
                break;
            default:
                return;
        }
    }

    @Override
    public void keyPressed(int i, char c) {
        if (!isAlive) {
            return;
        }
        if (i == controls[0] && isOnGround()) {
            setVelocityY(-MAX_JUMP);
            try {
                (new Sound("data/sounds/jumping.wav")).play();
            } catch (SlickException ex) {
            }
            if (animation != null) {
                animation = animations.getJumpAnimation(getSide());
            }
        }
        if (i == controls[3]) {
            if (isObjectLooksRight()) {
                if (image != null) {
                    image = image.getFlippedCopy(true, false);
                }
            }
            setConstantSpeed(-SPEED);
            if (animation != null) {
                if (isOnGround()) {
                    animation = animations.getWalkAnimation(getSide());
                } else {
                    animation = animations.getJumpAnimation(getSide());
                }
            }
        }
        if (i == controls[1]) {
            if (isObjectLooksLeft()) {
                if (image != null) {
                    image = image.getFlippedCopy(true, false);
                }
            }
            setConstantSpeed(SPEED);
            if (animation != null) {
                if (isOnGround()) {
                    animation = animations.getWalkAnimation(getSide());
                } else {
                    animation = animations.getJumpAnimation(getSide());
                }
            }
        }

        if (action.isNextTick()) {
            if (i == controls[4]) {
                if (inventory.isPistolIn()) {
                    if (inventory.getPistol().reload(inventory.getAmmo(EAmmo.BULLETS))) {
                        if (inventory.getPistol().tryToFire(this)) {
                            animation = animations.getPistolFigure(getSide());
                        }
                    } else {
                        try {
                            (new Sound("data/sounds/gun_noammo.ogg")).play();
                        } catch (SlickException ex) {
                        }
                    }
                } else {
                    try {
                        (new Sound("data/sounds/eee.wav")).play();
                    } catch (SlickException ex) {
                    }
                }
            }
            if (i == controls[5]) {
                if (inventory.isShotgunIn()) {
                    if (inventory.getShotgun().reload(inventory.getAmmo(EAmmo.SHELLS))) {
                        if (inventory.getShotgun().tryToFire(this)) {
                            animation = animations.getShotgunFigure(getSide());
                        }
                    } else {
                        try {
                            (new Sound("data/sounds/gun_noammo.ogg")).play();
                        } catch (SlickException ex) {
                        }
                    }
                } else {
                    try {
                        (new Sound("data/sounds/eee.wav")).play();
                    } catch (SlickException ex) {
                    }
                }
            }
            if (i == controls[6]) {
                if (inventory.isBFG9KIn()) {
                    if (inventory.getBFG9K().reload(inventory.getAmmo(EAmmo.PLASMA))) {
                        if (inventory.getBFG9K().tryToFire(this)) {
                            animation = animations.getBFG9KFigure(getSide());
                        }
                    } else {
                        try {
                            (new Sound("data/sounds/gun_noammo.ogg")).play();
                        } catch (SlickException ex) {
                        }
                    }
                } else {
                    try {
                        (new Sound("data/sounds/eee.wav")).play();
                    } catch (SlickException ex) {
                    }
                }
            }
            if (i == controls[7]) {
                if (inventory.getHand().reload(inventory.getAmmo(EAmmo.GRENADES))) {
                    if (inventory.getHand().tryToThrow(this)) {
                        animation = animations.getThrowingFigure(getSide());
                    }
                } else {
                    try {
                        (new Sound("data/sounds/eee.wav")).play();
                    } catch (SlickException ex) {
                    }
                }
            }
            if (i == controls[8]) {
                if (inventory.getHand().reload(inventory.getAmmo(EAmmo.BOMBS))) {
                    if (inventory.getHand().tryToThrow(this)) {
                        animation = animations.getThrowingFigure(getSide());
                    }
                } else {
                    try {
                        (new Sound("data/sounds/eee.wav")).play();
                    } catch (SlickException ex) {
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(int i, char c) {
        if (!isAlive) {
            return;
        }
        if (i == controls[3]) {
            setConstantSpeed(0);
            if (animation != null) {
                animation = animations.getStayAnimation(getSide());
            }
        }
        if (i == controls[1]) {
            setConstantSpeed(0);
            if (animation != null) {
                animation = animations.getStayAnimation(getSide());
            }
        }
    }

    @Override
    public void setInput(Input input) {
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {
    }

    @Override
    public void inputStarted() {
    }

    @Override
    protected void doAfterCollision(ICollidableObject entity) {
        if (isAlive) {
            if (animation != null) {
                if (animations.isJumpAnimation(animation)) {
                    if (isOnGround() && velocity.x < 0.5 && velocity.x > -0.5) {
                        animation = animations.getStayAnimation(getSide());
                    }
                    if (isOnGround() && velocity.x > 1) {
                        animation = animations.getWalkAnimation(getSide());
                    } else if (isOnGround() && velocity.x < -1) {
                        animation = animations.getWalkAnimation(getSide());
                    }
                }
                if (isOnGround() && animations.isDamagedAnimation(animation)) {
                    animation = animations.getStayAnimation(getSide());
                }
            }
        }
    }

    public void setDamagedAnimation(ICollidableObject entity) {
        if (animation != null) {
            if (isAlive) {
                if (((PhysicalObject) entity).getSide() == ESide.RIGHT) {
                    position.y -= 2;
                    setVelocity(new Vector2f(10, -10));
                } else {
                    position.y -= 2;
                    setVelocity(new Vector2f(-10, -10));
                }
                animation = animations.getDamagedFigure(getSide());
            }
        }
    }

    @Override
    public void increaseHealth(float increase) {
        try {
            (new Sound("data/sounds/aaah.wav")).play();
        } catch (SlickException ex) {
        }
        if (getHealth() + increase > 100) {
            setHealth(100);
        } else {
            super.increaseHealth(increase);
        }
    }

    @Override
    public void decreaseHealth(float decrease) {
        try {
            (new Sound("data/sounds/ouch.wav")).play();
        } catch (SlickException ex) {
        }
        if (!Cheats.isImmortal()) {
            super.decreaseHealth(decrease);
        }
    }
}
