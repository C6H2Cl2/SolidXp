package c6h2cl2.solidxp.block

import c6h2cl2.solidxp.GUI_XP_COLLECTOR
import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import c6h2cl2.solidxp.tileentity.TileXpCollector
import net.minecraft.block.material.Material
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class BlockXpCollector: BlockXpMachineBase<TileXpCollector>(Material.ROCK, TileXpCollector::class.java, true, GUI_XP_COLLECTOR) {

    init {
        unlocalizedName = "xpCollector"
        registryName = ResourceLocation(MOD_ID, "xp_collector")
        setCreativeTab(SolidXpRegistry.tabSolidXp)
        setLightLevel(0.6f)
    }
}