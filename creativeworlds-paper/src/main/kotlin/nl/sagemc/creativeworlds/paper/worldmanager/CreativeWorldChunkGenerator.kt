package nl.sagemc.creativeworlds.paper.worldmanager

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*

class CreativeWorldChunkGenerator(private val surfaceHeight: Int, private var size: Int) : ChunkGenerator() {
    override fun generateSurface(
        worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData
    ) {
        if (!canGenerate(chunkX, chunkZ)) return
        for (x in 0 until 16) {
            for (z in 0 until 16) {
                chunkData.setBlock(x, surfaceHeight, z, Material.GRASS_BLOCK)
            }
        }
    }

    override fun generateNoise(
        worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData
    ) {
        if (!canGenerate(chunkX, chunkZ)) return
        val min = chunkData.minHeight + 1

        for (y in min until surfaceHeight) {
            for (x in 0 until 16) {
                for (z in 0 until 16) {
                    chunkData.setBlock(x, y, z, Material.DIRT)
                }
            }
        }
    }

    override fun generateBedrock(
        worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData
    ) {
        if (!canGenerate(chunkX, chunkZ)) return
        for (x in 0 until 16) {
            for (z in 0 until 16) {
                chunkData.setBlock(x, chunkData.minHeight, z, Material.BEDROCK)
            }
        }
    }

    private fun canGenerate(x: Int, z: Int) = x >= 0 && z >= 0 && x <= size && z <= size
}