package gui;

import data.Loader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Menu extends JPanel {

    ImageIcon background;

    public Menu() throws IOException {
        setSize(640, 480);
        setPreferredSize(new Dimension(640, 480));
        setBackground(Color.black);
        setLayout(null);
        setFocusable(true);
        init();
        repaint();
    }

    private void init() throws IOException {
        addButton(new NewGameButton(10, 330));
        addButton(new QuitButton(10, 390));
        this.background = new ImageIcon(Loader.getURL("data/images/menu/background.jpg"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.drawImage(background.getImage(), 0, 0, null);
    }

    private void addButton(Button button) {
        add(button);
    }
}
