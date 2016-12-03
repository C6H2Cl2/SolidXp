package c6h2cl2.mod.SolidXp.Item;

import c6h2cl2.mod.SolidXp.SolidXpRegistry;
import c6h2cl2.mod.SolidXp.Util.XpUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ItemXpExtractor extends ExtendedItem {
    public ItemXpExtractor(){
        super("xpExtractor");
        this.maxStackSize = 1;
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand enumHand){
        int meta = itemStack.getItemDamage();
        boolean isSucceed = true;
        if(player.isSneaking()){
            meta++;
            if(meta > 15){
                meta -= 15;
            }
            itemStack.setItemDamage(meta);
            if(!world.isRemote){
                player.addChatComponentMessage(new TextComponentString("Extract "+getXp(itemStack)+" Xps"));
            }
        }else{
            long extractXpSize = getXp(itemStack);
            isSucceed = player.experienceTotal >= extractXpSize;
            if(isSucceed){
                //player.experienceTotalはint型であるので、この場合extractXpSizeは必ずInteger.MAX_VALUE以下である。
                XpUtil.decreasePlayerXp(player,(int) extractXpSize);
                if(!world.isRemote){
                    ItemStack solidXp = new ItemStack(SolidXpRegistry.solidXp,1,meta);
                    if(!player.inventory.addItemStackToInventory(solidXp)){
                        EntityItem item = new EntityItem(world,player.posX,player.posY+1,player.posZ,new ItemStack(SolidXpRegistry.solidXp,1,meta));
                        item.setPickupDelay(0);
                        world.spawnEntityInWorld(item);
                    }
                }
            }else{
                if(!world.isRemote){
                    player.addChatComponentMessage(new TextComponentString("You don't have Enough Xps!!"));
                }
            }
        }
        if(isSucceed){
            return new ActionResult<>(EnumActionResult.SUCCESS,itemStack);
        }else{
            return new ActionResult<>(EnumActionResult.FAIL,itemStack);
        }
    }

    private long getXp(ItemStack itemStack){
        int meta = itemStack.getMetadata();
        return ((long)Math.pow(4,meta) *10);
    }
}
