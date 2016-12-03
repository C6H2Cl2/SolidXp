package c6h2cl2.mod.SolidXp.Util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by ma87k on 2016/07/10.
 */
public class RecipeUtil {

    public static void registerSwordRecipe(Item output, ItemStack ingot, ItemStack rod){
        GameRegistry.addRecipe(new ItemStack(output)," I "," I "," R ",'I',ingot,'R',rod);
    }
    public static void registerSwordRecipe(Item output, Item ingot, ItemStack rod){
        registerSwordRecipe(output,new ItemStack(ingot),rod);
    }
    public static void registerSwordRecipe(Item output, ItemStack ingot, Item rod){
        registerSwordRecipe(output,ingot,new ItemStack(rod));
    }
    public static void registerSwordRecipe(Item output, Item ingot, Item rod){
        registerSwordRecipe(output,new ItemStack(ingot),new ItemStack(rod));
    }
    public static void registerPickaxeRecipe(Item output, ItemStack ingot, ItemStack rod){
        GameRegistry.addRecipe(new ItemStack(output),"III"," R "," R ",'I',ingot,'R',rod);
    }
    public static void registerPickaxeRecipe(Item output, Item ingot, Item rod){
        registerPickaxeRecipe(output,new ItemStack(ingot),new ItemStack(rod));
    }
    public static void registerAxeRecipe(Item output, Item ingot, Item rod){
        GameRegistry.addRecipe(new ItemStack(output),"II ","IR "," R ",'I',ingot,'R',rod);
    }
    public static void registerShovelRecipe(Item output, Item ingot, Item rod){
        GameRegistry.addRecipe(new ItemStack(output)," I "," R "," R ",'I',ingot,'R',rod);
    }
    public static void registerHoeRecipe(Item output, Item ingot, Item rod){
        GameRegistry.addRecipe(new ItemStack(output),"II "," R "," R ",'I',ingot,'R',rod);
    }

}
