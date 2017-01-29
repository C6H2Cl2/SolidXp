package c6h2cl2.asm

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin

@IFMLLoadingPlugin.TransformerExclusions("net.minecraftforge", "java", "org")
class LoadingPluginSolidXp : IFMLLoadingPlugin {
    override fun getASMTransformerClass(): Array<String> {
        return arrayOf(ASM_PACKAGE + ".TransformerSolidXp")
    }

    override fun getModContainerClass(): String {
        return "c6h2cl2.mod.SolidXp.SolidXpCore"
    }

    override fun getSetupClass(): String? {
        return null
    }

    override fun injectData(data: Map<String, Any>) {

    }

    override fun getAccessTransformerClass(): String {
        return ASM_PACKAGE + ".TransformerSolidXp"
    }

    companion object {
        val ASM_PACKAGE = "c6h2cl2.asm"
    }
}
