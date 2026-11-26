package dev.erotoro.axisruler.compat;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public final class KeyboardInput {
    public static final InputConstants.Type TYPE = InputConstants.Type.KEYBOARD;

    private KeyboardInput() {}

    public static boolean isDown(Minecraft client, int key) {
        return InputConstants.isKeyDown(key);
    }
}
