package yeah.im.liam.rpglocatorbar.hud.v1_21_6;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import net.minecraft.client.gui.contextualbar.JumpableVehicleBarRenderer;
import net.minecraft.client.gui.contextualbar.LocatorBarRenderer;
import yeah.im.liam.rpglocatorbar.hud.BarPlan;
import yeah.im.liam.rpglocatorbar.hud.LocatorHud;

public final class ContextualBars extends LocatorHud<GuiGraphics, ContextualBarRenderer> {
    public static final ContextualBars INSTANCE = new ContextualBars();

    private ContextualBars() {}

    @Override
    protected BarPlan.Active classify(ContextualBarRenderer bar) {
        if (bar instanceof LocatorBarRenderer) {
            return BarPlan.Active.LOCATOR;
        }
        if (bar instanceof ExperienceBarRenderer) {
            return BarPlan.Active.EXPERIENCE;
        }
        if (bar == ContextualBarRenderer.EMPTY) {
            return BarPlan.Active.NONE;
        }
        return BarPlan.Active.JUMP;
    }

    @Override
    protected ContextualBarRenderer createExperienceBar(Minecraft mc) {
        return new ExperienceBarRenderer(mc);
    }

    @Override
    protected ContextualBarRenderer createJumpBar(Minecraft mc) {
        return new JumpableVehicleBarRenderer(mc);
    }

    @Override
    protected ContextualBarRenderer createLocatorBar(Minecraft mc) {
        return new LocatorBarRenderer(mc);
    }

    @Override
    protected void drawBar(ContextualBarRenderer bar, GuiGraphics graphics, DeltaTracker deltaTracker) {
        bar.renderBackground(graphics, deltaTracker);
        bar.render(graphics, deltaTracker);
    }

    @Override
    protected void drawExperienceLevel(GuiGraphics graphics, Font font, int level) {
        graphics.nextStratum();
        ContextualBarRenderer.renderExperienceLevel(graphics, font, level);
    }
}
