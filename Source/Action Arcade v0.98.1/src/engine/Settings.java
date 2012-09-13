package engine;

import engine.enums.EGameStatus;
import engine.level.Level;
import engine.level.objects.abstracts.Cheats;
import engine.level.objects.abstracts.GameStats;
import javax.swing.JOptionPane;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public final class Settings {

    private static int height = 480;
    private static int width = 640;
    private static boolean fullscreen = false;
    private static boolean showFPS = false;
    private static String title = "Action Arcade";
    private static int fpsLimit = 40;
    private static String cheats = "";

    public static int getFpsLimit() {
        return fpsLimit;
    }

    public static boolean isFullscreen() {
        return fullscreen;
    }

    public static int getHeight() {
        return height;
    }

    public static boolean isShowFPS() {
        return showFPS;
    }

    public static String getTitle() {
        return title;
    }

    public static int getWidth() {
        return width;
    }

    public static void setFullscreen(boolean fullscreen) {
        Settings.fullscreen = fullscreen;
    }

    public static KeyListener getKeyListener() {
        return new KeyListener() {

            @Override
            public void keyPressed(int i, char c) {
                cheats += c;
                if (cheats.contains("iddqd")) {
                    Cheats.setImmortal();
                    cheats = "";
                }
                if (cheats.contains("fullmap")) {
                    Cheats.showEnemyOnMap();
                    cheats = "";
                }
                if (cheats.length() > 100) {
                    cheats = "";
                }
                if (i == 1) {
                    if (JOptionPane.showConfirmDialog(null, "Opravdu chcete hru ukončit?") == JOptionPane.OK_OPTION) {
                        gui.App.killGame();
                    }
                }
                if (i == 57) {
                    if (GameStats.eGameStatus == EGameStatus.GAME_OVER
                            || GameStats.eGameStatus == EGameStatus.GAME_WIN) {
                        Level.waitingForPressAnyKey();
                    }
                    if (GameStats.eGameStatus == EGameStatus.GAME_PAUSED) {
                        Level.waitingForPressAnyKey();
                    }
                }
            }

            @Override
            public void keyReleased(int i, char c) {
            }

            @Override
            public void setInput(Input input) {
            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {
            }

            @Override
            public void inputStarted() {
            }
        };
    }
}
