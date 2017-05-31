package c6h2cl2.solidxp.block

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


/**
 * @author C6H2Cl2
 */
interface IXpHolderBlock {
    fun getUnlocalizedMachineName(): String
    fun getXpLimit(world: World, pos: BlockPos): Long
    fun getXpValue(world: World, pos: BlockPos): Long
}