package cn.cubegarden.miraiwhitelist.file

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.ConfigSection
import taboolib.module.configuration.Configuration
import java.util.concurrent.ForkJoinPool

object QQBindConfig {
    @Config("qqbind.yml", autoReload = true)
    private lateinit var file: Configuration

    @ConfigNode("qqbinds", bind = "qqbind.yml")
    lateinit var qqBinds: ConfigSection

    fun save() {
        ForkJoinPool.commonPool().submit { file.saveToFile() }
    }
}
