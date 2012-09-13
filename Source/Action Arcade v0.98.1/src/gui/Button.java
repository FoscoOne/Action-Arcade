package gui;

import data.Loader;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public abstract class Button extends JComponent implements MouseListener {

    private final ImageIcon image1;
    private final ImageIcon image2;
    private Image render = null;

    public Button(int x, int y, String pathImage1, String pathImage2) throws IOException {
        this.image1 = new ImageIcon(Loader.getURL(pathImage1));
        this.image2 =new ImageIcon(Loader.getURL(pathImage2));
        this.render = image1.getImage();
        setLocation(x, y);
        setSize(200, 50);
        setPreferredSize(new Dimension(200, 50));
        addMouseListener(this);
    }

    public abstract void doFunction();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(render, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        doFunction();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        render = image2.getImage();
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        render = image1.getImage();
        repaint();
    }
}
