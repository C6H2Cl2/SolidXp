package c6h2cl2.solidxp.Block

import c6h2cl2.solidxp.GUI_XP_CHEST
import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpCore
import c6h2cl2.solidxp.SolidXpRegistry
import c6h2cl2.solidxp.TileEntity.TileXpChest
import net.minecraft.block.Block
import net.minecraft.block.BlockChest
import net.minecraft.block.SoundType.WOOD
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing.EAST
import net.minecraft.util.EnumFacing.NORTH
import net.minecraft.util.EnumFacing.SOUTH
import net.minecraft.util.EnumFacing.WEST
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class BlockXpChest: BlockChest(BlockChest.Type.BASIC) {
    init {
        unlocalizedName = "xpChest"
        setRegistryName(MOD_ID, "xp_chest")
        setHardness(2.5f)
        setLightLevel(0.6f)
        setCreativeTab(SolidXpRegistry.tabSolidXp)
        soundType = WOOD
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity? {
        return TileXpChest()
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        playerIn.openGui(SolidXpCore.INSTANCE, GUI_XP_CHEST, worldIn, pos.x, pos.y, pos.z)
        return true
    }

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
        setDefaultFacing(world, pos, state)
    }
    private fun setDefaultFacing(world: World, pos: BlockPos, state: IBlockState) {
        if (world.isRemote)
            return
        val stateNorth = world.getBlockState(pos.north())
        val stateSouth = world.getBlockState(pos.south())
        val stateEast = world.getBlockState(pos.east())
        val stateWest = world.getBlockState(pos.west())
        var facing = state.getValue(BlockChest.FACING)
        when (facing) {
            NORTH -> {
                if (stateNorth.isFullBlock && !stateSouth.isFullBlock)
                    facing = SOUTH
            }
            SOUTH -> {
                if (stateSouth.isFullBlock && !stateNorth.isFullBlock)
                    facing = NORTH
            }
            EAST -> {
                if (stateEast.isFullBlock && !stateWest.isFullBlock)
                    facing = WEST
            }
            WEST -> {
                if (stateWest.isFullBlock && !stateEast.isFullBlock)
                    facing = EAST
            }
        }
        world.setBlockState(pos, state.withProperty(BlockChest.FACING, facing), 2)
    }

    override fun canPlaceBlockAt(worldIn: World, pos: BlockPos): Boolean {
        return worldIn.getBlockState(pos).block.isReplaceable(worldIn, pos)
    }

    override fun neighborChanged(state: IBlockState?, worldIn: World?, pos: BlockPos?, blockIn: Block?, fromPos: BlockPos?) {

    }

}