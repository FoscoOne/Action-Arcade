package engine.level.objects.abstracts;

import engine.enums.EGameStatus;
import engine.level.Level;
import engine.level.objects.dynamics.EnemyObject;
import engine.level.objects.dynamics.ItemObject;
import engine.level.objects.dynamics.PlayerObject;
import engine.level.objects.dynamics.items.WeaponObject;
import java.awt.Dimension;
import java.awt.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameStats {

    // Pocet zasahu
    private static int frags = 0;
    // Pocet smrti
    private static int deaths = 0;
    // Pocet nepratel v levelu
    private static int enemies = 0;
    // Pocet zivotu
    private static int lives = 0;
    // Celkove skore
    private static int score = 0;
    // Maximalni pocet nepratel v levelu
    public static final int MAX_ENEMIES = 25;
    // Herni status
    public static EGameStatus eGameStatus = null;
    // Pocet bossu
    private static int bosses = 0;
    // Naplnenost levelu
    private static float capacity;

    /*
     * Vykresleni hernich statistik
     */
    public static void draw(Graphics g) {
        if (eGameStatus == EGameStatus.GAME_RUNNING) {
            int step = 240 / MAX_ENEMIES;
            g.setColor(Color.white);
            g.drawString("Score: " + score, 10, 70);
            g.drawString("Lives: " + lives, 10, 90);
            g.setColor(Color.black);
            g.fillRect(200, 10, 240, 15);
            g.setColor(Color.white);
            g.drawRect(200, 10, 240, 15);
            if (enemies > (0.75 * MAX_ENEMIES)) {
                g.setColor(Color.red);
            } else if (enemies > (0.50 * MAX_ENEMIES)) {
                g.setColor(Color.yellow);
            } else {
                g.setColor(Color.green);
            }
            g.fillRect(201, 11, step * enemies, 14);

            // Mapa
            Dimension map = new Dimension(100, 50);
            float xstep = map.width / Level.getLevelShape().getWidth();
            float ystep = map.height / Level.getLevelShape().getHeight();
            Point s = new Point(10, 10);
            try {
                g.drawImage(new Image("data/images/hud/map.png"), s.x + 1, s.y + 1);
            } catch (SlickException ex) {
            }
            g.setColor(Color.white);
            g.drawRect(s.x - 1, s.y - 1, map.width + 2, map.height + 2);
            float deltaX = 0 - Level.getLevelShape().getMinX();
            float deltaY = 0 - Level.getLevelShape().getMinY();
            for (int i = 0; i < Level.getStaticObjects().size(); i++) {
                float x = Level.getStaticObjects().get(i).getCollisionShape().getCenterX();
                float y = Level.getStaticObjects().get(i).getCollisionShape().getCenterY();
                x = x + deltaX;
                y = y + deltaY;
                x *= xstep;
                y *= ystep;
                x += s.x;
                y += s.y;
                g.setColor(Color.gray);
                g.fillRect(x, y, 1, 1);
            }
            for (int i = 0; i < Level.getDynamicObjects().size(); i++) {
                if (Level.getDynamicObjects().get(i).isAlive()
                        && Level.getDynamicObjects().get(i).isActive()) {
                    float x = Level.getDynamicObjects().get(i).getCollisionShape().getCenterX();
                    float y = Level.getDynamicObjects().get(i).getCollisionShape().getCenterY();
                    x = x + deltaX;
                    y = y + deltaY;
                    x *= xstep;
                    y *= ystep;
                    x += s.x;
                    y += s.y;
                    if (Cheats.isFullMap()) {
                        if (Level.getDynamicObjects().get(i) instanceof EnemyObject) {
                            g.setColor(Color.red);
                            g.fillRect(x, y, 1, 1);
                        }
                    }
                    if (Level.getDynamicObjects().get(i) instanceof ItemObject) {
                        g.setColor(Color.green);
                        g.fillRect(x - 1, y - 1, 2, 2);
                    }
                    if (Level.getDynamicObjects().get(i) instanceof WeaponObject) {
                        g.setColor(Color.cyan);
                        g.fillRect(x - 1, y - 1, 2, 2);
                    }
                    if (Level.getDynamicObjects().get(i) instanceof PlayerObject) {
                        g.setColor(Color.blue);
                        g.fillRect(x - 1, y - 1, 2, 2);
                    }
                }
            }
        }
        if (eGameStatus == EGameStatus.GAME_OVER) {
            try {
                g.drawImage(new Image("data/images/black_screen.png"), 0, 0);
            } catch (SlickException ex) {
            }
            g.setColor(Color.red);
            g.drawString("GAME OVER", 260, 240);
            g.setColor(Color.gray);
            g.drawString("Planetu obsadili emzaci!", 200, 300);
            g.drawString("Zabito emzaku: " + frags, 200, 320);
            g.drawString("Celkove skore: " + score, 200, 340);
            g.drawString("Spustit novou hru skistnutim mezerniku", 160, 460);
        }
        if (eGameStatus == EGameStatus.GAME_WIN) {
            try {
                g.drawImage(new Image("data/images/black_screen.png"), 0, 0);
            } catch (SlickException ex) {
            }
            g.setColor(Color.green);
            g.drawString("YOU WIN !", 260, 240);
            g.setColor(Color.gray);
            g.drawString("Zachranil jsi planetu!", 200, 300);
            g.drawString("Zabito emzaku: " + frags, 200, 320);
            g.drawString("Celkove skore: " + score, 200, 340);
            g.drawString("TIP: Par tipu pro podvodniky", 50, 380);
            g.drawString("Kdykoliv napis 'iddqd' a budes nesmrtelny", 50, 400);
            g.drawString("Kdykoliv napis 'fullmap' a uvidis nepratele na mape", 50, 420);
            g.drawString("Spustit novou hru skistnutim mezerniku", 160, 460);
        }
        if (eGameStatus == EGameStatus.GAME_PAUSED) {
            try {
                g.drawImage(new Image("data/images/black_screen.png"), 0, 0);
            } catch (SlickException ex) {
            }
            g.setColor(Color.cyan);
            g.drawString("Action Arcade", 260, 100);
            g.setColor(Color.gray);
            g.drawString("Planeta je v ohrozeni! Byla napadena fasistickymi emzaky! Proto", 30, 150);
            g.drawString("jsme povolali vas, aby jste nam pomohl vycistit tajne sektory od ", 30, 170);
            g.drawString("emzaku. Nevime kolik jich muze byt, proto budte opatrny! Vezmete", 30, 190);
            g.drawString("si nas tajny superoblek a puste se do boje!", 30, 210);
            g.drawString("TIP: Na horni casti obrazovky je ukazatel zamoreni sektoru emzaky.", 30, 250);
            g.drawString("Vase mise skonci nezdarem, pokud se ukazatel naplni!", 30, 270);
            g.drawString("Pokracujte stisknutim mezerniku", 168, 460);
        }
    }

    public static void nextFrag() {
        frags++;
        enemies--;
        GameStats.increaseScore(10);
    }

    public static void nextDeath() {
        deaths++;
    }

    public static void nextEnemy() throws SlickException {
        enemies++;
        capacity = (float) enemies / (float) MAX_ENEMIES;
        if (enemies > MAX_ENEMIES) {
            Level.notifyGameOver();
        }
    }

    public static int getEnemiesCount() {
        return enemies;
    }

    public static void nextLive() throws SlickException {
        lives--;
        if (lives < 0) {
            Level.notifyGameOver();
        }
    }

    public static int getFrags() {
        return frags;
    }

    public static void initLives() {
        lives = 3;
    }

    public static void reset() {
        deaths = 0;
        enemies = 0;
        frags = 0;
        lives = 3;
        score = 0;
    }

    public static void nextBoss() {
        bosses++;
        GameStats.increaseScore(500);
        if (bosses >= 2) {
            GameStats.increaseScore(500);
            Level.notifyGameWin();
        }
    }

    public static void increaseScore(int i) {
        score += i;
    }

    public static int getScore() {
        return score;
    }
    
    public static float getCapacity() {
        return capacity;
    }
}
