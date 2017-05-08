package c6h2cl2.solidxp.Item

import c6h2cl2.solidxp.*
import c6h2cl2.solidxp.addAndSpawnXpOrb
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult.SUCCESS
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class ItemSolidXp : Item() {
    init {
        unlocalizedName = "SolidXp"
        registryName = ResourceLocation(MOD_ID, "solidxp")
        creativeTab = SolidXpRegistry.tabSolidXp
        hasSubtypes = true
    }

    override fun getSubItems(itemIn: Item, tab: CreativeTabs?, subItems: NonNullList<ItemStack>) {
        (0..15).mapTo(subItems) { ItemStack(itemIn, 1, it) }
    }

    override fun getUnlocalizedName(stack: ItemStack?): String {
        return "item.solidxp_${stack?.metadata}"
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val itemStack = player.getHeldItem(hand)
        val xpValue = getXpValue(itemStack.metadata)
        player.addAndSpawnXpOrb(xpValue, world)
        return ActionResult(SUCCESS, itemStack)
    }

    fun getXpValue(meta: Int): Long {
        return Math.pow(4.0, meta.toDouble()).toLong() * 10L
    }
}