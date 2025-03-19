package net.bricn.playerTeleportPlugin.commands

import net.bricn.playerTeleportPlugin.repository.TeleportRepository
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TpAcceptCommand(
    private val teleportRepository: TeleportRepository
): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            val requestedPlayer = sender as Player
            val teleportRequest = teleportRepository.findByRequestedPlayer(requestedPlayer)
                ?: throw RuntimeException("텔레포트 요청을 찾을 수 없습니다.")

            teleportRequest.senderPlayer.teleport(requestedPlayer.location)
            teleportRepository.removeBySenderPlayer(teleportRequest.senderPlayer)

            return true
        } catch (e: Exception) {
            sender.sendMessage("${ChatColor.DARK_RED}${e.message.toString()}")
            return false
        }
    }
}