package c6h2cl2.solidxp.Gui

import c6h2cl2.solidxp.Item.ItemSolidXp
import c6h2cl2.solidxp.RecipeRegistry
import c6h2cl2.solidxp.TileEntity.TileXpInfuser
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
 * Inventory
 * 0: Input Slot
 * 1: Output Slot
 * 2: Xp Slot
 */
class ContainerXpInfuser(val world: World, val pos: BlockPos, playerInventory: InventoryPlayer) : Container() {
    val tile = world.getTileEntity(pos) as TileXpInfuser

    init {
        val inventory = tile.inventory
        addSlotToContainer(SlotItemHandler(inventory, 0, 43, 34))
        addSlotToContainer(SlotItemHandler(inventory, 1, 108, 34))
        addSlotToContainer(SlotItemHandler(inventory, 2, 152, 61))
        for (j in 0 until 9) {
            addSlotToContainer(Slot(playerInventory, j, 8 + j * 18, 142))
        }
        for (j in 0..2) {
            for (k in 0 until 9) {
                addSlotToContainer(Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18))
            }
        }
    }

    override fun transferStackInSlot(playerIn: EntityPlayer?, index: Int): ItemStack {
        val slot = inventorySlots[index]
        if (!slot.hasStack || slot.stack.isEmpty) return ItemStack.EMPTY
        val itemStack = slot.stack
        val itemStackOrigin = itemStack.copy()
        if (index < 3) {
            if (!mergeItemStack(itemStack, 3, 39, false))
                return ItemStack.EMPTY
        } else {
            if (itemStack.item is ItemSolidXp) {
                if (!mergeItemStack(itemStack, 2, 3, false))
                    return ItemStack.EMPTY
            } else if (RecipeRegistry.isInfusingMaterial(itemStack)) {
                if (!mergeItemStack(itemStack, 0, 1, false))
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

    override fun canInteractWith(playerIn: EntityPlayer?) = true
}
//in:43,34
//out:108,34
//xp:151,60
//bar: 157,8-157, 56