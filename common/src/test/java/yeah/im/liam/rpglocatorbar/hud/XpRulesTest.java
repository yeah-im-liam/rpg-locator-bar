package yeah.im.liam.rpglocatorbar.hud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class XpRulesTest {
    private static final boolean HIDE = true;
    private static final boolean SHOW = false;

    @Test
    void creativeBarFollowsTheSettingRegardlessOfLevel() {
        assertFalse(XpRules.barVisible(false, true, false, HIDE));
        assertTrue(XpRules.barVisible(false, true, false, SHOW));
    }

    @Test
    void survivalBarIsShownWhenTheModeHasExperience() {
        assertTrue(XpRules.barVisible(false, false, true, HIDE));
        assertFalse(XpRules.barVisible(false, false, false, HIDE));
    }

    @Test
    void spectatorNeverShowsTheBar() {
        assertFalse(XpRules.barVisible(true, false, false, SHOW));
        assertFalse(XpRules.barVisible(true, true, false, SHOW));
    }

    @Test
    void levelNumberNeedsALevelAboveZero() {
        assertFalse(XpRules.levelVisible(false, true, false, SHOW, 0));
        assertTrue(XpRules.levelVisible(false, true, false, SHOW, 12));
        assertFalse(XpRules.levelVisible(false, true, false, HIDE, 12));
        assertTrue(XpRules.levelVisible(false, false, true, HIDE, 7));
        assertFalse(XpRules.levelVisible(false, false, true, HIDE, 0));
        assertFalse(XpRules.levelVisible(true, false, false, SHOW, 7));
    }
}
