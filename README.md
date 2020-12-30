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

Download the latest version from [releases](https://github.com/KaiNakamura/SpeedrunShowdown/releases) and place the JAR file in the plugins folder of your server.

## Setup

### Teams

Before you begin the game you will want to create teams

To add players to a team run
```
/team join <team> [<members>]
```

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

| Command | Description |
| --- | --- |
| `/start` | Start the game |
| `/stop` | Stop the game |
| `/suddendeath` | Begin sudden death immediately |
| `/settime <seconds>` | Set the time until sudden death (change the default time in the [config](#config)) |
| `/givecompass <player>` | Give a player a tracking compass (all players are given a compass at the start of the game, to be used if player doesn't receive a compass) |
| `/win <team \| player>` | Declare the winning team (winning team automatically declared when dragon is killed, to be used if something goes wrong such as the dragon being killed by an entity other than a player) |

*If a command from this plugin conflicts with a command from another plugin use the prefix `/speedrunshowdown:<command>`*

## Config

To edit the default configuration of the game edit the [config.yml](https://github.com/KaiNakamura/SpeedrunShowdown/blob/master/src/main/resources/config.yml) file created by the plugin located in plugins\SpeedrunShowdown\config.yml

## Issues

To report a bug or to request a feature go [here](https://github.com/KaiNakamura/SpeedrunShowdown/issues)
