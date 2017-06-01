package c6h2cl2.solidxp.gui

import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.PacketHandler
import c6h2cl2.solidxp.network.CMessageUpdateTileEntity
import c6h2cl2.solidxp.tileentity.getXpValue
import net.minecraft.client.gui.GuiButton
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
class GuiXpCollector(world: World, pos: BlockPos, playerInventory: InventoryPlayer) : GuiContainer(ContainerXpCollector(world, pos, playerInventory)) {
    val tile = (inventorySlots as ContainerXpCollector).tile

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.renderEngine.bindTexture(ResourceLocation(MOD_ID, "textures/gui/xp_collector.png"))
        val j = (width - xSize) / 2
        val k = (height - ySize) / 2
        drawTexturedModalRect(j, k - 10, 0, 0, xSize, ySize)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val s = TextComponentTranslation("$MOD_ID.gui.xp_infuser").formattedText
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 0, Color.BLACK.rgb)
        val xp = getXpValue(tile.xpTier).toString()
        fontRendererObj.drawString(xp, 138 - (fontRendererObj.getStringWidth(xp) / 2.0f), 32.0f, Color.BLACK.rgb, false)
    }

    override fun initGui() {
        super.initGui()
        var s = "+"
        addButton(GuiButton(0, 255 - (fontRendererObj.getStringWidth(s) / 2), 40, 20, 20, s))
        s = "-"
        addButton(GuiButton(1, 255 - (fontRendererObj.getStringWidth(s) / 2), 85, 20, 20, s))
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> tile.xpTier++
            1 -> tile.xpTier--
        }
        PacketHandler.INSTANCE.sendToServer(CMessageUpdateTileEntity(tile.updateTag, tile.pos))
    }
}