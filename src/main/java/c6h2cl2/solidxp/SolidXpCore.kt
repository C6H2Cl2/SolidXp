package c6h2cl2.solidxp

import c6h2cl2.solidxp.Proxy.CommonProxy
import mezz.jei.api.IJeiRuntime
import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.ISubtypeRegistry
import mezz.jei.api.ingredients.IModIngredientRegistration
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.Mod.Instance
import net.minecraftforge.fml.common.Mod.Metadata
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import java.io.File

/**
 * @author C6H2Cl2
 */
@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, useMetadata = true)
class SolidXpCore {

    companion object {
        @JvmStatic
        @SidedProxy(serverSide = "c6h2cl2.solidxp.Proxy.CommonProxy", clientSide = "c6h2cl2.solidxp.Proxy.ClientProxy")
        var proxy: CommonProxy? = null
        @JvmStatic
        @Metadata(value = MOD_ID)
        var meta: ModMetadata? = null
        var numXpOrb = 16
        @Instance
        var INSTANCE: SolidXpCore? = null
    }

    @EventHandler
    fun preinit(event: FMLPreInitializationEvent) {
        SolidXpRegistry.handlePreInit(event)
        loadMeta()
        getConfig()
    }

    @EventHandler
    fun init(event: FMLInitializationEvent) {
        SolidXpRegistry.handleInit(event)
    }

    private fun getConfig() {
        val proxy = proxy as CommonProxy
        val cfg = Configuration(File(proxy.getDir(), "config/SolidXp.cfg"))
        cfg.load()
        numXpOrb = cfg.getInt("XpOrbAmount_SolidXp", "General", 16, 1, 1024, "The number of XpOrb which spawn using item \"SolidXp\"")
        cfg.save()
    }

    private fun loadMeta() {
        val meta = meta as ModMetadata
        meta.modId = MOD_ID
        meta.name = MOD_NAME
        meta.version = VERSION
        meta.authorList.add("C6H2Cl2")
        meta.url = "http://minecraft.curseforge.com/projects/solid-xp"
        meta.autogenerated = false
    }
}