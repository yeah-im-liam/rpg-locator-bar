package yeah.im.liam.rpglocatorbar.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.level.GameType;
import yeah.im.liam.rpglocatorbar.config.LocatorConfig;
import yeah.im.liam.rpglocatorbar.config.LocatorConfigManager;

/**
 * Draws the contextual bars. Subclasses adapt it to one HUD layout: G is the graphics type and B the bar type.
 */
public abstract class LocatorHud<G, B> {
    private B experienceBar;
    private B locatorBar;
    private B jumpBar;
    private PlayerRideableJumping jumpBarVehicle;
    // Vanilla's level draw is only skipped once this hook has run at least once.
    private boolean barsHooked;

    public final void renderContextualBars(B active, G graphics, DeltaTracker deltaTracker, Runnable renderActive) {
        barsHooked = true;
        Minecraft mc = Minecraft.getInstance();
        LocatorConfig config = LocatorConfigManager.get();
        PlayerRideableJumping jumpable = mc.player != null ? mc.player.jumpableVehicle() : null;
        if (jumpable == null) {
            jumpBar = null;
            jumpBarVehicle = null;
        }
        BarPlan plan = BarPlan.of(classify(active), config, hasWaypoints(mc), isExperienceBarVisible(mc, config), jumpable != null);

        if (plan.drawExperience()) {
            drawBar(experienceBar(mc), graphics, deltaTracker);
        }
        if (plan.drawJump()) {
            drawBar(jumpBar(mc, jumpable), graphics, deltaTracker);
        }
        if (plan.renderActive()) {
            renderActive.run();
        }
        if (plan.drawLocator()) {
            drawBar(locatorBar(mc), graphics, deltaTracker);
        }
        if (isExperienceLevelVisible(mc, config)) {
            drawExperienceLevel(graphics, mc.font, mc.player.experienceLevel);
        }
    }

    public final void renderVanillaExperienceLevel(Runnable vanillaDraw) {
        if (!barsHooked) {
            vanillaDraw.run();
        }
    }

    public static int locatorTop(int guiScaledHeight, int barHeight, int marginBottom) {
        int desiredTop = LocatorConfig.DEFAULT_OFFSET + LocatorConfigManager.get().locatorYOffset;
        int maxTop = guiScaledHeight - 2 * barHeight - marginBottom;
        return Mth.clamp(desiredTop, 0, maxTop);
    }

    protected abstract BarPlan.Active classify(B bar);

    protected abstract B createExperienceBar(Minecraft mc);

    protected abstract B createJumpBar(Minecraft mc);

    protected abstract B createLocatorBar(Minecraft mc);

    protected abstract void drawBar(B bar, G graphics, DeltaTracker deltaTracker);

    protected abstract void drawExperienceLevel(G graphics, Font font, int level);

    private B experienceBar(Minecraft mc) {
        if (experienceBar == null) {
            experienceBar = createExperienceBar(mc);
        }
        return experienceBar;
    }

    // The jump bar reads the vehicle when it is created, so it is rebuilt for each new vehicle.
    private B jumpBar(Minecraft mc, PlayerRideableJumping vehicle) {
        if (jumpBar == null || jumpBarVehicle != vehicle) {
            jumpBar = createJumpBar(mc);
            jumpBarVehicle = vehicle;
        }
        return jumpBar;
    }

    private B locatorBar(Minecraft mc) {
        if (locatorBar == null) {
            locatorBar = createLocatorBar(mc);
        }
        return locatorBar;
    }

    private static boolean hasWaypoints(Minecraft mc) {
        return mc.player != null && mc.player.connection.getWaypointManager().hasWaypoints();
    }

    private static boolean isExperienceBarVisible(Minecraft mc, LocatorConfig config) {
        if (mc.player == null || mc.gameMode == null) {
            return false;
        }
        return XpRules.barVisible(mc.player.isSpectator(), isCreative(mc), mc.gameMode.hasExperience(), config.hideXpInCreative);
    }

    private static boolean isExperienceLevelVisible(Minecraft mc, LocatorConfig config) {
        if (mc.player == null || mc.gameMode == null) {
            return false;
        }
        return XpRules.levelVisible(mc.player.isSpectator(), isCreative(mc), mc.gameMode.hasExperience(),
            config.hideXpInCreative, mc.player.experienceLevel);
    }

    private static boolean isCreative(Minecraft mc) {
        return mc.gameMode.getPlayerMode() == GameType.CREATIVE;
    }
}
