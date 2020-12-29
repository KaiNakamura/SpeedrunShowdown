![maven](https://github.com/KaiNakamura/SpeedrunShowdown/workflows/maven/badge.svg)

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
* Obsidian
* Chorus
* Netherite

### Commands

To start the game run
```
/speedrunshowdown:start
```

To stop the game run
```
/speedrunshowdown:stop
```

To begin sudden death early run
```
/speedrunshowdown:suddendeath
```
*Sudden death begins automatically in 1 hour but can be started early with this command*

To set the time until sudden death run
```
/speedrunshowdown:settime <seconds>
```
*Sudden death time is automatically set to whatever value is set in the [config](#config) but this command can manually change it should something go wrong, such as the server crashing unexpectedly*

To give a player a tracking compass run
```
/speedrunshowdown:givecompass <player>
```
*All players will automatically be given a compass at the start of the game, but this command can be run in case a player doesn't receive a compass*

To declare the winning team early run
```
/speedrunshowdown:win <team | player>
```
*The winning team will be declared automatically when the Ender dragon is killed, but this command can be run in case something goes wrong, such as the dragon being killed by an entity other than a player*

## Config

To edit the default configuration of the game edit the [config.yml](https://github.com/KaiNakamura/SpeedrunShowdown/blob/master/src/main/resources/config.yml) file created by the plugin located in plugins\SpeedrunShowdown\config.yml

## Issues

To report a bug or to request a feature go [here](https://github.com/KaiNakamura/SpeedrunShowdown/issues)