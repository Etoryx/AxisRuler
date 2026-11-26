package dev.erotoro.axisruler.neoforge.mc12111.keybind;

import dev.erotoro.axisruler.core.text.AxisRulerKeys;
import dev.erotoro.axisruler.core.util.ModConstants;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

/**
 * NeoForge keybind registrar. Mirrors the Fabric registrar but registers through
 * {@link RegisterKeyMappingsEvent} (fired on the mod event bus) instead of Fabric's
 * {@code KeyMappingHelper}. The {@link KeyMapping} instances are created eagerly in the
 * constructor so the input handler can reference them immediately; they only take effect once
 * {@link #onRegisterKeyMappings(RegisterKeyMappingsEvent)} hands them to NeoForge.
 */
public final class KeybindRegistrar {
    // Construct the category directly (do NOT use the deprecated KeyMapping.Category.register,
    // which also mutates the vanilla SORT_ORDER); NeoForge registers it via registerCategory below.
    private final KeyMapping.Category category =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(ModConstants.MOD_ID, "controls"));
    private final KeyMapping setPointA = create(AxisRulerKeys.KEY_SET_POINT_A, GLFW.GLFW_KEY_Z);
    private final KeyMapping setPointB = create(AxisRulerKeys.KEY_SET_POINT_B, GLFW.GLFW_KEY_X);
    private final KeyMapping clearPoints = create(AxisRulerKeys.KEY_CLEAR_POINTS, GLFW.GLFW_KEY_C);
    private final KeyMapping swapPoints = create(AxisRulerKeys.KEY_SWAP_POINTS, GLFW.GLFW_KEY_V);
    private final KeyMapping cycleMode = create(AxisRulerKeys.KEY_CYCLE_MODE, GLFW.GLFW_KEY_R);
    private final KeyMapping toggleHud = create(AxisRulerKeys.KEY_TOGGLE_HUD, GLFW.GLFW_KEY_H);
    private final KeyMapping toggleGuides = create(AxisRulerKeys.KEY_TOGGLE_GUIDES, GLFW.GLFW_KEY_G);
    private final KeyMapping toggleLabels = create(AxisRulerKeys.KEY_TOGGLE_LABELS, GLFW.GLFW_KEY_L);
    private final KeyMapping toggleLine = create(AxisRulerKeys.KEY_TOGGLE_LINE, GLFW.GLFW_KEY_J);
    private final KeyMapping copyMeasurement = create(AxisRulerKeys.KEY_COPY_MEASUREMENT, GLFW.GLFW_KEY_M);
    private final KeyMapping pinMeasurement = create(AxisRulerKeys.KEY_PIN, GLFW.GLFW_KEY_P);
    private final KeyMapping clearPinned = create(AxisRulerKeys.KEY_CLEAR_PINNED, GLFW.GLFW_KEY_U);
    private final KeyMapping undoSelection = create(AxisRulerKeys.KEY_UNDO, GLFW.GLFW_KEY_B);
    private final KeyMapping shortcutModifier = create(AxisRulerKeys.KEY_SHORTCUT_MODIFIER, GLFW.GLFW_KEY_LEFT_ALT);
    private final List<KeyMapping> keyBindings = List.of(
            setPointA,
            setPointB,
            clearPoints,
            swapPoints,
            cycleMode,
            toggleHud,
            toggleGuides,
            toggleLabels,
            toggleLine,
            copyMeasurement,
            pinMeasurement,
            clearPinned,
            undoSelection
    );

    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(category);
        keyBindings.forEach(event::register);
        event.register(shortcutModifier);
    }

    public KeyMapping shortcutModifier() {
        return shortcutModifier;
    }

    public List<KeyMapping> keyBindings() {
        return keyBindings;
    }

    public KeyMapping setPointA() {
        return setPointA;
    }

    public KeyMapping setPointB() {
        return setPointB;
    }

    public KeyMapping clearPoints() {
        return clearPoints;
    }

    public KeyMapping swapPoints() {
        return swapPoints;
    }

    public KeyMapping cycleMode() {
        return cycleMode;
    }

    public KeyMapping toggleHud() {
        return toggleHud;
    }

    public KeyMapping toggleGuides() {
        return toggleGuides;
    }

    public KeyMapping toggleLabels() {
        return toggleLabels;
    }

    public KeyMapping toggleLine() {
        return toggleLine;
    }

    public KeyMapping copyMeasurement() {
        return copyMeasurement;
    }

    public KeyMapping pinMeasurement() {
        return pinMeasurement;
    }

    public KeyMapping clearPinned() {
        return clearPinned;
    }

    public KeyMapping undoSelection() {
        return undoSelection;
    }

    private KeyMapping create(String translationKey, int defaultKey) {
        return new KeyMapping(translationKey, InputConstants.Type.KEYSYM, defaultKey, category);
    }
}
