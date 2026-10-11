package yeah.im.liam.rpglocatorbar.config.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public final class Screens {
    private Screens() {}

    public static Screen current(Minecraft mc) {
        return mc.screen;
    }

    public static void open(Minecraft mc, Screen screen) {
        mc.setScreen(screen);
    }
}
