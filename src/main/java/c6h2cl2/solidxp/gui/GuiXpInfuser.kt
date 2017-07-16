package c6h2cl2.solidxp.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

import c6h2cl2.solidxp.*
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.text.TextComponentTranslation
import java.awt.Color

/**
 * @author C6H2Cl2
 */
class GuiXpInfuser(world: World, pos: BlockPos, playerInventory: InventoryPlayer) : GuiContainer(ContainerXpInfuser(world, pos, playerInventory)) {
    val tile = (inventorySlots as ContainerXpInfuser).tile

    init {

    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val s = TextComponentTranslation("$MOD_ID.gui.xp_infuser").formattedText
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, Color.BLACK.rgb)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.renderEngine.bindTexture(ResourceLocation(MOD_ID, "textures/gui/xp_infuser.png"))
        val j = (width - xSize) / 2
        val k = (height - ySize) / 2
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize)
        val bar = (50 * tile.xpStorage.xpValue / tile.xpStorage.maxXp).toInt()
        drawTexturedModalRect(j + 158, k + 56 - bar, 177, 0, 4, bar)
        drawTexturedModalRect(j + 72, k + 34, 176, 51, Math.floor(24.0 * tile.progress).toInt() + 1, 16)
    }
}