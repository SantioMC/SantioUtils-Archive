package me.santio.test.utils;

import me.santio.test.utils.tests.InvTest;
import me.santio.test.utils.tests.QueryTest;
import me.santio.utils.SantioUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilPlugin extends JavaPlugin {
    
    public static SantioUtils santioUtils;
    
    public static boolean exampleBoolean = false;
    public static int exampleInteger = 1;
    public static String exampleString = "Option 1";
    public static ExampleEnum exampleEnum = ExampleEnum.MySQL;
    
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        santioUtils = new SantioUtils(this);
        getServer().getPluginCommand("invtest").setExecutor(new InvTest());
        getServer().getPluginCommand("querytest").setExecutor(new QueryTest());
    }
    
}
