package c6h2cl2.solidxp.network

import io.netty.buffer.ByteBuf
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

/**
 * @author C6H2Cl2
 */
class CMessageUpdateTileEntity : IMessage {

    lateinit var data: NBTTagCompound
    lateinit var pos: BlockPos

    constructor()

    constructor(nbtTagCompound: NBTTagCompound, pos: BlockPos) {
        data = nbtTagCompound
        this.pos = pos
    }

    override fun fromBytes(buf: ByteBuf) {
        val packet = PacketBuffer(buf)
        data = packet.readCompoundTag() ?: return
        pos = packet.readBlockPos()
    }

    override fun toBytes(buf: ByteBuf) {
        val packet = PacketBuffer(buf)
        packet.writeCompoundTag(data)
        packet.writeBlockPos(pos)
    }
}