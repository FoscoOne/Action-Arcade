package engine.level.objects.abstracts;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Message {

    private static ArrayList<Element> buffer = new ArrayList<Element>();
    private static int ttl = 0;
    private static String msg = "";
    private static Color color = null;

    public Message(String msg) {
        buffer.add(new Element(msg, 3000, Color.white));
    }

    public Message(String msg, int duration) {
        buffer.add(new Element(msg, duration, Color.white));
    }

    public Message(String msg, int duration, Color color) {
        buffer.add(new Element(msg, duration, color));
    }

    public static void draw(Graphics g) {
        if (ttl == 0) {
            if (!buffer.isEmpty()) {
                ttl = buffer.get(0).getDuration() / 25;
                msg = buffer.get(0).getMessage();
                color = buffer.get(0).getColor();
                buffer.remove(0);
            }
        } else {
            g.setColor(color);
            g.drawString(msg, 10, 460);
            ttl--;
        }
    }

    private class Element {

        private final String message;
        private final int duration;
        private final Color color;

        public Element(String message, int duration, Color color) {
            this.message = message;
            this.duration = duration;
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public int getDuration() {
            return duration;
        }

        public String getMessage() {
            return message;
        }
    }
}
