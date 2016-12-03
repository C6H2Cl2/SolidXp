package c6h2cl2.mod.SolidXp.Event

import c6h2cl2.mod.SolidXp.SolidXpRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockCrops
import net.minecraft.block.state.IBlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemHoe
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

import java.util.Random

class XpDropBoostEventHandler {

    @SubscribeEvent
    fun onLivingKilledByXpBoostedWeapon(event: LivingExperienceDropEvent) {
        if (event.entityLiving is EntityPlayer || event.attackingPlayer == null) {
            return
        }
        val itemStack = event.attackingPlayer.getHeldItem(EnumHand.MAIN_HAND)
        if (itemStack == null || itemStack.enchantmentTagList == null) {
            return
        }
        val xpBoostLevel = EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.XpBoostWeapon, itemStack)
        if (xpBoostLevel == 0) {
            return
        }
        val random = event.attackingPlayer.worldObj.rand
        val boost = 1 + random.nextFloat() * xpBoostLevel
        event.droppedExperience = Math.round(event.originalExperience * boost)
    }

    @SubscribeEvent
    fun onBlockBreak(event: BlockEvent.BreakEvent) {
        event.result = Event.Result.DEFAULT
        event.isCanceled = false
        var xp = event.expToDrop
        xp = increaseXpByBreaking(event, xp)
        xp = increaseXpForCrops(event, xp)
        event.expToDrop = xp
    }

    private fun increaseXpByBreaking(event: BlockEvent.BreakEvent, xp: Int): Int {
        var xp = xp
        if (event.player.heldItemMainhand != null && xp != 0) {
            val itemStack = event.player.heldItemMainhand
            val level = EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.XpBoostTool, itemStack)
            val random = event.world.rand
            val xpBoost = random.nextFloat() * level
            val boost = if (xpBoost <= 1) xpBoost + 1 else xpBoost
            xp = Math.round(xp * boost)
        }
        return xp
    }

    private fun increaseXpForCrops(event: BlockEvent.BreakEvent, xp: Int): Int {
        var xp = xp
        if (event.player.heldItemMainhand != null && event.player.heldItemMainhand!!.item is ItemHoe) {
            val state = event.world.getBlockState(event.pos)
            val block = state.block
            if (block is BlockCrops) {
                if (block.isMaxAge(state)) {
                    xp += EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.XpBoostTool, event.player.heldItemMainhand)
                }
            }
        }
        return xp
    }
}
