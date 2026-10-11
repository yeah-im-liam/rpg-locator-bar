package yeah.im.liam.rpglocatorbar.config;

import net.minecraft.util.Mth;

public final class LocatorConfig {
    // Physical baseline; the UI shows it as offset 0.
    public static final int DEFAULT_OFFSET = 7;
    public static final int MIN_OFFSET = -DEFAULT_OFFSET;
    public static final int MAX_OFFSET = 220;
    public static final boolean DEFAULT_RENDER_XP_OVERLAY = true;
    public static final boolean DEFAULT_HIDE_XP_IN_CREATIVE = true;
    public static final boolean DEFAULT_KEEP_LOCATOR_DURING_XP = true;
    public static final boolean DEFAULT_ALWAYS_SHOW_LOCATOR = false;

    public int locatorYOffset = 0;
    public boolean renderExperienceOverlay = DEFAULT_RENDER_XP_OVERLAY;
    public boolean hideXpInCreative = DEFAULT_HIDE_XP_IN_CREATIVE;
    public boolean keepLocatorVisibleDuringXpGain = DEFAULT_KEEP_LOCATOR_DURING_XP;
    public boolean alwaysShowLocator = DEFAULT_ALWAYS_SHOW_LOCATOR;

    void sanitize() {
        locatorYOffset = Mth.clamp(locatorYOffset, MIN_OFFSET, MAX_OFFSET);
    }
}
