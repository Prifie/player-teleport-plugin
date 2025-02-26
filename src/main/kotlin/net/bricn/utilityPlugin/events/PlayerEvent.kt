package net.bricn.utilityPlugin.events

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerEvent: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val joinMessage = "${ChatColor.YELLOW}${event.player.name}${if(player.hasPlayedBefore()) "님이 접속함" else "님이 처음으로 접속함"}"
        event.joinMessage = joinMessage
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = "${ChatColor.RED}${event.player.name}님의 연결이 끊김"
    }
}