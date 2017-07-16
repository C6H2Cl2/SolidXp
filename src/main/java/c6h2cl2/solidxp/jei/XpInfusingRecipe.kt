package c6h2cl2.solidxp.jei

import c6h2cl2.solidxp.*
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.IRecipeWrapper
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.item.ItemStack
import java.util.*

/**
 * @author C6H2Cl2
 */
class XpInfusingRecipe(val input: ItemStack, val output: ItemStack, val xpValue: Int) : IRecipeWrapper {
    private val mainColor = 0xFF80FF20.toInt()

    override fun drawInfo(minecraft: Minecraft, recipeWidth: Int, recipeHeight: Int, mouseX: Int, mouseY: Int) {
        drawRepairCost(minecraft, "${translateToLocal(XP_INFUSING_COST)}: $xpValue", mainColor, recipeWidth)
    }

    override fun getTooltipStrings(mouseX: Int, mouseY: Int): MutableList<String> {
        return Collections.emptyList()
    }

    override fun handleClick(minecraft: Minecraft?, mouseX: Int, mouseY: Int, mouseButton: Int): Boolean {
        return false
    }

    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInput(ItemStack::class.java, input)
        ingredients.setOutput(ItemStack::class.java, output)
    }

    private fun drawRepairCost(minecraft: Minecraft, text: String, mainColor: Int, recipeWidth: Int) {
        val shadowColor = 0xFF000000.toInt() or (mainColor and 0xFCFCFC shr 2)
        val width = minecraft.fontRenderer.getStringWidth(text)
        val x = recipeWidth / 2 - 18 - width / 2
        val y = 55

        if (minecraft.fontRenderer.unicodeFlag) {
            Gui.drawRect(x - 2, y - 2, x + width + 2, y + 10, 0xFF000000.toInt())
            Gui.drawRect(x - 1, y - 1, x + width + 1, y + 9, 0xFF3B3B3B.toInt())
        } else {
            minecraft.fontRenderer.drawString(text, x + 1, y, shadowColor)
            minecraft.fontRenderer.drawString(text, x, y + 1, shadowColor)
            minecraft.fontRenderer.drawString(text, x + 1, y + 1, shadowColor)
        }

        minecraft.fontRenderer.drawString(text, x, y, mainColor)
    }
}