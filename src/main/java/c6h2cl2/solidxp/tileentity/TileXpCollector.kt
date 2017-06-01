package c6h2cl2.solidxp.tileentity

import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.entity.item.EntityXPOrb
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

/**
 * @author C6H2Cl2
 */
class TileXpCollector : TileEntity(), IInventory, ITickable {

    val inventory = (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.defaultInstance as ItemStackHandler)
    var range = 4.0
    var limit = 100
    var xpTier = 0
        set(value) {
            if (value in 0..15) {
                val itemStack = inventory.getStackInSlot(0)
                val xp: Long
                if (itemStack.item == SolidXpRegistry.solidXp) {
                    val xpValue = getXpValue(itemStack.metadata)
                    xp = inventory.getStackInSlot(0).count * xpValue
                    inventory.setStackInSlot(0, ItemStack.EMPTY)
                } else {
                    xp = 0
                }
                field = value
                xpStorage += xp
            }
        }
    var xpStorage = 0L
        set(value) {
            if (value < 0) return
            field = value
            val xpValue = getXpValue(xpTier)
            var count = 0
            while (field >= getXpValue(xpTier)) {
                count++
                field -= xpValue
                if (count >= 64) {
                    break
                }
            }
            if (count > 0) {
                val itemStack = inventory.insertItem(0, ItemStack(SolidXpRegistry.solidXp, count, xpTier), false)
                if (itemStack != ItemStack.EMPTY) {
                    field += xpValue * itemStack.count
                }
            }
        }

    init {
        inventory.setSize(1)
    }

    override fun update() {
        val entities = world.getEntitiesWithinAABB(EntityXPOrb::class.java, AxisAlignedBB(pos.x - range, pos.y - range, pos.z - range, pos.x + range, pos.y + range, pos.z + range))
        var totalXp = 0L
        entities.forEach {
            totalXp += it.xpValue
            world.removeEntity(it)
            if (totalXp >= limit){
                return@forEach
            }
        }
        if (totalXp != 0L || xpStorage > getXpValue(xpTier)) {
            xpStorage += totalXp
        }
    }

    // Template Overrides ===========================================================================================================================

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) inventory as T else super.getCapability(capability, facing)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag(INVENTORY, inventory.serializeNBT())
        compound.setInteger(XP_TIER, xpTier)
        compound.setLong(XP_STORAGE, xpStorage)
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        inventory.deserializeNBT(compound.getTag(INVENTORY) as NBTTagCompound)
        xpStorage = compound.getLong(XP_STORAGE)
        xpTier = compound.getInteger(XP_TIER)
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

    // IInventory Impl ==============================================================================================================================

    override fun getStackInSlot(index: Int): ItemStack {
        return inventory.getStackInSlot(index)
    }

    override fun decrStackSize(index: Int, count: Int): ItemStack {
        return inventory.extractItem(index, count, false)
    }

    override fun clear() {
        inventory.setStackInSlot(0, ItemStack.EMPTY)
    }

    override fun isEmpty(): Boolean {
        return inventory.getStackInSlot(0) == ItemStack.EMPTY
    }

    override fun setInventorySlotContents(index: Int, stack: ItemStack) {
        inventory.setStackInSlot(index, stack)
    }

    override fun removeStackFromSlot(index: Int): ItemStack {
        return inventory.extractItem(index, 64, false)
    }

    override fun getFieldCount() = 0
    override fun getField(id: Int) = 0
    override fun hasCustomName() = false
    override fun getSizeInventory() = 1
    override fun getName() = "$MOD_ID.xpCollector"
    override fun isUsableByPlayer(player: EntityPlayer?) = true
    override fun isItemValidForSlot(index: Int, stack: ItemStack?) = true
    override fun getInventoryStackLimit() = 64
    override fun openInventory(player: EntityPlayer?) {}
    override fun setField(id: Int, value: Int) {}
    override fun closeInventory(player: EntityPlayer?) {}
}