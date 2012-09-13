package library;

import java.util.ArrayList;
import java.util.Iterator;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Ray {

    // Zacatecni bod
    private final Point start;
    // Konecny bod
    private final Point end;
    // Pole bodu paprsku
    private final ArrayList<Point> points = new ArrayList<Point>();
    // Kolizni bod
    private Vector2f collisionPoint = null;

    public Ray(Point start, Point end) {
        this.start = start;
        this.end = end;
        calculatePoints();
    }

    public Ray(float x1, float y1, float x2, float y2) {
        this.start = new Point((int) x1, (int) y1);
        this.end = new Point((int) x2, (int) y2);
        calculatePoints();
    }

    /*
     * Vypocitej body paprsku
     */
    private void calculatePoints() {
        points.clear();
        float dx = end.getX() - start.getX();
        float dy = end.getY() - start.getY();

        if (dx >= dy) {
            // podle osy X
            int x = start.getX();
            float y = start.getY();

            float step = Math.abs(dy / dx);
            if (start.getY() > end.getY()) {
                step = -step;
            }
            for (int i = 0; i <= Math.abs(dx); i++) {
                points.add(new Point((int) x, (int) y));
                y += step;

                if (start.getX() < end.getX()) {
                    x++;
                } else {
                    x--;
                }

            }
        } else {
            // podle osy Y
            float x = start.getX();
            int y = start.getY();
            float step = Math.abs(dx / dy);
            if (start.getX() > end.getX()) {
                step = -step;
            }
            for (int i = 0; i <= Math.abs(dy); i++) {
                points.add(new Point((int) x, (int) y));
                x += step;
                if (start.getY() < end.getY()) {
                    y++;
                } else {
                    y--;
                }

            }
        }
    }

    public Point getEndPoint() {
        return end;
    }

    public Point getStartPoint() {
        return start;
    }

    public void draw(Graphics g) {
        if (collisionPoint == null) {
            g.setColor(Color.gray);
            g.drawLine(getStartPoint().getX(), getStartPoint().getY(),
                    getEndPoint().getX(), getEndPoint().getY());
        } else {
            g.setColor(Color.gray);
            g.drawLine(getStartPoint().getX(), getStartPoint().getY(),
                    collisionPoint.x, collisionPoint.y);
            g.setColor(Color.red);
            g.drawLine(collisionPoint.x, collisionPoint.y, getEndPoint().getX(), getEndPoint().getY());
            collisionPoint = null;
        }
    }

    public boolean isRayInterectsShape(Shape shape) {
        for (int i = 0; i < points.size(); i++) {
            if (shape.contains(points.get(i).getX(), points.get(i).getY())) {
                collisionPoint = new Vector2f(points.get(i).getX(), points.get(i).getY());
                return true;
            }
        }
        collisionPoint = null;
        return false;
    }

    public Vector2f getCollisionPoint() {
        return collisionPoint;
    }

    public Iterator getIterator() {
        return points.iterator();
    }
}
