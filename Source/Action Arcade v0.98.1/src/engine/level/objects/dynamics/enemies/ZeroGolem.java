package engine.level.objects.dynamics.enemies;

import engine.enums.EFighter;
import engine.level.Level;
import engine.level.objects.abstracts.GameStats;
import engine.level.objects.dynamics.AIEnemyObject;
import java.util.Random;
import library.Clock;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ZeroGolem extends AIEnemyObject {

    private Clock attacking = new Clock(5000);

    public ZeroGolem(Animation animation, Vector2f position, float health) 
            throws SlickException {
        super(animation, new Rectangle(0, 0, 94, 110), position, 250, health, 
                EFighter.ZEROGOLEM, 10);
        (new Sound("data/sounds/add_boss1.ogg")).play();
        (new Sound("data/sounds/add_boss1_atmo.ogg")).play();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        super.update(gc, sbg, delta);
        if (isAlive) {
            tryToAttack();
        }
    }

    private void tryToAttack() {
        if (attacking.isNextTick()) {
            try {
                float posx, posy;
                posx = position.x + getCollisionShape().getWidth() / 2;
                posy = position.y - 10;
                for (int i = 0; i < 15; i++) {
                    Level.addDynamicObject(new EnemyBulletObject("Green plasma",
                            new Vector2f(posx, posy), getSide(), false,
                            new Image("data/images/items/green_plasma.png"),
                            new Rectangle(0, 0, 15, 15), 1.0f, new Vector2f(10 + (new Random().nextInt(20)), -10 - (new Random().nextInt(20))), 5,
                            false, 0, 250));
                    Level.addDynamicObject(new EnemyBulletObject("Green plasma",
                            new Vector2f(posx, posy), getSide(), false,
                            new Image("data/images/items/green_plasma.png"),
                            new Rectangle(0, 0, 15, 15), 1.0f, new Vector2f(-10 - (new Random().nextInt(20)), -10 - (new Random().nextInt(20))), 5,
                            false, 0, 250));
                }

                try {
                    (new Sound("data/sounds/boss1_attack.ogg")).play();
                } catch (SlickException ex) {
                }
            } catch (SlickException ex) {
            }
        }
    }

    @Override
    public void notifyDeath() {
        super.notifyDeath();
        GameStats.nextBoss();
    }
}
