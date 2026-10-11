package yeah.im.liam.rpglocatorbar.neoforge;

import java.util.function.Supplier;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.config.ui.LocatorConfigScreen;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

@Mod(value = RpgLocatorBar.MOD_ID, dist = Dist.CLIENT)
public final class RpgLocatorBarNeoForge {
    private static KeyMapping openConfigKey;

    public RpgLocatorBarNeoForge(IEventBus modEventBus, ModContainer container) {
        RpgLocatorBar.init(FMLPaths.CONFIGDIR.get());

        Supplier<IConfigScreenFactory> configScreenFactory = () -> (modContainer, parent) -> new LocatorConfigScreen(parent);
        container.registerExtensionPoint(IConfigScreenFactory.class, configScreenFactory);

        modEventBus.addListener(RpgLocatorBarNeoForge::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(RpgLocatorBarNeoForge::onClientTick);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        // NeoForge sorts modded categories itself, so KeyMapping.Category.register is not used here.
        KeyMapping.Category category = new KeyMapping.Category(LocatorKeys.CATEGORY_ID);
        event.registerCategory(category);
        openConfigKey = LocatorKeys.createOpenConfigKey(category);
        event.register(openConfigKey);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        if (openConfigKey != null) {
            LocatorKeys.openConfigIfPressed(openConfigKey);
        }
    }
}
