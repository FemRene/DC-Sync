# DC-Sync Documentation

## Overview
DC-Sync is a comprehensive Minecraft-Discord integration system that synchronizes player accounts between Minecraft and Discord. The system consists of two components:

1. **DC-Sync_Paper** - A Paper plugin for Minecraft servers
2. **DC-Sync_BungeeCord** - A BungeeCord plugin for proxy servers (in development)

This integration allows server administrators to link player accounts, automatically assign Discord roles based on in-game permissions, and maintain synchronized user data across platforms.

## Components

### DC-Sync_Paper
The Paper plugin component handles:
- Player verification through a code-based system
- Discord role assignment based on LuckPerms groups
- Discord nickname prefixing based on player groups
- Database storage of verification data

[View DC-Sync_Paper Documentation](./DC-Sync_Paper/README.md)

### DC-Sync_BungeeCord
The BungeeCord plugin component (in development) will handle:
- Network-wide verification across multiple servers
- Centralized management of Discord integration
- Cross-server synchronization

## System Requirements

### Minecraft Server
- Paper 1.20+
- LuckPerms plugin
- MySQL database

### Discord
- Discord Bot with appropriate permissions:
  - Manage Roles
  - Manage Nicknames
  - Read/Send Messages
  - Use Slash Commands

## Installation
Please refer to the individual component documentation for specific installation instructions:
- [DC-Sync_Paper Installation](./DC-Sync_Paper/README.md#installation)
- DC-Sync_BungeeCord Installation (Coming Soon)

## Configuration
Each component has its own configuration file. Please refer to the individual documentation for details:
- [DC-Sync_Paper Configuration](./DC-Sync_Paper/README.md#configuration)
- DC-Sync_BungeeCord Configuration (Coming Soon)

## How It Works
1. Players initiate verification in-game using the `/verify` command
2. A unique verification code is generated and displayed to the player
3. The player enters this code in Discord using the `/verify` slash command
4. The system verifies the code, links the accounts, and assigns appropriate Discord roles and nickname prefixes
5. The verification data is stored in a database for persistence

## Support
For support, please contact the plugin author or open an issue on the plugin's repository.

## Credits
Developed by FemRene
Website: https://femrene.dev/