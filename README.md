# uSkyFix
This plugin allows you to hook uSkyBlock into **PlaceholderAPI** (`%placeholder_name%`) as well as **MVdWPlaceholderAPI** (`{placeholder_name}`), and adds a player head leaderboard.

## Placeholders
### `uskyfix_island_level`
> Shows you the level of your island, rounded to 2 decimal places.

### `uskyfix_island_leader`
> Shows you the leader of the island you're *currently standing on* (not your own).

### `uskyfix_island_global`
> Shows you the level of the island you're *currently standing on* (not your own).

### `uskyfix_island_biome`
> Lists the biome of your island.

### `uskyfix_island_togglewarp`
> Displays the warp status of your island (Inactive/Active).

### `uskyfix_island_lock`
> Displays the lock status of your island (Unlocked/Locked).

### `uskyfix_island_members`
> Displays how many members your island has.

## Building
This requires maven, a `uSkyBlock.jar` and a `MVdWPlaceholderAPI.jar` file. To build, simply place your jar files in the `lib` folder, and the run `mvn clean package` in the command line or in Eclipse run `Maven build...` with the goals of `clean package`.
