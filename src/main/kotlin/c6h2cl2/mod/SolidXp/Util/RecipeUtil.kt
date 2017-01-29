package c6h2cl2.mod.SolidXp.Util

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by ma87k on 2016/07/10.
 */
object RecipeUtil {

    fun registerSwordRecipe(output: Item, ingot: ItemStack, rod: ItemStack) {
        GameRegistry.addRecipe(ItemStack(output), " I ", " I ", " R ", 'I', ingot, 'R', rod)
    }

    fun registerSwordRecipe(output: Item, ingot: Item, rod: ItemStack) {
        registerSwordRecipe(output, ItemStack(ingot), rod)
    }

    fun registerSwordRecipe(output: Item, ingot: ItemStack, rod: Item) {
        registerSwordRecipe(output, ingot, ItemStack(rod))
    }

    fun registerSwordRecipe(output: Item, ingot: Item, rod: Item) {
        registerSwordRecipe(output, ItemStack(ingot), ItemStack(rod))
    }

    fun registerPickaxeRecipe(output: Item, ingot: ItemStack, rod: ItemStack) {
        GameRegistry.addRecipe(ItemStack(output), "III", " R ", " R ", 'I', ingot, 'R', rod)
    }

    fun registerPickaxeRecipe(output: Item, ingot: Item, rod: Item) {
        registerPickaxeRecipe(output, ItemStack(ingot), ItemStack(rod))
    }

    fun registerAxeRecipe(output: Item, ingot: Item, rod: Item) {
        GameRegistry.addRecipe(ItemStack(output), "II ", "IR ", " R ", 'I', ingot, 'R', rod)
    }

    fun registerShovelRecipe(output: Item, ingot: Item, rod: Item) {
        GameRegistry.addRecipe(ItemStack(output), " I ", " R ", " R ", 'I', ingot, 'R', rod)
    }

    fun registerHoeRecipe(output: Item, ingot: Item, rod: Item) {
        GameRegistry.addRecipe(ItemStack(output), "II ", " R ", " R ", 'I', ingot, 'R', rod)
    }

}
