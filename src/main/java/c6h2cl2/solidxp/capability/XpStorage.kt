package c6h2cl2.solidxp.capability

import c6h2cl2.solidxp.tileentity.getXpValue
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.INBTSerializable

/**
 * @author C6H2Cl2
 */
class XpStorage : IXpStorage, INBTSerializable<NBTTagCompound> {

    override var xpTier: Int = 0
        set(value) {
            if (value in (0..15)) {
                field = value
            }
        }
    override var xpValue: Long = 0L
        set(value) {
            if (value in (0..maxXp)) {
                field = value
            }
        }
    override val maxXp: Long
        get() = getXpValue(xpTier + 1) * 64

    override fun deserializeNBT(nbt: NBTTagCompound?) {
        xpTier = nbt?.getInteger(XP_TIER) ?: 0
        xpValue = nbt?.getLong(XP_VALUE) ?: 0L
    }

    override fun serializeNBT(): NBTTagCompound {
        val tag = NBTTagCompound()
        tag.setInteger(XP_TIER, xpTier)
        tag.setLong(XP_VALUE, xpValue)
        return tag
    }

}