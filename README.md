# AxisRuler

AxisRuler is a client-side Minecraft mod for precise visual block measurement, available for Fabric and NeoForge.

It shows exact X, Y, and Z size in-world, renders the measured area around your selection, and keeps the result readable while you build.

Download AxisRuler 1.3.0 from [Modrinth](https://modrinth.com/mod/axisruler/versions) or
[GitHub Releases](https://github.com/Etoryx/AxisRuler/releases).

## What It Does

AxisRuler gives you:

- two-point measurement with exact axis readouts
- live preview after Point A before you place Point B
- in-world selection box, guides, labels, and line rendering
- pinned measurements you can keep on screen while working
- HUD readout for the current measurement
- color customization and style presets
- configurable shortcut modifier, single-step undo/redo, and copyable setup commands

## Use

Hold the AxisRuler shortcut modifier (either `Alt` key by default) and press an action key.

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
- `Alt + Shift + M` copy setup commands (`/fill` + WorldEdit `//pos1` `//pos2`)
- `Alt + B` undo the last point change (press again to redo)
- `Alt + P` pin current measurement
- `Alt + U` clear all pinned measurements

Pinned measurements let you keep finished boxes visible while working on something else.

You can change the keybinds in:

`Options -> Controls -> Key Binds -> AxisRuler`

Change **Shortcut Modifier** to an unused keyboard key or mouse button (for
example, Mouse Button 4) to avoid conflicts with mods such as Axiom. Hold that
button in place of `Alt` in all the shortcuts above; the action keys stay the
same. Unbinding the modifier disables AxisRuler shortcuts until you bind it again.

## Configuration

Open AxisRuler's visual settings from Mod Menu on Fabric or the in-game mod
list on NeoForge. Shortcut bindings are configured separately in Minecraft's controls.

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
| `26.1`, `26.1.1` | yes | no |
| `26.1.2` | yes | yes |
| `26.2` | yes | yes |
| `26.3` | yes | yes |

There are separate release files for each Minecraft branch and loader. In
particular, the Fabric `mc261x` JAR covers `26.1` through `26.1.2`, while the
NeoForge `mc261x` JAR is for `26.1.2` only.

Release files:

- `axisruler-mc1214-1.3.0.jar` (Fabric)
- `axisruler-mc1218-1.3.0.jar` (Fabric)
- `axisruler-mc12111-1.3.0.jar` (Fabric)
- `axisruler-mc261x-1.3.0.jar` (Fabric)
- `axisruler-mc262x-1.3.0.jar` (Fabric)
- `axisruler-mc263x-1.3.0.jar` (Fabric)
- `axisruler-neoforge-mc1214-1.3.0.jar` (NeoForge)
- `axisruler-neoforge-mc1218-1.3.0.jar` (NeoForge)
- `axisruler-neoforge-mc12111-1.3.0.jar` (NeoForge)
- `axisruler-neoforge-mc261x-1.3.0.jar` (NeoForge)
- `axisruler-neoforge-mc262x-1.3.0.jar` (NeoForge)
- `axisruler-neoforge-mc263x-1.3.0.jar` (NeoForge)

## Install

On Fabric you should have:

- Fabric Loader
- Fabric API
- Mod Menu (optional, for the config screen)

On NeoForge you only need NeoForge itself (the config screen is reached from the in-game mod list).

Use Java 21 for Minecraft 1.21.x and Java 25 for Minecraft 26.x. Then place
only the AxisRuler JAR matching your Minecraft version and loader in your
`mods` folder.

## Build and Test

Install JDK 21 and JDK 25, then run `./gradlew build` (`.\gradlew.bat build` on
Windows). Each platform module writes its release JAR to `build/libs`.

Run `./gradlew :axisruler-mc263x:runClientGameTest` to check the shortcut
modifier in a temporary singleplayer world. This starts a Minecraft client
and requires a graphical environment.

## Notes

- Fabric and NeoForge
- Client-side only
- Safe on multiplayer clients (client may join servers that do not have the mod)
- No server installation required
