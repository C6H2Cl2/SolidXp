package c6h2cl2.solidxp.Block

import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.block.BlockPane
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material

/**
 * @author C6H2Cl2
 */
class BlockXpGlassPane: BlockPane(Material.GLASS, false) {
    init {
        unlocalizedName = "xpGlassPane"
        setRegistryName(MOD_ID, "xp_glass_pane")
        setCreativeTab(SolidXpRegistry.tabSolidXp)
        setLightLevel(0.55f)
        setLightOpacity(0)
        setHardness(0.3f)
        soundType = SoundType.GLASS
    }
}