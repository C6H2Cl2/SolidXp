package c6h2cl2.solidxp

import c6h2cl2.solidxp.network.CMessageUpdateTileEntity
import c6h2cl2.solidxp.network.MessageHandlerUpdateTileEntity
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.relauncher.Side.*

/**
 * @author C6H2Cl2
 */
class PacketHandler {
    companion object{
        @JvmStatic
        val INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID)!!

        fun init(){
            INSTANCE.registerMessage(MessageHandlerUpdateTileEntity::class.java, CMessageUpdateTileEntity::class.java, 0, SERVER)
        }
    }
}