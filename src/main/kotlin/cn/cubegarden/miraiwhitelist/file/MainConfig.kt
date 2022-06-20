package cn.cubegarden.miraiwhitelist.file

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

object MainConfig {
    @Config(autoReload = true)
    private lateinit var file: Configuration

    @ConfigNode("admins")
    lateinit var admins: List<Long>
}
