package dev.erotoro.axisruler.gametest;

import com.mojang.blaze3d.platform.InputConstants;
import dev.erotoro.axisruler.mc262x.AxisRulerClient;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.TestInput;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.client.KeyMapping;

@SuppressWarnings("UnstableApiUsage")
public final class ShortcutModifierTest implements FabricClientGameTest {
    private static final String MODIFIER_ID = "key.axisruler.shortcut_modifier";
    private static final int MOUSE_BUTTON = InputConstants.MOUSE_BUTTON_4;

    @Override
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {
            singleplayer.getConnection().waitForChunksRender();
            context.waitFor(client -> client.player != null && client.gui.screen() == null);

            TestInput input = context.getInput();
            boolean initialHud = hudEnabled(context);
            input.pressKey(InputConstants.KEY_H);
            expectHud(context, initialHud, "action alone");

            pressWithKeyboardModifier(context, InputConstants.KEY_LALT, !initialHud, "default Left Alt");
            pressWithKeyboardModifier(context, InputConstants.KEY_RALT, initialHud, "default Right Alt");

            rebind(context, InputConstants.Type.MOUSE.getOrCreate(MOUSE_BUTTON));
            input.holdAlt();
            try {
                input.pressKey(InputConstants.KEY_H);
                expectHud(context, initialHud, "Alt after remapping");
            } finally {
                input.releaseAlt();
            }

            input.holdMouse(MOUSE_BUTTON);
            try {
                input.pressKey(InputConstants.KEY_H);
                expectHud(context, !initialHud, "rebound mouse modifier");
            } finally {
                input.releaseMouse(MOUSE_BUTTON);
            }
            input.pressKey(InputConstants.KEY_H);
            expectHud(context, !initialHud, "released mouse modifier");

            rebind(context, InputConstants.Type.KEYBOARD.getOrCreate(InputConstants.KEY_F8));
            pressWithKeyboardModifier(context, InputConstants.KEY_F8, initialHud, "rebound keyboard modifier");

            rebind(context, InputConstants.UNKNOWN);
            pressWithKeyboardModifier(context, InputConstants.KEY_F8, initialHud, "unbound modifier");
        }
    }

    private static void pressWithKeyboardModifier(ClientGameTestContext context, int key, boolean expected, String scenario) {
        TestInput input = context.getInput();
        input.holdKey(key);
        try {
            input.pressKey(InputConstants.KEY_H);
            expectHud(context, expected, scenario);
        } finally {
            input.releaseKey(key);
        }
    }

    private static void rebind(ClientGameTestContext context, InputConstants.Key key) {
        context.runOnClient(client -> {
            KeyMapping.get(MODIFIER_ID).setKey(key);
            KeyMapping.resetMapping();
        });
    }

    private static boolean hudEnabled(ClientGameTestContext context) {
        return context.computeOnClient(client -> AxisRulerClient.selectionState().hudEnabled());
    }

    private static void expectHud(ClientGameTestContext context, boolean expected, String scenario) {
        context.waitTicks(2);
        if (hudEnabled(context) != expected) {
            throw new AssertionError("Unexpected HUD state after " + scenario);
        }
    }
}
