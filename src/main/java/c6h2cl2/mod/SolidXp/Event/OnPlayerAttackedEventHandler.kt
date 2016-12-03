package c6h2cl2.mod.SolidXp.Event

import c6h2cl2.mod.SolidXp.Item.Armor.IXpArmor
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

import java.util.LinkedList

class OnPlayerAttackedEventHandler {

    @SubscribeEvent
    fun onPlayerAttacked(event: LivingAttackEvent) {
        if (event.entityLiving !is EntityPlayer) {
            return
        }
        val player = event.entityLiving as EntityPlayer
        val armors = LinkedList<ItemStack>()
        var shieldAmount = 0
        var shieldLimitTotal = 0
        var className: String? = null
        var flag = true
        for (armor in player.inventory.armorInventory) {
            if (armor != null && armor.item is IXpArmor) {
                armors.add(armor)
                val xpArmor = armor.item as IXpArmor
                shieldAmount += xpArmor.armorShield.toInt()
                shieldLimitTotal += xpArmor.shieldLimit.toInt()
                if (className == null) {
                    className = xpArmor.javaClass.name
                } else if (flag) {
                    flag = className == xpArmor.javaClass.name
                }
            }
        }
        val shieldLimit = shieldLimitTotal / armors.size
        var shield = player.experienceLevel * shieldAmount / 100
        if (shield > shieldLimit) {
            shield = shieldLimit
        }

    }
}
