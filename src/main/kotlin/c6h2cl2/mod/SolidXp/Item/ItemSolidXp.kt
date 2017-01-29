package c6h2cl2.mod.SolidXp.Item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.world.World

class ItemSolidXp : ExtendedItem("SolidXp") {
    init {
        setHasSubtypes(true)
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, enumHand: EnumHand): ActionResult<ItemStack> {
        val itemStack = if(player.heldItemMainhand.item == this){
            player.heldItemMainhand
        }else{
            player.heldItemOffhand
        }
        player.addExperience(getXpInteger(itemStack))
        itemStack.shrink(1)
        return ActionResult(EnumActionResult.SUCCESS, itemStack)
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, subItems: NonNullList<ItemStack>) {
        (0..15).mapTo(subItems) { ItemStack(item, 1, it) }
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
//TODO Jsonとテクスチャのファイル名を全部小文字に