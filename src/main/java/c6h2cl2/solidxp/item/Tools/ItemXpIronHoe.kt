package c6h2cl2.solidxp.item.Tools

import c6h2cl2.YukariLib.EnumToolType
import c6h2cl2.YukariLib.EnumToolType.HOE
import c6h2cl2.solidxp.item.ICraftResultEnchanted
import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.enchantment.EnumEnchantmentType.DIGGER
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemHoe
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class ItemXpIronHoe : ItemHoe(SolidXpRegistry.materialXpIron), ICraftResultEnchanted {
    init {
        unlocalizedName = "XpIronHoe"
        registryName = ResourceLocation(MOD_ID, "xpIronHoe")
        creativeTab = SolidXpRegistry.tabSolidXp
        hasSubtypes = true
    }

    override fun getSubItems(tab: CreativeTabs?, subItems: NonNullList<ItemStack>) {
        if (tab != SolidXpRegistry.tabSolidXp){
            return
        }
        val itemStack = ItemStack(this, 1, 0)
        itemStack.addEnchantment(Enchantments.MENDING, 1)
        itemStack.addEnchantment(SolidXpRegistry.xpBoost[DIGGER], 2)
        subItems.add(itemStack)
    }

    override fun onCreated(stack: ItemStack, worldIn: World?, playerIn: EntityPlayer?) {
        stack.addEnchantment(Enchantments.MENDING, 1)
        stack.addEnchantment(SolidXpRegistry.xpBoost[DIGGER], 2)
        //playerIn?.addStat(SolidXpRegistry.achievementCraftXpIronHoe)
    }

    override fun getEnchanted(): ItemStack {
        val stack = ItemStack(this)
        stack.addEnchantment(Enchantments.MENDING, 1)
        stack.addEnchantment(SolidXpRegistry.xpBoost[DIGGER], 2)
        return stack
    }

    override fun getToolType(): EnumToolType {
        return HOE
    }
}