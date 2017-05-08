package c6h2cl2.solidxp

import c6h2cl2.solidxp.Gui.ContainerXpInfuser
import c6h2cl2.solidxp.Gui.GuiXpInfuser
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

/**
 * @author C6H2Cl2
 */
class SolidXpGuiHandler : IGuiHandler{
    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        return when(ID){
            GUI_INFUSER -> GuiXpInfuser(world, BlockPos(x, y, z), player.inventory)
            else -> null
        }
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        return when(ID){
            GUI_INFUSER -> ContainerXpInfuser(world, BlockPos(x, y, z), player.inventory)
            else -> null
        }
    }
}