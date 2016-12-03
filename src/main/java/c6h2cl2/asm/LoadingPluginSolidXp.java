package c6h2cl2.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by C6H2Cl2 on 2016/09/06.
 */
@IFMLLoadingPlugin.TransformerExclusions({"net.minecraftforge","java","org"})
public class LoadingPluginSolidXp implements IFMLLoadingPlugin {
    public static final String ASM_PACKAGE = "c6h2cl2.asm";
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{ASM_PACKAGE + ".TransformerSolidXp"};
    }

    @Override
    public String getModContainerClass() {
        return "c6h2cl2.mod.SolidXp.SolidXpCore";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return ASM_PACKAGE + ".TransformerSolidXp";
    }
}
