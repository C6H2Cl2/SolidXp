package c6h2cl2.solidxp.Render

import c6h2cl2.solidxp.MOD_ID
import c6h2cl2.solidxp.TileEntity.TileXpChest
import net.minecraft.block.BlockChest
import net.minecraft.client.model.ModelChest
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

    override fun renderTileEntityAt(te: TileXpChest, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int) {
        GlStateManager.enableDepth()
        GlStateManager.depthFunc(515)
        GlStateManager.depthMask(true)
        var i: Int

        if (te.hasWorld()) {
            val block = te.blockType
            i = te.blockMetadata

            if (block is BlockChest && i == 0) {
                block.checkForSurroundingChests(te.world, te.pos, te.world.getBlockState(te.pos))
                i = te.blockMetadata
            }
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
        /*f = 1.0f - f
        f = 1.0f - f * f * f
        modelChest.chestLid.rotateAngleX = -(f * (Math.PI.toFloat() / 2f))*/
        modelChest.renderAll()
        GlStateManager.disableRescaleNormal()
        GlStateManager.popMatrix()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890)
            GlStateManager.popMatrix()
            GlStateManager.matrixMode(5888)
        }
/*
        if (te.adjacentChestZNeg == null && te.adjacentChestXNeg == null) {
            val modelchest: ModelChest

            if (te.adjacentChestXPos == null && te.adjacentChestZPos == null) {
                modelchest = this.modelChest

                if (destroyStage >= 0) {
                    this.bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[destroyStage])
                    GlStateManager.matrixMode(5890)
                    GlStateManager.pushMatrix()
                    GlStateManager.scale(4.0f, 4.0f, 1.0f)
                    GlStateManager.translate(0.0625f, 0.0625f, 0.0625f)
                    GlStateManager.matrixMode(5888)
                } else if (this.isChristmas) {
                    this.bindTexture(TEXTURE_CHRISTMAS)
                } else if (te.chestType == BlockChest.Type.TRAP) {
                    this.bindTexture(TEXTURE_TRAPPED)
                } else {
                    this.bindTexture(TEXTURE_NORMAL)
                }
            } else {
                modelchest = this.largeChest

                if (destroyStage >= 0) {
                    this.bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[destroyStage])
                    GlStateManager.matrixMode(5890)
                    GlStateManager.pushMatrix()
                    GlStateManager.scale(8.0f, 4.0f, 1.0f)
                    GlStateManager.translate(0.0625f, 0.0625f, 0.0625f)
                    GlStateManager.matrixMode(5888)
                } else if (this.isChristmas) {
                    this.bindTexture(TEXTURE_CHRISTMAS_DOUBLE)
                } else if (te.chestType == BlockChest.Type.TRAP) {
                    this.bindTexture(TEXTURE_TRAPPED_DOUBLE)
                } else {
                    this.bindTexture(TEXTURE_NORMAL_DOUBLE)
                }
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

            if (i == 2 && te.adjacentChestXPos != null) {
                GlStateManager.translate(1.0f, 0.0f, 0.0f)
            }

            if (i == 5 && te.adjacentChestZPos != null) {
                GlStateManager.translate(0.0f, 0.0f, -1.0f)
            }

            GlStateManager.rotate(j.toFloat(), 0.0f, 1.0f, 0.0f)
            GlStateManager.translate(-0.5f, -0.5f, -0.5f)
            var f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks

            if (te.adjacentChestZNeg != null) {
                val f1 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks

                if (f1 > f) {
                    f = f1
                }
            }

            if (te.adjacentChestXNeg != null) {
                val f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks

                if (f2 > f) {
                    f = f2
                }
            }

            f = 1.0f - f
            f = 1.0f - f * f * f
            modelchest.chestLid.rotateAngleX = -(f * (Math.PI.toFloat() / 2f))
            modelchest.renderAll()
            GlStateManager.disableRescaleNormal()
            GlStateManager.popMatrix()
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)

            if (destroyStage >= 0) {
                GlStateManager.matrixMode(5890)
                GlStateManager.popMatrix()
                GlStateManager.matrixMode(5888)
            }
        }
        */
    }
}