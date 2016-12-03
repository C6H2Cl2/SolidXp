package c6h2cl2.mod.SolidXp.Item.Armor;

import c6h2cl2.mod.SolidXp.SolidXpRegistry;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

/**
 * Created by C6H2Cl2 on 2016/09/06.
 */
public class ItemXpIronArmor extends ItemArmor implements IXpArmor{
    public ItemXpIronArmor( EntityEquipmentSlot equipmentSlotIn) {
        super(SolidXpRegistry.armorMaterialXpIron,0, equipmentSlotIn);
    }

    @Override
    public float getArmorShield() {
        return 10;
    }

    @Override
    public float getShieldBonus() {
        return 10;
    }

    @Override
    public float getShieldLimit() {
        return 20;
    }
}
