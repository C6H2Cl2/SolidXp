package c6h2cl2.mod.SolidXp.Event;

import c6h2cl2.mod.SolidXp.Item.Armor.IXpArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;

/**
 * Created by C6H2Cl2 on 2016/09/10.
 */
public class OnPlayerAttackedEventHandler {

    @SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event){
        if(!(event.getEntityLiving() instanceof EntityPlayer)){
            return;
        }
        EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        LinkedList<ItemStack> armors = new LinkedList<>();
        int shieldAmount = 0;
        int shieldLimitTotal = 0;
        String className = null;
        boolean flag = true;
        for (ItemStack armor : player.inventory.armorInventory) {
            if(armor != null && armor.getItem() instanceof IXpArmor){
                armors.add(armor);
                IXpArmor xpArmor = (IXpArmor)armor.getItem();
                shieldAmount += xpArmor.getArmorShield();
                shieldLimitTotal += xpArmor.getShieldLimit();
                if(className == null){
                    className = xpArmor.getClass().getName();
                }else if (flag){
                    flag = className.equals(xpArmor.getClass().getName());
                }
            }
        }
        int shieldLimit = shieldLimitTotal/armors.size();
        int shield = player.experienceLevel * shieldAmount / 100;
        if (shield > shieldLimit){
            shield = shieldLimit;
        }

    }
}
