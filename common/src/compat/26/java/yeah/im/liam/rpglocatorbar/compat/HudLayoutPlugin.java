package yeah.im.liam.rpglocatorbar.compat;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/**
 * Picks the mixins for the running game: 26.2 moved the HUD from Gui into Hud and renamed the contextual bar classes.
 */
public final class HudLayoutPlugin implements IMixinConfigPlugin {
    private static final String V26_1 = ".v26_1.";
    private static final String V26_2 = ".v26_2.";

    private boolean hudClass;

    @Override
    public void onLoad(String mixinPackage) {
        hudClass = HudLayoutPlugin.class.getClassLoader().getResource("net/minecraft/client/gui/Hud.class") != null;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains(V26_1)) {
            return !hudClass;
        }
        if (mixinClassName.contains(V26_2)) {
            return hudClass;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
