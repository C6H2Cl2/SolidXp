package c6h2cl2.mod.SolidXp;

import c6h2cl2.mod.SolidXp.Block.XpHopper;
import c6h2cl2.mod.SolidXp.Enchantment.XpBoost;
import c6h2cl2.mod.SolidXp.Event.XpDropBoostEventHandler;
import c6h2cl2.mod.SolidXp.Item.*;
import c6h2cl2.mod.SolidXp.Item.Tool.*;
import c6h2cl2.mod.SolidXp.Util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jetbrains.annotations.NotNull;

/**
 * Created by ma87k on 2016/07/10.
 */
public class SolidXpRegistry {

    public static final Enchantment XpBoostWeapon = new XpBoost(EnumEnchantmentType.WEAPON);
    public static final Enchantment XpBoostTool = new XpBoost(EnumEnchantmentType.DIGGER);
    //Item・Block→ToolMaterial→Toolの順番で
    public static final ExtendedItem solidXp = new ItemSolidXp();
    public static final Block xpHopper = new XpHopper();
    //メタ付きItemのArray
    public static final ItemStack[] solidXps = {new ItemStack(solidXp,1,0),new ItemStack(solidXp,1,1),new ItemStack(solidXp,1,2),new ItemStack(solidXp,1,3),new ItemStack(solidXp,1,4),new ItemStack(solidXp,1,5),new ItemStack(solidXp,1,6),new ItemStack(solidXp,1,7),new ItemStack(solidXp,1,8),new ItemStack(solidXp,1,9),new ItemStack(solidXp,1,10),new ItemStack(solidXp,1,11),new ItemStack(solidXp,1,12),new ItemStack(solidXp,1,13),new ItemStack(solidXp,1,14),new ItemStack(solidXp,1,15)};
    public static final ExtendedItem xpExtractor = new ItemXpExtractor();
    public static final ExtendedItem xpIron = new ItemExperienceIron();
    public static final ExtendedItem xpDiamond = new ItemExperienceDiamond();
    //ToolMaterial
    public static final Item.ToolMaterial materialXpIron = EnumHelper.addToolMaterial("XpIron", 2, Math.round(Item.ToolMaterial.IRON.getMaxUses() * 1.2F), 7.0F, 2.5F, 16).setRepairItem(new ItemStack(xpIron));
    //ArmorMaterial
    public static final ItemArmor.ArmorMaterial armorMaterialXpIron = EnumHelper.addArmorMaterial("XpIron","xpIronArmor",300,new int[]{2,5,6,2},11, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0.0f);
    //Tool
    public static final XpIronSword xpIronSword = new XpIronSword();
    public static final XpIronAxe xpIronAxe = new XpIronAxe();
    public static final XpIronPickaxe xpIronPickaxe = new XpIronPickaxe();
    public static final XpIronShovel xpIronShovel = new XpIronShovel();
    public static final XpIronHoe xpIronHoe = new XpIronHoe();

    //汎用ModelResourceLocation取得関数
    @NotNull
    public static ModelResourceLocation getModelResourceLocation(Item item){
        ResourceLocation name = item instanceof ItemBlock ? ((ItemBlock)item).getBlock().getRegistryName() : item.getRegistryName();
        return new ModelResourceLocation(name,"inventory");
    }

    @NotNull
    public static ModelResourceLocation getModelResourceLocation(ResourceLocation location){
        return new ModelResourceLocation(location,"inventory");
    }

    private static void setCustomModelResourceLocation(Item item){
        ModelLoader.setCustomModelResourceLocation(item,0,getModelResourceLocation(item));
    }

    //preInit用の登録処理
    public static void registerPreInit(FMLPreInitializationEvent event){
        //アイテムの登録
        GameRegistry.register(solidXp);
        GameRegistry.register(xpExtractor);
        GameRegistry.register(xpIron);
        GameRegistry.register(xpDiamond);
        GameRegistry.register(xpIronSword);
        GameRegistry.register(xpIronAxe);
        GameRegistry.register(xpIronPickaxe);
        GameRegistry.register(xpIronShovel);
        GameRegistry.register(xpIronHoe);

        //Blockの登録
        GameRegistry.register(xpHopper);
        //Modelの登録
        if(event.getSide().isClient()){
            for (int i=0;i<16;++i){
                ModelLoader.setCustomModelResourceLocation(solidXp,i,new ModelResourceLocation("SolidXp:SolidXp_"+i,"inventory"));
            }
            for(int i=0;i<16;++i){
                ModelLoader.setCustomModelResourceLocation(xpExtractor,i, xpExtractor.getModelResourceLocation());
            }
            setCustomModelResourceLocation(xpIron);
            setCustomModelResourceLocation(xpDiamond);
            setCustomModelResourceLocation(xpIronSword);
            setCustomModelResourceLocation(xpIronPickaxe);
            setCustomModelResourceLocation(xpIronAxe);
            setCustomModelResourceLocation(xpIronShovel);
            setCustomModelResourceLocation(xpIronHoe);
            setCustomModelResourceLocation(new ItemBlock(xpHopper));
        }
    }

    //Init用の登録処理
    public static void registerInit(FMLInitializationEvent event){
        ForgeRegistries.ENCHANTMENTS.register(XpBoostWeapon);
        ForgeRegistries.ENCHANTMENTS.register(XpBoostTool);
        MinecraftForge.EVENT_BUS.register(new XpDropBoostEventHandler());
        //レシピ登録
        //XpExtractorのレシピ
        GameRegistry.addRecipe(new ItemStack(SolidXpRegistry.xpExtractor),"III","GRG","D D",'I', Items.IRON_INGOT,'G',Items.GOLD_INGOT,'R', Blocks.REDSTONE_BLOCK,'D',Items.DIAMOND);
        //素材系のレシピ
        GameRegistry.addRecipe(new ItemStack(SolidXpRegistry.xpIron)," S ","SIS"," S ",'I',Items.IRON_INGOT,'S', SolidXpRegistry.solidXps[2]);
        GameRegistry.addRecipe(new ItemStack(SolidXpRegistry.xpDiamond)," S ","SDS"," S ",'D',Items.DIAMOND,'S', SolidXpRegistry.solidXps[4]);
        //Solid Xpの変換レシピ
        for(int i=0;i<16;++i){
            if(i != 0){
                GameRegistry.addShapelessRecipe(SolidXpRegistry.solidXps[i], SolidXpRegistry.solidXps[i-1], SolidXpRegistry.solidXps[i-1], SolidXpRegistry.solidXps[i-1], SolidXpRegistry.solidXps[i-1]);
            }
            if(i != 15){
                ItemStack itemStack = ItemStack.copyItemStack(SolidXpRegistry.solidXps[i]);
                itemStack.stackSize = 4;
                GameRegistry.addShapelessRecipe(itemStack, SolidXpRegistry.solidXps[i+1]);
            }
        }
        //Toolのレシピ
        RecipeUtil.registerPickaxeRecipe(xpIronPickaxe,xpIron,Items.STICK);
        RecipeUtil.registerAxeRecipe(xpIronAxe,xpIron, Items.STICK);
        RecipeUtil.registerShovelRecipe(xpIronShovel,xpIron,Items.STICK);
        RecipeUtil.registerSwordRecipe(xpIronSword,xpIron,Items.STICK);
        RecipeUtil.registerHoeRecipe(xpIronHoe,xpIron,Items.STICK);
    }
}
