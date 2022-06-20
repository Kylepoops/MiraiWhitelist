package cn.cubegarden.miraiwhitelist.listener

import cn.cubegarden.miraiwhitelist.manager.WhitelistManager
import org.bukkit.event.player.PlayerLoginEvent
import taboolib.common.platform.event.SubscribeEvent

class BukkitListener {
    @SubscribeEvent
    fun onPlayerJoin(event: PlayerLoginEvent) {
        if (!WhitelistManager.isWhitelisted(event.player)) {
            event.result = PlayerLoginEvent.Result.KICK_WHITELIST
        }
    }
}
