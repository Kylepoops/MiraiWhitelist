package cn.cubegarden.miraiwhitelist.file

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration
import java.util.*
import java.util.concurrent.ForkJoinPool

object WhitelistConfig {
    @Config("whitelist.yml", autoReload = true)
    private lateinit var file: Configuration

    @ConfigNode("UUIDs", bind = "whitelist.yml")
    lateinit var UUIDs: List<String>

    @ConfigNode("temp-names", bind = "whitelist.yml")
    lateinit var tempNames: List<String>

    fun save() {
        ForkJoinPool.commonPool().submit { file.saveToFile() }
    }
}
