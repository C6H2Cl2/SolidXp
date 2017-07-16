package c6h2cl2.solidxp.render

import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.tileentity.TileXpChest
import net.minecraft.client.model.ModelChest
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * @author C6H2Cl2
 */
@SideOnly(Side.CLIENT)
class RenderXpChest : TileEntitySpecialRenderer<TileXpChest>() {
    private val modelChest = ModelChest()
    private val TEXTURE_NORMAL = ResourceLocation(MOD_ID, "textures/blocks/tileentity/xp_chest.png")

    override fun render(te: TileXpChest, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, partial: Float) {
        GlStateManager.enableDepth()
        GlStateManager.depthFunc(515)
        GlStateManager.depthMask(true)
        val i: Int

        if (te.hasWorld()) {
            i = te.blockMetadata
        } else {
            i = 0
        }
        if (destroyStage >= 0) {
            this.bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[destroyStage])
            GlStateManager.matrixMode(5890)
            GlStateManager.pushMatrix()
            GlStateManager.scale(4.0f, 4.0f, 1.0f)
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f)
            GlStateManager.matrixMode(5888)
        } else {
            this.bindTexture(TEXTURE_NORMAL)
        }
        GlStateManager.pushMatrix()
        GlStateManager.enableRescaleNormal()

        if (destroyStage < 0) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        }

        GlStateManager.translate(x.toFloat(), y.toFloat() + 1.0f, z.toFloat() + 1.0f)
        GlStateManager.scale(1.0f, -1.0f, -1.0f)
        GlStateManager.translate(0.5f, 0.5f, 0.5f)
        var j = 0

        if (i == 2) {
            j = 180
        }

        if (i == 3) {
            j = 0
        }

        if (i == 4) {
            j = 90
        }

        if (i == 5) {
            j = -90
        }
        GlStateManager.rotate(j.toFloat(), 0.0f, 1.0f, 0.0f)
        GlStateManager.translate(-0.5f, -0.5f, -0.5f)
        modelChest.renderAll()
        GlStateManager.disableRescaleNormal()
        GlStateManager.popMatrix()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890)
            GlStateManager.popMatrix()
            GlStateManager.matrixMode(5888)
        }
    }
}