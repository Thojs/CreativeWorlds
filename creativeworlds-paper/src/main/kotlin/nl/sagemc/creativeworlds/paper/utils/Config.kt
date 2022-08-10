package nl.sagemc.creativeworlds.paper.utils

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class Config(private val file: File) : YamlConfiguration() {
    init {
        if (file.isDirectory) throw IllegalArgumentException("File cannot be a directory.")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        super.load(file)
    }

    fun save() {
        save(file)
    }

    fun clear() {
        getKeys(false).forEach {
            set(it, null)
        }
        save()
    }
}