package c6h2cl2.mod.SolidXp.Item.Tool;

import c6h2cl2.mod.SolidXp.SolidXpCore;
import c6h2cl2.mod.SolidXp.SolidXpRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by C6H2Cl2 on 2016/06/02.
 */
public class XpIronSword extends ItemSword {
    public XpIronSword() {
        super(SolidXpRegistry.materialXpIron);
        setCreativeTab(SolidXpCore.tabSolidXp);
        setUnlocalizedName("XpIronSword");
        setRegistryName(SolidXpCore.DOMAIN,"XpIronSword");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list){
        ItemStack itemStack = new ItemStack(this);
        itemStack.addEnchantment(Enchantments.MENDING,1);
        itemStack.addEnchantment(SolidXpRegistry.XpBoostWeapon,2);
        list.add(itemStack);
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.addEnchantment(Enchantments.MENDING,1);
        itemStack.addEnchantment(SolidXpRegistry.XpBoostWeapon,2);
    }
}
