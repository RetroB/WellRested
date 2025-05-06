package com.minecraft.wellrested;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WellRested extends JavaPlugin implements Listener {

    private int saturationDuration;
    private int saturationAmplifier;
    private String wellRestedMessage;

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();
        
        // Load configuration
        loadConfig();
        
        // Register events
        getServer().getPluginManager().registerEvents(this, this);
        
        // Register commands
        getCommand("wellrested").setExecutor(new com.minecraft.wellrested.commands.ReloadCommand(this));
        
        getLogger().info("WellRested plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WellRested plugin has been disabled!");
    }
    
    /**
     * Loads configuration values from config.yml
     */
    public void loadConfig() {
        FileConfiguration config = getConfig();
        
        // Get configuration values with defaults if not present
        saturationDuration = config.getInt("saturation.duration", 60);
        saturationAmplifier = config.getInt("saturation.amplifier", 1);
        wellRestedMessage = ChatColor.translateAlternateColorCodes('&', 
                config.getString("messages.well-rested", "&aYou got a very good sleep, and now you're well rested!"));
        
        // Load detection method configuration
        useAlternativeDetection = config.getBoolean("detection.use_alternative_method", true);
        getLogger().info("Using alternative detection method: " + useAlternativeDetection);
    }
    
    // Configuration for alternative detection method
    private boolean useAlternativeDetection;
    
    /**
     * Handles when a player leaves a bed
     */
    @EventHandler
    public void onPlayerWakeUp(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        
        // Get the current world time
        long worldTime = player.getWorld().getTime();
        boolean sleptThroughNight = false;
        
        // Check if player slept through the night using one of two methods:
        // 1. Standard method: Check if it's morning (time between 0-1000)
        // 2. Alternative method: Check if the player actually slept (bed occupied) and now it's day
        if (worldTime >= 0 && worldTime <= 1000) {
            // Standard detection - it's morning now
            sleptThroughNight = true;
        } else if (useAlternativeDetection && player.getWorld().getTime() < 12500) {
            // Alternative detection - it's daytime (before sunset at 12500)
            // This helps with plugins that accelerate time instead of skipping it
            // We assume if they're leaving bed during the day, they likely slept
            sleptThroughNight = true;
        }
        
        if (sleptThroughNight) {
            // Apply saturation effect
            player.addPotionEffect(new PotionEffect(
                    PotionEffectType.SATURATION,
                    saturationDuration * 20, // Convert seconds to ticks (20 ticks = 1 second)
                    saturationAmplifier
            ));
            
            // Send well-rested message
            player.sendMessage(wellRestedMessage);
        }
    }
}
