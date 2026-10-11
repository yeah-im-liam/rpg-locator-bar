package yeah.im.liam.rpglocatorbar.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import yeah.im.liam.rpglocatorbar.config.ui.LocatorConfigScreen;

public final class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return LocatorConfigScreen::new;
    }
}
