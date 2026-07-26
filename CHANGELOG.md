# Changelog

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
