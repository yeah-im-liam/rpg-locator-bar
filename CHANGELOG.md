# Changelog

## 1.1.0

- Now available for Minecraft 1.21.6 to 1.21.11 on Fabric and NeoForge.
- The jump bar on horses and other mounts now stays visible for the whole ride, even when the locator bar is active.
- The XP bar now shows in Creative when "Hide XP bar in creative" is off, even at level 0.
- The XP bar now shows under the locator at level 0, like vanilla.
- The locator bar stops above the XP bar instead of overlapping it at large GUI scales.
- The settings screen applies changes live (including the vertical offset) and saves them as you change them.
- A broken config file no longer crashes the game; it falls back to the defaults.
- Fabric: fixed the HUD changes (XP bar under the locator, keeping the locator during XP gain, always-on) not being applied in normal installs of 1.0.1. Only the locator position was.
- NeoForge: the config keybind is now saved, the XP bar and locator are no longer drawn twice, and the mod no longer loads on dedicated servers.
- The Architectury API is no longer required.
- Better compatibility with other HUD mods.
