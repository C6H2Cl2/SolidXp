@file:Suppress("UNUSED")

package c6h2cl2.solidxp.Event

import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.block.BlockCrops
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnumEnchantmentType.DIGGER
import net.minecraft.enchantment.EnumEnchantmentType.WEAPON
import net.minecraft.item.ItemHoe
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent
import net.minecraftforge.event.world.BlockEvent.BreakEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * @author C6H2Cl2
 */
class XpBoostEventHandler {
    @SubscribeEvent
    fun onLivingDropExperience(event: LivingExperienceDropEvent) {
        val player = event.attackingPlayer ?: return
        val itemStack = player.heldItemMainhand
        if (itemStack == ItemStack.EMPTY || itemStack.enchantmentTagList == null || itemStack.enchantmentTagList?.tagCount() != 0) return
        val xpBoostLevel = EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.xpBoost[WEAPON], itemStack)
        if (xpBoostLevel == 0) return
        val rand = player.world.rand
        val boost = 1.0f + (rand.nextFloat() * xpBoostLevel * rand.nextFloat() * xpBoostLevel)
        event.droppedExperience = Math.round(event.originalExperience * boost)
    }

    @SubscribeEvent
    fun onBlockBreak(event: BreakEvent) {
        var xp = event.expToDrop
        val player = event.player ?: return
        val itemStack = player.heldItemMainhand
        if (itemStack == ItemStack.EMPTY) return
        val xpBoostLevel = EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.xpBoost[DIGGER], itemStack)
        val rand = event.world.rand
        val boost = 1.0f + (rand.nextFloat() * xpBoostLevel * rand.nextFloat() * xpBoostLevel)
        xp = Math.round(xp * boost)
        if (itemStack.item is ItemHoe || itemStack.item.getToolClasses(itemStack).contains("hoe")) {
            val block = event.state.block
            if (block is BlockCrops && block.isMaxAge(event.state)) {
                xp += xpBoostLevel
            }
        }
        event.expToDrop = xp
    }
}