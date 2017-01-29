package c6h2cl2.mod.SolidXp.Item.Tool

import c6h2cl2.mod.SolidXp.SolidXpCore
import c6h2cl2.mod.SolidXp.SolidXpRegistry
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.NonNullList
import net.minecraft.world.World

class LevelablePickaxe : ItemPickaxe(SolidXpRegistry.materialXpIron) {
    init {
        creativeTab = SolidXpCore.tabSolidXp
        unlocalizedName = "LevelablePickaxe"
        setRegistryName(SolidXpCore.DOMAIN, "LevelablePickaxe")
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: NonNullList<ItemStack>) {
        val itemStack = ItemStack(this)
        //appendTagToStack(itemStack)
        list.add(itemStack)
    }

    override fun onCreated(itemStack: ItemStack, world: World?, player: EntityPlayer?) {
        //appendTagToStack(itemStack)
    }


}
