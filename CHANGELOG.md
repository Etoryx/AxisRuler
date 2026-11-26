# Changelog

## 1.3.0

- **Minecraft 26.2 and 26.3 support** for Fabric and NeoForge; existing targets are retained.
- **Rebindable shortcut modifier** in Minecraft's controls. Assign a keyboard key or a mouse button instead of Alt.
- Undo no longer restores selection points from a previous dimension or connection.
- **Undo point change** (`Alt + B`) — restores the selection as it was before the last set/clear/swap; press again to redo.
- **Copy setup commands** (`Alt + Shift + M`) — puts ready-to-paste `/fill` plus WorldEdit `//pos1` `//pos2` commands on the clipboard.
- **HUD scale now scales text too** — the whole panel (including text) follows the HUD Scale slider, and values below 1.0 finally work. The default 1.0 look is unchanged.
- Fixed in-world label backgrounds being dimmer than configured (the Subtle mode alpha was applied twice).
- If the config file is unreadable it is now backed up to `axisruler.json.corrupt` before defaults are written, instead of being overwritten silently.
- Completed the Russian and Ukrainian translations of the config screen.
- Internal: per-frame render allocations reduced (box shapes are cached), color parsing sped up.

## 1.2.0

- **NeoForge support** — AxisRuler now ships for NeoForge on every supported Minecraft line: 1.21.4, 1.21.8, 1.21.11 and 26.1.x. Pick the jar matching your loader and version.
- The config screen is reachable on NeoForge directly from the in-game mod list (no Mod Menu needed).
- NeoForge builds are marked client-only and stay connection-safe, so you can join servers that do not have the mod.
- No gameplay changes for existing Fabric users — same features, same keybinds.

## 1.1.0

- Multi-version Fabric releases for 1.21.4, 1.21.8, 1.21.11 and 26.1.x.
- Two-point measurement, live preview, in-world box/guides/labels, HUD readout, pinned measurements, color customization and style presets.
