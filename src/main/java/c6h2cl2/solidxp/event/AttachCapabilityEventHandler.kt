package c6h2cl2.solidxp.event

import net.minecraft.tileentity.TileEntity
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * @author C6H2Cl2
 */
class AttachCapabilityEventHandler {
    @SubscribeEvent
    fun attachTileEntityEvent(event: AttachCapabilitiesEvent<TileEntity>){

    }
}