package c6h2cl2.mod.SolidXp.Item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class ItemSolidXp : ExtendedItem("SolidXp") {
    init {
        setHasSubtypes(true)
    }

    override fun onItemRightClick(itemStack: ItemStack, world: World?, player: EntityPlayer?, enumHand: EnumHand?): ActionResult<ItemStack> {
        player!!.addExperience(getXpInteger(itemStack))
        --itemStack.stackSize
        return ActionResult(EnumActionResult.SUCCESS, itemStack)
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<ItemStack>) {
        for (i in 0..15) {
            list.add(ItemStack(item, 1, i))
        }
    }

    override fun getUnlocalizedName(stack: ItemStack?): String {
        return this.unlocalizedName + "_" + stack!!.itemDamage
    }

    fun getXp(itemStack: ItemStack): Long {
        val meta = itemStack.metadata
        return Math.pow(4.0, meta.toDouble()).toLong() * 10
    }

    fun getXpInteger(itemStack: ItemStack): Int {
        val xp = getXp(itemStack)
        if (xp > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE
        } else {
            return xp.toInt()
        }
    }
}
