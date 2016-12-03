package c6h2cl2.mod.SolidXp.Enchantment;

import c6h2cl2.mod.SolidXp.SolidXpCore;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Created by C6H2Cl2 on 2016/06/26.
 */
public class XpBoost extends Enchantment {

    public XpBoost(EnumEnchantmentType enchantmentType) {
        super(Rarity.RARE, enchantmentType,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        setName("xpBoost."+enchantmentType.name());
        setRegistryName(SolidXpCore.DOMAIN,"xpBoost."+enchantmentType.name());
    }

    @Override
    public int getMaxLevel(){
        return 5;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel){
        return 15 + enchantmentLevel * 8;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel){
        return getMinEnchantability(enchantmentLevel) + 50;
    }
}
