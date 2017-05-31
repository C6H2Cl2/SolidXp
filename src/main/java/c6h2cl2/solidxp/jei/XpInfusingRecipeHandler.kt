package c6h2cl2.solidxp.jei

import mezz.jei.api.recipe.IRecipeHandler

/**
 * @author C6H2Cl2
 */
class XpInfusingRecipeHandler: IRecipeHandler<XpInfusingRecipe> {
    override fun getRecipeClass(): Class<XpInfusingRecipe> {
        return XpInfusingRecipe::class.java
    }

    override fun isRecipeValid(recipe: XpInfusingRecipe?) = true

    override fun getRecipeCategoryUid(recipe: XpInfusingRecipe?) = XP_INFUSING_RECIPE_UID

    override fun getRecipeWrapper(recipe: XpInfusingRecipe) = recipe
}