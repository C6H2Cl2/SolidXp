package c6h2cl2.solidxp

import c6h2cl2.solidxp.gui.ContainerXpChest
import c6h2cl2.solidxp.gui.ContainerXpCollector
import c6h2cl2.solidxp.gui.ContainerXpInfuser
import c6h2cl2.solidxp.gui.GuiXpChest
import c6h2cl2.solidxp.gui.GuiXpCollector
import c6h2cl2.solidxp.gui.GuiXpInfuser
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
            GUI_XP_CHEST -> GuiXpChest(world, BlockPos(x, y, z), player.inventory)
            GUI_XP_COLLECTOR -> GuiXpCollector(world, BlockPos(x, y, z), player.inventory)
            else -> null
        }
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        return when(ID){
            GUI_INFUSER -> ContainerXpInfuser(world, BlockPos(x, y, z), player.inventory)
            GUI_XP_CHEST -> ContainerXpChest(world, BlockPos(x, y, z), player.inventory)
            GUI_XP_COLLECTOR -> ContainerXpCollector(world, BlockPos(x, y, z), player.inventory)
            else -> null
        }
    }
}