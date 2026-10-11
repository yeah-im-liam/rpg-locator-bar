package yeah.im.liam.rpglocatorbar.mixin.v26_2;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import net.minecraft.client.gui.contextualbar.LocatorBar;
import org.spongepowered.asm.mixin.Mixin;

import yeah.im.liam.rpglocatorbar.hud.LocatorHud;

@Mixin(LocatorBar.class)
public abstract class LocatorBarMixin implements ContextualBar {
    @Override
    public int top(Window window) {
        return LocatorHud.locatorTop(window.getGuiScaledHeight(), ContextualBar.HEIGHT, ContextualBar.MARGIN_BOTTOM);
    }
}
