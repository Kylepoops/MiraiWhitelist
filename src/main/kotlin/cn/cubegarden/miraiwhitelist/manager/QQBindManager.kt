package cn.cubegarden.miraiwhitelist.manager

import cn.cubegarden.miraiwhitelist.file.QQBindConfig
import java.util.*

object QQBindManager {
    fun bind(qq: Long, id: UUID) {
        QQBindConfig.qqBinds[qq.toString()] = id.toString()
        QQBindConfig.save()
    }

    fun unbind(qq: Long) {
        QQBindConfig.qqBinds[qq.toString()] = null
        QQBindConfig.save()
    }

    fun getQQ(id: UUID): Long? {
        val keys = QQBindConfig.qqBinds.getKeys(false)
        for (key in keys) {
            if (QQBindConfig.qqBinds[key] == id.toString()) {
                return key.toLong()
            }
        }
        return null
    }

    fun getID(qq: Long): UUID? {
        return UUID.fromString(QQBindConfig.qqBinds[qq.toString()] as String)
    }

    fun hasBind(qq: Long): Boolean {
        return QQBindConfig.qqBinds.contains(qq.toString())
    }
}
