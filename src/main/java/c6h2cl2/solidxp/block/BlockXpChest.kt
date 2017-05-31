package c6h2cl2.solidxp.block

import c6h2cl2.solidxp.*
import c6h2cl2.solidxp.SolidXpRegistry
import c6h2cl2.solidxp.tileentity.TileXpChest
import net.minecraft.block.BlockContainer
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.SoundType.WOOD
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType.ENTITYBLOCK_ANIMATED
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing.*
import net.minecraft.util.EnumHand
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * @author C6H2Cl2
 */
class BlockXpChest : BlockContainer(Material.WOOD) {
    companion object {
        @JvmStatic
        private val FACING = BlockHorizontal.FACING
    }

    private val AABB = AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375)

    init {
        unlocalizedName = "xpChest"
        setRegistryName(MOD_ID, "xp_chest")
        setHardness(2.5f)
        setLightLevel(0.6f)
        setCreativeTab(SolidXpRegistry.tabSolidXp)
        soundType = WOOD
    }

    override fun createNewTileEntity(worldIn: World?, meta: Int): TileEntity? {
        return TileXpChest()
    }

    override fun getRenderType(state: IBlockState?) = ENTITYBLOCK_ANIMATED

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        playerIn.openGui(SolidXpCore.INSTANCE, GUI_XP_CHEST, worldIn, pos.x, pos.y, pos.z)
        return true
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        val tile = world.getTileEntity(pos)
        if (tile is TileXpChest) {
            InventoryHelper.dropInventoryItems(world, pos, tile)
            world.updateComparatorOutputLevel(pos, this)
        }
        super.breakBlock(world, pos, state)
    }

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
        setDefaultFacing(world, pos, state)
    }

    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        if (placer is EntityPlayer)
            placer.addStat(SolidXpRegistry.achievementPutXpInfuser)
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.horizontalFacing.opposite), 2)
    }

    private fun setDefaultFacing(world: World, pos: BlockPos, state: IBlockState) {
        if (world.isRemote)
            return
        val stateNorth = world.getBlockState(pos.north())
        val stateSouth = world.getBlockState(pos.south())
        val stateEast = world.getBlockState(pos.east())
        val stateWest = world.getBlockState(pos.west())
        var facing = state.getValue(FACING)
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
        world.setBlockState(pos, state.withProperty(FACING, facing), 2)
    }

    override fun getStateForPlacement(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
        return this.defaultState.withProperty(FACING, placer.horizontalFacing.opposite)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        var enumfacing = EnumFacing.getFront(meta)

        if (enumfacing.axis == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH
        }

        return this.defaultState.withProperty(FACING, enumfacing)
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return (state.getValue(FACING) as EnumFacing).index
    }

    override fun withRotation(state: IBlockState, rot: Rotation): IBlockState {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING) as EnumFacing))
    }

    override fun withMirror(state: IBlockState, mirrorIn: Mirror): IBlockState {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING) as EnumFacing))
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, *arrayOf<IProperty<*>>(FACING))
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    override fun isOpaqueCube(state: IBlockState): Boolean {
        return false
    }

    override fun isFullCube(state: IBlockState): Boolean {
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun hasCustomBreakingProgress(state: IBlockState): Boolean {
        return true
    }

    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return AABB
    }
}