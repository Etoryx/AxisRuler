package dev.erotoro.axisruler.core.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ConfigManagerTest {
    @Test
    void invalidRootIsBackedUpBeforeReset(@TempDir Path dir) throws Exception {
        Path file = dir.resolve("axisruler.json");
        Files.writeString(file, "[]");

        assertEquals(AxisRulerConfig.defaults(), new ConfigManager(file).load());

        assertEquals("[]", Files.readString(dir.resolve("axisruler.json.corrupt")));
        assertNotEquals("[]", Files.readString(file));
    }

    @Test
    void failedBackupDoesNotOverwriteOriginal(@TempDir Path dir) throws Exception {
        Path file = dir.resolve("axisruler.json");
        Files.writeString(file, "[]");
        Path backup = Files.createDirectory(dir.resolve("axisruler.json.corrupt"));
        Files.createFile(backup.resolve("keep"));

        assertEquals(AxisRulerConfig.defaults(), new ConfigManager(file).load());

        assertEquals("[]", Files.readString(file));
    }

    @Test
    void loadCreatesFileWithDefaultsWhenMissing(@TempDir Path dir) {
        Path file = dir.resolve("axisruler.json");
        ConfigManager manager = new ConfigManager(file);

        AxisRulerConfig loaded = manager.load();

        assertTrue(Files.exists(file));
        assertEquals(AxisRulerConfig.defaults(), loaded);
    }

    @Test
    void updatePersistsAcrossReload(@TempDir Path dir) {
        Path file = dir.resolve("axisruler.json");
        ConfigManager writer = new ConfigManager(file);
        writer.load();
        writer.update(writer.config().withHudEnabledDefault(false));

        ConfigManager reader = new ConfigManager(file);
        AxisRulerConfig reloaded = reader.load();

        assertFalse(reloaded.hudEnabledDefault());
    }

    @Test
    void previewDoesNotPersistUntilCommitted(@TempDir Path dir) {
        Path file = dir.resolve("axisruler.json");
        ConfigManager manager = new ConfigManager(file);
        manager.load();

        manager.beginPreview();
        manager.preview(manager.persistedConfig().withGuidesEnabledDefault(false));
        assertTrue(manager.previewActive());
        assertFalse(manager.config().guidesEnabledDefault());
        assertTrue(manager.persistedConfig().guidesEnabledDefault());

        manager.discardPreview();
        assertFalse(manager.previewActive());
        assertTrue(manager.config().guidesEnabledDefault());
    }
}
