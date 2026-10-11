package yeah.im.liam.rpglocatorbar.hud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import yeah.im.liam.rpglocatorbar.config.LocatorConfig;
import yeah.im.liam.rpglocatorbar.hud.BarPlan.Active;

class BarPlanTest {
    private static LocatorConfig config(boolean overlay, boolean keepDuringXp, boolean always) {
        LocatorConfig config = new LocatorConfig();
        config.renderExperienceOverlay = overlay;
        config.keepLocatorVisibleDuringXpGain = keepDuringXp;
        config.alwaysShowLocator = always;
        return config;
    }

    private static void assertPlan(BarPlan plan, boolean renderActive, boolean experience, boolean jump, boolean locator) {
        assertEquals(new BarPlan(renderActive, experience, jump, locator), plan);
    }

    @Test
    void locatorShowsExperienceUnderneathWhenVisible() {
        LocatorConfig defaults = new LocatorConfig();
        assertPlan(BarPlan.of(Active.LOCATOR, defaults, true, true, false), true, true, false, false);
        assertPlan(BarPlan.of(Active.LOCATOR, defaults, true, false, false), true, false, false, false);
        assertPlan(BarPlan.of(Active.LOCATOR, config(false, true, false), true, true, false), true, false, false, false);
    }

    @Test
    void experienceKeepsLocatorAccordingToSettings() {
        assertPlan(BarPlan.of(Active.EXPERIENCE, new LocatorConfig(), true, true, false), true, false, false, true);
        assertPlan(BarPlan.of(Active.EXPERIENCE, new LocatorConfig(), false, true, false), true, false, false, false);
        assertPlan(BarPlan.of(Active.EXPERIENCE, config(true, false, false), true, true, false), true, false, false, false);
        assertPlan(BarPlan.of(Active.EXPERIENCE, config(true, false, true), false, true, false), true, false, false, true);
    }

    @Test
    void nothingFromVanillaStillShowsConfiguredBars() {
        LocatorConfig defaults = new LocatorConfig();
        assertPlan(BarPlan.of(Active.NONE, defaults, false, true, false), true, true, false, false);
        assertPlan(BarPlan.of(Active.NONE, defaults, false, false, false), true, false, false, false);
        assertPlan(BarPlan.of(Active.NONE, config(true, true, true), false, false, false), true, false, false, true);
        assertPlan(BarPlan.of(Active.NONE, config(true, true, true), false, true, false), true, true, false, true);
    }

    @Test
    void jumpBarStaysVisibleWhileRidingEvenWhenVanillaShowsTheLocator() {
        LocatorConfig defaults = new LocatorConfig();
        assertPlan(BarPlan.of(Active.LOCATOR, defaults, true, true, true), true, false, true, false);
        assertPlan(BarPlan.of(Active.LOCATOR, config(false, true, false), true, true, true), true, false, true, false);
    }

    @Test
    void jumpBarReplacesTheExperienceFlashWhileRiding() {
        LocatorConfig defaults = new LocatorConfig();
        assertPlan(BarPlan.of(Active.EXPERIENCE, defaults, true, true, true), false, false, true, true);
        assertPlan(BarPlan.of(Active.EXPERIENCE, defaults, false, true, true), false, false, true, false);
    }

    @Test
    void experienceIsNeverDrawnOverTheJumpBar() {
        assertPlan(BarPlan.of(Active.JUMP, new LocatorConfig(), false, true, true), true, false, false, false);
        assertPlan(BarPlan.of(Active.JUMP, new LocatorConfig(), true, true, true), true, false, false, true);
        assertPlan(BarPlan.of(Active.JUMP, config(true, true, true), false, true, true), true, false, false, true);
    }

    @Test
    void invariantsHoldForEveryInput() {
        for (Active active : Active.values()) {
            for (int bits = 0; bits < 64; bits++) {
                LocatorConfig config = config((bits & 1) != 0, (bits & 2) != 0, (bits & 4) != 0);
                boolean waypoints = (bits & 8) != 0;
                boolean xpVisible = (bits & 16) != 0;
                boolean riding = (bits & 32) != 0;
                if (active == Active.NONE && riding) {
                    continue;
                }
                BarPlan plan = BarPlan.of(active, config, waypoints, xpVisible, riding);
                String context = active + " bits=" + bits;

                if (active == Active.LOCATOR) {
                    assertFalse(plan.drawLocator(), "locator drawn twice: " + context);
                }
                if (active == Active.EXPERIENCE) {
                    assertFalse(plan.drawExperience(), "experience drawn twice: " + context);
                }
                if (active == Active.JUMP) {
                    assertFalse(plan.drawJump() || plan.drawExperience(), "bar drawn over the jump bar: " + context);
                }
                assertFalse(plan.drawJump() && plan.drawExperience(), "jump and experience share a slot: " + context);
                assertEquals(riding && active != Active.JUMP, plan.drawJump(), "jump bar rule: " + context);
                assertFalse(!plan.renderActive() && !plan.drawJump(), "active bar skipped without a replacement: " + context);
            }
        }
    }
}
