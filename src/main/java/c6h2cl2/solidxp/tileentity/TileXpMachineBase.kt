package c6h2cl2.solidxp.tileentity

import c6h2cl2.solidxp.SolidXpRegistry
import c6h2cl2.solidxp.capability.XpStorage
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

/**
 * @author C6H2Cl2
 */
open class TileXpMachineBase : TileEntity {

    val xpStorage = SolidXpRegistry.XP_STORAGE_CAPABILITY.defaultInstance as XpStorage

    constructor() : this(0)
    constructor(xpTier: Int) : super() {
        xpStorage.xpTier = xpTier
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag(XP_STORAGE, xpStorage.serializeNBT())
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound?) {
        val tag = compound ?: return
        if (tag.hasKey(XP_STORAGE)) {
            xpStorage.deserializeNBT(tag.getCompoundTag(XP_STORAGE))
        }
        super.readFromNBT(compound)
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == SolidXpRegistry.XP_STORAGE_CAPABILITY || super.hasCapability(capability, facing)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability == SolidXpRegistry.XP_STORAGE_CAPABILITY) xpStorage as T else super.getCapability(capability, facing)
    }

    override fun getUpdateTag(): NBTTagCompound {
        return writeToNBT(NBTTagCompound())
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity {
        return SPacketUpdateTileEntity(getPos(), 0, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, pkt: SPacketUpdateTileEntity?) {
        super.onDataPacket(net, pkt)
        if (pkt != null) {
            readFromNBT(pkt.nbtCompound)
        }
    }
}