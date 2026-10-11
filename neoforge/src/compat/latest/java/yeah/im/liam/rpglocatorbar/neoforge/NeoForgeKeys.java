package yeah.im.liam.rpglocatorbar.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

final class NeoForgeKeys {
    private NeoForgeKeys() {}

    // NeoForge sorts modded categories itself, so KeyMapping.Category.register is not used here.
    static KeyMapping register(RegisterKeyMappingsEvent event) {
        KeyMapping.Category category = new KeyMapping.Category(Identifier.fromNamespaceAndPath(RpgLocatorBar.MOD_ID, LocatorKeys.CATEGORY_PATH));
        event.registerCategory(category);
        KeyMapping key = new KeyMapping(LocatorKeys.NAME, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), category);
        event.register(key);
        return key;
    }
}
