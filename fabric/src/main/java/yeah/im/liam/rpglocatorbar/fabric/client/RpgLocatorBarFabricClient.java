package yeah.im.liam.rpglocatorbar.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

public final class RpgLocatorBarFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RpgLocatorBar.init(FabricLoader.getInstance().getConfigDir());

        KeyMapping.Category category = KeyMapping.Category.register(LocatorKeys.CATEGORY_ID);
        KeyMapping openConfigKey = KeyBindingHelper.registerKeyBinding(LocatorKeys.createOpenConfigKey(category));
        ClientTickEvents.END_CLIENT_TICK.register(client -> LocatorKeys.openConfigIfPressed(openConfigKey));
    }
}
