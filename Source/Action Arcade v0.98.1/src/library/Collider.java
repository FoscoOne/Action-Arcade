package library;

import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Collider {

    // Bude kolize nahore
    private boolean willBeCollisionUp = false;
    // Bude kolize vpravo
    private boolean willBeCollisionRight = false;
    // Bude kolize dole
    private boolean willBeCollisionDown = false;
    // Bude kolize vlevo
    private boolean willBeCollisionLeft = false;
    // Byla kolize nahore
    private boolean wasCollisionUp = false;
    // Byla kolize vpravo
    private boolean wasCollisionRight = false;
    // Byla kolize dole
    private boolean wasCollisionDown = false;
    // Byla kolize vlevo
    private boolean wasCollisionLeft = false;
    // Byla kolize
    private boolean wasCollision = false;
    // Y-souradnice kolize nahore
    private float collisionUp = 0;
    // X-souradnice kolize vpravo
    private float collisionRight = 0;
    // Y-souradnice kolize dole
    private float collisionDown = 0;
    // X-souradnice kolize vlevo
    private float collisionLeft = 0;
    // Kolize dole jiz byla zkontrolovana
    private boolean checkDown = false;
    // Kolize nahore jiz byla zkontrolovana
    private boolean checkUp = false;
    // Kolize vpravo jiz byla zkontrolovana
    private boolean checkRight = false;
    // Kolize vlevo jiz byla zkontrolovana
    private boolean checkLeft = false;
    // Pole koliznich paprsku
    ArrayList<Ray> rays = new ArrayList<Ray>();

    /*
     * Vraci hodnotu TRUE pokud bude kolizce
     */
    public boolean willBeCollision(Shape me, Shape him) {
        return him.intersects(getMyPredicateShape(me));
    }

    public void calculateCollision(Shape me, Shape him) {
        rays.clear();
        if (him.intersects(me)) {
            // Kolize jiz probehla
            int border = 10;
            wasCollision = true;

            Ray down = new Ray(me.getMinX() + border, me.getMaxY(),
                    me.getMaxX() - border, me.getMaxY());
            rays.add(down);
            if (down.isRayInterectsShape(him)) {
                wasCollisionDown = true;
                collisionDown = him.getMinY();
                return;
            }

            Ray up = new Ray(me.getMinX() + border, me.getMinY(),
                    me.getMaxX() - border, me.getMinY());
            rays.add(up);
            if (up.isRayInterectsShape(him)) {
                wasCollisionUp = true;
                collisionUp = him.getMaxY();
                return;
            }

            Ray right = new Ray(me.getMaxX(), me.getMinY() + border,
                    me.getMaxX(), me.getMaxY() - border);
            rays.add(right);
            if (right.isRayInterectsShape(him)) {
                wasCollisionRight = true;
                collisionRight = him.getMinX();
                return;
            }

            Ray left = new Ray(me.getMinX(), me.getMinY() + border,
                    me.getMinX(), me.getMaxY() - border);
            rays.add(left);
            if (left.isRayInterectsShape(him)) {
                wasCollisionLeft = true;
                collisionLeft = him.getMaxX();
                return;
            }
        } else {
            // Kolize teprve probehne
            int border = 2;

            // DOWN
            Ray down_1 = new Ray(me.getMinX() + border, me.getMaxY(),
                    me.getMaxX() - border, getMyPredicateShape(me).getMaxY());
            Ray down_2 = new Ray(me.getMaxX() - border, me.getMaxY(),
                    me.getMinX() + border, getMyPredicateShape(me).getMaxY());
            rays.add(down_1);
            rays.add(down_2);
            Float delta_down_1 = Float.NaN;
            Float delta_down_2 = Float.NaN;
            if (down_1.isRayInterectsShape(him)) {
                delta_down_1 = down_1.getCollisionPoint().y;
                willBeCollisionDown = true;
            }
            if (down_2.isRayInterectsShape(him)) {
                delta_down_2 = down_2.getCollisionPoint().y;
                willBeCollisionDown = true;
            }
            if (!delta_down_1.isNaN() && !delta_down_2.isNaN()) {
                if (delta_down_1 < delta_down_2) {
                    collisionDown = delta_down_1;
                } else {
                    collisionDown = delta_down_2;
                }
                return;
            } else {
                if (!delta_down_1.isNaN()) {
                    collisionDown = delta_down_1;
                    return;
                }
                if (!delta_down_2.isNaN()) {
                    collisionDown = delta_down_2;
                    return;
                }
            }

            // UP
            Ray up_1 = new Ray(me.getMinX() + border, me.getMinY(),
                    me.getMaxX() - border, getMyPredicateShape(me).getMinY());
            Ray up_2 = new Ray(me.getMaxX() - border, me.getMinY(),
                    me.getMinX() + border, getMyPredicateShape(me).getMinY());
            rays.add(up_1);
            rays.add(up_2);
            Float delta_up_1 = Float.NaN;
            Float delta_up_2 = Float.NaN;
            if (up_1.isRayInterectsShape(him)) {
                delta_up_1 = up_1.getCollisionPoint().y;
                willBeCollisionUp = true;
            }
            if (up_2.isRayInterectsShape(him)) {
                delta_up_2 = up_2.getCollisionPoint().y;
                willBeCollisionUp = true;
            }
            if (!delta_up_1.isNaN() && !delta_up_2.isNaN()) {
                if (delta_up_1 > delta_up_2) {
                    collisionUp = delta_up_1;
                } else {
                    collisionUp = delta_up_2;
                }
                return;
            } else {
                if (!delta_up_1.isNaN()) {
                    collisionUp = delta_up_1;
                    return;
                }
                if (!delta_up_2.isNaN()) {
                    collisionUp = delta_up_2;
                    return;
                }
            }

            // LEFT
            Ray left_1 = new Ray(me.getMinX(), me.getMinY() + border,
                    getMyPredicateShape(me).getMinX(), me.getMaxY() - border);
            Ray left_2 = new Ray(me.getMinX(), me.getMaxY() - border,
                    getMyPredicateShape(me).getMinX(), me.getMinY() + border);
            rays.add(left_1);
            rays.add(left_2);
            Float delta_left_1 = Float.NaN;
            Float delta_left_2 = Float.NaN;
            if (left_1.isRayInterectsShape(him)) {
                delta_left_1 = left_1.getCollisionPoint().x;
                willBeCollisionLeft = true;
            }
            if (left_2.isRayInterectsShape(him)) {
                delta_left_2 = left_2.getCollisionPoint().x;
                willBeCollisionLeft = true;
            }
            if (!delta_left_1.isNaN() && !delta_left_2.isNaN()) {
                if (delta_left_1 > delta_left_2) {
                    collisionLeft = delta_left_1;
                } else {
                    collisionLeft = delta_left_2;
                }
                return;
            } else {
                if (!delta_left_1.isNaN()) {
                    collisionLeft = delta_left_1;
                    return;
                }
                if (!delta_left_2.isNaN()) {
                    collisionLeft = delta_left_2;
                    return;
                }
            }

            // RIGHT
            Ray right_1 = new Ray(me.getMaxX(), me.getMinY() + border,
                    getMyPredicateShape(me).getMaxX(), me.getMaxY() - border);
            Ray right_2 = new Ray(me.getMaxX(), me.getMaxY() - border,
                    getMyPredicateShape(me).getMaxX(), me.getMinY() + border);
            rays.add(right_1);
            rays.add(right_2);
            Float delta_right_1 = Float.NaN;
            Float delta_right_2 = Float.NaN;
            if (right_1.isRayInterectsShape(him)) {
                delta_right_1 = right_1.getCollisionPoint().x;
                willBeCollisionRight = true;
            }
            if (right_2.isRayInterectsShape(him)) {
                delta_right_2 = right_2.getCollisionPoint().x;
                willBeCollisionRight = true;
            }
            if (!delta_right_1.isNaN() && !delta_right_2.isNaN()) {
                if (delta_right_1 > delta_right_2) {
                    collisionRight = delta_right_1;
                } else {
                    collisionRight = delta_right_2;
                }
                return;
            } else {
                if (!delta_right_1.isNaN()) {
                    collisionRight = delta_right_1;
                    return;
                }
                if (!delta_right_2.isNaN()) {
                    collisionRight = delta_right_2;
                    return;
                }
            }
        }
    }

    public Shape getMyPredicateShape(Shape me) {
        int border = 15;
        return new Rectangle(me.getMinX() - border, me.getMinY() - border,
                me.getWidth() + 2 * border, me.getHeight() + 2 * border);
    }

    public void reset() {
        willBeCollisionUp = false;
        willBeCollisionRight = false;
        willBeCollisionDown = false;
        willBeCollisionLeft = false;
        wasCollisionUp = false;
        wasCollisionRight = false;
        wasCollisionDown = false;
        wasCollisionLeft = false;
        wasCollision = false;
        collisionUp = 0;
        collisionRight = 0;
        collisionDown = 0;
        collisionLeft = 0;
        checkDown = false;
        checkUp = false;
        checkRight = false;
        checkLeft = false;
    }

    public float getCollisionUp() {
        return collisionUp;
    }

    public float getCollisionRight() {
        return collisionRight;
    }

    public float getCollisionDown() {
        return collisionDown;
    }

    public float getCollisionLeft() {
        return collisionLeft;
    }

    public void setWillBeCollisionUp() {
        this.willBeCollisionUp = true;
    }

    public void setWillBeCollisionRight() {
        this.willBeCollisionRight = true;
    }

    public void setWillBeCollisionDown() {
        this.willBeCollisionDown = true;
    }

    public void setWillBeCollisionLeft() {
        this.willBeCollisionLeft = true;
    }

    public void setWasCollision() {
        this.wasCollision = true;
    }

    public void setWasCollisionUp() {
        this.wasCollisionUp = true;
    }

    public void setWasCollisionRight() {
        this.wasCollisionRight = true;
    }

    public void setWasCollisionDown() {
        this.wasCollisionDown = true;
    }

    public void setWasCollisionLeft() {
        this.wasCollisionLeft = true;
    }

    public void setCollisionDown(float collisionDown) {
        this.collisionDown = collisionDown;
    }

    public void setCollisionLeft(float collisionLeft) {
        this.collisionLeft = collisionLeft;
    }

    public void setCollisionRight(float collisionRight) {
        this.collisionRight = collisionRight;
    }

    public void setCollisionUp(float collisionUp) {
        this.collisionUp = collisionUp;
    }

    public boolean wasCollisionDown() {
        return wasCollisionDown;
    }

    public boolean wasCollisionLeft() {
        return wasCollisionLeft;
    }

    public boolean wasCollisionRight() {
        return wasCollisionRight;
    }

    public boolean wasCollisionUp() {
        return wasCollisionUp;
    }

    public boolean wasCollision() {
        return wasCollision;
    }

    public boolean willBeCollisionDown() {
        return willBeCollisionDown;
    }

    public boolean willBeCollisionLeft() {
        return willBeCollisionLeft;
    }

    public boolean willBeCollisionRight() {
        return willBeCollisionRight;
    }

    public boolean willBeCollisionUp() {
        return willBeCollisionUp;
    }

    public void printReport(Object object) {
        System.out.println("***** COLLIDER REPORT FOR "
                + object.toString() + " *****");
        System.out.println("was collision : " + wasCollision);
        System.out.println("was collision down : " + wasCollisionDown);
        System.out.println("was collision up : " + wasCollisionUp);
        System.out.println("was collision left : " + wasCollisionLeft);
        System.out.println("was collision right : " + wasCollisionRight);
        System.out.println("will be collision down : " + willBeCollisionDown
                + " Y = " + collisionDown);
        System.out.println("will be collision up : " + willBeCollisionUp
                + " Y = " + collisionUp);
        System.out.println("will be collision left : " + willBeCollisionLeft
                + " X = " + collisionLeft);
        System.out.println("will be collision right : " + willBeCollisionRight
                + " X = " + collisionRight);
        System.out.println("***** END OF REPORT *****");
    }

    public void draw(Graphics g) {
        Iterator<Ray> it = rays.iterator();
        while (it.hasNext()) {
            it.next().draw(g);
        }
    }

    public void setCheckedDown() {
        this.checkDown = true;
    }

    public void setCheckedLeft() {
        this.checkLeft = true;
    }

    public void setCheckedRight() {
        this.checkRight = true;
    }

    public void setCheckedUp() {
        this.checkUp = true;
    }

    public boolean isCheckDown() {
        return checkDown;
    }

    public boolean isCheckLeft() {
        return checkLeft;
    }

    public boolean isCheckRight() {
        return checkRight;
    }

    public boolean isCheckUp() {
        return checkUp;
    }
}
