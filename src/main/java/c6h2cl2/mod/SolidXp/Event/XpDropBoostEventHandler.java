package c6h2cl2.mod.SolidXp.Event;

import c6h2cl2.mod.SolidXp.SolidXpRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * Created by C6H2Cl2 on 2016/06/26.
 */
public class XpDropBoostEventHandler {

    @SubscribeEvent
    public void onLivingKilledByXpBoostedWeapon(LivingExperienceDropEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer || event.getAttackingPlayer() == null){
            return;
        }
        ItemStack itemStack = event.getAttackingPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if(itemStack == null || itemStack.getEnchantmentTagList() == null){
            return;
        }
        int xpBoostLevel = EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.XpBoostWeapon,itemStack);
        if(xpBoostLevel == 0){
            return;
        }
        Random random = event.getAttackingPlayer().worldObj.rand;
        float boost = 1 + random.nextFloat()*xpBoostLevel;
        event.setDroppedExperience((Math.round(event.getOriginalExperience() * boost)));
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event){
        event.setResult(Event.Result.DEFAULT);
        event.setCanceled(false);
        int xp = event.getExpToDrop();
        xp = increaseXpByBreaking(event,xp);
        xp = increaseXpForCrops(event,xp);
        event.setExpToDrop(xp);
    }

    private int increaseXpByBreaking(BlockEvent.BreakEvent event,int xp){
        if(event.getPlayer().getHeldItemMainhand() != null && xp != 0){
            ItemStack itemStack = event.getPlayer().getHeldItemMainhand();
            int level = EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.XpBoostTool,itemStack);
            Random random = event.getWorld().rand;
            float xpBoost = random.nextFloat() * level;
            float boost = (xpBoost) <= 1 ? xpBoost + 1 : xpBoost;
            xp = Math.round(xp * boost);
        }
        return xp;
    }

    private int increaseXpForCrops(BlockEvent.BreakEvent event,int xp){
        if(event.getPlayer().getHeldItemMainhand() != null && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemHoe){
            IBlockState state = event.getWorld().getBlockState(event.getPos());
            Block block = state.getBlock();
            if(block instanceof BlockCrops){
                BlockCrops crops = (BlockCrops) block;
                if(crops.isMaxAge(state)){
                    xp += EnchantmentHelper.getEnchantmentLevel(SolidXpRegistry.XpBoostTool,event.getPlayer().getHeldItemMainhand());
                }
            }
        }
        return xp;
    }
}
