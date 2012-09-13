package gui;

import java.io.IOException;

public class QuitButton extends Button {

    public QuitButton(int x, int y) throws IOException {
        super(x, y, "data/images/menu/quit.png",
                "data/images/menu/quit_s.png");
    }

    @Override
    public void doFunction() {
        System.exit(0);
    }
}
