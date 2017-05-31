package c6h2cl2.solidxp.gui

import c6h2cl2.solidxp.MOD_ID
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import java.awt.Color

/**
 * @author C6H2Cl2
 */
class GuiXpChest(world: World, pos: BlockPos, playerInventory: InventoryPlayer) : GuiContainer(ContainerXpChest(world, pos, playerInventory)) {
    val tile = (inventorySlots as ContainerXpChest).tile

    init {

    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val s = TextComponentTranslation("$MOD_ID.gui.xp_chest").formattedText
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, -4, Color.BLACK.rgb)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.renderEngine.bindTexture(ResourceLocation(MOD_ID, "textures/gui/xp_chest.png"))
        val j = (width - xSize) / 2
        val k = (height - ySize) / 2
        drawTexturedModalRect(j, k - 10, 0, 0, xSize, ySize + 20)
    }
}