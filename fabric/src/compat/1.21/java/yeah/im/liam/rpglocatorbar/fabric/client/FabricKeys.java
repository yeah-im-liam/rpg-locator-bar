package yeah.im.liam.rpglocatorbar.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LegacyKeyMapping;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

final class FabricKeys {
    private FabricKeys() {}

    static KeyMapping register() {
        int unbound = InputConstants.UNKNOWN.getValue();
        KeyMapping key = LegacyKeyMapping.isNeeded()
            ? LegacyKeyMapping.create(LocatorKeys.NAME, unbound)
            : WithCategory.create(unbound);
        return KeyBindingHelper.registerKeyBinding(key);
    }

    // Kept in its own class so 1.21.6 to 1.21.8, which lack KeyMapping.Category, never load it.
    private static final class WithCategory {
        static KeyMapping create(int keyCode) {
            KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(RpgLocatorBar.MOD_ID, LocatorKeys.CATEGORY_PATH));
            return new KeyMapping(LocatorKeys.NAME, keyCode, category);
        }
    }
}
