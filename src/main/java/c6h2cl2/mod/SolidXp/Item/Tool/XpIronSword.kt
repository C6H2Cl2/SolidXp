package c6h2cl2.mod.SolidXp.Item.Tool

import c6h2cl2.mod.SolidXp.SolidXpCore
import c6h2cl2.mod.SolidXp.SolidXpRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.world.World

class XpIronSword : ItemSword(SolidXpRegistry.materialXpIron) {
    init {
        creativeTab = SolidXpCore.tabSolidXp
        unlocalizedName = "XpIronSword"
        setRegistryName(SolidXpCore.DOMAIN, "XpIronSword")
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<ItemStack>) {
        val itemStack = ItemStack(this)
        itemStack.addEnchantment(Enchantments.MENDING, 1)
        itemStack.addEnchantment(SolidXpRegistry.XpBoostWeapon, 2)
        list.add(itemStack)
    }

    override fun onCreated(itemStack: ItemStack?, world: World?, player: EntityPlayer?) {
        itemStack!!.addEnchantment(Enchantments.MENDING, 1)
        itemStack.addEnchantment(SolidXpRegistry.XpBoostWeapon, 2)
    }
}
