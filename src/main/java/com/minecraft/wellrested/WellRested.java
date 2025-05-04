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
    }
    
    /**
     * Handles when a player leaves a bed
     */
    @EventHandler
    public void onPlayerWakeUp(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        
        // Check if it's morning (player actually slept through the night)
        // Time is between 0 and 24000, with 0 being sunrise
        long worldTime = player.getWorld().getTime();
        if (worldTime >= 0 && worldTime <= 1000) {
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