package net.bricn.onServerPaper

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class OnServerPaper : JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val joinMessage = if(player.hasPlayedBefore()) "${event.player.name}님이 접속함" else "${event.player.name}님이 처음으로 접속함"
        val textComponent = Component
            .text(joinMessage, TextColor.color(220, 220, 0))
        event.joinMessage(textComponent)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val textComponent = Component
            .text("${event.player.name}님의 연결이 끊김", TextColor.color(220, 0, 0))
        event.quitMessage(textComponent)
    }
}
