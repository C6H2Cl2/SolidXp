package c6h2cl2.solidxp

import com.google.common.collect.Lists
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

/**
 * @author C6H2Cl2
 */

object RecipeRegistry {

    private val xpInfuserRecipes = Lists.newArrayList<XpInfuserRecipe>()

    init {
        addXpInfuserRecipe(ItemStack(Items.IRON_INGOT), ItemStack(SolidXpRegistry.xpIron), 120)
        addXpInfuserRecipe(ItemStack(Items.DIAMOND), ItemStack(SolidXpRegistry.xpDiamond), 1280)
        addXpInfuserRecipe(ItemStack(Blocks.COBBLESTONE), ItemStack(SolidXpRegistry.xpCobbleStone), 40)
        addXpInfuserRecipe(ItemStack(Blocks.GLASS), ItemStack(SolidXpRegistry.xpGlass), 60)
        addXpInfuserRecipe(ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE), ItemStack(SolidXpRegistry.xpWoodPlank), 60)
        addXpInfuserRecipe(ItemStack(Items.COAL), ItemStack(SolidXpRegistry.xpCoal), 160)
        addXpInfuserRecipe(ItemStack(SolidXpRegistry.xpCoal), ItemStack(SolidXpRegistry.xpFuel), 800)
        addXpInfuserRecipe(ItemStack(SolidXpRegistry.xpFuel), ItemStack(SolidXpRegistry.xpFuelAdv), 7680)
        addXpInfuserRecipe(ItemStack(SolidXpRegistry.xpFuelAdv), ItemStack(SolidXpRegistry.xpFuelBlock), 77760)
    }

    fun register(entry: ISolidXpRecipe) {
        when (entry) {
            is XpInfuserRecipe -> addXpInfuserRecipe(entry)
        }
    }

    fun isInfusingMaterial(stack: ItemStack): Boolean {
        return xpInfuserRecipes.find { it.material.isItemEqual(stack) } != null
    }

    fun addXpInfuserRecipe(recipe: XpInfuserRecipe) {
        recipe.id = xpInfuserRecipes.size
        xpInfuserRecipes.add(recipe)
    }

    fun addXpInfuserRecipe(material: ItemStack, output: ItemStack, xp: Int) {
        addXpInfuserRecipe(XpInfuserRecipe(material, output, xp))
    }

    fun getXpInfuserRecipe(materialItem: ItemStack): XpInfuserRecipe {
        return xpInfuserRecipes.find { it.material.isItemEqual(materialItem) } ?: throw IllegalArgumentException("No such recipe for XpInfuser")
    }

    fun getXpInfuserRecipe(id: Int): XpInfuserRecipe {
        return xpInfuserRecipes[id]
    }

    fun getXpInfusingRecipe(): List<XpInfuserRecipe> {
        @Suppress("UNCHECKED_CAST")
        return xpInfuserRecipes.clone() as List<XpInfuserRecipe>
    }

    interface ISolidXpRecipe {
        var id: Int
    }

    class XpInfuserRecipe(val material: ItemStack, val output: ItemStack, val xp: Int) : ISolidXpRecipe {
        override var id = Int.MIN_VALUE
            set(value) {
                if (field == Int.MIN_VALUE) {
                    field = value
                } else {
                    throw IllegalStateException("The id of this recipe is already registered!")
                }
            }
    }
}