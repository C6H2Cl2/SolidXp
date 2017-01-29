package c6h2cl2.mod.SolidXp.Util

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import org.jetbrains.annotations.Contract

import java.util.HashMap

object XpUtil {

    fun getXpForLevel(target: Int): Int {
        val xp = (0..target - 1).sumBy { getXpCapForLevel(it) }
        return xp
    }

    fun getXpForLevel(base: Int, target: Int): Int {
        return getXpForLevel(target) - getXpForLevel(base)
    }

    fun getXpCapForLevel(level: Int): Int {
        return if (level >= 30) 112 + (level - 30) * 9 else if (level >= 15) 37 + (level - 15) * 5 else 7 + level * 2
    }

    fun getLevelForXp(xp: Int): Int {
        var xp = xp
        var level = 0
        while (xp > getXpCapForLevel(level)) {
            xp -= getXpCapForLevel(level)
            ++level
        }
        return level
    }

    /**エンチャント取得関連 */
    fun getEnchantmentsFromItemStack(itemStack: ItemStack): Map<Short, Short>? {
        val tagList = itemStack.enchantmentTagList
        if (tagList == null || tagList.hasNoTags()) {
            return null
        }
        val enchants = HashMap<Short, Short>()
        for (i in 0..tagList.tagCount() - 1) {
            val tagCompound = tagList.getCompoundTagAt(i)
            if (!tagCompound.hasKey("id")) {
                return null
            }
            enchants.put(tagCompound.getShort("id"), tagCompound.getShort("lvl"))
        }
        return enchants
    }

    fun getEnchantLevel(enchants: Map<Short, Short>, enchantment: Enchantment): Int {
        var enchantLevel: Short = 0
        for ((key, value) in enchants) {
            if (key == Enchantment.getEnchantmentID(enchantment).toShort()) {
                enchantLevel = value
                break
            }
        }
        return enchantLevel.toInt()
    }

    fun getEnchantLevel(itemStack: ItemStack, enchantment: Enchantment): Int {
        val enchants = getEnchantmentsFromItemStack(itemStack)
        return if (enchants != null){
            getEnchantLevel(enchants,enchantment)
        }else{
            0
        }
    }

    /**経験値関連 */
    fun decreasePlayerXp(player: EntityPlayer, amount: Int) {
        var amount_ = amount
        val i = player.experienceTotal
        if (amount_ > i) {
            amount_ = i
        }
        player.experienceTotal -= amount_
        player.experienceLevel = getLevelForXp(player.experienceTotal)
        player.experience = (player.experienceTotal.toFloat() - getXpForLevel(player.experienceLevel).toFloat()) / player.xpBarCap().toFloat()
    }

}
