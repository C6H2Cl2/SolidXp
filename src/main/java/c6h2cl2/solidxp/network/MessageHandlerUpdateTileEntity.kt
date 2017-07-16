package c6h2cl2.solidxp.network

import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

/**
 * @author C6H2Cl2
 */
class MessageHandlerUpdateTileEntity: IMessageHandler<CMessageUpdateTileEntity, IMessage> {
    override fun onMessage(message: CMessageUpdateTileEntity, ctx: MessageContext): IMessage? {
        val tile = ctx.serverHandler.player.world.getTileEntity(message.pos)
        tile?.readFromNBT(message.data)
        return null
    }
}