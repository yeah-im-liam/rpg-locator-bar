package yeah.im.liam.rpglocatorbar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class LocatorConfigManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static LocatorConfig config;
    private static Path configPath;

    private LocatorConfigManager() {}

    public static synchronized void init(Path configDir) {
        configPath = configDir.resolve("rpglocatorbar.json");
        load();
    }

    public static synchronized LocatorConfig get() {
        if (config == null) {
            load();
        }
        return config;
    }

    public static synchronized void save() {
        if (configPath == null) {
            return;
        }

        try {
            Files.createDirectories(configPath.getParent());
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(get(), writer);
            }
        } catch (IOException e) {
            LOGGER.warn("Could not save {}", configPath, e);
        }
    }

    private static void load() {
        if (configPath != null && Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                LocatorConfig loaded = GSON.fromJson(reader, LocatorConfig.class);
                if (loaded != null) {
                    loaded.sanitize();
                    config = loaded;
                    return;
                }
            } catch (IOException | JsonParseException e) {
                LOGGER.warn("Could not read {}, using defaults", configPath, e);
            }
        }

        config = new LocatorConfig();
    }
}
