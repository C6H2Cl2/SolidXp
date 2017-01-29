package c6h2cl2.mod.SolidXp.Enchantment

import c6h2cl2.mod.SolidXp.SolidXpCore
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.inventory.EntityEquipmentSlot

class XpBoost(enchantmentType: EnumEnchantmentType) : Enchantment(Enchantment.Rarity.RARE, enchantmentType, arrayOf(EntityEquipmentSlot.MAINHAND)) {

    init {
        setName("xpBoost." + enchantmentType.name)
        setRegistryName(SolidXpCore.DOMAIN, "xpBoost." + enchantmentType.name)
    }

    override fun getMaxLevel(): Int {
        return 5
    }

    override fun getMinEnchantability(enchantmentLevel: Int): Int {
        return 15 + enchantmentLevel * 8
    }

    override fun getMaxEnchantability(enchantmentLevel: Int): Int {
        return getMinEnchantability(enchantmentLevel) + 50
    }
}
