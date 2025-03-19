package net.bricn.playerTeleportPlugin

import net.bricn.playerTeleportPlugin.commands.TpAcceptCommand
import net.bricn.playerTeleportPlugin.commands.TpDenyCommand
import net.bricn.playerTeleportPlugin.commands.TpaCommand
import net.bricn.playerTeleportPlugin.repository.TeleportRepository
import org.bukkit.plugin.java.JavaPlugin

class PlayerTeleportPlugin: JavaPlugin() {
    companion object {
        private val teleportRepository = TeleportRepository()
    }

    override fun onEnable() {
        server
            .getPluginCommand("tpa")
            ?.setExecutor(TpaCommand(teleportRepository, this@PlayerTeleportPlugin))

        server
            .getPluginCommand("tpaccept")
            ?.setExecutor(TpAcceptCommand(teleportRepository))

        server
            .getPluginCommand("tpdeny")
            ?.setExecutor(TpDenyCommand(teleportRepository))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
