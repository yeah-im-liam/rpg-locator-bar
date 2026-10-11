package yeah.im.liam.rpglocatorbar.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import net.minecraft.client.gui.contextualbar.JumpableVehicleBarRenderer;
import net.minecraft.client.gui.contextualbar.LocatorBarRenderer;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.level.GameType;
import yeah.im.liam.rpglocatorbar.config.LocatorConfig;
import yeah.im.liam.rpglocatorbar.config.LocatorConfigManager;

public final class LocatorHud {
    private static ExperienceBarRenderer experienceRenderer;
    private static LocatorBarRenderer locatorRenderer;
    private static JumpableVehicleBarRenderer jumpRenderer;
    private static PlayerRideableJumping jumpRendererVehicle;
    // Vanilla's level draw is only skipped once this hook has run at least once.
    private static boolean barsHooked;

    private LocatorHud() {}

    public static void renderContextualBars(ContextualBarRenderer active, GuiGraphics graphics, DeltaTracker deltaTracker) {
        barsHooked = true;
        Minecraft mc = Minecraft.getInstance();
        LocatorConfig config = LocatorConfigManager.get();
        PlayerRideableJumping jumpable = mc.player != null ? mc.player.jumpableVehicle() : null;
        if (jumpable == null) {
            jumpRenderer = null;
            jumpRendererVehicle = null;
        }
        BarPlan plan = BarPlan.of(classify(active), config, hasWaypoints(mc), isExperienceBarVisible(mc, config), jumpable != null);

        if (plan.drawExperience()) {
            drawBar(experience(mc), graphics, deltaTracker);
        }
        if (plan.drawJump()) {
            drawBar(jump(mc, jumpable), graphics, deltaTracker);
        }
        if (plan.renderActive()) {
            active.render(graphics, deltaTracker);
        }
        if (plan.drawLocator()) {
            drawBar(locator(mc), graphics, deltaTracker);
        }
        renderExperienceLevel(mc, config, graphics);
    }

    public static void renderVanillaExperienceLevel(GuiGraphics graphics, Font font, int level) {
        if (!barsHooked) {
            ContextualBarRenderer.renderExperienceLevel(graphics, font, level);
        }
    }

    private static void renderExperienceLevel(Minecraft mc, LocatorConfig config, GuiGraphics graphics) {
        if (!isExperienceLevelVisible(mc, config)) {
            return;
        }
        graphics.nextStratum();
        ContextualBarRenderer.renderExperienceLevel(graphics, mc.font, mc.player.experienceLevel);
    }

    private static BarPlan.Active classify(ContextualBarRenderer renderer) {
        if (renderer instanceof LocatorBarRenderer) {
            return BarPlan.Active.LOCATOR;
        }
        if (renderer instanceof ExperienceBarRenderer) {
            return BarPlan.Active.EXPERIENCE;
        }
        if (renderer == ContextualBarRenderer.EMPTY) {
            return BarPlan.Active.NONE;
        }
        return BarPlan.Active.JUMP;
    }

    private static void drawBar(ContextualBarRenderer renderer, GuiGraphics graphics, DeltaTracker deltaTracker) {
        renderer.renderBackground(graphics, deltaTracker);
        renderer.render(graphics, deltaTracker);
    }

    private static ExperienceBarRenderer experience(Minecraft mc) {
        if (experienceRenderer == null) {
            experienceRenderer = new ExperienceBarRenderer(mc);
        }
        return experienceRenderer;
    }

    private static JumpableVehicleBarRenderer jump(Minecraft mc, PlayerRideableJumping vehicle) {
        if (jumpRenderer == null || jumpRendererVehicle != vehicle) {
            jumpRenderer = new JumpableVehicleBarRenderer(mc);
            jumpRendererVehicle = vehicle;
        }
        return jumpRenderer;
    }

    private static LocatorBarRenderer locator(Minecraft mc) {
        if (locatorRenderer == null) {
            locatorRenderer = new LocatorBarRenderer(mc);
        }
        return locatorRenderer;
    }

    private static boolean hasWaypoints(Minecraft mc) {
        return mc.player != null && mc.player.connection.getWaypointManager().hasWaypoints();
    }

    private static boolean isExperienceBarVisible(Minecraft mc, LocatorConfig config) {
        if (mc.player == null || mc.gameMode == null || mc.options.hideGui) {
            return false;
        }
        return XpRules.barVisible(mc.player.isSpectator(), isCreative(mc), mc.gameMode.hasExperience(),
            config.hideXpInCreative, mc.player.experienceLevel);
    }

    private static boolean isExperienceLevelVisible(Minecraft mc, LocatorConfig config) {
        if (mc.player == null || mc.gameMode == null || mc.options.hideGui) {
            return false;
        }
        return XpRules.levelVisible(mc.player.isSpectator(), isCreative(mc), mc.gameMode.hasExperience(),
            config.hideXpInCreative, mc.player.experienceLevel);
    }

    private static boolean isCreative(Minecraft mc) {
        return mc.gameMode.getPlayerMode() == GameType.CREATIVE;
    }
}
