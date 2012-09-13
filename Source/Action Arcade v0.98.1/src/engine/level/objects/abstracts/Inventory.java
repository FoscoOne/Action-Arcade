package engine.level.objects.abstracts;

import engine.enums.EAmmo;
import engine.level.objects.dynamics.ItemObject;
import engine.level.objects.dynamics.items.AmmoObject;
import engine.level.objects.dynamics.items.BFG9KObject;
import engine.level.objects.dynamics.items.HandObject;
import engine.level.objects.dynamics.items.LifeObject;
import engine.level.objects.dynamics.items.PistolObject;
import engine.level.objects.dynamics.items.ShotgunObject;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Inventory {

    private final Vector2f position;
    private final Dimension dimension;
    private final Rectangle window = new Rectangle(0, 0, 20, 20);
    private final ArrayList<ItemObject> items = new ArrayList<ItemObject>();

    /*
     * Konstruktor
     */
    public Inventory(float x, float y, int width, int height) throws SlickException {
        this.position = new Vector2f(x, y);
        this.dimension = new Dimension(width, height);
        items.add(new HandObject());
    }

    /*
     * Pridani predmetu do inventare
     */
    public void addItem(ItemObject item) {
        if (items.contains(item)) {
            return;
        }
        if (item instanceof LifeObject) {
            item.deactivate();
            return;
        }
        item.deactivate();
        items.add(item);
    }

    /*
     * Vykresleni inventare
     */
    public void draw(Graphics g) {
        if (!items.isEmpty()) {
            int x = (int) position.x;
            int y = (int) position.y;
            int xstep = (int) window.getWidth() + 2;
            int ystep = (int) window.getHeight() + 2;
            Iterator it = items.iterator();
            while (it.hasNext()) {
                ItemObject io = (ItemObject) it.next();
                g.drawImage(io.getIcon(), x, y);
                g.setColor(Color.gray);
                //g.drawString(String.valueOf(io.getQuantity()), x, y + 15);
                x += xstep;
                if (x > (position.x + dimension.width)) {
                    y += ystep;
                    x = (int) position.x;
                }
            }
        }
    }

    /*
     * Dej pistol
     */
    public PistolObject getPistol() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof PistolObject) {
                return (PistolObject) items.get(i);
            }
        }
        return null;
    }

    /*
     * Dej brokovnici
     */
    public ShotgunObject getShotgun() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ShotgunObject) {
                return (ShotgunObject) items.get(i);
            }
        }
        return null;
    }

    /*
     * Dej naboje
     */
    public AmmoObject getAmmo(EAmmo eAmmo) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof AmmoObject) {
                if (((AmmoObject) items.get(i)).getAmmoType() == eAmmo) {
                    if (((AmmoObject) items.get(i)).isEmpty()) {
                        items.remove(i);
                    } else {
                        return (AmmoObject) items.get(i);
                    }
                }
            }
        }
        return null;
    }

    /*
     * Dej ruku
     */
    public HandObject getHand() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof HandObject) {
                return (HandObject) items.get(i);
            }
        }
        return null;
    }

    /*
     * Dej BFG9K
     */
    public BFG9KObject getBFG9K() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof BFG9KObject) {
                return (BFG9KObject) items.get(i);
            }
        }
        return null;
    }

    /*
     * Mam pistol
     */
    public boolean isPistolIn() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof PistolObject) {
                return true;
            }
        }
        return false;
    }

    /*
     * Mam brokovnici
     */
    public boolean isShotgunIn() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ShotgunObject) {
                return true;
            }
        }
        return false;
    }

    /*
     * Mam brokovnici
     */
    public boolean isBFG9KIn() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof BFG9KObject) {
                return true;
            }
        }
        return false;
    }
}
