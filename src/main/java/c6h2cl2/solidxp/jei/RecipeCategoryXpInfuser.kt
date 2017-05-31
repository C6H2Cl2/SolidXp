package c6h2cl2.solidxp.jei

import mezz.jei.api.gui.IDrawable
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.ingredients.IIngredients
import c6h2cl2.solidxp.*
import mezz.jei.api.IGuiHelper
import mezz.jei.api.gui.IDrawableAnimated.StartDirection.*
import mezz.jei.api.recipe.BlankRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class RecipeCategoryXpInfuser(private val guiHelper: IGuiHelper): BlankRecipeCategory<XpInfusingRecipe>() {
    private val texture = ResourceLocation(MOD_ID, "textures/gui/xp_infuser.png")
    private val background = guiHelper.createDrawable(texture, 4, 4, 168, 78, 0, 30, 0, 25)!!
    private val xpBar = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(texture, 177, 0, 4, 52 ), 40, BOTTOM, false)
    private val timer = guiHelper.createTickTimer(300, 15, false)

    override fun getTitle(): String {
        return translateToLocal("solidxp.jei.xp_infusing")
    }

    override fun getBackground(): IDrawable {
        return background
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: XpInfusingRecipe, ingredients: IIngredients) {
        val itemStacks = recipeLayout.itemStacks
        //Input item
        itemStacks.init(0, true, 38, 29)
        //Output item
        itemStacks.init(1, false, 103, 29)

        itemStacks.set(ingredients)
    }


    override fun getUid(): String {
        return XP_INFUSING_RECIPE_UID
    }

    override fun drawExtras(minecraft: Minecraft) {
        xpBar.draw(minecraft, 154, 2)
        val meta = timer.value
        minecraft.renderItem.renderItemIntoGUI(ItemStack(SolidXpRegistry.solidXp, 1, meta), 148, 57)
    }
}