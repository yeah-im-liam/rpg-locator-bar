package yeah.im.liam.rpglocatorbar.neoforge.mixin.v26_2;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import yeah.im.liam.rpglocatorbar.hud.v26_2.ContextualBars;

// NeoForge draws each HUD layer in its own method instead of one extractHotbarAndDecorations.
@Mixin(Hud.class)
public abstract class HudLayersMixin {
    @WrapOperation(
        method = "extractContextualInfoBar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/contextualbar/ContextualBar;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V"
        ),
        require = 0
    )
    private void rpglocatorbar$renderContextualBars(ContextualBar bar, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Operation<Void> original) {
        ContextualBars.INSTANCE.renderContextualBars(bar, graphics, deltaTracker, () -> original.call(bar, graphics, deltaTracker));
    }

    @WrapOperation(
        method = "extractExperienceLevel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/contextualbar/ContextualBar;extractExperienceLevel(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;I)V"
        ),
        require = 0
    )
    private void rpglocatorbar$replaceVanillaLevel(GuiGraphicsExtractor graphics, Font font, int level, Operation<Void> original) {
        ContextualBars.INSTANCE.renderVanillaExperienceLevel(() -> original.call(graphics, font, level));
    }
}
