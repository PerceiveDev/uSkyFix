# uSkyFix
This plugin allows you to hook into PlaceholderAPI (via `%uskyfix_island_level%` and `%uskyfix_island_leader%`) as well as MVdWPlaceholderAPI (via `{uskyfix_island_level}` and `{uskyfix_island_leader}`)

### Placeholders
`uskyfix_island_level`: This shows you the level of your island, rounded to 2 decimal places.
`uskyfix_island_leader`: This shows you the leader of the island you're *currently on* (not your own).

### Building
This requires both maven and a `uSkyBlock.jar` file. To build, simply place your `uSkyBlock.jar` file in the `lib` folder, and the run `mvn clean package` in the command line or in Eclipse run `Maven build...` with the goals of `clean package`.
