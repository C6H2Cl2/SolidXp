package c6h2cl2.solidxp.enchant

import c6h2cl2.solidxp.*

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.Enchantment.Rarity.VERY_RARE
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class XpBoost(enchantmentType: EnumEnchantmentType) : Enchantment(VERY_RARE, enchantmentType, getEquipmentSlotForType(enchantmentType)) {
    init {
        setName("xpBoost.$enchantmentType")
        registryName = ResourceLocation(MOD_ID, getName())
    }

    override fun getMinEnchantability(enchantmentLevel: Int): Int {
        return 16 + (enchantmentLevel - 1) * 10
    }

    override fun getMaxEnchantability(enchantmentLevel: Int): Int {
        return getMinEnchantability(enchantmentLevel) + 60
    }

    override fun getMaxLevel() = 5
}