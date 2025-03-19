package net.bricn.playerTeleportPlugin.commands

import net.bricn.playerTeleportPlugin.repository.TeleportRepository
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class TpaCommand(
    private val teleportRepository: TeleportRepository,
    private val plugin: Plugin
): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            val senderPlayer = sender as Player
            val requestedPlayer = Bukkit.getPlayer(args[0]) ?:
                throw RuntimeException("해당 플레이어를 찾을 수 없습니다.")

            if(senderPlayer == requestedPlayer) {
                throw RuntimeException("자신에게 텔레포트할 수 없습니다.")
            }

            if(teleportRepository.findBySenderPlayer(sender) != null){
                throw RuntimeException("이미 상대방에게 보낸 요청이 있습니다.")
            }

            val expiresMinute = 5L
            teleportRepository.putPlayer(senderPlayer, requestedPlayer, expiresMinute)

            requestedPlayer.sendMessage("""
            ${ChatColor.GRAY}${sender.name}님이 텔레포트 요청을 보냈습니다.
            ${ChatColor.RED}/tpaccept${ChatColor.GOLD}를 통해서 수락하거나,
            ${ChatColor.RED}/tpdeny${ChatColor.GOLD}로 거절할 수 있습니다.
            """.trimIndent())

            startCleanupTask(sender)

            return true
        } catch (e: Exception) {
            sender.sendMessage("${ChatColor.DARK_RED}${e.message.toString()}")
            return false
        }
    }

    private fun startCleanupTask(cleanPlayer: Player) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            teleportRepository.removeExpiredPlayer(cleanPlayer)
        }, 20L * 60, 20L * 60)
    }
}