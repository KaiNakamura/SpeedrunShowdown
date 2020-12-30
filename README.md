[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://github.com/KaiNakamura/SpeedrunShowdown/blob/master/LICENSE)
[![maven](https://github.com/KaiNakamura/SpeedrunShowdown/workflows/maven/badge.svg)](https://github.com/KaiNakamura/SpeedrunShowdown/actions)

<p align="center">
	<a href="https://github.com/KaiNakamura/SpeedrunShowdown">
		<img src="logo.png" alt="logo" width="250" height="250"/>
	</a>
</p>

## Table of Contents

* [About](#about)
* [Installation](#installation)
* [Setup](#setup)
	* [Teams](#teams)
	* [Commands](#commands)
* [Config](#config)
* [Issues](#issues)

## About

This project is a Minecraft PvP and speedrunning plugin. Teams of players race to be the first to kill the Ender dragon. All players are equipped with a tracking compass that will point to the nearest enemy or teammate.

## Installation

Download the latest version from [releases](https://github.com/KaiNakamura/SpeedrunShowdown/releases) and place the JAR file in the plugins folder of your server

## Setup

### Teams

Before you begin the game you will want to create teams

To add players to a team run
```
/team join <team> [<members>]
```
For more team commands see the [minecraft wiki](https://minecraft.gamepedia.com/Commands/team)

The default team names are the following, but can be edited in the [config](#config):
* Redstone
* Lapis
* Emerald
* Gold
* Diamond
* Quartz
* Purpur
* Iron
* Crimson
* Ice
* Slime
* Glowstone
* Prismarine
* Netherite
* Chorus
* Obsidian

### Commands

| Command | Description | Notes |
| --- | --- | --- |
| `/start` | Start the game | |
| `/stop` | Stop the game | |
| `/suddendeath` | Begin sudden death immediately | |
| `/settime <seconds>` | Set the time until sudden death | The default time can be changed in the [config](#config) file, this command is to be used if something goes wrong such as the server crashing unexpectedly |
| `/givecompass <player>` | Give a player a tracking compass | All players are given a compass at the start of the game, this command is to be used if a player doesn't receive a compass) |
| `/win <team \| player>` | Declare the winning team | The winning team automatically declared when dragon is killed, this command is to be used if something goes wrong such as the dragon being killed by an entity other than a player |

*If a command from this plugin conflicts with a command from another plugin use the prefix `/speedrunshowdown:<command>`*

## Config

To edit the default configuration of the game navigate to the [config.yml](https://github.com/KaiNakamura/SpeedrunShowdown/blob/master/src/main/resources/config.yml) file in the plugins folder of your server under `SpeedrunShowdown\config.yml`

| Attribute | Default | Description | Notes |
| --- | --- | --- | --- |
| `sudden-death-time` | 3600 *seconds* | The time in seconds until sudden death |  |
| `warning-times` | [60, 30, 15, 10, 5, 4, 3, 2, 1] *seconds* | The times in seconds at which a warning will be given before sudden death |  |
| `indestructable-spawners` | true | Make spawners indestructable |  |
| `prevent-bed-explosions` | true | Prevent players from exploding beds | Beds can still be used in the overworld |
| `prevent-respawn-anchor-explosions` | true | Prevent players from exploding respawn anchors | Respawn anchors can still be used in the nether |
| `must-kill-dragon-to-win` | true | Players must kill the dragon to win | If set to false, the last remaining team will be declared the winner, otherwise if all teams are dead players will be respawned |
| `teams` | See [teams](#teams) | The team names and colors | A team requires a name and a color, `name: "COLOR"`, the color must be a [minecraft chat color](https://minecraft.gamepedia.com/Formatting_codes): BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE |

## Issues

To report a bug or to request a feature go [here](https://github.com/KaiNakamura/SpeedrunShowdown/issues)
