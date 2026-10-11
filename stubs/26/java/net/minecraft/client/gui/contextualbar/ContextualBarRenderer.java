package net.minecraft.client.gui.contextualbar;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

// Compile-only copy of the 26.1 API (renamed to ContextualBar in 26.2). Not packaged.
public interface ContextualBarRenderer {
    int WIDTH = 182;
    int HEIGHT = 5;
    int MARGIN_BOTTOM = 24;
    ContextualBarRenderer EMPTY = null;

    default int left(Window window) {
        throw new AssertionError();
    }

    default int top(Window window) {
        throw new AssertionError();
    }

    void extractBackground(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);

    void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);

    static void extractExperienceLevel(GuiGraphicsExtractor graphics, Font font, int level) {
        throw new AssertionError();
    }
}
