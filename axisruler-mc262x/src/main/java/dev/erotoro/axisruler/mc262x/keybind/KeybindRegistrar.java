package dev.erotoro.axisruler.mc262x.keybind;

import dev.erotoro.axisruler.core.text.AxisRulerKeys;
import dev.erotoro.axisruler.core.util.ModConstants;
import com.mojang.blaze3d.platform.InputConstants;
import dev.erotoro.axisruler.compat.KeyboardInput;
import java.util.List;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public final class KeybindRegistrar {
    private final KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(ModConstants.MOD_ID, "controls"));
    private List<KeyMapping> keyBindings = List.of();
    private KeyMapping setPointA;
    private KeyMapping setPointB;
    private KeyMapping clearPoints;
    private KeyMapping swapPoints;
    private KeyMapping cycleMode;
    private KeyMapping toggleHud;
    private KeyMapping toggleGuides;
    private KeyMapping toggleLabels;
    private KeyMapping toggleLine;
    private KeyMapping copyMeasurement;
    private KeyMapping pinMeasurement;
    private KeyMapping clearPinned;
    private KeyMapping undoSelection;
    private KeyMapping shortcutModifier;
    private boolean registered;

    public void register() {
        if (registered) {
            return;
        }

        setPointA = registerKey(AxisRulerKeys.KEY_SET_POINT_A, InputConstants.KEY_Z);
        setPointB = registerKey(AxisRulerKeys.KEY_SET_POINT_B, InputConstants.KEY_X);
        clearPoints = registerKey(AxisRulerKeys.KEY_CLEAR_POINTS, InputConstants.KEY_C);
        swapPoints = registerKey(AxisRulerKeys.KEY_SWAP_POINTS, InputConstants.KEY_V);
        cycleMode = registerKey(AxisRulerKeys.KEY_CYCLE_MODE, InputConstants.KEY_R);
        toggleHud = registerKey(AxisRulerKeys.KEY_TOGGLE_HUD, InputConstants.KEY_H);
        toggleGuides = registerKey(AxisRulerKeys.KEY_TOGGLE_GUIDES, InputConstants.KEY_G);
        toggleLabels = registerKey(AxisRulerKeys.KEY_TOGGLE_LABELS, InputConstants.KEY_L);
        toggleLine = registerKey(AxisRulerKeys.KEY_TOGGLE_LINE, InputConstants.KEY_J);
        copyMeasurement = registerKey(AxisRulerKeys.KEY_COPY_MEASUREMENT, InputConstants.KEY_M);
        pinMeasurement = registerKey(AxisRulerKeys.KEY_PIN, InputConstants.KEY_P);
        clearPinned = registerKey(AxisRulerKeys.KEY_CLEAR_PINNED, InputConstants.KEY_U);
        undoSelection = registerKey(AxisRulerKeys.KEY_UNDO, InputConstants.KEY_B);
        shortcutModifier = registerKey(AxisRulerKeys.KEY_SHORTCUT_MODIFIER, InputConstants.KEY_LALT);
        keyBindings = List.of(
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
        registered = true;
    }

    public List<KeyMapping> keyBindings() {
        ensureRegistered();
        return keyBindings;
    }

    public KeyMapping shortcutModifier() {
        ensureRegistered();
        return shortcutModifier;
    }

    public KeyMapping setPointA() {
        ensureRegistered();
        return setPointA;
    }

    public KeyMapping setPointB() {
        ensureRegistered();
        return setPointB;
    }

    public KeyMapping clearPoints() {
        ensureRegistered();
        return clearPoints;
    }

    public KeyMapping swapPoints() {
        ensureRegistered();
        return swapPoints;
    }

    public KeyMapping cycleMode() {
        ensureRegistered();
        return cycleMode;
    }

    public KeyMapping toggleHud() {
        ensureRegistered();
        return toggleHud;
    }

    public KeyMapping toggleGuides() {
        ensureRegistered();
        return toggleGuides;
    }

    public KeyMapping toggleLabels() {
        ensureRegistered();
        return toggleLabels;
    }

    public KeyMapping toggleLine() {
        ensureRegistered();
        return toggleLine;
    }

    public KeyMapping copyMeasurement() {
        ensureRegistered();
        return copyMeasurement;
    }

    public KeyMapping pinMeasurement() {
        ensureRegistered();
        return pinMeasurement;
    }

    public KeyMapping clearPinned() {
        ensureRegistered();
        return clearPinned;
    }

    public KeyMapping undoSelection() {
        ensureRegistered();
        return undoSelection;
    }

    private KeyMapping registerKey(String translationKey, int defaultKey) {
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(
                translationKey,
                KeyboardInput.TYPE,
                defaultKey,
                category
        ));
    }

    private void ensureRegistered() {
        if (!registered) {
            throw new IllegalStateException("AxisRuler key bindings are not registered yet");
        }
    }
}
