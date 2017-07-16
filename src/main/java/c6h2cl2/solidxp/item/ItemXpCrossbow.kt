package c6h2cl2.solidxp.item

import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class ItemXpCrossbow(pullCount: Int) : Item() {
    init {
        unlocalizedName = "xpCrossbow"
        registryName = ResourceLocation(MOD_ID, "xp_cross_bow")
        creativeTab = SolidXpRegistry.tabSolidXp
        maxStackSize = 1
        maxDamage = pullCount
        addPropertyOverride(ResourceLocation("pull"), {
            stack, _, entity ->
            if (entity == null) 0.0f else {
                if (entity.activeItemStack.item !is ItemXpCrossbow) 0.0f else (stack.maxDamage - stack.itemDamage) / 20.0f
            }
        })
    }
}