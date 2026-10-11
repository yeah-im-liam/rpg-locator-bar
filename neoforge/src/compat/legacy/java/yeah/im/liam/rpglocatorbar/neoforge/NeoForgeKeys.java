package yeah.im.liam.rpglocatorbar.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

final class NeoForgeKeys {
    private NeoForgeKeys() {}

    static KeyMapping register(RegisterKeyMappingsEvent event) {
        KeyMapping key = new KeyMapping(LocatorKeys.NAME, InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "key.categories." + RpgLocatorBar.MOD_ID);
        event.register(key);
        return key;
    }
}
