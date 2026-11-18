# AdvancedLobby ObsidianGrove Server Selector

For `inventories.teleporter.items.<id>`, select **one** destination: `location: spawn` (existing local teleport) or `server: survival` (Velocity/Bungee backend).

Example:

```yaml
hotbar_items:
  teleporter:
    enabled: true
    slot: 0
    material: COMPASS
    subid: 0
    displayname: '&bTeleporter &8× &7rightclick'
    lore: []
  custom_item:
    enabled: false

inventories:
  teleporter:
    title: '&bTeleporter'
    rows: 5
    items:
      survival:
        material: GRASS_BLOCK
        slot: 31
        displayname: '&8● &bSurvival'
        lore:
          - ' &8» &7Click to join Survival'
        subid: 0
        server: 'survival'
```

### Requirements

- Lobby backend runs AdvancedLobby.
- Velocity's `bungee-plugin-message-channel` option must be `true` (or a BungeeCord-compatible proxy with plugin messaging enabled).
- Backend named `survival` must exist in Velocity's `[servers]`.
- The `BungeeCord` outgoing plugin message requests a transfer; it does **not** confirm online status or connection success. The proxy will handle failed connections.

### Build

```sh
cd /srv/code/active/minecraft/AdvancedLobby-update
mvn clean package
```

The artifact is `target/AdvancedLobby-2.1.1-ObsidianGrove-1.17-26.3.jar`. Stop the Lobby backend, back up/remove the old AdvancedLobby jar (avoid two plugin jars), install the new jar and restart.
