package yeah.im.liam.rpglocatorbar.hud;

public final class XpRules {
    private XpRules() {}

    public static boolean barVisible(boolean spectator, boolean creative, boolean hasExperience, boolean hideInCreative, int level) {
        if (spectator) {
            return false;
        }
        if (creative) {
            return !hideInCreative;
        }
        return hasExperience && level > 0;
    }

    public static boolean levelVisible(boolean spectator, boolean creative, boolean hasExperience, boolean hideInCreative, int level) {
        return level > 0 && barVisible(spectator, creative, hasExperience, hideInCreative, level);
    }
}
