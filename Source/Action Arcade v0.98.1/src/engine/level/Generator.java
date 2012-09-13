package engine.level;

import engine.enums.EAmmo;
import engine.level.objects.abstracts.GameStats;
import engine.level.objects.abstracts.Message;
import engine.level.objects.dynamics.enemies.ERobot;
import engine.level.objects.dynamics.enemies.ZeroGolem;
import engine.level.objects.dynamics.items.AmmoObject;
import engine.level.objects.dynamics.items.BFG9KObject;
import engine.level.objects.dynamics.items.LifeObject;
import engine.level.objects.dynamics.items.ShotgunObject;
import java.util.Random;
import library.Clock;
import library.RandomNumber;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Generator {

    // Hodiny pro generovani naboju
    private final Clock ammo;
    // Hodiny pro generovani nepratel
    private final Clock enemies;
    // Hodiny pro generovani zbrani
    private final Clock weapons;
    // Dalsi zbran ke generovani
    private int currentGenerateWeapon = 2;
    // Byl vygenerovan ZEROGOLEM 1 a 2
    private boolean boss1generated = false;
    private boolean boss2generated = false;

    /*
     * Konstruktor
     */
    public Generator(int intervalForAmmo, int intervalForEnemies,
            int intervalForWeapons) {
        this.ammo = new Clock(intervalForAmmo);
        this.enemies = new Clock(intervalForEnemies);
        this.weapons = new Clock(intervalForWeapons);
    }

    public void tryGenerateAmmo() {
        if (ammo.isNextTick()) {
            boolean put = false;
            int x = 0;
            int y = 0;

            while (!put) {
                x = (new RandomNumber(Level.getLevelMinX(),
                        Level.getLevelMaxX())).getRandomInt();
                y = (new RandomNumber(Level.getLevelMinY(),
                        Level.getLevelMaxY())).getRandomInt();
                Shape s = new Rectangle(x, y, 50, 50);
                put = true;
                for (int j = 0; j < Level.getStaticObjects().size(); j++) {
                    if (Level.getStaticObjects().get(j).getCollisionShape().intersects(s)) {
                        put = false;
                        continue;
                    }
                }
                for (int j = 0; j < Level.getDynamicObjects().size(); j++) {
                    if (Level.getDynamicObjects().get(j).getCollisionShape().intersects(s)) {
                        put = false;
                        continue;
                    }
                }
            }

            switch ((new Random()).nextInt(6)) {
                case 0:
                    try {
                        Level.addDynamicObject(new AmmoObject(EAmmo.BULLETS,
                                new Image("data/images/icons/bullets.png"),
                                99,
                                new Image("data/images/items/ammobox.png"),
                                "Bullets", new Vector2f(x, y)));
                    } catch (SlickException ex) {
                    }
                    new Message("Radio: Posilame nejakej naboje, dopln si zasobnik...");
                    break;
                case 1:
                    if (currentGenerateWeapon > 2) {
                        try {
                            Level.addDynamicObject(new AmmoObject(EAmmo.SHELLS,
                                    new Image("data/images/icons/shells.png"),
                                    79,
                                    new Image("data/images/items/ammobox.png"),
                                    "Shells", new Vector2f(x, y)));
                        } catch (SlickException ex) {
                        }
                        new Message("Radio: Vyzvedni si naboje do brokovnice...");
                    } else {
                        ammo.notifyNextTick();
                    }
                    break;
                case 2:
                    try {
                        Level.addDynamicObject(new AmmoObject(EAmmo.GRENADES,
                                new Image("data/images/icons/grenade.png"),
                                7,
                                new Image("data/images/items/ammobox.png"),
                                "Grenades", new Vector2f(x, y)));
                    } catch (SlickException ex) {
                    }
                    new Message("Radio: Tyhle granaty se ti budou hodit...");
                    break;
                case 3:
                    try {
                        Level.addDynamicObject(new AmmoObject(EAmmo.BOMBS,
                                new Image("data/images/icons/bomb.png"),
                                2,
                                new Image("data/images/items/ammobox.png"),
                                "Bombs", new Vector2f(x, y)));
                    } catch (SlickException ex) {
                    }
                    new Message("Radio: Vezmi si tyhle bomby, jsou velice ucinne...");
                    break;
                case 4:
                    if (currentGenerateWeapon > 3) {
                        try {
                            Level.addDynamicObject(new AmmoObject(EAmmo.PLASMA,
                                    new Image("data/images/icons/plasma.png"),
                                    29,
                                    new Image("data/images/items/ammobox.png"),
                                    "Plasma", new Vector2f(x, y)));
                        } catch (SlickException ex) {
                        }
                        new Message("Radio: Narvi tuhle plasmu do BFG9000 a usmaz ty hajzly!");
                    } else {
                        ammo.notifyNextTick();
                    }
                    break;
                case 5:
                    try {
                        Level.addDynamicObject(new LifeObject(new Vector2f(x, y)));
                    } catch (SlickException ex) {
                    }
                    break;
            }
            try {
                (new Sound("data/sounds/ammo.ogg")).play();
            } catch (SlickException ex) {
            }
            ammo.increaseInterval(-10);
        }
    }

    public void tryGenerateEnemy() {
        if (GameStats.getFrags() > 25 && !boss1generated) {
            boss1generated = true;
            try {
                Image frames[] = new Image[1];
                frames[0] = new Image("data/images/entities/zerogolem/walk_1.png");
                Animation a = new Animation(frames, 500);
                Level.addDynamicObject(new ZeroGolem(a,
                        new Vector2f(Level.getScreenXToLevelX(500),
                        Level.getScreenYToLevelY(50)), 1000));
                GameStats.nextEnemy();
            } catch (SlickException ex) {
            }
            new Message("Radio: Pozor! Objevil se tu nejaky obrovsky emzak!", 4000);
            return;
        }

        if (GameStats.getFrags() > 50 && !boss2generated) {
            boss2generated = true;
            try {
                Image frames[] = new Image[1];
                frames[0] = new Image("data/images/entities/zerogolem/walk_1.png");
                Animation a = new Animation(frames, 500);
                Level.addDynamicObject(new ZeroGolem(a,
                        new Vector2f(Level.getScreenXToLevelX(500),
                        Level.getScreenYToLevelY(50)), 2500));
                GameStats.nextEnemy();
            } catch (SlickException ex) {
            }
            new Message("Radio: Paneboze! Snad je tenhle obrovsky emzak posledni!", 4000);
            return;
        }

        if (enemies.isNextTick()) {
            int x = 0;
            int y = (int) Level.getScreenYToLevelY(0);

            x = (new RandomNumber(Level.getLevelMinX(),
                    Level.getLevelMaxX())).getRandomInt();

            try {
                Image frames[] = new Image[1];
                frames[0] = new Image("data/images/entities/erobot/walk_1.png");
                Animation a = new Animation(frames, 500);
                Level.addDynamicObject(new ERobot(a, new Vector2f(x,
                        Level.getScreenYToLevelY(y))));
            } catch (SlickException ex) {
            }
            enemies.increaseInterval(30);
            try {
                GameStats.nextEnemy();
            } catch (SlickException ex) {
            }

            if (GameStats.getCapacity() > 0.5f && GameStats.getCapacity() < 0.75f) {
                new Message("Radio: Dalsi emzak! Zacinaji se ti tam mnozit, neco s tim delej...");
            } else if (GameStats.getCapacity() > 0.75f) {
                new Message("Radio: To je v prdeli! Musis neco delat, nebo to tu emzaci obsadi!");
            } else {
                new Message("Radio: Roboticky emzak na obzoru...");
            }
        }
    }

    public void tryGenerateWeapon() {
        if (currentGenerateWeapon > 3) {
            return;
        }
        if (weapons.isNextTick()) {

            int x = 0;
            int y = (int) Level.getScreenYToLevelY(0);

            x = (new RandomNumber(Level.getLevelMinX(),
                    Level.getLevelMaxX())).getRandomInt();

            switch (currentGenerateWeapon) {
                case 1:
                    break;
                case 2:
                    try {
                        Level.addDynamicObject(new ShotgunObject(new Vector2f(x, y)));
                    } catch (SlickException ex) {
                    }
                    new Message("Radio: Najdi brokovnici, kterou sme ti poslali..");
                    break;
                case 3:
                    try {
                        Level.addDynamicObject(new BFG9KObject(new Vector2f(x, y)));
                    } catch (SlickException ex) {
                    }
                    new Message("Radio: Tohle je BFG9000! Vezmi ji a vycisti sektor!");
                    break;
            }
            currentGenerateWeapon++;
            try {
                (new Sound("data/sounds/new_weapon.ogg")).play();
            } catch (SlickException ex) {
            }
        }
    }
}
