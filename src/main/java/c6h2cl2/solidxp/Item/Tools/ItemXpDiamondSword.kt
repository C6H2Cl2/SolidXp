package c6h2cl2.solidxp.Item.Tools

import c6h2cl2.YukariLib.EnumToolType
import c6h2cl2.YukariLib.EnumToolType.SWORD
import c6h2cl2.solidxp.Item.ICraftResultEnchanted
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
class ItemXpDiamondSword : ItemSword(SolidXpRegistry.materialXpDiamond), ICraftResultEnchanted {
    init {
        unlocalizedName = "XpDiamondSword"
        registryName = ResourceLocation(MOD_ID, "xp_diamond_sword")
        creativeTab = SolidXpRegistry.tabSolidXp
        hasSubtypes = true
    }

    override fun getSubItems(itemIn: Item, tab: CreativeTabs?, subItems: NonNullList<ItemStack>) {
        val itemStack = ItemStack(itemIn)
        itemStack.addEnchantment(Enchantments.MENDING, 3)
        itemStack.addEnchantment(SolidXpRegistry.xpBoost[WEAPON], 5)
        subItems.add(itemStack)
    }

    override fun onCreated(stack: ItemStack, worldIn: World?, playerIn: EntityPlayer?) {
        stack.addEnchantment(Enchantments.MENDING, 3)
        stack.addEnchantment(SolidXpRegistry.xpBoost[WEAPON], 5)
        playerIn?.addStat(SolidXpRegistry.achievementCraftXpIronSword)
    }

    override fun getEnchanted(): ItemStack {
        val stack = ItemStack(this)
        stack.addEnchantment(Enchantments.MENDING, 3)
        stack.addEnchantment(SolidXpRegistry.xpBoost[WEAPON], 5)
        return stack
    }

    override fun getToolType(): EnumToolType {
        return SWORD
    }
}