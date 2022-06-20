package cn.cubegarden.miraiwhitelist.manager

import cn.cubegarden.miraiwhitelist.entity.WhitelistRequest
import cn.cubegarden.miraiwhitelist.file.WhitelistConfig
import org.bukkit.entity.Player
import java.util.*

object WhitelistManager {
    fun addWhitelist(playerName: String) {
        WhitelistConfig.tempNames += playerName
        WhitelistConfig.save()
    }

    fun addWhitelist(id: UUID) {
        WhitelistConfig.UUIDs += id.toString()
        WhitelistConfig.save()
    }

    fun removeWhitelist(playerName: String) {
        WhitelistConfig.tempNames -= playerName
        WhitelistConfig.save()
    }

    fun removeWhitelist(id: UUID) {
        WhitelistConfig.UUIDs -= id.toString()
        WhitelistConfig.save()
    }

    fun isWhitelisted(player: Player): Boolean {
        return WhitelistConfig.UUIDs.contains(player.uniqueId.toString())
    }

    fun acceptRequest(request: WhitelistRequest) {
        addWhitelist(request.uuid)
        QQBindManager.bind(request.requester, request.uuid)
        RequestManager.removeRequest(request)
    }
}
