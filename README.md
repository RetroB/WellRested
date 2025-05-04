# WellRested Plugin

A simple Minecraft plugin that gives players a saturation effect when they wake up from a bed.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Features

- Gives players a saturation effect when they wake up from a bed
- Configurable duration and amplifier for the saturation effect
- Customizable message shown to players
- Simple reload command

## Installation

1. Download the latest release from the [Modrinth](https://modrinth.com/plugin/wellrested) page
2. Place the JAR file in your server's `plugins` folder
3. Restart your server or use a plugin manager to load the plugin
4. Edit the configuration file if needed

## Configuration

You can customize the plugin by editing the `config.yml` file in the plugin's folder:

```yaml
# Saturation effect settings
saturation:
  # Duration in seconds
  duration: 30
  # Amplifier level (0 = level I, 1 = level II, etc.)
  amplifier: 1

# Messages
messages:
  # Message shown when player wakes up
  well-rested: "&aYou got a very good sleep, and now you're well rested!"
```

### Configuration Options

- `saturation.duration`: How long the saturation effect lasts (in seconds)
- `saturation.amplifier`: The strength of the saturation effect (0 = level I, 1 = level II, etc.)
- `messages.well-rested`: The message shown to players when they wake up (supports color codes with &)

## Requirements

- Minecraft 1.21.x server (Paper, Spigot, or compatible fork)
- Java 17 or higher
