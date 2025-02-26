package net.bricn.utilityPlugin.repository

import org.bukkit.entity.Player
import java.time.LocalDateTime

class TeleportRequest (
    val senderPlayer: Player,
    val requestedPlayer: Player,
    val expiredAt: LocalDateTime
)