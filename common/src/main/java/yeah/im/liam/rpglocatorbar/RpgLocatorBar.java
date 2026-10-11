package yeah.im.liam.rpglocatorbar;

import java.nio.file.Path;

import yeah.im.liam.rpglocatorbar.config.LocatorConfigManager;

public final class RpgLocatorBar {
    public static final String MOD_ID = "rpglocatorbar";

    private RpgLocatorBar() {}

    public static void init(Path configDir) {
        LocatorConfigManager.init(configDir);
    }
}
