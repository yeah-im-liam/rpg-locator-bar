package yeah.im.liam.rpglocatorbar.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

final class FabricKeys {
    private FabricKeys() {}

    static KeyMapping register() {
        KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(RpgLocatorBar.MOD_ID, LocatorKeys.CATEGORY_PATH));
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(LocatorKeys.NAME, InputConstants.UNKNOWN.getValue(), category));
    }
}
