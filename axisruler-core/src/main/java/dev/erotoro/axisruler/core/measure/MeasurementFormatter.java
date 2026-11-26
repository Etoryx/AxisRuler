package dev.erotoro.axisruler.core.measure;

import dev.erotoro.axisruler.core.geometry.BlockPoint;
import dev.erotoro.axisruler.core.geometry.Vec3d;
import java.util.Locale;
import java.util.Objects;

public final class MeasurementFormatter {
    private MeasurementFormatter() {
    }

    public static String formatDimensions(int sizeX, int sizeY, int sizeZ) {
        return sizeX + " x " + sizeY + " x " + sizeZ;
    }

    public static String formatSignedDelta(int dx, int dy, int dz) {
        return "dx=" + dx + ", dy=" + dy + ", dz=" + dz;
    }

    public static String formatAbsoluteDelta(int absDx, int absDy, int absDz) {
        return "x=" + absDx + ", y=" + absDy + ", z=" + absDz;
    }

    public static String formatDecimal(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }

    public static String formatBlockPos(BlockPoint blockPos) {
        Objects.requireNonNull(blockPos, "blockPos");
        return blockPos.x() + " " + blockPos.y() + " " + blockPos.z();
    }

    public static String formatVec3(Vec3d vec3) {
        Objects.requireNonNull(vec3, "vec3");
        return formatDecimal(vec3.x()) + ", " + formatDecimal(vec3.y()) + ", " + formatDecimal(vec3.z());
    }

    /**
     * Ready-to-paste setup commands for the selection: a vanilla {@code /fill} over the
     * min..max bounds plus WorldEdit {@code //pos1}/{@code //pos2} at the user's actual
     * Point A / Point B, preserving pick order. Requires a valid measurement.
     */
    public static String formatSetupCommands(MeasurementResult result) {
        Objects.requireNonNull(result, "result");
        if (!result.valid()) {
            throw new IllegalArgumentException("setup commands require a valid measurement");
        }
        BlockPoint min = result.minBlockPos();
        BlockPoint max = result.maxBlockPos();
        MeasurePoint pointA = result.pointAOrNull();
        MeasurePoint pointB = result.pointBOrNull();
        return String.join(
                System.lineSeparator(),
                "/fill " + min.x() + " " + min.y() + " " + min.z()
                        + " " + max.x() + " " + max.y() + " " + max.z() + " minecraft:stone",
                "//pos1 " + pointA.x() + "," + pointA.y() + "," + pointA.z(),
                "//pos2 " + pointB.x() + "," + pointB.y() + "," + pointB.z()
        );
    }
}
