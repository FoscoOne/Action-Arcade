package gui;

import java.io.IOException;
import org.newdawn.slick.SlickException;

public class NewGameButton extends Button {

    public NewGameButton(int x, int y) throws IOException {
        super(x, y, "data/images/menu/newgame.png",
                "data/images/menu/newgame_s.png");
    }

    @Override
    public void doFunction() {
        try {
            App.runGame();
        } catch (SlickException ex) {
        }
    }
}
