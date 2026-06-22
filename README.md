# AxisRuler

AxisRuler is a client-side Minecraft mod for precise visual block measurement, available for Fabric and NeoForge.

It shows exact X, Y, and Z size in-world, renders the measured area around your selection, and keeps the result readable while you build.

## What It Does

AxisRuler gives you:

- two-point measurement with exact axis readouts
- live preview after Point A before you place Point B
- in-world selection box, guides, labels, and line rendering
- pinned measurements you can keep on screen while working
- HUD readout for the current measurement
- color customization and style presets

## Use

AxisRuler works with `Alt` + keybinds.

Basic workflow:

1. Look at a block and press `Alt + Z` to set Point A
2. Look at another block and press `Alt + X` to set Point B
3. AxisRuler will show the X / Y / Z size in-world and in the HUD

After Point A is set, the mod shows a live preview on the block under your crosshair before you confirm Point B.

Default controls:

- `Alt + Z` set Point A
- `Alt + X` set Point B
- `Alt + C` clear points
- `Alt + V` swap points
- `Alt + R` cycle measurement mode
- `Alt + H` toggle HUD
- `Alt + G` toggle guides
- `Alt + L` toggle labels
- `Alt + J` toggle connection line
- `Alt + M` copy current measurement
- `Alt + P` pin current measurement
- `Alt + U` clear all pinned measurements

Pinned measurements let you keep finished boxes visible while working on something else.

You can change the keybinds in:

`Options -> Controls -> AxisRuler`

## Configuration

AxisRuler includes visual configuration for supported builds with Mod Menu integration.

You can adjust:

- overlay colors
- HUD position
- HUD scale
- visible overlay elements
- presets
- custom preset values

## Supported Versions

AxisRuler currently supports:

| Minecraft | Fabric | NeoForge |
| --- | --- | --- |
| `1.21.4` | yes | yes |
| `1.21.8` | yes | yes |
| `1.21.11` | yes | yes |
| `26.1`, `26.1.1`, `26.1.2` | yes | yes |

There are separate release files for each Minecraft branch and loader.

Release files:

- `axisruler-mc1214-1.2.0.jar` (Fabric)
- `axisruler-mc1218-1.2.0.jar` (Fabric)
- `axisruler-mc12111-1.2.0.jar` (Fabric)
- `axisruler-mc261x-1.2.0.jar` (Fabric)
- `axisruler-neoforge-mc1214-1.2.0.jar` (NeoForge)
- `axisruler-neoforge-mc1218-1.2.0.jar` (NeoForge)
- `axisruler-neoforge-mc12111-1.2.0.jar` (NeoForge)
- `axisruler-neoforge-mc261x-1.2.0.jar` (NeoForge)

## Install

On Fabric you should have:

- Fabric Loader
- Fabric API
- Mod Menu for config screen support

On NeoForge you only need NeoForge itself (the config screen is reached from the in-game mod list).

Then place the correct AxisRuler jar (matching your Minecraft version and loader) into your `mods` folder.

## Notes

- Fabric and NeoForge
- Client-side only
- Safe on multiplayer clients (client may join servers that do not have the mod)
- No server installation required
