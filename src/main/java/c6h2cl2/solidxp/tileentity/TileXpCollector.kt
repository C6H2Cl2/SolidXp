package c6h2cl2.solidxp.tileentity

import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import net.minecraft.entity.item.EntityXPOrb
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

/**
 * @author C6H2Cl2
 */
class TileXpCollector : TileXpMachineBase(), IInventory, ITickable {

    val inventory = (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.defaultInstance as ItemStackHandler)
    var range = 4.0
    var limit = 100
    init {
        inventory.setSize(1)
    }

    override fun update() {
        //経験値オーブの取得
        val entities = world.getEntitiesWithinAABB(EntityXPOrb::class.java, AxisAlignedBB(pos.x - range, pos.y - range, pos.z - range, pos.x + range, pos.y + range, pos.z + range))
        var totalXp = 0L
        //経験値オーブの経験値の総量をカウント
        entities.forEach {
            totalXp += it.xpValue
            world.removeEntity(it)
            if (totalXp >= limit) {
                return@forEach
            }
        }
        totalXp += xpStorage.xpValue
        val stack = inventory.getStackInSlot(0)
        val xpValue = getXpValue(xpStorage.xpTier)
        //XpTierが変わっていた場合、変換
        if (stack.metadata != xpStorage.xpTier) {
            totalXp += getXpValue(stack.metadata) * stack.count
            inventory.setStackInSlot(0, ItemStack.EMPTY)
        }
        //追加するItemの数
        var count = 0
        while (totalXp >= xpValue && count < 64) {
            count++
            totalXp -= xpValue
        }
        //Itemの追加処理
        if (count != 0) {
            val itemStack = inventory.insertItem(0, ItemStack(SolidXpRegistry.solidXp, count, xpStorage.xpTier), false)
            //余剰分は経験値に戻しましょうねー
            if (itemStack != ItemStack.EMPTY) {
                totalXp += xpValue * itemStack.count
            }
        }
        xpStorage.xpValue = totalXp
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

    override fun readFromNBT(compound: NBTTagCompound?) {
        compound ?: return
        inventory.deserializeNBT(compound.getTag(INVENTORY) as NBTTagCompound)
        super.readFromNBT(compound)
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