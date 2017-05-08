package c6h2cl2.solidxp.Item

import c6h2cl2.solidxp.SOLIDXP_TEXT
import c6h2cl2.solidxp.*
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult.*
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraftforge.items.ItemHandlerHelper

/**
 * @author C6H2Cl2
 */
class ItemXpExtractor : Item() {
    init {
        unlocalizedName = "xpExtractor"
        registryName = ResourceLocation(MOD_ID, "xpextractor")
        creativeTab = SolidXpRegistry.tabSolidXp
        maxStackSize = 1
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val itemStack = player.getHeldItem(hand)
        var meta = itemStack.metadata
        if (player.isSneaking) {
            meta++
            if (meta > 15) meta = 0
            itemStack.itemDamage = meta
            player.sendMessageOnlyServer(TextComponentTranslation("$SOLIDXP_TEXT.xp_extractor", getXpValue(meta)))
            return ActionResult.newResult(SUCCESS, itemStack)
        } else {
            val xp = getXpValue(meta)
            if (player.experienceTotal < xp) {
                player.sendMessageOnlyServer(TextComponentTranslation("$SOLIDXP_TEXT.not_enough_xp"))
                return ActionResult.newResult(FAIL, itemStack)
            } else {
                player.extractExperience(xp.toInt())
                ItemHandlerHelper.giveItemToPlayer(player, ItemStack(SolidXpRegistry.solidXp, 1, meta))
                player.addStat(SolidXpRegistry.achievementExtractXp)
                return ActionResult.newResult(SUCCESS, itemStack)
            }
        }
    }


    fun getXpValue(meta: Int): Long {
        return Math.pow(4.0, meta.toDouble()).toLong() * 10L
    }
}