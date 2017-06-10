package c6h2cl2.solidxp.block

import c6h2cl2.solidxp.SolidXpRegistry
import c6h2cl2.solidxp.capability.IXpStorage
import c6h2cl2.solidxp.spawnXpOrb
import c6h2cl2.solidxp.tileentity.TileXpMachineBase
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.InventoryHelper
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType.MODEL
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
open class BlockXpMachineBase<T : TileXpMachineBase>(
        material: Material,
        private val classEntity: Class<T>,
        protected val removeTileEntity: Boolean = false,
        protected val guiId: Int = Int.MIN_VALUE
) : BlockContainer(material), IXpHolderBlock {

    override fun getUnlocalizedMachineName() = "$unlocalizedName.name"

    override fun getXpLimit(world: World, pos: BlockPos) = getXpStorage(world, pos)?.maxXp ?: 0

    override fun getXpValue(world: World, pos: BlockPos) = getXpStorage(world, pos)?.xpValue ?: 0

    fun getXpStorage(world: World, pos: BlockPos): IXpStorage? = world.getTileEntity(pos)?.getCapability(SolidXpRegistry.XP_STORAGE_CAPABILITY, null)

    override fun createNewTileEntity(worldIn: World?, meta: Int): TileEntity? {
        return classEntity.newInstance()
    }

    override fun breakBlock(world: World?, pos: BlockPos, state: IBlockState?) {
        val tile = world?.getTileEntity(pos) ?: return
        if (tile is TileXpMachineBase) {
            world.spawnXpOrb(tile.xpStorage.xpValue, pos)
        }
        if (tile is IInventory) {
            InventoryHelper.dropInventoryItems(world, pos, tile)
            world.updateComparatorOutputLevel(pos, this)
        }
        if (removeTileEntity) {
            world.removeTileEntity(pos)
        }
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState?, playerIn: EntityPlayer?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        playerIn?.openGui(guiId, worldIn, pos) ?: return false
        return true
    }

    override fun getRenderType(state: IBlockState?) = MODEL
}