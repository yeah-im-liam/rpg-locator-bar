package yeah.im.liam.rpglocatorbar.neoforge;

import java.lang.reflect.Constructor;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import yeah.im.liam.rpglocatorbar.RpgLocatorBar;
import yeah.im.liam.rpglocatorbar.input.LegacyKeyMapping;
import yeah.im.liam.rpglocatorbar.input.LocatorKeys;

final class NeoForgeKeys {
    private NeoForgeKeys() {}

    static KeyMapping register(RegisterKeyMappingsEvent event) {
        int unbound = InputConstants.UNKNOWN.getValue();
        KeyMapping key = LegacyKeyMapping.isNeeded()
            ? LegacyKeyMapping.create(LocatorKeys.NAME, unbound)
            : WithCategory.create(event, unbound);
        event.register(key);
        return key;
    }

    // Kept in its own class so 1.21.6 to 1.21.8, which lack KeyMapping.Category, never load it.
    // NeoForge sorts modded categories itself, so KeyMapping.Category.register is not used here.
    private static final class WithCategory {
        static KeyMapping create(RegisterKeyMappingsEvent event, int keyCode) {
            KeyMapping.Category category = newCategory();
            event.registerCategory(category);
            return new KeyMapping(LocatorKeys.NAME, keyCode, category);
        }

        // The category id is a ResourceLocation before 1.21.11 and an Identifier from then on.
        private static KeyMapping.Category newCategory() {
            try {
                Constructor<?> constructor = KeyMapping.Category.class.getConstructors()[0];
                Class<?> idType = constructor.getParameterTypes()[0];
                Object id = idType.getMethod("fromNamespaceAndPath", String.class, String.class)
                    .invoke(null, RpgLocatorBar.MOD_ID, LocatorKeys.CATEGORY_PATH);
                return (KeyMapping.Category) constructor.newInstance(id);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Could not create the key category", e);
            }
        }
    }
}
