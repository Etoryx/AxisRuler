package dev.erotoro.axisruler.core.measure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SelectionStateUndoTest {
    private static final MeasurePoint POINT_1 = MeasurePoint.of("minecraft:overworld", 1, 2, 3);
    private static final MeasurePoint POINT_2 = MeasurePoint.of("minecraft:overworld", 4, 5, 6);
    private static final MeasurePoint POINT_3 = MeasurePoint.of("minecraft:overworld", 7, 8, 9);

    private final SelectionState state = new SelectionState();

    @Test
    void nothingToUndoInitially() {
        assertFalse(state.undoLastPointChange());
    }

    @Test
    void switchingDimensionsClearsSelectionAndUndo() {
        state.setCurrentWorldKey("minecraft:overworld");
        state.setPointA(POINT_1);
        state.setPointB(POINT_2);
        state.setPreviewTarget(POINT_3);
        state.setCurrentWorldKey("minecraft:the_nether");

        assertNull(state.pointAOrNull());
        assertNull(state.pointBOrNull());
        assertNull(state.previewTargetOrNull());
        assertFalse(state.undoLastPointChange());
    }

    @Test
    void reconnectingToSameDimensionCannotRestorePreviousSelection() {
        state.setCurrentWorldKey("minecraft:overworld");
        state.setPointA(POINT_1);
        state.setPointA(POINT_2);
        state.setCurrentWorldKey("minecraft:overworld");
        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_1, state.pointAOrNull());

        state.setCurrentWorldKey(null);
        state.setCurrentWorldKey("minecraft:overworld");
        assertNull(state.pointAOrNull());
        assertFalse(state.undoLastPointChange());
    }

    @Test
    void undoRestoresOverwrittenPointA() {
        state.setPointA(POINT_1);
        state.setPointA(POINT_2);

        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_1, state.pointAOrNull());
    }

    @Test
    void settingSamePointDoesNotOverwriteUndoSlot() {
        state.setPointA(POINT_1);
        state.setPointA(POINT_2);
        state.setPointA(POINT_2);

        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_1, state.pointAOrNull());
    }

    @Test
    void undoTwiceActsAsRedo() {
        state.setPointA(POINT_1);
        state.setPointA(POINT_2);

        assertTrue(state.undoLastPointChange());
        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_2, state.pointAOrNull());
    }

    @Test
    void undoRestoresClearedSelection() {
        state.setPointA(POINT_1);
        state.setPointB(POINT_2);
        state.clearPoints();

        assertNull(state.pointAOrNull());
        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_1, state.pointAOrNull());
        assertEquals(POINT_2, state.pointBOrNull());
    }

    @Test
    void clearingEmptySelectionDoesNotOverwriteUndoSlot() {
        state.setPointA(POINT_1);
        state.setPointB(POINT_2);
        state.clearPoints();
        state.clearPoints();
        state.clearPoints();

        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_1, state.pointAOrNull());
        assertEquals(POINT_2, state.pointBOrNull());
    }

    @Test
    void undoRevertsSwap() {
        state.setPointA(POINT_1);
        state.setPointB(POINT_2);
        assertTrue(state.swapPoints());

        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_1, state.pointAOrNull());
        assertEquals(POINT_2, state.pointBOrNull());
    }

    @Test
    void undoUsesMostRecentChangeOnly() {
        state.setPointA(POINT_1);
        state.setPointB(POINT_2);
        state.setPointB(POINT_3);

        assertTrue(state.undoLastPointChange());
        assertEquals(POINT_1, state.pointAOrNull());
        assertEquals(POINT_2, state.pointBOrNull());
    }

    @Test
    void failedSwapDoesNotTouchUndoSlot() {
        state.setPointA(POINT_1);
        assertFalse(state.swapPoints());

        assertTrue(state.undoLastPointChange());
        assertNull(state.pointAOrNull());
    }
}
