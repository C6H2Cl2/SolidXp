package c6h2cl2.mod.SolidXp

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.jetbrains.annotations.Contract

@Mod(modid = SolidXpCore.DOMAIN, version = SolidXpCore.Version, name = SolidXpCore.MOD_ID,useMetadata = true, updateJSON = "https://dl.dropboxusercontent.com/s/8dwyop1506kxpgu/solidXp.json", dependencies = "required-after:yukarilib")
class SolidXpCore {

    companion object {
        const val DOMAIN = "solidxp"
        const val Version = "2.0.0"
        const val MOD_ID = "SolidXp"
        const val GuiIdXpExtractor = 0

        var tabSolidXp: CreativeTabs = object : CreativeTabs("tabSolidXp") {
            @Contract(pure = true)
            override fun getTabIconItem(): ItemStack {
                return ItemStack(SolidXpRegistry.solidXp, 1, 14)
            }
        }

        @Mod.Instance
        var INSTANCE: SolidXpCore? = null

        @Mod.Metadata("SolidXp")
        var metaData: ModMetadata? = null
    }

    //以下初期化処理
    @Mod.EventHandler
    fun preinit(event: FMLPreInitializationEvent) {
        loadModMetadata(metaData)
        SolidXpRegistry.registerPreInit(event)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        SolidXpRegistry.registerInit(event)
    }

    @Mod.EventHandler
    fun postinit(event: FMLPostInitializationEvent) {

    }

    //ModMetadataに情報を格納する関数
    private fun loadModMetadata(modMetadata: ModMetadata?) {
        if (modMetadata !is ModMetadata) {
            return
        }
        modMetadata.modId = SolidXpCore.MOD_ID
        modMetadata.name = "SolidXp"
        modMetadata.version = SolidXpCore.Version
        modMetadata.authorList.add("C6H2Cl2")
        modMetadata.url = "http://minecraft.curseforge.com/projects/solid-xp"
        //modMetadata.updateJSON = "https://dl.dropboxusercontent.com/s/8dwyop1506kxpgu/solidXp.json"
        modMetadata.autogenerated = false
    }
}