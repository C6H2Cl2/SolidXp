package c6h2cl2.solidxp.block

import c6h2cl2.solidxp.GUI_XP_COLLECTOR
import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpCore
import c6h2cl2.solidxp.SolidXpRegistry
import c6h2cl2.solidxp.tileentity.TileXpCollector
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType.MODEL
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class BlockXpCollector: BlockContainer(Material.ROCK), IXpHolderBlock {

    init {
        unlocalizedName = "xpCollector"
        registryName = ResourceLocation(MOD_ID, "xp_collector")
        setCreativeTab(SolidXpRegistry.tabSolidXp)
        setLightLevel(0.6f)
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity {
        return TileXpCollector()
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        playerIn.openGui(SolidXpCore.INSTANCE, GUI_XP_COLLECTOR, worldIn, pos.x, pos.y, pos.z)
        return true
    }

    override fun getRenderType(state: IBlockState?) = MODEL

    override fun getUnlocalizedMachineName() = unlocalizedName + ".name"

    override fun getXpLimit(world: World, pos: BlockPos) = Long.MAX_VALUE

    override fun getXpValue(world: World, pos: BlockPos): Long {
        val tile = world.getTileEntity(pos) as TileXpCollector
        return tile.xpStorage + (Math.pow(4.0, tile.xpTier.toDouble()).toLong() * 10L) * tile.inventory.getStackInSlot(0).count
    }
}