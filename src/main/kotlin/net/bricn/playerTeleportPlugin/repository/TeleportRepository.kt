package net.bricn.playerTeleportPlugin.repository

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.time.LocalDateTime

class TeleportRepository {
    // hashMapOf<요청한 플레이어, 요청받은 플레이어>()
    private val teleportRequestList = mutableListOf<TeleportRequest>()

    fun findBySenderPlayer(player: Player): TeleportRequest? {
        return teleportRequestList.find { it.requestedPlayer == player || it.senderPlayer == player }
    }

    fun findByRequestedPlayer(requestedPlayer: Player): TeleportRequest? {
        return teleportRequestList.find { it.requestedPlayer == requestedPlayer }
    }

    fun removeBySenderPlayer(senderPlayer: Player){
        teleportRequestList.removeIf { it.senderPlayer == senderPlayer }
    }

    fun putPlayer(senderPlayer: Player, requestedPlayer: Player, expiresMinute: Long) {
        if(teleportRequestList.find { it.senderPlayer == senderPlayer } != null) {
            throw RuntimeException("${ChatColor.DARK_RED}이미 보낸 요청이 존재합니다.")
        }

        val teleportRequest = TeleportRequest(
            senderPlayer = senderPlayer,
            requestedPlayer = requestedPlayer,
            expiredAt = LocalDateTime.now().plusMinutes(expiresMinute)
        )
        teleportRequestList.add(teleportRequest)
    }

    fun removeExpiredPlayer(senderPlayer: Player){
        val currentTime = LocalDateTime.now()
        teleportRequestList.removeIf { it.senderPlayer == senderPlayer && it.expiredAt.isBefore(currentTime) }
    }
}