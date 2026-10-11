package yeah.im.liam.rpglocatorbar.input;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import yeah.im.liam.rpglocatorbar.config.ui.LocatorConfigScreen;
import yeah.im.liam.rpglocatorbar.config.ui.Screens;

public final class LocatorKeys {
    public static final String NAME = "key.rpglocatorbar.config_menu";
    public static final String CATEGORY_PATH = "key_category";

    private LocatorKeys() {}

    public static void openConfigIfPressed(KeyMapping key) {
        Minecraft mc = Minecraft.getInstance();
        while (key.consumeClick()) {
            if (mc.player != null) {
                Screens.open(mc, new LocatorConfigScreen(Screens.current(mc)));
            }
        }
    }
}
