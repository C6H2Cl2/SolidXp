package c6h2cl2.solidxp.Item

import c6h2cl2.YukariLib.IToolWithType
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */
interface ICraftResultEnchanted: IToolWithType {
    fun getEnchanted(): ItemStack
}