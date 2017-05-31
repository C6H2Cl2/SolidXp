package c6h2cl2.solidxp.item.Tools

import c6h2cl2.YukariLib.EnumToolType
import c6h2cl2.YukariLib.EnumToolType.SWORD
import c6h2cl2.solidxp.item.ICraftResultEnchanted
import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.enchantment.EnumEnchantmentType.WEAPON
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class ItemXpIronSword : ItemSword(SolidXpRegistry.materialXpIron), ICraftResultEnchanted {
    init {
        unlocalizedName = "XpIronSword"
        registryName = ResourceLocation(MOD_ID, "xpIronSword")
        creativeTab = SolidXpRegistry.tabSolidXp
        hasSubtypes = true
    }

    override fun getSubItems(itemIn: Item, tab: CreativeTabs?, subItems: NonNullList<ItemStack>) {
        val itemStack = ItemStack(itemIn, 1, 0)
        itemStack.addEnchantment(Enchantments.MENDING, 1)
        itemStack.addEnchantment(SolidXpRegistry.xpBoost[WEAPON], 2)
        subItems.add(itemStack)
    }

    override fun onCreated(stack: ItemStack, worldIn: World?, playerIn: EntityPlayer?) {
        stack.addEnchantment(Enchantments.MENDING, 1)
        stack.addEnchantment(SolidXpRegistry.xpBoost[WEAPON], 2)
        playerIn?.addStat(SolidXpRegistry.achievementCraftXpIronSword)
    }

    override fun getEnchanted(): ItemStack {
        val stack = ItemStack(this)
        stack.addEnchantment(Enchantments.MENDING, 1)
        stack.addEnchantment(SolidXpRegistry.xpBoost[WEAPON], 2)
        return stack
    }

    override fun getToolType(): EnumToolType {
        return SWORD
    }
}