package yeah.im.liam.rpglocatorbar.hud.v26_2;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import net.minecraft.client.gui.contextualbar.ExperienceBar;
import net.minecraft.client.gui.contextualbar.JumpableVehicleBar;
import net.minecraft.client.gui.contextualbar.LocatorBar;
import yeah.im.liam.rpglocatorbar.hud.BarPlan;
import yeah.im.liam.rpglocatorbar.hud.LocatorHud;

public final class ContextualBars extends LocatorHud<GuiGraphicsExtractor, ContextualBar> {
    public static final ContextualBars INSTANCE = new ContextualBars();

    private ContextualBars() {}

    @Override
    protected BarPlan.Active classify(ContextualBar bar) {
        if (bar instanceof LocatorBar) {
            return BarPlan.Active.LOCATOR;
        }
        if (bar instanceof ExperienceBar) {
            return BarPlan.Active.EXPERIENCE;
        }
        if (bar == ContextualBar.EMPTY) {
            return BarPlan.Active.NONE;
        }
        return BarPlan.Active.JUMP;
    }

    @Override
    protected ContextualBar createExperienceBar(Minecraft mc) {
        return new ExperienceBar(mc);
    }

    @Override
    protected ContextualBar createJumpBar(Minecraft mc) {
        return new JumpableVehicleBar(mc);
    }

    @Override
    protected ContextualBar createLocatorBar(Minecraft mc) {
        return new LocatorBar(mc);
    }

    @Override
    protected void drawBar(ContextualBar bar, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        bar.extractBackground(graphics, deltaTracker);
        bar.extractRenderState(graphics, deltaTracker);
    }

    @Override
    protected void drawExperienceLevel(GuiGraphicsExtractor graphics, Font font, int level) {
        graphics.nextStratum();
        ContextualBar.extractExperienceLevel(graphics, font, level);
    }
}
