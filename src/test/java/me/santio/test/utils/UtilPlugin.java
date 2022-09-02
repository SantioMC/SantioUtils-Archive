package me.santio.test.utils;

import me.santio.utils.bukkit.Book;
import me.santio.utils.commands.CommandBuilder;
import me.santio.utils.commands.CommandHandler;
import me.santio.utils.inventory.CustomInventory;
import me.santio.utils.inventory.Slots;
import me.santio.utils.item.CustomItem;
import me.santio.utils.query.QueryUtils;
import me.santio.utils.template.AttachedJavaPlugin;
import me.santio.utils.text.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UtilPlugin extends AttachedJavaPlugin {
    
    @Override
    public void onEnable() {
        super.onEnable();
        
        Player player = Bukkit.getPlayer("santio71");
        if (player == null) return;
        
        new CustomInventory(4, "I got pagination!")
            .set(Slots.getALL(), new CustomItem(Material.GRAY_STAINED_GLASS_PANE, "&7"))
            .paginate(
                Slots.rect(10, 25),
                InventoryItemList.class,
                (value) -> new CustomItem(value.getMaterial(), value.getName()),
                (item) -> (event) -> {
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage("You clicked on " + item.getName());
                }
            )
            .addBackButton(27, new CustomItem(Material.ARROW, "&cBack"))
            .addForwardButton(35, new CustomItem(Material.ARROW, "&aForward"))
            .onPageChange((inventory, page) -> inventory.rename("I got pagination! Page " + page))
            .open(player);
    
        QueryUtils.query(player, "&aEnter punishment reason")
            .timeout(5)
            .cancellable()
            .send((response) -> {
                player.sendMessage("You entered " + response);
            });
    
        CommandHandler.simple("test", (data) -> {
            data.getSender().sendMessage("You executed the command!");
        });
        
        CustomItem.book()
            .page(1, "&aHello world!")
            .page(2, new Message("&aHover over me!").hover("Hey ;)"))
            .open(player);
        
        new CommandBuilder("test")
            .description("Test command")
            .aliases("t")
            .permission("test.command")
            .onExecute((data) -> {
                data.getSender().sendMessage("You executed the command!");
            })
            .create();
        
        new Message("&cYou are a nerd, click ")
            .add(new Message("here").url("https://www.youtube.com/watch?v=dQw4w9WgXcQ").hover("&aClick me ;)"))
            .add(new Message("!"))
            .send(player);
    }
    
}
