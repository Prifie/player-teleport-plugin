package net.bricn.utilityPlugin

import net.bricn.utilityPlugin.commands.TpAcceptCommand
import net.bricn.utilityPlugin.commands.TpaCommand
import net.bricn.utilityPlugin.events.PlayerEvent
import net.bricn.utilityPlugin.repository.TeleportRepository
import org.bukkit.plugin.java.JavaPlugin

class UtilityPlugin: JavaPlugin() {
    companion object {
        private val teleportRepository = TeleportRepository()
    }

    override fun onEnable() {
        server
            .getPluginCommand("tpa")
            ?.setExecutor(TpaCommand(teleportRepository, this@UtilityPlugin))

        server
            .getPluginCommand("tpaccept")
            ?.setExecutor(TpAcceptCommand(teleportRepository))

        server
            .pluginManager
            .registerEvents(PlayerEvent(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
