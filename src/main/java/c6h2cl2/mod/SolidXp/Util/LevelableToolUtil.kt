package c6h2cl2.mod.SolidXp.Util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

class LevelableToolUtil {
    public fun appendTagToStack(itemStack: ItemStack) :ItemStack {
        val stackTagCompound = itemStack.tagCompound
        //基本タグ
        val tagCompound = NBTTagCompound()
        tagCompound.setInteger("xp", 0)
        tagCompound.setInteger("level", 0)
        tagCompound.setFloat("sub_xp", 0f)
        //上昇値
        val tagRising = NBTTagCompound()
        tagRising.setFloat("speed", 0.1f)
        tagRising.setFloat("mining", 0.02f)
        tagRising.setInteger("enchant", 10)
        //補正値
        val tagCorrection = NBTTagCompound()
        tagCorrection.setFloat("level", 1.0f)
        tagCompound.setFloat("xp", 1.0f)
        //タグの統合
        tagCompound.setTag("rise", tagRising)
        tagCompound.setTag("correction", tagCorrection)
        stackTagCompound!!.setTag("tool_level", tagCompound)
        itemStack.tagCompound = stackTagCompound
        return itemStack
    }
    /*public fun addXp(itemStack: ItemStack, value:Int):ItemStack{
        val tagLevel = itemStack.tagCompound?.getTag("tool_level") as? NBTTagCompound
        val xp:Int? = tagLevel?.getInteger("xp");
        val level:Int? = tagLevel?.getInteger("level")
    }*/
}