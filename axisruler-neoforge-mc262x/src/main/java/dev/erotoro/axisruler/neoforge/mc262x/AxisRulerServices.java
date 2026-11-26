package dev.erotoro.axisruler.neoforge.mc262x;

import dev.erotoro.axisruler.neoforge.mc262x.input.ClientInputHandler;
import dev.erotoro.axisruler.neoforge.mc262x.integration.modmenu.AxisRulerConfigScreen;
import dev.erotoro.axisruler.neoforge.mc262x.keybind.KeybindRegistrar;
import dev.erotoro.axisruler.neoforge.mc262x.render.HudOverlayRenderer;
import dev.erotoro.axisruler.neoforge.mc262x.render.WorldOverlayRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Wires the AxisRuler platform glue onto NeoForge's two event buses, mirroring the Fabric
 * {@code AxisRulerServices} orchestration.
 *
 * <ul>
 *   <li><b>Mod event bus</b> (registration phase): keybinds and the HUD GUI layer.</li>
 *   <li><b>Game event bus</b> ({@link NeoForge#EVENT_BUS}): the per-tick input handler and the
 *       per-frame world overlay renderer.</li>
 *   <li>The mod-list config screen is exposed via {@link IConfigScreenFactory}.</li>
 * </ul>
 */
public final class AxisRulerServices {
    private final KeybindRegistrar keybindRegistrar;
    private final ClientInputHandler inputHandler;
    private final WorldOverlayRenderer overlayRenderer;
    private final HudOverlayRenderer hudOverlayRenderer;

    public AxisRulerServices(
            KeybindRegistrar keybindRegistrar,
            ClientInputHandler inputHandler,
            WorldOverlayRenderer overlayRenderer,
            HudOverlayRenderer hudOverlayRenderer
    ) {
        this.keybindRegistrar = keybindRegistrar;
        this.inputHandler = inputHandler;
        this.overlayRenderer = overlayRenderer;
        this.hudOverlayRenderer = hudOverlayRenderer;
    }

    public void initialize(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(keybindRegistrar::onRegisterKeyMappings);
        modEventBus.addListener(hudOverlayRenderer::onRegisterGuiLayers);

        NeoForge.EVENT_BUS.addListener(inputHandler::onClientTick);
        NeoForge.EVENT_BUS.addListener(overlayRenderer::onSubmitCustomGeometry);

        modContainer.registerExtensionPoint(
                IConfigScreenFactory.class,
                (IConfigScreenFactory) (container, modListScreen) -> AxisRulerConfigScreen.create(modListScreen)
        );
    }
}
