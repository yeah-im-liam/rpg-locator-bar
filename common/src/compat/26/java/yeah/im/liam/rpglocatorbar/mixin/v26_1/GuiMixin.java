package yeah.im.liam.rpglocatorbar.mixin.v26_1;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import yeah.im.liam.rpglocatorbar.hud.v26_1.ContextualBars;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @WrapOperation(
        method = "extractHotbarAndDecorations",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V"
        ),
        require = 0
    )
    private void rpglocatorbar$renderContextualBars(ContextualBarRenderer bar, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Operation<Void> original) {
        ContextualBars.INSTANCE.renderContextualBars(bar, graphics, deltaTracker, () -> original.call(bar, graphics, deltaTracker));
    }

    @WrapOperation(
        method = "extractHotbarAndDecorations",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractExperienceLevel(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;I)V"
        ),
        require = 0
    )
    private void rpglocatorbar$replaceVanillaLevel(GuiGraphicsExtractor graphics, Font font, int level, Operation<Void> original) {
        ContextualBars.INSTANCE.renderVanillaExperienceLevel(() -> original.call(graphics, font, level));
    }
}
