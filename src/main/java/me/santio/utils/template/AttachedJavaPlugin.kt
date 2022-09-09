package me.santio.utils.template

import lombok.Getter
import me.santio.utils.SantioUtils
import me.santio.utils.bukkit.AsyncUtils
import org.bukkit.plugin.java.JavaPlugin

abstract class AttachedJavaPlugin : JavaPlugin() {
    override fun onEnable() {
        utils = SantioUtils(this)
        scheduler = AsyncUtils(this)
        SantioUtils.register(this)
    }

    companion object {
        @JvmStatic
        var utils: SantioUtils? = null

        @JvmStatic
        var scheduler: AsyncUtils? = null
    }
}