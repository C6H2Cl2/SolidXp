package c6h2cl2.mod.SolidXp;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.jetbrains.annotations.Contract;

@Mod(modid = SolidXpCore.MOD_ID,version = SolidXpCore.Version,useMetadata = true,updateJSON = "https://dl.dropboxusercontent.com/s/8dwyop1506kxpgu/solidXp.json")
public class SolidXpCore{
    public static final String DOMAIN = "SolidXp";
    public static final String Version = "1.1.2";
    public static final String MOD_ID = "solidxp";
    public static final int GuiIdXpExtractor = 0;

    public static CreativeTabs tabSolidXp = new CreativeTabs("tabSolidXp") {
        @Contract(pure = true)
        @Override
        public Item getTabIconItem() {
            return SolidXpRegistry.solidXp;
        }
    };

    @Mod.Instance
    public static SolidXpCore INSTANCE;

    @Mod.Metadata(value = DOMAIN)
    public static ModMetadata metaData;

    //インスタンスは最後尾に

    //以下初期化処理
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event){
        loadModMetadata(metaData);
        SolidXpRegistry.registerPreInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        SolidXpRegistry.registerInit(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event){

    }

    //ModMetadataに情報を格納する関数
    private void loadModMetadata(ModMetadata modMetadata){
        modMetadata.modId = SolidXpCore.MOD_ID;
        modMetadata.name = "SolidXp";
        modMetadata.version = SolidXpCore.Version;
        modMetadata.authorList.add("C6H2Cl2");
        modMetadata.url = "http://minecraft.curseforge.com/projects/solid-xp";
        modMetadata.updateJSON = "https://dl.dropboxusercontent.com/s/8dwyop1506kxpgu/solidXp.json";
        modMetadata.autogenerated = false;
    }
}
