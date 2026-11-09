<div align="center">

![AdvancedLobby](https://raw.githubusercontent.com/creativedm0031/AdvancedLobby-update/refs/heads/main/branding/banner.png)

# AdvancedLobby × v2.0.1

**Fully updated for Minecraft 26.3**

**Original plugin by cyne** • [Original resource](https://www.spigotmc.org/resources/35799/)
**Updated for 26.3 by CreativeDM** • [Source code](https://github.com/creativedm0031/AdvancedLobby-update)

</div>

---

## » Information

AdvancedLobby is a free and fully customizable lobby plugin for your Minecraft server.
This is an updated version of the original AdvancedLobby by **cyne**, rewritten to run on Minecraft 26.3 (Spigot & Paper). All credits for the original plugin go to cyne.

Currently, there are no ranks and no scoreboard implemented into the lobby plugin. Feel free to use any other plugin for ranks and the scoreboard.

## » Requirements

- Minecraft **26.3** (Spigot / Paper)
- Java **25**
- [PlaceholderAPI](https://www.spigotmc.org/resources/6245/) (optional, needed for placeholders)

> **Note:** Minecraft 1.8 – 1.16 is no longer supported by this version. Use the original plugin for those versions.

## » Installation

1. Put the plugin file into the `plugins` folder of your server
2. Restart your server
3. Set your spawn and teleporter locations with `/al location`
4. Customize everything in `config.yml`, `messages.yml` and `sounds.yml`
5. Apply your changes with `/al reload`

## » Features

### Hotbar items
- Teleporter (customizable navigator menu)
- Player hider (show all / show VIP / show none)
- Cosmetics menu
- Gadget slot
- Silent lobby
- Shield
- Custom item

### Cosmetics
- **Hats:** Melon, TNT, Glass, Sponge, Pumpkin, Cactus
- **Particles:** Hearts, Music, Flames, Villager, Rainbow
- **Balloons:** Yellow, Red, Green, Blue, Hay block, Sea lantern, Bookshelf, Note block
- **Gadgets:** Grappling hook, Rocket jump

### Lobby
- Integrated MultiWorld mode (multiple lobby worlds)
- Jump pads
- Void teleport
- World border
- Weather lock
- Command blocker
- Join settings (gamemode, health, clear inventory, spawn teleport)
- Protection: no block breaking/placing, no damage, no hunger, no item drops & more (unless in build mode)

### Display
- Join title & subtitle
- Actionbar messages
- Tablist header & footer
- Custom MOTD
- Chat format
- Supporting PlaceholderAPI

## » Changelog

### 2.0.1
- Fixed a NullPointerException on player join on Paper servers (lobby items were not given)
- Default MOTD now shows 26.3 instead of 1.8-1.12.2 (only for new configs)

### 2.0
- Full support for Minecraft 26.3 & Java 25
- Removed all NMS code – titles, actionbar, tablist, particles and skulls now use the official API
- Old particle names in configs are converted automatically
- Sounds can be set by name (`ENTITY_PLAYER_LEVELUP`) or key (`minecraft:entity.player.levelup`)
- Volume & pitch support decimal values
- Async update checker
- Many fixes for removed/deprecated API

## » Commands & Permissions

| Command | Aliases | Permission |
|---|---|---|
| `/advancedlobby` | `/al` | `advancedlobby.admin` |
| `/build` | `/edit` | `advancedlobby.commands.build` |
| `/chatclear` | `/cc` | `advancedlobby.commands.chatclear` |
| `/fly` | – | `advancedlobby.commands.fly` |
| `/gamemode` | `/gm` | `advancedlobby.commands.gamemode` |
| `/globalmute` | `/gmute` | `advancedlobby.commands.globalmute` |
| `/lobby` | `/hub`, `/l` | – |
| `/teleportall` | `/tpall` | `advancedlobby.commands.teleportall` |

### Other permissions

| Permission | Description |
|---|---|
| `advancedlobby.*` | All permissions |
| `advancedlobby.cosmetics.*` | All cosmetics |
| `advancedlobby.cosmetics.hats.*` / `.particles.*` / `.balloons.*` / `.gadgets.*` | All cosmetics of one category |
| `advancedlobby.silentlobby` | Use the silent lobby |
| `advancedlobby.shield` | Use the shield |
| `advancedlobby.shield.bypass` | Bypass other players' shield |
| `advancedlobby.player_hider.bypass` | Always visible (VIP) |
| `advancedlobby.chatclear.bypass` | Bypass chat clear |
| `advancedlobby.globalmute.bypass` | Bypass global mute |
| `advancedlobby.custom_item` | Receive the custom item |
| `advancedlobby.chatcolor` | Use color codes in chat |

## » Credits

- **cyne** – original author of AdvancedLobby
- **CreativeDM** – update to Minecraft 26.3

---

<div align="center">

*Found a bug? Please report it in the discussion tab or via PM – please do not use the review section for bug reports.*

</div>
