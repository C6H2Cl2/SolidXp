package c6h2cl2.mod.SolidXp.Item.Tool

import c6h2cl2.mod.SolidXp.SolidXpCore
import c6h2cl2.mod.SolidXp.SolidXpRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemAxe
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.world.World

class XpIronAxe : ItemAxe(SolidXpRegistry.materialXpIron, 8.0f, -3.0f) {
    init {
        creativeTab = SolidXpCore.tabSolidXp
        unlocalizedName = "XpIronAxe"
        setRegistryName(SolidXpCore.DOMAIN, "XpIronAxe")
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: NonNullList<ItemStack>) {
        val itemStack = ItemStack(this)
        itemStack.addEnchantment(Enchantments.MENDING, 1)
        itemStack.addEnchantment(SolidXpRegistry.XpBoostTool, 2)
        list.add(itemStack)
    }

    override fun onCreated(itemStack: ItemStack?, world: World?, player: EntityPlayer?) {
        itemStack!!.addEnchantment(Enchantments.MENDING, 1)
        itemStack.addEnchantment(SolidXpRegistry.XpBoostTool, 2)
    }
}
