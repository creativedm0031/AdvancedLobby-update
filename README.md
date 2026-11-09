# AdvancedLobby-update

Uptake of the [AdvancedLobby](https://github.com/cyne79/AdvancedLobby) plugin for current Minecraft versions.

* **Original author:** cyne ([cyne79/AdvancedLobby](https://github.com/cyne79/AdvancedLobby))
* **Updated for Minecraft 26.3 by:** CreativeDM

Current version: **2.0**

## Supported versions

* **Minecraft 26.3** (Spigot / Paper and forks)
* Java **25** (required by Minecraft 26.x)

Legacy versions (1.8 – 1.12) are no longer supported by this fork.

## Building

```bash
mvn clean package
```

The plugin jar is created at `target/AdvancedLobby-<version>-26.3.jar`.

## Changes in 2.0 (compared to the original 1.2)

* Built against `spigot-api 26.3-R0.1-SNAPSHOT`, Java 25, `api-version: '26.3'`
* Removed all NMS / CraftBukkit reflection (did not work anymore since the server packages are no longer versioned):
  * Titles, action bar and tab list header/footer now use the Bukkit/Spigot API
  * Particles use `Player#spawnParticle`; legacy particle names (e.g. `SPELL_WITCH`, `EXPLOSION_LARGE`, `VILLAGER_HAPPY`) are mapped to the new names automatically
  * Custom skull textures use the `PlayerProfile` API instead of authlib (authlib dependency removed)
* Sounds are resolved by constant name (`ENTITY_PLAYER_LEVELUP`) or namespaced key (`minecraft:entity.player.levelup`); volume/pitch may now be decimals
* Replaced removed/deprecated API: `ItemFlag.HIDE_POTION_EFFECTS`, `PlayerPickupItemEvent`, `setMaxHealth`, `get/setItemInHand`, `hide/showPlayer(Player)`
* Update check runs asynchronously and no longer crashes without an internet connection
