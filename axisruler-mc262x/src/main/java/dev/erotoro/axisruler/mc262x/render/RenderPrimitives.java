package dev.erotoro.axisruler.mc262x.render;

import dev.erotoro.axisruler.core.config.LabelBackgroundMode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 26.2 removed immediate-mode {@code MultiBufferSource} world rendering; geometry and text are
 * handed to the {@link SubmitNodeCollector} during the submit phase instead
 * ({@code LevelRenderEvents.COLLECT_SUBMITS}). The collector captures the pose at submit time,
 * so shape offsets are applied by translating the pose around each submit call.
 */
public final class RenderPrimitives {
    private static final VoxelShape FULL_BLOCK_SHAPE = Shapes.block();
    private static final int FULL_BRIGHT_LIGHT = 0x00F000F0;
    private static final float DEFAULT_MARKER_LINE_WIDTH = 1.75F;

    private RenderPrimitives() {
    }

    public static void drawBlockOutline(
            PoseStack matrices,
            SubmitNodeCollector consumers,
            BlockPos pos,
            int color,
            float lineWidth
    ) {
        drawOutline(matrices, consumers, FULL_BLOCK_SHAPE, pos.getX(), pos.getY(), pos.getZ(), color, lineWidth);
    }

    public static void drawBlockOutline(
            PoseStack matrices,
            SubmitNodeCollector consumers,
            BlockPos pos,
            int color
    ) {
        drawBlockOutline(matrices, consumers, pos, color, DEFAULT_MARKER_LINE_WIDTH);
    }

    public static void drawShapeOutline(
            PoseStack matrices,
            SubmitNodeCollector consumers,
            VoxelShape shape,
            double offsetX,
            double offsetY,
            double offsetZ,
            int color,
            float lineWidth
    ) {
        drawOutline(matrices, consumers, shape, offsetX, offsetY, offsetZ, color, lineWidth);
    }

    public static void drawFilledBox(
            PoseStack matrices,
            SubmitNodeCollector consumers,
            double minX,
            double minY,
            double minZ,
            double maxX,
            double maxY,
            double maxZ,
            int color
    ) {
        consumers.submitCustomGeometry(matrices, RenderTypes.debugFilledBox(), (entry, consumer) -> {
            quad(consumer, entry, minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, color);
            quad(consumer, entry, minX, minY, maxZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, color);
            quad(consumer, entry, minX, minY, minZ, minX, maxY, minZ, minX, maxY, maxZ, minX, minY, maxZ, color);
            quad(consumer, entry, maxX, minY, minZ, maxX, minY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, color);
            quad(consumer, entry, minX, maxY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, minX, maxY, maxZ, color);
            quad(consumer, entry, minX, minY, minZ, minX, minY, maxZ, maxX, minY, maxZ, maxX, minY, minZ, color);
        });
    }

    public static void drawWorldLabel(
            PoseStack matrices,
            SubmitNodeCollector consumers,
            Font textRenderer,
            Camera camera,
            String label,
            Vec3 position,
            float scale,
            int color,
            LabelBackgroundMode backgroundMode,
            int backgroundColor,
            boolean billboard
    ) {
        matrices.pushPose();
        try {
            matrices.translate(position.x, position.y, position.z);
            if (billboard) {
                matrices.mulPose(new org.joml.Matrix4f().rotation(camera.rotation()));
            }
            matrices.scale(scale, -scale, scale);

            float textWidth = textRenderer.width(label);
            float textOffsetX = -textWidth / 2.0F;
            int effectiveBackgroundColor = backgroundColor(backgroundMode, backgroundColor);
            FormattedCharSequence orderedText = FormattedCharSequence.forward(label, Style.EMPTY);

            consumers.submitText(
                    matrices,
                    textOffsetX,
                    0.0F,
                    orderedText,
                    false,
                    Font.DisplayMode.SEE_THROUGH,
                    FULL_BRIGHT_LIGHT,
                    color,
                    effectiveBackgroundColor,
                    0
            );
            consumers.submitText(
                    matrices,
                    textOffsetX,
                    0.0F,
                    orderedText,
                    false,
                    Font.DisplayMode.NORMAL,
                    FULL_BRIGHT_LIGHT,
                    color,
                    0x00000000,
                    0
            );
        } finally {
            matrices.popPose();
        }
    }

    private static void drawOutline(
            PoseStack matrices,
            SubmitNodeCollector consumers,
            VoxelShape shape,
            double offsetX,
            double offsetY,
            double offsetZ,
            int color,
            float lineWidth
    ) {
        matrices.pushPose();
        try {
            matrices.translate(offsetX, offsetY, offsetZ);
            consumers.submitShapeOutline(matrices, shape, RenderTypes.lines(), color, lineWidth, false);
        } finally {
            matrices.popPose();
        }
    }

    private static void quad(
            VertexConsumer consumer,
            PoseStack.Pose entry,
            double ax,
            double ay,
            double az,
            double bx,
            double by,
            double bz,
            double cx,
            double cy,
            double cz,
            double dx,
            double dy,
            double dz,
            int color
    ) {
        consumer.addVertex(entry, (float) ax, (float) ay, (float) az).setColor(color);
        consumer.addVertex(entry, (float) bx, (float) by, (float) bz).setColor(color);
        consumer.addVertex(entry, (float) cx, (float) cy, (float) cz).setColor(color);
        consumer.addVertex(entry, (float) dx, (float) dy, (float) dz).setColor(color);
    }

    private static int backgroundColor(LabelBackgroundMode mode, int color) {
        // Callers pass AxisRulerConfig#effectiveLabelBackgroundColorArgb, which already applies the
        // mode's alpha scaling, so SUBTLE must not be scaled a second time here.
        return mode == LabelBackgroundMode.NONE ? 0x00000000 : color;
    }
}
