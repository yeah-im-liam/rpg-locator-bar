package yeah.im.liam.rpglocatorbar.input;

import java.lang.reflect.Constructor;

import net.minecraft.client.KeyMapping;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;

/**
 * Minecraft 1.21.6 to 1.21.8 name a key category with a translation key; 1.21.9 replaced it with KeyMapping.Category.
 */
public final class LegacyKeyMapping {
    private static final String CATEGORY = "key.categories." + RpgLocatorBar.MOD_ID;
    private static final Constructor<KeyMapping> CONSTRUCTOR = findConstructor();

    private LegacyKeyMapping() {}

    public static boolean isNeeded() {
        return CONSTRUCTOR != null;
    }

    public static KeyMapping create(String name, int keyCode) {
        try {
            return CONSTRUCTOR.newInstance(name, keyCode, CATEGORY);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Could not create key mapping " + name, e);
        }
    }

    private static Constructor<KeyMapping> findConstructor() {
        try {
            return KeyMapping.class.getConstructor(String.class, int.class, String.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
