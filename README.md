# uSkyFix
This plugin allows you to hook into PlaceholderAPI (`%placeholder_name%`) as well as MVdWPlaceholderAPI (`{placeholder_name}`)

### Placeholders
`uskyfix_island_level`: This shows you the level of your island, rounded to 2 decimal places.
`uskyfix_island_leader`: This shows you the leader of the island you're *currently on* (not your own).
`uskyfix_island_global`: This shows you the level of the island you're *currently on* (not your own).

### Building
This requires maven, a `uSkyBlock.jar` and a `MVdWPlaceholderAPI.jar` file. To build, simply place your jar files in the `lib` folder, and the run `mvn clean package` in the command line or in Eclipse run `Maven build...` with the goals of `clean package`.
