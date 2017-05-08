package c6h2cl2.solidxp.TileEntity

import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.ItemStackHandler

/**
 * @author C6H2Cl2
 */
class TileXpChest: TileEntity() {
    private val inventory = ItemStackHandler(36)
}