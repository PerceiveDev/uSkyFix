# uSkyFix
This plugin allows you to hook into PlaceholderAPI (`%placeholder_name%`) as well as MVdWPlaceholderAPI (`{placeholder_name}`)

### Placeholders
* `uskyfix_island_level`: This shows you the level of your island, rounded to 2 decimal places.
* `uskyfix_island_leader`: This shows you the leader of the island you're *currently on* (not your own).
* `uskyfix_island_global`: This shows you the level of the island you're *currently on* (not your own).
* `uskyfix_island_biome`: This shows the biome of your island.
* `uskyfix_island_togglewarp`: This displays the status of your warp, Inactive/Active.
* `uskyfix_island_lock`: This displays the status of your lock, Unlocked/Locked.
* `uskyfix_island_members`: This displays how many members your island has.

### Building
This requires maven, a `uSkyBlock.jar` and a `MVdWPlaceholderAPI.jar` file. To build, simply place your jar files in the `lib` folder, and the run `mvn clean package` in the command line or in Eclipse run `Maven build...` with the goals of `clean package`.
