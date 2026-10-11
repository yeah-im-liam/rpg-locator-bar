package yeah.im.liam.rpglocatorbar.mixin.v26_1;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.contextualbar.LocatorBarRenderer;
import org.spongepowered.asm.mixin.Mixin;

import yeah.im.liam.rpglocatorbar.hud.LocatorHud;

@Mixin(LocatorBarRenderer.class)
public abstract class LocatorBarRendererMixin implements ContextualBarRenderer {
    @Override
    public int top(Window window) {
        return LocatorHud.locatorTop(window.getGuiScaledHeight(), ContextualBarRenderer.HEIGHT, ContextualBarRenderer.MARGIN_BOTTOM);
    }
}
