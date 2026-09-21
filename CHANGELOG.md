# Changelog

## 26.3.0

- Target Minecraft 26.3 (Wilderness Bound) by building against spigot-api 26.3-R0.1-SNAPSHOT.
- Support the new year-based versioning scheme (26.1 - 26.3) alongside legacy 1.16 - 1.21.x.
- Fix version detection that disabled the plugin on 26.x servers and silently blocked pearl cooldown compensation.
- Report detected game versions like `26.3` instead of legacy `1_21_R0`-style strings.
- Compile with Java 17 (modern JDKs can no longer emit Java 8 bytecode); Minecraft 26.x servers run on Java 25.
- Extend Modrinth game-version list through 26.3.

## 26.2.0

- Add `/gs ping` so players can view their ping and compensation status.
- Add `/gs toggle` so players can opt their own compensation on or off.
- Make knockback compensation functional via `knockback-multiplier`, gated behind
  `knockback.enabled` (default `false`) so vanilla gameplay is unchanged out of the box.
- Add `ping-update-interval` config option to control how often ping is sampled.
- Refactor player data storage to use `computeIfAbsent` and drop a dead lookup.
- Remove unused knockback/velocity code paths and tidy `PlayerData`.

## 26.1.2

- Update build metadata for GooseSync 26.1.2 and target Spigot API 1.21.2.
- Add safer configuration loading with bounded compensation values.
- Fix repeated action tick checks for consumption, pearls, and potion handling.
- Make combat velocity handling safer around damage event types and timing.
- Add `/gs status` and tab completion.
- Add Modrinth publish workflow that refuses to publish without a changelog.
