@file:JvmName("SolidXpConst")

package c6h2cl2.solidxp

import net.minecraft.block.Block
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.enchantment.EnumEnchantmentType.*
import net.minecraft.entity.item.EntityXPOrb
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.inventory.EntityEquipmentSlot.*
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.translation.I18n
import net.minecraft.world.World
import java.util.*

/**
 * @author C6H2Cl2
 */
const val SOLIDXP_TEXT = "solidxp.text"
const val MOD_ID = "solidxp"
const val MOD_NAME = "SolidXp"
const val VERSION = "1.3.0-beta"
const val INVENTORY = "tile.inventory"
const val PROGRESS = "tile.progress"
const val XP_STORAGE = "tile.xp_storage_value"
const val RECIPE_CACHE_ID = "tile.recipe_cache.id"

//GUI ID
const val GUI_INFUSER = 0
const val GUI_XP_CHEST = 1

fun EntityPlayer.sendMessageOnlyServer(component: ITextComponent) {
    if (!world.isRemote) sendMessage(component)
}

fun EntityPlayer.addAndSpawnXpOrb(value: Long, world: World) {
    var over = if (value > Int.MAX_VALUE) {
        value - Int.MAX_VALUE
    } else {
        0
    }
    val xp = value - over
    over += Math.max(0, experienceTotal + xp - Int.MAX_VALUE)
    addExperience((value - over).toInt())
    if (over > 0 && !world.isRemote) {
        if (over > SolidXpCore.numXpOrb) {
            val reminder = over % SolidXpCore.numXpOrb
            over -= reminder
            val xpPerOrb = (over / SolidXpCore.numXpOrb).toInt()
            (0..15).forEach {
                world.spawnEntity(EntityXPOrb(world, posX, posY, posZ, xpPerOrb))
            }
            world.spawnEntity(EntityXPOrb(world, posX, posY, posZ, reminder.toInt()))
        } else {
            (0 until over).forEach {
                world.spawnEntity(EntityXPOrb(world, posX, posY, posZ, 1))
            }
        }
    }
}

fun EntityPlayer.extractExperience(amount: Int) {
    val xp = when {
        experienceTotal > amount -> amount
        else -> experienceTotal
    }
    experienceTotal -= xp
    experienceLevel = getLevelForXp(experienceTotal)
    experience = (experienceTotal.toFloat() - getXpForLevel(experienceLevel).toFloat()) / xpBarCap().toFloat()
}

fun Block.getItemBlock(): Item {
    return Item.getItemFromBlock(this)
}

fun Block.initItemBlock(registryName: ResourceLocation = this.registryName!!): Item {
    val name = if (!registryName.resourcePath.startsWith("block")) {
        ResourceLocation(registryName.resourceDomain, "block/${registryName.resourcePath}")
    } else {
        registryName
    }
    return ItemBlock(this).setRegistryName(name)
}

@JvmOverloads
fun getXpForLevel(target: Int, base: Int = 0): Int {
    var xp = 0
    (base..target).forEach {
        xp += getXpCapForLevel(it)
    }
    return xp
}

fun getXpCapForLevel(level: Int): Int {
    return when {
        level >= 30 -> 112 + (level - 30) * 9
        level >= 15 -> 37 + (level - 15) * 5
        else -> 7 + level * 2
    }
}

fun getLevelForXp(xp: Int): Int {
    var level = 0
    var xp_val = xp
    var cap = getXpCapForLevel(0)
    while (cap <= xp_val) {
        xp_val -= cap
        level++
        cap = getXpCapForLevel(level)
    }
    return level
}

fun getEquipmentSlotForType(enchantmentType: EnumEnchantmentType): Array<EntityEquipmentSlot> {
    return when (enchantmentType) {
        ALL -> arrayOf(MAINHAND, OFFHAND, HEAD, CHEST, LEGS, FEET)
        ARMOR, WEARABLE -> arrayOf(HEAD, CHEST, LEGS, FEET)
        ARMOR_HEAD -> arrayOf(HEAD)
        ARMOR_CHEST -> arrayOf(CHEST)
        ARMOR_LEGS -> arrayOf(LEGS)
        ARMOR_FEET -> arrayOf(FEET)
        WEAPON, DIGGER -> arrayOf(MAINHAND)
        BREAKABLE, BOW, FISHING_ROD -> arrayOf(MAINHAND, OFFHAND)
    }
}

fun translateToLocal(key: String): String {
    if (I18n.canTranslate(key)) {
        return I18n.translateToLocal(key)
    } else {
        return I18n.translateToFallback(key)
    }
}

fun translateToLocalFormatted(key: String, vararg format: Any): String {
    val s = translateToLocal(key)
    try {
        return String.format(s, *format)
    } catch (e: IllegalFormatException) {
        val errorMessage = "Format error: " + s
        println(errorMessage)
        return errorMessage
    }

}