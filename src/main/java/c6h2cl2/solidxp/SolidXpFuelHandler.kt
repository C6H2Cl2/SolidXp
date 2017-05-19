package c6h2cl2.solidxp

import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.IFuelHandler

/**
 * @author C6H2Cl2
 */
object SolidXpFuelHandler: IFuelHandler {
    override fun getBurnTime(fuel: ItemStack?): Int {
        if (fuel == null || fuel.isEmpty)
            return 0
        return when(fuel.item){
            SolidXpRegistry.xpCoal -> 3200
            else -> 0
        }
    }
}