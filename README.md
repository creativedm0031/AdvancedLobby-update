# AdvancedLobby-update

Uptake of the [AdvancedLobby](https://github.com/cyne79/AdvancedLobby) plugin for current Minecraft versions.

* **Original author:** cyne ([cyne79/AdvancedLobby](https://github.com/cyne79/AdvancedLobby))
* **Updated for Minecraft 1.17 – 26.3 by:** CreativeDM

Current version: **2.1**

## Supported versions

One jar for **Minecraft 1.17 – 26.3** (Spigot / Paper and forks, Bukkit).

| Minecraft | Java |
|---|---|
| 1.17 | 16+ |
| 1.18 – 1.20.4 | 17+ |
| 1.20.5 – 1.21.x | 21+ |
| 26.x | 25 |

Tested on Paper 1.17.1, 1.18.2, 1.19.4, 1.20.4, 1.20.6, 1.21.1, 1.21.4, 1.21.10, 26.2 and 26.3.
Legacy versions (1.8 – 1.16) are not supported – use the original plugin for those.

## Building

```bash
mvn clean package
```

The plugin jar is created at `target/AdvancedLobby-<version>-1.17-26.3.jar`.

The plugin is compiled against the oldest supported API (`spigot-api 1.17.1`, Java 16). API that only exists
in newer versions (player profiles, renamed attributes/item flags/particles, sound registry) is resolved at
runtime in `crossversion/VCompat`, `VParticle` and `AdvancedLobby#getSound`.

## Changes in 2.1

* Support for Minecraft **1.17 – 26.3** in a single jar (compiled for Java 16, `api-version: '1.17'`)
* Version-independent handling of max health attribute, item flags, skull textures and particle names

## Changes in 2.0.1

* Fixed a NullPointerException on player join on Paper servers

## Changes in 2.0 (compared to the original 1.2)

* Ported to Minecraft 26.3
* Removed all NMS / CraftBukkit reflection (did not work anymore since the server packages are no longer versioned):
  * Titles, action bar and tab list header/footer now use the Bukkit/Spigot API
  * Particles use `Player#spawnParticle`; legacy particle names (e.g. `SPELL_WITCH`, `EXPLOSION_LARGE`, `VILLAGER_HAPPY`) are mapped to the new names automatically
  * Custom skull textures use the `PlayerProfile` API instead of authlib (authlib dependency removed)
* Sounds are resolved by constant name (`ENTITY_PLAYER_LEVELUP`) or namespaced key (`minecraft:entity.player.levelup`); volume/pitch may now be decimals
* Replaced removed/deprecated API: `ItemFlag.HIDE_POTION_EFFECTS`, `PlayerPickupItemEvent`, `setMaxHealth`, `get/setItemInHand`, `hide/showPlayer(Player)`
* Update check runs asynchronously and no longer crashes without an internet connection
