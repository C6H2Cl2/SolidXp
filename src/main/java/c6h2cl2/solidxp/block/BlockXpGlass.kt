package c6h2cl2.solidxp.block

import c6h2cl2.solidxp.*
import net.minecraft.block.BlockBreakable
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockRenderLayer
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

/**
 * @author C6H2Cl2
 */
class BlockXpGlass : BlockBreakable(Material.GLASS, false){

    init {
        unlocalizedName = "xpGlass"
        setRegistryName(MOD_ID, "xp_glass")
        setCreativeTab(SolidXpRegistry.tabSolidXp)
        setLightLevel(0.55f)
        setLightOpacity(0)
        setHardness(0.3f)
        soundType = SoundType.GLASS
    }

    override fun quantityDropped(random: Random): Int {
        return 0
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT
    }

    override fun isFullCube(state: IBlockState): Boolean {
        return false
    }

    override fun canSilkHarvest(): Boolean {
        return true
    }
}