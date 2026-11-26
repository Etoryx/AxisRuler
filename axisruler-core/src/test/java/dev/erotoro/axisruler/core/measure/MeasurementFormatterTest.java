package dev.erotoro.axisruler.core.measure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MeasurementFormatterTest {
    private final MeasurementService service = new MeasurementService(new SelectionState());

    @Test
    void setupCommandsUseBoundsForFillAndPickOrderForWorldEdit() {
        MeasurePoint a = MeasurePoint.of("minecraft:overworld", 5, 9, 2);
        MeasurePoint b = MeasurePoint.of("minecraft:overworld", 1, 3, 8);

        String commands = MeasurementFormatter.formatSetupCommands(service.calculate(a, b));
        String[] lines = commands.split(System.lineSeparator());

        assertEquals(3, lines.length);
        assertEquals("/fill 1 3 2 5 9 8 minecraft:stone", lines[0]);
        assertEquals("//pos1 5,9,2", lines[1]);
        assertEquals("//pos2 1,3,8", lines[2]);
    }

    @Test
    void setupCommandsHandleNegativeCoordinates() {
        MeasurePoint a = MeasurePoint.of("minecraft:overworld", -3, -64, -10);
        MeasurePoint b = MeasurePoint.of("minecraft:overworld", 2, -60, -1);

        String commands = MeasurementFormatter.formatSetupCommands(service.calculate(a, b));

        assertEquals("/fill -3 -64 -10 2 -60 -1 minecraft:stone", commands.split(System.lineSeparator())[0]);
    }

    @Test
    void setupCommandsRejectInvalidMeasurement() {
        MeasurementResult invalid = service.calculate(null, null);

        assertThrows(IllegalArgumentException.class, () -> MeasurementFormatter.formatSetupCommands(invalid));
    }
}
