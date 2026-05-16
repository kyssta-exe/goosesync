# 🦢 GooseSync - Advanced Latency Compensation Plugin

> **The Ultimate Solution for High-Ping Players on Minecraft Servers**

[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.16%20--%201.21.2-brightgreen.svg)](https://www.minecraft.net/)
[![Java Version](https://img.shields.io/badge/Java-8+-orange.svg)](https://adoptium.net/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 🌟 Overview

GooseSync is a revolutionary Minecraft plugin designed to eliminate the frustration of high latency gameplay. Whether you're playing on servers across the globe or experiencing network issues, GooseSync intelligently compensates for ping-related delays, providing a smooth and fair gaming experience for all players.

## ✨ Key Features

### 🥊 **Combat Compensation System**
- **Smart Knockback Adjustment**: Automatically adjusts knockback based on player ping
- **Damage Timing Optimization**: Compensates for latency in combat scenarios
- **Velocity Stabilization**: Prevents unfair knockback for high-ping players
- **Real-time Ping Monitoring**: Continuously tracks player connection quality

### 🍎 **Consumption Speed Enhancement**
- **Golden Apple Optimization**: Reduces consumption delays for high-ping players
- **Food Consumption Boost**: Faster eating mechanics for better gameplay flow
- **Smart Delay Reduction**: Automatically adjusts based on ping thresholds

### 🔮 **Ender Pearl Optimization**
- **Cooldown Reduction**: Reduces ender pearl cooldown for high-ping players
- **Teleportation Compensation**: Smoother pearl usage experience
- **Fair Play Balancing**: Maintains game balance while improving accessibility

### 🧪 **Potion Throw Enhancement**
- **Throw Speed Optimization**: Faster potion throwing for high-ping players
- **Splash Potion Support**: Enhanced mechanics for all potion types
- **Timing Compensation**: Reduces delay in potion usage

### 📊 **Advanced Ping Monitoring**
- **Real-time Tracking**: Monitors player ping every second
- **Automatic Compensation**: Applies optimizations based on ping levels
- **Performance Metrics**: Detailed logging for server administrators

## 🚀 Installation

### Prerequisites
- **Minecraft Server**: 1.16.x - 1.21.2 (Spigot, Paper, Purpur, or any Bukkit-based server)
- **Java**: Version 8 or higher (Java 17+ recommended for optimal performance)
- **Permissions**: Server restart capability

### Quick Installation Guide

1. **Download the Plugin**
   ```bash
   # Download goosesync-26.1.2.jar from the releases
   ```

2. **Install on Your Server**
   ```bash
   # Place the JAR file in your plugins folder
   cp goosesync-26.1.2.jar /path/to/your/server/plugins/
   ```

3. **Start Your Server**
   ```bash
   # Restart your server to load the plugin
   ./start.sh
   ```

4. **Verify Installation**
   ```
   [INFO] GooseSync has been enabled successfully!
   [INFO] Compatible with Minecraft versions 1.16 - 1.21.2
   ```

## ⚙️ Configuration

### Default Configuration
The plugin automatically creates a `plugins/GooseSync/config.yml` file:

```yaml
# Main plugin settings
enabled: true
ping-threshold: 100
knockback-multiplier: 0.8

# Consumption settings
consumption:
  enabled: true
  delay-reduction: 0.2

# Ender pearl settings
pearl:
  enabled: true
  cooldown-reduction: 0.2

# Potion settings
potions:
  enabled: true
  throw-delay-reduction: 0.2
```

### Configuration Options

| Setting | Default | Description |
|---------|---------|-------------|
| `enabled` | `true` | Master toggle for the plugin |
| `ping-threshold` | `100` | Minimum ping (ms) to trigger compensation |
| `knockback-multiplier` | `0.8` | Knockback reduction multiplier |
| `consumption.delay-reduction` | `0.2` | Food consumption speed boost (20%) |
| `pearl.cooldown-reduction` | `0.2` | Ender pearl cooldown reduction (20%) |
| `potions.throw-delay-reduction` | `0.2` | Potion throw speed boost (20%) |

## 🎮 Commands

### Player Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/gs` or `/goosesync` | Show plugin information | `goosesync.use` |
| `/gs help` | Display help menu | `goosesync.use` |
| `/gs version` | Show version details | `goosesync.use` |
| `/gs status` | Show live config/status | `goosesync.status` |

### Admin Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/gs reload` | Reload configuration | `goosesync.reload` |

### Example Usage
```
/gs
╔══════════════════════════════════════════╗
║              GooseSync              ║
╠══════════════════════════════════════════╣
║  A Plugin to fix all the latency issues!  ║
║  Version: 26.1.2                   ║
║  Made by Kyssta!                        ║
║  Kyssta Network: kyssta.xyz        ║
║  Server Version: 1_21_R0           ║
╚══════════════════════════════════════════╝
```

## 🔧 Permissions

### Permission Nodes
| Permission | Default | Description |
|------------|---------|-------------|
| `goosesync.use` | `true` | Allows use of basic commands |
| `goosesync.reload` | `op` | Allows configuration reload |

### Permission Examples
```yaml
# Give all players access to basic commands
permissions:
  goosesync.use:
    default: true

# Give only admins access to reload
permissions:
  goosesync.reload:
    default: op
```

## 📈 Performance Impact

### Minimal Overhead
- **CPU Usage**: < 1% additional server load
- **Memory**: ~2MB RAM usage
- **Network**: Minimal packet overhead
- **Compatibility**: Works with all major server software

### Optimization Features
- **Efficient Ping Monitoring**: Updates every second with minimal impact
- **Smart Compensation**: Only applies when needed (ping > threshold)
- **Version-Specific Optimizations**: Tailored for each Minecraft version

## 🌍 Server Compatibility

### Supported Server Software
- ✅ **Spigot** - Full compatibility
- ✅ **Paper** - Enhanced performance
- ✅ **Purpur** - Full feature support
- ✅ **Any Bukkit-based server** - Universal compatibility

### Minecraft Version Support
| Version | Status | Features |
|---------|--------|----------|
| 1.16.x | ✅ Full Support | Basic compensation |
| 1.17.x | ✅ Full Support | Enhanced cooldown API |
| 1.18.x | ✅ Full Support | Performance optimizations |
| 1.19.x | ✅ Full Support | All features |
| 1.20.x | ✅ Full Support | New combat system |
| 1.21 - 1.21.2 | ✅ Full Support | Latest optimizations |

## 🛠️ Troubleshooting

### Common Issues

**Plugin won't start:**
```
[ERROR] Error occurred while enabling GooseSync
```
**Solution**: Ensure you're using Java 8+ and a supported Minecraft version (1.16+)

**Commands not working:**
```
Unknown command. Type /help for help.
```
**Solution**: Restart your server after installation

**No compensation effects:**
```
[INFO] Detected server version: 1_21_R0
```
**Solution**: Check if player ping is above the threshold (default: 100ms)

### Debug Information
Enable debug logging by setting `debug: true` in config.yml:
```yaml
debug: true
```

## 📊 Metrics & Monitoring

### Server Logs
The plugin provides detailed logging:
```
[INFO] GooseSync has been enabled successfully!
[INFO] Compatible with Minecraft versions 1.16 - 1.21.2
[INFO] Detected server version: 1_21_R0
```

### Performance Monitoring
Monitor plugin performance through:
- Server console logs
- Plugin command output
- Built-in metrics

## 🔄 Updates & Maintenance

### Automatic Features
- **Version Detection**: Automatically detects server version
- **Compatibility Checks**: Ensures plugin works with your setup
- **Graceful Degradation**: Falls back to compatible methods

### Manual Updates
1. Download the latest version
2. Stop your server
3. Replace the old JAR file
4. Start your server

## 💡 Best Practices

### Server Configuration
- **Java Version**: Use Java 17+ for optimal performance
- **Server Software**: Paper or Purpur recommended
- **Memory**: Ensure adequate RAM allocation

### Plugin Settings
- **Ping Threshold**: Adjust based on your player base
- **Compensation Levels**: Start with defaults, adjust as needed
- **Monitoring**: Enable debug mode for troubleshooting

## 🆘 Support

### Getting Help
- **Documentation**: Check this README first
- **Issues**: Report bugs with server logs
- **Questions**: Contact support with detailed information

### Required Information
When seeking support, please provide:
- Minecraft version
- Server software (Spigot/Paper/Purpur)
- Java version
- Plugin version
- Error logs
- Configuration file

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Credits

**Developed by:** Kyssta  
**Website:** [kyssta.xyz](https://kyssta.xyz)  
**Version:** 26.1.2

---

**Thank you for choosing GooseSync!** 🦢

*Transform your high-ping gameplay experience today.* 
