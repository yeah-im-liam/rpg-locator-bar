package yeah.im.liam.rpglocatorbar.config.ui;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;

/**
 * 26.2 moved the open screen from Minecraft to Gui. 26.1 is reached by name, which is safe because 26.x is not obfuscated.
 */
public final class Screens {
    private static final boolean ON_GUI = hasGuiScreens();

    private Screens() {}

    public static Screen current(Minecraft mc) {
        return ON_GUI ? mc.gui.screen() : Minecraft26_1.current(mc);
    }

    public static void open(Minecraft mc, Screen screen) {
        if (ON_GUI) {
            mc.gui.setScreen(screen);
        } else {
            Minecraft26_1.open(mc, screen);
        }
    }

    private static boolean hasGuiScreens() {
        try {
            Gui.class.getMethod("setScreen", Screen.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static final class Minecraft26_1 {
        private static final MethodHandle SCREEN;
        private static final MethodHandle SET_SCREEN;

        static {
            try {
                MethodHandles.Lookup lookup = MethodHandles.publicLookup();
                SCREEN = lookup.findGetter(Minecraft.class, "screen", Screen.class);
                SET_SCREEN = lookup.findVirtual(Minecraft.class, "setScreen", MethodType.methodType(void.class, Screen.class));
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        static Screen current(Minecraft mc) {
            try {
                return (Screen) SCREEN.invokeExact(mc);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }

        static void open(Minecraft mc, Screen screen) {
            try {
                SET_SCREEN.invokeExact(mc, screen);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
