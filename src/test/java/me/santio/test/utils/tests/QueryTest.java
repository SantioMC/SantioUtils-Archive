package me.santio.test.utils.tests;

import me.santio.test.utils.UtilPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueryTest implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (args.length < 1) {
            sender.sendMessage("Please type in anything!");
            UtilPlugin.santioUtils.queryUtils.awaitMessage((Player) sender, (res) -> sender.sendMessage("Response: "+res));
        } else if (args[0].equalsIgnoreCase("int")) {
            sender.sendMessage("Please type in an integer!");
            UtilPlugin.santioUtils.queryUtils.awaitMessage((Player) sender, (res) -> sender.sendMessage("Response: "+res), UtilPlugin.santioUtils::isInteger);
        }
        
        return true;
    }
    
}
