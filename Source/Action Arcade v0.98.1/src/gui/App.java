package gui;

import engine.Game;
import engine.Settings;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class App {

    private static final String version = "v0.92";
    private static AppGameContainer app;
    private static final StartScreen startScreen = new StartScreen(Settings.getTitle());

    public static void runGame() throws SlickException {
        startScreen.setVisible(false);
        app = new AppGameContainer(
                new Game(Settings.getTitle()));
        app.setDisplayMode(Settings.getWidth(), Settings.getHeight(),
                Settings.isFullscreen());
        app.setSmoothDeltas(true);
        app.setTargetFrameRate(Settings.getFpsLimit());
        app.setShowFPS(Settings.isShowFPS());
        app.start();
    }

    public static void killGame() {
        app.exit();
    }

    public static void pauseGame() {
        app.pause();
    }

    public static String getVersion() {
        return version;
    }

    public static void main(String[] args) {
        startScreen.setVisible(true);
    }
}
