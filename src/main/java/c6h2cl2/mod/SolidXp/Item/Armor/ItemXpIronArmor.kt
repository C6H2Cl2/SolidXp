package c6h2cl2.mod.SolidXp.Item.Armor

import c6h2cl2.mod.SolidXp.SolidXpRegistry
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor

class ItemXpIronArmor(equipmentSlotIn: EntityEquipmentSlot) : ItemArmor(SolidXpRegistry.armorMaterialXpIron, 0, equipmentSlotIn), IXpArmor {

    override fun getArmorShield(): Float {
        return 10f
    }

    override fun getShieldBonus(): Float {
        return 10f
    }

    override fun getShieldLimit(): Float {
        return 20f
    }
}
