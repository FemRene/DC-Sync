# DC-Sync_Paper Documentation

## Overview
DC-Sync_Paper is a Minecraft Paper plugin that synchronizes player accounts between Minecraft and Discord. It allows players to verify their Minecraft account with their Discord account, and automatically assigns Discord roles based on their Minecraft permissions groups.

## Features
- Link Minecraft accounts to Discord accounts
- Automatically assign Discord roles based on LuckPerms groups
- Set Discord nickname prefixes based on LuckPerms groups
- Secure verification process using randomly generated codes
- MySQL database storage for persistent verification data

## Dependencies
- [Paper](https://papermc.io/) 1.20+
- [LuckPerms](https://luckperms.net/) for permission group management

## Installation
1. Download the latest version of DC-Sync_Paper
2. Place the JAR file in your server's `plugins` folder
3. Start or restart your server to generate the configuration file
4. Configure the plugin settings in `plugins/DC-Sync_Paper/config.yml`
5. Restart your server again to apply the changes

## Configuration
The plugin's configuration file (`config.yml`) contains several sections:

### Storage Configuration
```yaml
storageType: "mysql"  # Currently only MySQL is supported
```

### MySQL Configuration
```yaml
mysql:
  host: "localhost"  # Your MySQL server address
  port: 3306         # Your MySQL server port
  user: "root"       # MySQL username
  password: "your password"  # MySQL password
  database: "DC-Sync"  # Database name
  ssl: true          # Whether to use SSL for the connection
```

### Discord Configuration
```yaml
discord:
  BotToken: "your-token-here"  # Your Discord bot token
  serverID: "000000000000000000"  # Your Discord server ID
```

### LuckPerms Configuration
```yaml
luckperms:
  roleMeta: "dcGroup"   # Metadata key for Discord role IDs
  nameMeta: "dcPrefix"  # Metadata key for Discord name prefixes
```

## Setting Up LuckPerms
The plugin uses LuckPerms metadata to determine which Discord roles and name prefixes to assign to players. You need to set up these metadata values for your LuckPerms groups:

1. Set the Discord role ID for a group:
   ```
   /lp group <group> meta set dcGroup.<discord_role_id> true
   ```

2. Set the Discord name prefix for a group:
   ```
   /lp group <group> meta set dcPrefix.<prefix> true
   ```

## Commands
### In-Game Commands
- `/verify` - Generates a verification code for the player to use in Discord

### Discord Commands
- `/verify <code>` - Verifies a Discord account with a Minecraft account using the provided code

## How It Works
1. A player runs the `/verify` command in Minecraft
2. The plugin generates a random 6-character code and displays it to the player
3. The player uses the `/verify <code>` slash command in Discord
4. If the code is valid, the plugin:
   - Links the Minecraft UUID to the Discord ID in the database
   - Assigns the appropriate Discord role based on the player's LuckPerms group
   - Sets the player's Discord nickname with the appropriate prefix
   - Confirms the verification to the player

## Troubleshooting
### Discord Bot Not Connecting
- Ensure your bot token is correct
- Make sure the bot has been invited to your server with the correct permissions
- Check that the server ID in the configuration is correct

### Database Connection Issues
- Verify your MySQL credentials are correct
- Ensure the MySQL server is running and accessible
- Check that the database exists and the user has appropriate permissions

### Role Assignment Not Working
- Ensure the Discord bot has permission to manage roles
- Verify that the role IDs in LuckPerms metadata are correct
- Make sure the bot's role is higher in the hierarchy than the roles it needs to assign

### Verification Not Working
- Check the console for any error messages
- Ensure the player is not already verified
- Verify that the verification code is being entered correctly in Discord

## Support
For support, please contact the plugin author or open an issue on the plugin's repository.

## Credits
Developed by FemRene
Website: https://femrene.dev/