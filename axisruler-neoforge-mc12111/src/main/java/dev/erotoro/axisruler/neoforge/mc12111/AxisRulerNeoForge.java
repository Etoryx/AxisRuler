package dev.erotoro.axisruler.neoforge.mc12111;

import dev.erotoro.axisruler.core.config.AxisRulerConfig;
import dev.erotoro.axisruler.core.config.ConfigManager;
import dev.erotoro.axisruler.core.measure.MeasurementService;
import dev.erotoro.axisruler.core.measure.SelectionState;
import dev.erotoro.axisruler.core.util.ModConstants;
import dev.erotoro.axisruler.neoforge.mc12111.input.ClientInputHandler;
import dev.erotoro.axisruler.neoforge.mc12111.input.TargetingService;
import dev.erotoro.axisruler.neoforge.mc12111.keybind.KeybindRegistrar;
import dev.erotoro.axisruler.neoforge.mc12111.render.HudOverlayRenderer;
import dev.erotoro.axisruler.neoforge.mc12111.render.WorldOverlayRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

/**
 * NeoForge client entrypoint for AxisRuler on the Minecraft 26.1.x line.
 *
 * <p>Mirrors the Fabric {@code AxisRulerClient}: it loads the shared config, builds the
 * version-agnostic {@link MeasurementService} from {@link dev.erotoro.axisruler.core core}, and
 * (in later steps) wires the NeoForge-specific keybind/render/input glue onto the mod and game
 * event buses. The whole mod is marked {@link Dist#CLIENT} so NeoForge never loads it on a server.
 */
@Mod(value = ModConstants.MOD_ID, dist = Dist.CLIENT)
public final class AxisRulerNeoForge {
    private static ConfigManager configManager;
    private static SelectionState selectionState;

    public AxisRulerNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        configManager = new ConfigManager(
                FMLPaths.CONFIGDIR.get().resolve(ConfigManager.CONFIG_FILE_NAME)
        );
        AxisRulerConfig config = configManager.load();
        selectionState = new SelectionState();
        selectionState.applyConfigDefaults(config);
        MeasurementService measurementService = new MeasurementService(selectionState);

        KeybindRegistrar keybindRegistrar = new KeybindRegistrar();
        TargetingService targetingService = new TargetingService();
        ClientInputHandler inputHandler = new ClientInputHandler(
                keybindRegistrar,
                measurementService,
                targetingService,
                configManager
        );
        WorldOverlayRenderer overlayRenderer = new WorldOverlayRenderer(measurementService, configManager);
        HudOverlayRenderer hudOverlayRenderer = new HudOverlayRenderer(measurementService, configManager);

        AxisRulerServices services = new AxisRulerServices(
                keybindRegistrar,
                inputHandler,
                overlayRenderer,
                hudOverlayRenderer
        );
        services.initialize(modEventBus, modContainer);

        ModConstants.LOGGER.info("{} initialized for NeoForge client by Erotoro", ModConstants.MOD_NAME);
    }

    public static ConfigManager configManager() {
        return configManager;
    }

    public static SelectionState selectionState() {
        return selectionState;
    }
}
