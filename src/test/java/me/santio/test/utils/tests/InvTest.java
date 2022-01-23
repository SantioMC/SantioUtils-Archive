package me.santio.test.utils.tests;

import me.santio.test.utils.ExampleEnum;
import me.santio.test.utils.UtilPlugin;
import me.santio.utils.CustomItem;
import me.santio.utils.inventories.CustomInventory;
import me.santio.utils.inventories.buttons.EnumButton;
import me.santio.utils.inventories.buttons.ListButton;
import me.santio.utils.inventories.buttons.ToggleButton;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvTest implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CustomInventory inventory = UtilPlugin.santioUtils.createInventory(3, "&3Something");
        
        inventory
            .border(new CustomItem(Material.BLACK_STAINED_GLASS_PANE))
            .setItem(10,
                new ToggleButton(
                    new CustomItem(Material.DIRT),
                    UtilPlugin.exampleBoolean,
                    (val) -> UtilPlugin.exampleBoolean = val
                )
            )
            .setItem(11,
                new ListButton(
                    new CustomItem(Material.FEATHER),
                    UtilPlugin.exampleString,
                    (val) -> UtilPlugin.exampleString = val,
                    "Option 1",
                    "Option 2",
                    "Option 3"
                )
            )
            .setItem(12,
                new EnumButton(
                    new CustomItem(Material.TNT),
                    UtilPlugin.exampleEnum,
                    ExampleEnum.class,
                    (val) -> UtilPlugin.exampleEnum = val
                )
            )
            .open((Player) sender);
        
        return true;
    }
    
}
