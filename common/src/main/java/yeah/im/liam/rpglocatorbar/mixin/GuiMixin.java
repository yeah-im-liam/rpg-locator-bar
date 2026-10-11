package yeah.im.liam.rpglocatorbar.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Redirect;

import yeah.im.liam.rpglocatorbar.hud.LocatorHud;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Redirect(
        method = "renderHotbarAndDecorations",
        at = @At(
            value = "INVOKE",
            desc = @Desc(owner = ContextualBarRenderer.class, value = "render", args = { GuiGraphics.class, DeltaTracker.class })
        ),
        require = 0
    )
    private void rpglocatorbar$renderContextualBars(ContextualBarRenderer renderer, GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        LocatorHud.renderContextualBars(renderer, guiGraphics, deltaTracker);
    }

    @Redirect(
        method = "renderHotbarAndDecorations",
        at = @At(
            value = "INVOKE",
            desc = @Desc(owner = ContextualBarRenderer.class, value = "renderExperienceLevel", args = { GuiGraphics.class, Font.class, int.class })
        ),
        require = 0
    )
    private void rpglocatorbar$replaceVanillaLevel(GuiGraphics guiGraphics, Font font, int level) {
        LocatorHud.renderVanillaExperienceLevel(guiGraphics, font, level);
    }
}
