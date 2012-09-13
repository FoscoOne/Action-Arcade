package engine.level.objects.abstracts;

public class Cheats {

    private static boolean immortal = false;
    private static boolean enemyOnMap = false;

    public static void setImmortal() {
        if (!immortal) {
            System.out.println("Cheat on: Immortal");
            GameStats.increaseScore(-GameStats.getScore());
            immortal = true;
        } else {
            System.out.println("Cheat off: Immortal");
            immortal = false;
        }

    }

    public static boolean isImmortal() {
        return immortal;
    }

    public static void showEnemyOnMap() {
        if (!enemyOnMap) {
            System.out.println("Cheat on: Full map");
            GameStats.increaseScore(-GameStats.getScore());
            enemyOnMap = true;
        } else {
            System.out.println("Cheat off: Full map");
            enemyOnMap = false;
        }
    }

    public static boolean isFullMap() {
        return enemyOnMap;
    }
}
