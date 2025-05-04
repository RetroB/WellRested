package com.minecraft.wellrested.commands;

import com.minecraft.wellrested.WellRested;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final WellRested plugin;

    public ReloadCommand(WellRested plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            // Reload the configuration
            plugin.reloadConfig();
            plugin.loadConfig();
            
            // Send confirmation message
            sender.sendMessage(ChatColor.GREEN + "WellRested configuration has been reloaded!");
            return true;
        }
        
        // If we get here, the command wasn't used correctly
        sender.sendMessage(ChatColor.RED + "Usage: /wellrested reload");
        return false;
    }
}