package cn.cubegarden.miraiwhitelist.listener

import cn.cubegarden.miraiwhitelist.entity.WhitelistRequest
import cn.cubegarden.miraiwhitelist.file.MainConfig
import cn.cubegarden.miraiwhitelist.manager.QQBindManager
import cn.cubegarden.miraiwhitelist.manager.RequestCacheManager
import cn.cubegarden.miraiwhitelist.manager.RequestManager
import cn.cubegarden.miraiwhitelist.manager.UUIDManager
import cn.cubegarden.miraiwhitelist.manager.WhitelistManager
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import taboolib.common.platform.event.SubscribeEvent

object MiraiListener {
    @SubscribeEvent
    fun onRequest(event: MiraiGroupMessageEvent) {
        if (!event.message.startsWith("#request ")) return
        if (event.message.split(" ").size != 2) {
            event.group.sendMessage("参数错误")
            return
        }

        if (RequestCacheManager.queryRequestByQQ(event.senderID) != null) {
            event.group.sendMessage("有一个待确认请求正在进行中")
            return
        }

        if (QQBindManager.hasBind(event.senderID)) {
            event.group.sendMessage("你已经申请了白名单，无法发起请求")
            return
        }

        val playerName = event.message.split(" ")[1]
        if (playerName.isEmpty()) return

        UUIDManager.queryUUIDAsync(playerName)
            .thenAccept {
                val request = WhitelistRequest(playerName, it, event.senderID)
                RequestCacheManager.addCache(event.senderID, request)
                event.group.sendMessage(
                    """
                    请确认UUID是否正确，如果正确，请输入#confirm
                    UUID: $it
                    """.trimIndent()
                )
            }
            .exceptionally {
                event.group.sendMessage("查询UUID失败")
                null
            }
    }

    @SubscribeEvent
    fun onCancel(event: MiraiGroupMessageEvent) {
        if (!event.message.startsWith("#cancel")) return
        val request = RequestCacheManager.queryRequestByQQ(event.senderID)

        if (request == null) {
            event.group.sendMessage("没有待确认请求")
            return
        }

        RequestCacheManager.removeCache(event.senderID)

        event.group.sendMessage("已取消待确认请求")
    }

    @SubscribeEvent
    fun onConfirm(event: MiraiGroupMessageEvent) {
        if (!event.message.startsWith("#confirm")) return

        val request = RequestCacheManager.queryRequestByQQ(event.senderID)
        if (request == null) {
            event.group.sendMessage("你还没有申请白名单")
            return
        }

        RequestCacheManager.removeCache(event.senderID)

        RequestManager.addRequest(request)
        event.group.sendMessage("申请成功")
    }

    @SubscribeEvent
    fun onAccept(event: MiraiGroupMessageEvent) {
        if (!event.message.startsWith("#accept ")) return
        if (!MainConfig.admins.any { it == event.senderID }) {
            event.group.sendMessage("你不是管理员，无法接受请求")
            return
        }
        if (event.message.split(" ").size != 2) {
            event.group.sendMessage("参数错误")
            return
        }

        val id = event.message.split(" ")[1]
        if (id.isEmpty()) return

        val request = RequestManager.queryRequestById(id.toInt())

        if (request == null) {
            event.group.sendMessage("请求不存在")
            return
        }

        if (QQBindManager.hasBind(request.requester)) {
            event.group.sendMessage("对方已经拥有了白名单，无法同意该请求")
            return
        }

        WhitelistManager.acceptRequest(request)
        event.group.sendMessage("请求已同意")
    }

    @SubscribeEvent
    fun onRequestListQuery(event: MiraiGroupMessageEvent) {
        if (!event.message.startsWith("#requests")) return
        if (!MainConfig.admins.any { it == event.senderID }) {
            event.group.sendMessage("你不是管理员，无法查询请求列表")
            return
        }

        val requests = RequestManager.getRequests()
        if (requests.isEmpty()) {
            event.group.sendMessage("暂无请求")
            return
        }

        val sb = StringBuilder()
        requests.forEach {
            sb.append("${it.requestId}: ${it.requester} - ${it.playerName}")
            sb.appendLine()
        }

        event.group.sendMessage(sb.toString())
    }
}
