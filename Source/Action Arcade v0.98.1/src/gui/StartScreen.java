package gui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

public class StartScreen extends JFrame {

    Menu menu;
    
    public StartScreen(String title) throws HeadlessException {
        super(title);
        init();
    }

    private void init() {
        setVisible(false);
        setSize(640, 480);
        setPreferredSize(new Dimension(640, 480));
        setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2,
                (screenSize.height - getHeight()) / 2);
        try {
            menu = new Menu();
        } catch (IOException ex) {
        }
        add(menu);
        repaint();
    }
}
