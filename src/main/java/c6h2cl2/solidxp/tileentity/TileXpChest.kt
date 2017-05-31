package c6h2cl2.solidxp.tileentity

import c6h2cl2.solidxp.MOD_ID
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing.DOWN
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

/**
 * @author C6H2Cl2
 */
class TileXpChest: TileEntity(), ISidedInventory {
    val inventory = (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.defaultInstance as ItemStackHandler)

    init {
        inventory.setSize(36)
        validate()
    }

    // Template Overrides ===========================================================================================================================

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) inventory as T else super.getCapability(capability, facing)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag(INVENTORY, inventory.serializeNBT())
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        inventory.deserializeNBT(compound.getTag(INVENTORY) as NBTTagCompound)
        super.readFromNBT(compound)
    }

    override fun getUpdateTag(): NBTTagCompound {
        return writeToNBT(NBTTagCompound())
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity {
        return SPacketUpdateTileEntity(getPos(), 0, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, pkt: SPacketUpdateTileEntity?) {
        super.onDataPacket(net, pkt)
        if (pkt != null) {
            readFromNBT(pkt.nbtCompound)
        }
    }

    // ISidedInventory Impl =========================================================================================================================

    override fun clear() {
        (0 until inventory.slots).forEach {
            inventory.setStackInSlot(it, ItemStack.EMPTY)
        }
    }

    override fun removeStackFromSlot(index: Int): ItemStack {
        return inventory.extractItem(index, 64, false)
    }

    override fun decrStackSize(index: Int, count: Int): ItemStack {
        return inventory.extractItem(index, count, false)
    }

    override fun isEmpty(): Boolean {
        return (0 until inventory.slots).all { inventory.getStackInSlot(it).isEmpty }
    }

    override fun setInventorySlotContents(index: Int, stack: ItemStack) {
        inventory.setStackInSlot(index, stack)
    }

    override fun getStackInSlot(index: Int) = inventory.getStackInSlot(index)
    override fun getName() = "$MOD_ID.xpChest"
    override fun getInventoryStackLimit() = 64
    override fun getSlotsForFace(side: EnumFacing?) = (0..36).toList().toIntArray()
    override fun openInventory(player: EntityPlayer?){}
    override fun closeInventory(player: EntityPlayer?) {}
    override fun setField(id: Int, value: Int) {}
    override fun getFieldCount() = 0
    override fun getField(id: Int) = 0
    override fun isItemValidForSlot(index: Int, stack: ItemStack?) = true
    override fun canInsertItem(index: Int, itemStackIn: ItemStack?, direction: EnumFacing?) = direction != null && direction != DOWN
    override fun isUsableByPlayer(player: EntityPlayer?) = false
    override fun canExtractItem(index: Int, stack: ItemStack?, direction: EnumFacing?) = direction == DOWN
    override fun hasCustomName() = false
    override fun getSizeInventory() = 36
}