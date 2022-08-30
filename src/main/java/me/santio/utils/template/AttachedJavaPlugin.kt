package me.santio.utils.template

import lombok.Getter
import me.santio.utils.SantioUtils
import me.santio.utils.bukkit.AsyncUtils
import org.bukkit.plugin.java.JavaPlugin

abstract class AttachedJavaPlugin : JavaPlugin() {
    override fun onLoad() {
        utils = SantioUtils(this)
        scheduler = AsyncUtils(this)
    }

    companion object {
        @Getter
        private var utils: SantioUtils? = null

        @Getter
        private var scheduler: AsyncUtils? = null
    }
}