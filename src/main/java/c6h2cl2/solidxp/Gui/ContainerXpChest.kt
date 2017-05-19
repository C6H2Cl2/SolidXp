package c6h2cl2.solidxp.Gui

import c6h2cl2.solidxp.TileEntity.TileXpChest
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.SlotItemHandler

/**
 * @author C6H2Cl2
 */
class ContainerXpChest(val world: World, val pos: BlockPos, playerInventory: InventoryPlayer) : Container() {
    val tile = world.getTileEntity(pos) as TileXpChest

    init {
        for (j in 0 until 4) {
            for (k in 0 until 9) {
                addSlotToContainer(SlotItemHandler(tile.inventory, j * 9 + k, 8 + k * 18, 8 + j * 18))
            }
        }


        for (j in 0 until 9) {
            addSlotToContainer(Slot(playerInventory, j, 8 + j * 18, 150))
        }
        for (j in 0..2) {
            for (k in 0 until 9) {
                addSlotToContainer(Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 92 + j * 18))
            }
        }
    }

    override fun canInteractWith(playerIn: EntityPlayer?) = true

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        val slot = inventorySlots[index]
        if (!slot.hasStack || slot.stack.isEmpty) return ItemStack.EMPTY
        val itemStack = slot.stack
        val itemStackOrigin = itemStack.copy()
        if (index < 36) {
            if (!mergeItemStack(itemStack, 36, 63, false)) {
                return ItemStack.EMPTY
            }
        } else {
            if (!mergeItemStack(itemStack, 0, 36, false)) {
                return ItemStack.EMPTY
            }
        }
        if (itemStack.count == 0) {
            slot.putStack(ItemStack.EMPTY)
        } else {
            slot.onSlotChanged()
        }
        if (itemStack.count == itemStackOrigin.count)
            return ItemStack.EMPTY
        return itemStackOrigin
    }
}