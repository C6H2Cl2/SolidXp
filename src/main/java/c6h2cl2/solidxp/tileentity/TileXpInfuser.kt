package c6h2cl2.solidxp.tileentity

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.ItemStackHandler
import c6h2cl2.solidxp.*
import c6h2cl2.solidxp.item.ItemSolidXp
import c6h2cl2.solidxp.RecipeRegistry.XpInfuserRecipe
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.NetworkManager
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumFacing.*
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler

/**
 * @author C6H2Cl2
 */
class TileXpInfuser : TileXpMachineBase(), ITickable, ISidedInventory {
    /**
     * Inventory
     * 0: Input Slot
     * 1: Output Slot
     * 2: Xp Slot
     * */
    val inventory = (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.defaultInstance as ItemStackHandler)
    var progress = 0f
        private set
    var xpRate = 2
        private set
    private var recipeCache: XpInfuserRecipe? = null

    init {
        inventory.setSize(3)
        validate()
    }

    override fun update() {
        //ItemSolidXpを内蔵経験値に変換するナリ
        convertItemToXpStorage()
        //加工処理
        processInfusing()
        markDirty()
    }

    private fun convertItemToXpStorage(){
        if (inventory.getStackInSlot(2).item is ItemSolidXp && xpStorage.xpValue != xpStorage.maxXp) {
            val itemStack = inventory.getStackInSlot(2)
            val xp = (itemStack.item as ItemSolidXp).getXpValue(itemStack.metadata)
            if (xpStorage.xpValue + xp <= xpStorage.maxXp) {
                xpStorage.xpValue += xp.toInt()
                inventory.extractItem(2, 1, false)
            }
        }
    }

    private fun processInfusing(){
        val material = inventory.getStackInSlot(0)
        val output = inventory.getStackInSlot(1)
        if (material.isEmpty || recipeCache == null || !material.isItemEqual(recipeCache?.material)) {
            progress = 0f
            recipeCache = null
        }
        if (recipeCache == null) {
            if (xpStorage.xpValue < xpRate || !RecipeRegistry.isInfusingMaterial(material) || output.maxStackSize == output.count)
                return
            recipeCache = RecipeRegistry.getXpInfuserRecipe(material)
        }
        val recipe = recipeCache as XpInfuserRecipe
        if ((output.isEmpty || recipe.output.isItemEqual(output)) && xpStorage.xpValue >= xpRate) {
            progress += xpRate / recipe.xp.toFloat()
            xpStorage.xpValue -= xpRate
        }
        if (progress >= 1) {
            progress = 0f
            inventory.insertItem(1, recipe.output.copy(), false)
            inventory.extractItem(0, 1, false)
            if (inventory.getStackInSlot(0).isEmpty) {
                recipeCache = null
            }
        }
    }

    // Template Overrides ===========================================================================================================================


    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        val tag = super.writeToNBT(compound)
        tag.setTag(INVENTORY, inventory.serializeNBT())
        tag.setFloat(PROGRESS, progress)
        if (recipeCache != null)
            tag.setInteger(RECIPE_CACHE_ID, recipeCache!!.id)
        return tag
    }

    override fun readFromNBT(compound: NBTTagCompound?) {
        super.readFromNBT(compound)
        compound ?: return
        if (compound.hasKey(INVENTORY)) {
            inventory.deserializeNBT(compound.getTag(INVENTORY) as NBTTagCompound)
        }
        if (compound.hasKey(PROGRESS)) {
            progress = compound.getFloat(PROGRESS)
        }
        if (compound.hasKey(RECIPE_CACHE_ID))
            recipeCache = RecipeRegistry.getXpInfuserRecipe(compound.getInteger(RECIPE_CACHE_ID))
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability) inventory as T else super.getCapability(capability, facing)
    }

    // ISidedInventory Impl =========================================================================================================================

    override fun clear() {
        (0 until inventory.slots).forEach { inventory.setStackInSlot(it, ItemStack.EMPTY) }
    }

    override fun getSlotsForFace(side: EnumFacing): IntArray {
        return when (side) {
            UP -> intArrayOf(0)
            NORTH, SOUTH, EAST, WEST -> intArrayOf(2)
            DOWN -> intArrayOf(1)
        }
    }

    override fun isEmpty(): Boolean {
        return (0 until inventory.slots).all { inventory.getStackInSlot(it).isEmpty }
    }

    override fun isItemValidForSlot(index: Int, stack: ItemStack): Boolean {
        return when (index) {
            0 -> !RecipeRegistry.isInfusingMaterial(stack)
            1 -> false
            2 -> stack.item is ItemSolidXp
            else -> false
        }
    }

    override fun canInsertItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean {
        return when (index) {
            0 -> !RecipeRegistry.isInfusingMaterial(stack) && direction == UP
            1 -> false
            2 -> stack.item is ItemSolidXp
            else -> false
        } && getSlotsForFace(direction).contains(index)
    }

    override fun canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean {
        return when (index) {
            0 -> !RecipeRegistry.isInfusingMaterial(stack) && direction == UP
            1 -> true
            2 -> stack.item is ItemSolidXp
            else -> false
        } && getSlotsForFace(direction).contains(index)
    }

    override fun setInventorySlotContents(index: Int, stack: ItemStack) {
        inventory.setStackInSlot(index, stack)
    }

    override fun openInventory(player: EntityPlayer?) {}
    override fun closeInventory(player: EntityPlayer?) {}

    override fun setField(id: Int, value: Int) {}
    override fun getField(id: Int) = 0
    override fun getFieldCount() = 0

    override fun isInvalid() = false
    override fun getName() = "$MOD_ID.xpInfuser"
    override fun getInventoryStackLimit() = 64
    override fun isUsableByPlayer(player: EntityPlayer?) = true
    override fun hasCustomName(): Boolean = false
    override fun getStackInSlot(index: Int) = inventory.getStackInSlot(index)
    override fun removeStackFromSlot(index: Int) = inventory.extractItem(index, 64, false)
    override fun decrStackSize(index: Int, count: Int) = inventory.extractItem(index, count, false)
    override fun getSizeInventory() = inventory.slots
}