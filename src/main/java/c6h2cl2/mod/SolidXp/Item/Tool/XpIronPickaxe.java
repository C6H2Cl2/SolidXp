package c6h2cl2.mod.SolidXp.Item.Tool;

import c6h2cl2.mod.SolidXp.SolidXpCore;
import c6h2cl2.mod.SolidXp.SolidXpRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by ma87k on 2016/07/08.
 */
public class XpIronPickaxe extends ItemPickaxe {

    public XpIronPickaxe() {
        super(SolidXpRegistry.materialXpIron);
        setCreativeTab(SolidXpCore.tabSolidXp);
        setUnlocalizedName("XpIronPickaxe");
        setRegistryName(SolidXpCore.DOMAIN,"XpIronPickaxe");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list){
        ItemStack itemStack = new ItemStack(this);
        itemStack.addEnchantment(Enchantments.MENDING,1);
        itemStack.addEnchantment(SolidXpRegistry.XpBoostTool,2);
        list.add(itemStack);
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.addEnchantment(Enchantments.MENDING,1);
        itemStack.addEnchantment(SolidXpRegistry.XpBoostTool,2);
    }
}
