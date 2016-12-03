package c6h2cl2.mod.SolidXp.Block;

import c6h2cl2.mod.SolidXp.SolidXpCore;
import net.minecraft.block.BlockHopper;

/**
 * @author C6H2Cl2
 */
public class XpHopper extends BlockHopper {
    public XpHopper(){
        super();
        setCreativeTab(SolidXpCore.tabSolidXp);
        setRegistryName(SolidXpCore.DOMAIN,"xp_hopper");
        setUnlocalizedName("xpHopper");
    }
}
