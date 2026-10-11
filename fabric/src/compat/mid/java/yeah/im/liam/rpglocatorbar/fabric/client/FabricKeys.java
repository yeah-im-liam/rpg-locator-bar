package yeah.im.liam.rpglocatorbar.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

final class FabricKeys {
    private FabricKeys() {}

    static KeyMapping register() {
        KeyMapping.Category category = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(RpgLocatorBar.MOD_ID, LocatorKeys.CATEGORY_PATH));
        return KeyBindingHelper.registerKeyBinding(
            new KeyMapping(LocatorKeys.NAME, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), category)
        );
    }
}
