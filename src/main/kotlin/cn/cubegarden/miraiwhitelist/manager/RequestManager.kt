package cn.cubegarden.miraiwhitelist.manager

import cn.cubegarden.miraiwhitelist.entity.WhitelistRequest

object RequestManager {
    private val requests = mutableSetOf<WhitelistRequest>()

    fun addRequest(request: WhitelistRequest) {
        requests.add(request)
    }

    fun removeRequest(request: WhitelistRequest) {
        requests.remove(request)
    }

    fun hasRequest(request: WhitelistRequest) = requests.contains(request)

    fun clearRequests() {
        requests.clear()
    }

    fun getRequests() = requests.toList()

    fun queryRequestByName(name: String) = requests.singleOrNull { it.playerName == name }

    fun queryRequestById(id: Int) = requests.singleOrNull { it.requestId == id }
}
