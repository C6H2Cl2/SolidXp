package c6h2cl2.solidxp.block

import c6h2cl2.solidxp.SolidXpCore
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */

fun EntityPlayer.openGui(id: Int, world: World, pos: BlockPos) {
    openGui(SolidXpCore.INSTANCE, id, world, pos.x, pos.y, pos.z)
}