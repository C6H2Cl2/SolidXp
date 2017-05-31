package c6h2cl2.solidxp.item

import c6h2cl2.solidxp.block.IXpHolderBlock
import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.SolidXpRegistry
import c6h2cl2.solidxp.translateToLocal
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumActionResult.PASS
import net.minecraft.util.EnumActionResult.SUCCESS
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class ItemXpChecker : Item() {
    init {
        unlocalizedName = "xpChecker"
        registryName = ResourceLocation(MOD_ID, "xp_checker")
        creativeTab = SolidXpRegistry.tabSolidXp
        maxStackSize = 1
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (worldIn.isRemote){
            playerIn.sendMessage(TextComponentString("${translateToLocal("solidxp.text.player_xp")}: ${playerIn.experienceTotal} / ${Int.MAX_VALUE}"))
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val block = worldIn.getBlockState(pos).block as? IXpHolderBlock ?: return PASS
        if (!worldIn.isRemote){
            player.sendMessage(TextComponentString("${translateToLocal(block.getUnlocalizedMachineName())}: ${block.getXpValue(worldIn, pos)} / ${block.getXpLimit(worldIn, pos)}"))
        }
        return SUCCESS
    }

    override fun addInformation(stack: ItemStack, playerIn: EntityPlayer, tooltip: MutableList<String>, advanced: Boolean) {
        tooltip.add("Right Click to see your experience")
        tooltip.add("Using for Xp-Machines, you can see xp value in it")
    }
}