package yeah.im.liam.rpglocatorbar.neoforge.mixin.v1_21_6;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import yeah.im.liam.rpglocatorbar.hud.v1_21_6.ContextualBars;

// NeoForge draws each HUD layer in its own method instead of one renderHotbarAndDecorations.
@Mixin(Gui.class)
public abstract class GuiLayersMixin {
    @WrapOperation(
        method = "renderContextualInfoBar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"
        ),
        require = 0
    )
    private void rpglocatorbar$renderContextualBars(ContextualBarRenderer renderer, GuiGraphics guiGraphics, DeltaTracker deltaTracker, Operation<Void> original) {
        ContextualBars.INSTANCE.renderContextualBars(renderer, guiGraphics, deltaTracker, () -> original.call(renderer, guiGraphics, deltaTracker));
    }

    @WrapOperation(
        method = "renderExperienceLevel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;renderExperienceLevel(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;I)V"
        ),
        require = 0
    )
    private void rpglocatorbar$replaceVanillaLevel(GuiGraphics guiGraphics, Font font, int level, Operation<Void> original) {
        ContextualBars.INSTANCE.renderVanillaExperienceLevel(() -> original.call(guiGraphics, font, level));
    }
}
