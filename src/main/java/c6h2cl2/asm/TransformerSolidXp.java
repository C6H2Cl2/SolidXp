package c6h2cl2.asm;

import net.minecraft.launchwrapper.IClassTransformer;

/**
 * Created by C6H2Cl2 on 2016/09/06.
 */
public class TransformerSolidXp implements IClassTransformer {
    //改変対象の完全修飾名
    private static final String TARGET_CLASSES_NAMES = "net.minecraft.entity.player.EntityPlayer";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(!transformedName.equals(TARGET_CLASSES_NAMES)){
            return basicClass;
        }
        return basicClass;
    }
}
