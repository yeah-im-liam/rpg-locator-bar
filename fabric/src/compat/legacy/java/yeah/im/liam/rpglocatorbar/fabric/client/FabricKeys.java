package yeah.im.liam.rpglocatorbar.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

final class FabricKeys {
    private FabricKeys() {}

    static KeyMapping register() {
        return KeyBindingHelper.registerKeyBinding(
            new KeyMapping(LocatorKeys.NAME, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "key.categories." + RpgLocatorBar.MOD_ID)
        );
    }
}
