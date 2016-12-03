package c6h2cl2.mod.SolidXp.Item

import c6h2cl2.mod.SolidXp.SolidXpRegistry
import c6h2cl2.mod.SolidXp.Util.XpUtil
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

class ItemXpExtractor : ExtendedItem("xpExtractor") {
    init {
        this.maxStackSize = 1
    }

    override fun onItemRightClick(itemStack: ItemStack, world: World?, player: EntityPlayer?, enumHand: EnumHand?): ActionResult<ItemStack> {
        var meta = itemStack.itemDamage
        var isSucceed = true
        if (player!!.isSneaking) {
            meta++
            if (meta > 15) {
                meta -= 15
            }
            itemStack.itemDamage = meta
            if (!world!!.isRemote) {
                player.addChatComponentMessage(TextComponentString("Extract " + getXp(itemStack) + " Xps"))
            }
        } else {
            val extractXpSize = getXp(itemStack)
            isSucceed = player.experienceTotal >= extractXpSize
            if (isSucceed) {
                //player.experienceTotalはint型であるので、この場合extractXpSizeは必ずInteger.MAX_VALUE以下である。
                XpUtil.decreasePlayerXp(player, extractXpSize.toInt())
                if (!world!!.isRemote) {
                    val solidXp = ItemStack(SolidXpRegistry.solidXp, 1, meta)
                    if (!player.inventory.addItemStackToInventory(solidXp)) {
                        val item = EntityItem(world, player.posX, player.posY + 1, player.posZ, ItemStack(SolidXpRegistry.solidXp, 1, meta))
                        item.setPickupDelay(0)
                        world.spawnEntityInWorld(item)
                    }
                }
            } else {
                if (!world!!.isRemote) {
                    player.addChatComponentMessage(TextComponentString("You don't have Enough Xps!!"))
                }
            }
        }
        if (isSucceed) {
            return ActionResult(EnumActionResult.SUCCESS, itemStack)
        } else {
            return ActionResult(EnumActionResult.FAIL, itemStack)
        }
    }

    private fun getXp(itemStack: ItemStack): Long {
        val meta = itemStack.metadata
        return Math.pow(4.0, meta.toDouble()).toLong() * 10
    }
}
