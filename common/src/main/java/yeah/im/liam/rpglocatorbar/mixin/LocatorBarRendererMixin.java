package yeah.im.liam.rpglocatorbar.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.contextualbar.LocatorBarRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;

import yeah.im.liam.rpglocatorbar.config.LocatorConfig;
import yeah.im.liam.rpglocatorbar.config.LocatorConfigManager;

@Mixin(LocatorBarRenderer.class)
public abstract class LocatorBarRendererMixin implements ContextualBarRenderer {
    @Override
    public int top(Window window) {
        int desiredTop = LocatorConfig.DEFAULT_OFFSET + LocatorConfigManager.get().locatorYOffset;
        int maxTop = window.getGuiScaledHeight() - 2 * ContextualBarRenderer.HEIGHT - ContextualBarRenderer.MARGIN_BOTTOM;
        return Mth.clamp(desiredTop, 0, maxTop);
    }
}
