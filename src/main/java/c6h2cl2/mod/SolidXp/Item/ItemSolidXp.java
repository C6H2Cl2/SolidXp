package c6h2cl2.mod.SolidXp.Item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemSolidXp extends ExtendedItem {

    public ItemSolidXp(){
        super("SolidXp");
        setHasSubtypes(true);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand enumHand){
        player.addExperience(getXpInteger(itemStack));
        --itemStack.stackSize;
        return new ActionResult<>(EnumActionResult.SUCCESS,itemStack);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list){
        for(int i=0;i<16;++i){
            list.add(new ItemStack(item,1,i));
        }
    }

    @NotNull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "_" + stack.getItemDamage();
    }

    public long getXp(ItemStack itemStack){
        int meta = itemStack.getMetadata();
        return ((long)Math.pow(4,meta) *10);
    }

    public int getXpInteger(ItemStack itemStack){
        long xp = getXp(itemStack);
        if(xp > Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else {
            return (int)xp;
        }
    }
}
