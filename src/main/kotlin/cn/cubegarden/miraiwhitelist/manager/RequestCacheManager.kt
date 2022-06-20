package cn.cubegarden.miraiwhitelist.manager

import cn.cubegarden.miraiwhitelist.entity.WhitelistRequest

object RequestCacheManager {
    private val cache = mutableMapOf<Long, WhitelistRequest>()

    fun addCache(id: Long, request: WhitelistRequest) {
        cache[id] = request
    }

    fun removeCache(id: Long) {
        cache.remove(id)
    }

    fun queryRequestByQQ(qq: Long): WhitelistRequest? {
        return cache[qq]
    }
}
