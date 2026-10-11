package yeah.im.liam.rpglocatorbar.config.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import yeah.im.liam.rpglocatorbar.config.LocatorConfig;
import yeah.im.liam.rpglocatorbar.config.LocatorConfigManager;

public final class LocatorConfigScreen extends Screen {
    private static final int MIN_OFFSET_PX = LocatorConfig.MIN_OFFSET;
    private static final int MAX_OFFSET_PX = LocatorConfig.MAX_OFFSET;
    private static final int SAVE_DELAY_TICKS = 10;
    private final Screen parent;
    private LocatorOffsetSlider offsetSlider;
    private CycleButton<Boolean> xpToggle;
    private CycleButton<Boolean> hideXpCreativeToggle;
    private CycleButton<Boolean> locatorDuringXpToggle;
    private CycleButton<Boolean> locatorAlwaysOnToggle;
    private int saveCountdown = -1;

    public LocatorConfigScreen(Screen parent) {
        super(Component.translatable("rpglocatorbar.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        LocatorConfig config = LocatorConfigManager.get();
        int center = this.width / 2;
        int y = Math.max(20, this.height / 4 - 24);

        offsetSlider = new LocatorOffsetSlider(center - 100, y, 200, 20, config.locatorYOffset, () -> saveCountdown = SAVE_DELAY_TICKS);
        addRenderableWidget(offsetSlider);

        y += 26;
        xpToggle = CycleButton.onOffBuilder(config.renderExperienceOverlay)
            .create(center - 100, y, 200, 20, Component.translatable("rpglocatorbar.config.render_xp_overlay"), (button, value) -> {
                config.renderExperienceOverlay = value;
                LocatorConfigManager.save();
            });
        addRenderableWidget(xpToggle);

        y += 24;
        hideXpCreativeToggle = CycleButton.onOffBuilder(config.hideXpInCreative)
            .create(center - 100, y, 200, 20, Component.translatable("rpglocatorbar.config.hide_xp_creative"), (button, value) -> {
                config.hideXpInCreative = value;
                LocatorConfigManager.save();
            });
        addRenderableWidget(hideXpCreativeToggle);

        y += 24;
        locatorDuringXpToggle = CycleButton.onOffBuilder(config.keepLocatorVisibleDuringXpGain)
            .create(center - 100, y, 200, 20, Component.translatable("rpglocatorbar.config.keep_locator_during_xp"), (button, value) -> {
                config.keepLocatorVisibleDuringXpGain = value;
                LocatorConfigManager.save();
            });
        addRenderableWidget(locatorDuringXpToggle);

        y += 24;
        locatorAlwaysOnToggle = CycleButton.onOffBuilder(config.alwaysShowLocator)
            .create(center - 100, y, 200, 20, Component.translatable("rpglocatorbar.config.always_show_locator"), (button, value) -> {
                config.alwaysShowLocator = value;
                LocatorConfigManager.save();
            });
        addRenderableWidget(locatorAlwaysOnToggle);

        y += 24;
        Button reset = Button.builder(Component.translatable("rpglocatorbar.config.default_settings"), b -> {
            LocatorConfig defaults = new LocatorConfig();
            config.locatorYOffset = defaults.locatorYOffset;
            config.renderExperienceOverlay = defaults.renderExperienceOverlay;
            config.hideXpInCreative = defaults.hideXpInCreative;
            config.keepLocatorVisibleDuringXpGain = defaults.keepLocatorVisibleDuringXpGain;
            config.alwaysShowLocator = defaults.alwaysShowLocator;
            offsetSlider.setOffset(config.locatorYOffset);
            xpToggle.setValue(config.renderExperienceOverlay);
            hideXpCreativeToggle.setValue(config.hideXpInCreative);
            locatorDuringXpToggle.setValue(config.keepLocatorVisibleDuringXpGain);
            locatorAlwaysOnToggle.setValue(config.alwaysShowLocator);
            LocatorConfigManager.save();
        }).pos(center - 100, y).size(200, 20).build();
        addRenderableWidget(reset);

        y += 30;
        Button done = Button.builder(Component.translatable("gui.done"), b -> onClose())
            .pos(center - 50, y)
            .size(100, 20)
            .build();
        addRenderableWidget(done);
    }

    @Override
    public void tick() {
        super.tick();
        // Slider drags change the offset many times a second; write the file once the value settles.
        if (saveCountdown > 0 && --saveCountdown == 0) {
            LocatorConfigManager.save();
            saveCountdown = -1;
        }
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(parent);
    }

    @Override
    public void removed() {
        LocatorConfigManager.save();
    }

    private static final class LocatorOffsetSlider extends AbstractSliderButton {
        private final Runnable onChanged;
        private int offset;

        LocatorOffsetSlider(int x, int y, int width, int height, int initialOffset, Runnable onChanged) {
            super(x, y, width, height, Component.empty(), 0.0);
            this.onChanged = onChanged;
            setOffset(initialOffset);
        }

        void setOffset(int newOffset) {
            this.offset = Mth.clamp(newOffset, MIN_OFFSET_PX, MAX_OFFSET_PX);
            this.value = (this.offset - MIN_OFFSET_PX) / (double) (MAX_OFFSET_PX - MIN_OFFSET_PX);
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            this.setMessage(Component.translatable("rpglocatorbar.config.offset", offset));
        }

        @Override
        protected void applyValue() {
            double range = MAX_OFFSET_PX - MIN_OFFSET_PX;
            this.offset = Mth.clamp((int) Math.round(MIN_OFFSET_PX + this.value * range), MIN_OFFSET_PX, MAX_OFFSET_PX);
            LocatorConfigManager.get().locatorYOffset = this.offset;
            onChanged.run();
        }
    }
}
