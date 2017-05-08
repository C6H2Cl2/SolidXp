package c6h2cl2.solidxp.JEI

import c6h2cl2.solidxp.RecipeRegistry
import c6h2cl2.solidxp.SolidXpRegistry
import mezz.jei.api.IJeiRuntime
import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.ISubtypeRegistry
import mezz.jei.api.JEIPlugin
import mezz.jei.api.ingredients.IModIngredientRegistration
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */

@JEIPlugin
class JEIPlugin : IModPlugin{

    override fun register(registry: IModRegistry) {
        registry.addRecipeCategories(RecipeCategoryXpInfuser(registry.jeiHelpers.guiHelper))
        registry.addRecipeHandlers(XpInfusingRecipeHandler())
        registry.addRecipeCategoryCraftingItem(ItemStack(SolidXpRegistry.xpInfuser), XP_INFUSING_RECIPE_UID)
        //Register Xp Infusing Recipe
        val xpInfusingRecipes = RecipeRegistry.getXpInfusingRecipe()
        registry.addRecipes(xpInfusingRecipes.map { XpInfusingRecipe(it.material, it.output, it.xp) })
    }


    override fun onRuntimeAvailable(jeiRuntime: IJeiRuntime?) {

    }

    override fun registerItemSubtypes(subtypeRegistry: ISubtypeRegistry?) {

    }

    override fun registerIngredients(registry: IModIngredientRegistration?) {

    }
}