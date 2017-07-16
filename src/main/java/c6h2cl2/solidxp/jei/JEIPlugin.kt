package c6h2cl2.solidxp.jei

import c6h2cl2.solidxp.RecipeRegistry
import c6h2cl2.solidxp.SolidXpRegistry
import mezz.jei.api.IJeiRuntime
import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.ISubtypeRegistry
import mezz.jei.api.JEIPlugin
import mezz.jei.api.ingredients.IModIngredientRegistration
import mezz.jei.api.recipe.IRecipeCategoryRegistration
import mezz.jei.api.recipe.IRecipeWrapper
import mezz.jei.api.recipe.IRecipeWrapperFactory
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */

@JEIPlugin
class JEIPlugin : IModPlugin{
    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(RecipeCategoryXpInfuser(registry.jeiHelpers.guiHelper))

    }

    override fun register(registry: IModRegistry) {
        registry.handleRecipes<XpInfusingRecipe>(XpInfusingRecipe::class.java, { recipe -> recipe }, XP_INFUSING_RECIPE_UID)
        registry.addRecipeCatalyst(ItemStack(SolidXpRegistry.xpInfuser), XP_INFUSING_RECIPE_UID)
        //Register Xp Infusing Recipe
        val xpInfusingRecipes = RecipeRegistry.getXpInfusingRecipe()
        registry.addRecipes(xpInfusingRecipes.map { XpInfusingRecipe(it.material, it.output, it.xp) }, XP_INFUSING_RECIPE_UID)
    }


    override fun onRuntimeAvailable(jeiRuntime: IJeiRuntime?) {

    }

    override fun registerItemSubtypes(subtypeRegistry: ISubtypeRegistry?) {

    }

    override fun registerIngredients(registry: IModIngredientRegistration?) {

    }
}