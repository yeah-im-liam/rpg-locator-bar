package yeah.im.liam.rpglocatorbar.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.config.ui.LocatorConfigScreen;

public final class LocatorKeys {
    public static final Identifier CATEGORY_ID = Identifier.fromNamespaceAndPath(RpgLocatorBar.MOD_ID, "key_category");

    private LocatorKeys() {}

    public static KeyMapping createOpenConfigKey(KeyMapping.Category category) {
        return new KeyMapping(
            "key.rpglocatorbar.config_menu",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            category
        );
    }

    public static void openConfigIfPressed(KeyMapping key) {
        Minecraft mc = Minecraft.getInstance();
        while (key.consumeClick()) {
            if (mc.player != null) {
                mc.setScreen(new LocatorConfigScreen(mc.screen));
            }
        }
    }
}
