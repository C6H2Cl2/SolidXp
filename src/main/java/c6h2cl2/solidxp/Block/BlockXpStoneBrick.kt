package c6h2cl2.solidxp.Block

import c6h2cl2.solidxp.*
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockStoneBrick
import net.minecraft.block.material.Material
import net.minecraft.util.ResourceLocation
import net.minecraft.block.BlockStoneBrick.*
import net.minecraft.block.BlockStoneBrick.EnumType.*
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * @author C6H2Cl2
 */
class BlockXpStoneBrick : Block(Material.ROCK) {
    init {
        unlocalizedName = "xpStoneBrick"
        registryName = ResourceLocation(MOD_ID, "xp_stone_brick")
        setCreativeTab(SolidXpRegistry.tabSolidXp)
        setLightLevel(0.6f)
        setHarvestLevel("pickaxe", 0)
        setHardness(1.5f)
        setResistance(15f)
        defaultState = blockState.baseState.withProperty(VARIANT, DEFAULT)
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    override fun damageDropped(state: IBlockState): Int {
        return (state.getValue(VARIANT) as BlockStoneBrick.EnumType).metadata
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    override fun getSubBlocks(itemIn: Item, tab: CreativeTabs, list: NonNullList<ItemStack>) {
        BlockStoneBrick.EnumType.values().mapTo(list) { ItemStack(itemIn, 1, it.metadata) }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(VARIANT, BlockStoneBrick.EnumType.byMetadata(meta))
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    override fun getMetaFromState(state: IBlockState): Int {
        return (state.getValue(VARIANT) as BlockStoneBrick.EnumType).metadata
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, *arrayOf<IProperty<*>>(VARIANT))
    }
}