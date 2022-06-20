package cn.cubegarden.miraiwhitelist.entity

import java.util.*
import kotlin.random.Random

data class WhitelistRequest(
    val playerName: String,
    val uuid: UUID,
    val requester: Long,
) {
    val requestId: Int = Random.nextInt(1000, 9999)
}
