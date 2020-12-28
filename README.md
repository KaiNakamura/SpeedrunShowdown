<p alight="center">
	<a href="https://github.com/KaiNakamura/SpeedrunShowdown">
		<img src="logo.png" alt="logo" width="250" height="250">
	</a>
</p>

## Table of Contents

* [About](#about)
* [Installation](#installation)
* [Setup](#setup)
	* [Teams](#teams)
	* [Commands](#commands)
* [Config](#config)

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

The default team names are but can be edited in the [config](#config):
* Redstone
* Lapis
* Emerald
* Gold
* Diamond
* Quartz
* Purpur
* Iron

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

To set the time until sudden death run
```
/speedrunshowdown:settime <seconds>
```

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