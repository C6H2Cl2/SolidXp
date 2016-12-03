package c6h2cl2.mod.SolidXp.Util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ma87k on 2016/07/10.
 */
public class XpUtil {

    public static int getXpForLevel(int target){
        int xp = 0;
        for(int i=0;i<target;++i){
            xp += getXpCapForLevel(i);
        }
        return xp;
    }

    public static int getXpForLevel(int base, int target){
        return getXpForLevel(target) - getXpForLevel(base);
    }

    public static int getXpCapForLevel(int level){
        return level >= 30 ? 112 + (level - 30) * 9 : (level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2);
    }

    public static int getLevelForXp(int xp){
        int level = 0;
        for(;xp>getXpCapForLevel(level);++level){
            xp -= getXpCapForLevel(level);
        }
        return level;
    }

    /**エンチャント取得関連*/
    public static Map<Short,Short> getEnchantmentsFromItemStack(ItemStack itemStack){
        NBTTagList tagList = itemStack.getEnchantmentTagList();
        if(tagList.hasNoTags()){
            return null;
        }
        HashMap<Short,Short> enchants = new HashMap<>();
        for(int i=0;i<tagList.tagCount();++i){
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            if(!tagCompound.hasKey("id")){
                return null;
            }
            enchants.put(tagCompound.getShort("id"),tagCompound.getShort("lvl"));
        }
        return enchants;
    }

    public static int getEnchantLevel(Map<Short,Short> enchants, Enchantment enchantment){
        short enchantLevel = 0;
        for(Map.Entry<Short,Short> entry:enchants.entrySet()){
            if(entry.getKey() == Enchantment.getEnchantmentID(enchantment)){
                enchantLevel = entry.getValue();
                break;
            }
        }
        return enchantLevel;
    }

    public static int getEnchantLevel(ItemStack itemStack, Enchantment enchantment){
        return getEnchantLevel(getEnchantmentsFromItemStack(itemStack),enchantment);
    }

    /**経験値関連*/
    public static void decreasePlayerXp(EntityPlayer player, int amount){
        int i = player.experienceTotal;
        if (amount > i)
        {
            amount = i;
        }
        player.experienceTotal -= amount;
        player.experienceLevel = getLevelForXp(player.experienceTotal);
        player.experience = ((float)player.experienceTotal-(float) getXpForLevel(player.experienceLevel)) / (float) player.xpBarCap();
    }

}
