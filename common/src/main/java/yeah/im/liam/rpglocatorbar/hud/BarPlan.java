package yeah.im.liam.rpglocatorbar.hud;

import yeah.im.liam.rpglocatorbar.config.LocatorConfig;

public record BarPlan(boolean renderActive, boolean drawExperience, boolean drawJump, boolean drawLocator) {
    public enum Active {
        LOCATOR,
        EXPERIENCE,
        JUMP,
        NONE
    }

    // Vanilla only shows the jump bar while a jump is charging once waypoints exist; here it stays up for the whole ride.
    public static BarPlan of(Active active, LocatorConfig config, boolean hasWaypoints, boolean xpVisible, boolean riding) {
        return switch (active) {
            case LOCATOR -> new BarPlan(true, !riding && config.renderExperienceOverlay && xpVisible, riding, false);
            case EXPERIENCE -> new BarPlan(!riding, false, riding, config.alwaysShowLocator || (config.keepLocatorVisibleDuringXpGain && hasWaypoints));
            case JUMP -> new BarPlan(true, false, false, config.alwaysShowLocator || hasWaypoints);
            case NONE -> new BarPlan(true, xpVisible, false, config.alwaysShowLocator);
        };
    }
}
