package c6h2cl2.mod.SolidXp.Item;

import c6h2cl2.mod.SolidXp.SolidXpCore;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by C6H2Cl2 on 2016/05/19.
 */
public abstract class ExtendedItem extends Item {

    private final ModelResourceLocation modelResourceLocation;

    protected ExtendedItem(String name){
        this(name,name);
    }

    protected ExtendedItem(String name,String resourceName){
        setCreativeTab(SolidXpCore.tabSolidXp);
        setRegistryName(new ResourceLocation("SolidXp:"+resourceName));
        setUnlocalizedName(name);
        modelResourceLocation = new ModelResourceLocation(getRegistryName(),"inventory");
    }

    public ModelResourceLocation getModelResourceLocation() {
        return modelResourceLocation;
    }
}
