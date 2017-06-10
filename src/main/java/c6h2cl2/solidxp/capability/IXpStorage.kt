package c6h2cl2.solidxp.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.INBTSerializable

/**
 * @author C6H2Cl2
 */
interface IXpStorage: INBTSerializable<NBTTagCompound> {
    var xpTier: Int
    var xpValue: Long
    val maxXp: Long
}