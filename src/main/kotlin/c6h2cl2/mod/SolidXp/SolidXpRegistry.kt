package c6h2cl2.mod.SolidXp

import c6h2cl2.mod.SolidXp.Enchantment.XpBoost
import c6h2cl2.mod.SolidXp.Event.XpDropBoostEventHandler
import c6h2cl2.mod.SolidXp.Item.*
import c6h2cl2.mod.SolidXp.Item.Tool.*
import c6h2cl2.mod.SolidXp.Util.RecipeUtil
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.common.registry.GameRegistry

object SolidXpRegistry {

    val XpBoostWeapon: Enchantment = XpBoost(EnumEnchantmentType.WEAPON)
    val XpBoostTool: Enchantment = XpBoost(EnumEnchantmentType.DIGGER)
    //Item・Block→ToolMaterial→Toolの順番で
    val solidXp: ExtendedItem = ItemSolidXp()
    //メタ付きItemのArray
    val solidXps = arrayOf(ItemStack(solidXp, 1, 0), ItemStack(solidXp, 1, 1), ItemStack(solidXp, 1, 2), ItemStack(solidXp, 1, 3), ItemStack(solidXp, 1, 4), ItemStack(solidXp, 1, 5), ItemStack(solidXp, 1, 6), ItemStack(solidXp, 1, 7), ItemStack(solidXp, 1, 8), ItemStack(solidXp, 1, 9), ItemStack(solidXp, 1, 10), ItemStack(solidXp, 1, 11), ItemStack(solidXp, 1, 12), ItemStack(solidXp, 1, 13), ItemStack(solidXp, 1, 14), ItemStack(solidXp, 1, 15))
    val xpExtractor: ExtendedItem = ItemXpExtractor()
    val xpIron: ExtendedItem = ItemExperienceIron()
    val xpDiamond: ExtendedItem = ItemExperienceDiamond()
    //ToolMaterial
    val materialXpIron: Item.ToolMaterial = EnumHelper.addToolMaterial("XpIron", 2, Math.round(Item.ToolMaterial.IRON.maxUses * 1.2f), 7.0f, 2.5f, 16).setRepairItem(ItemStack(xpIron))
    //ArmorMaterial
    val armorMaterialXpIron = EnumHelper.addArmorMaterial("XpIron", "xpIronArmor", 300, intArrayOf(2, 5, 6, 2), 11, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f)
    //Tool
    val xpIronSword = XpIronSword()
    val xpIronAxe = XpIronAxe()
    val xpIronPickaxe = XpIronPickaxe()
    val xpIronShovel = XpIronShovel()
    val xpIronHoe = XpIronHoe()

    //汎用ModelResourceLocation取得関数
    fun getModelResourceLocation(item: Item): ModelResourceLocation {
        val name = if (item is ItemBlock) item.getBlock().registryName else item.registryName
        return ModelResourceLocation(name, "inventory")
    }

    fun getModelResourceLocation(location: ResourceLocation): ModelResourceLocation {
        return ModelResourceLocation(location, "inventory")
    }

    private fun setCustomModelResourceLocation(item: Item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, getModelResourceLocation(item))
    }

    //preInit用の登録処理
    fun registerPreInit(event: FMLPreInitializationEvent) {
        //アイテムの登録
        GameRegistry.register(solidXp)
        GameRegistry.register(xpExtractor)
        GameRegistry.register(xpIron)
        GameRegistry.register(xpDiamond)
        GameRegistry.register(xpIronSword)
        GameRegistry.register(xpIronAxe)
        GameRegistry.register(xpIronPickaxe)
        GameRegistry.register(xpIronShovel)
        GameRegistry.register(xpIronHoe)

        //Blockの登録

        //Modelの登録
        if (event.side.isClient) {
            for (i in 0..15) {
                ModelLoader.setCustomModelResourceLocation(solidXp, i, ModelResourceLocation("SolidXp:SolidXp_" + i, "inventory"))
            }
            for (i in 0..15) {
                ModelLoader.setCustomModelResourceLocation(xpExtractor, i, xpExtractor.modelResourceLocation)
            }
            setCustomModelResourceLocation(xpIron)
            setCustomModelResourceLocation(xpDiamond)
            setCustomModelResourceLocation(xpIronSword)
            setCustomModelResourceLocation(xpIronPickaxe)
            setCustomModelResourceLocation(xpIronAxe)
            setCustomModelResourceLocation(xpIronShovel)
            setCustomModelResourceLocation(xpIronHoe)
        }
    }

    //Init用の登録処理
    fun registerInit(event: FMLInitializationEvent) {
        ForgeRegistries.ENCHANTMENTS.register(XpBoostWeapon)
        ForgeRegistries.ENCHANTMENTS.register(XpBoostTool)
        MinecraftForge.EVENT_BUS.register(XpDropBoostEventHandler())
        //レシピ登録
        //XpExtractorのレシピ
        GameRegistry.addRecipe(ItemStack(SolidXpRegistry.xpExtractor), "III", "GRG", "D D", 'I', Items.IRON_INGOT, 'G', Items.GOLD_INGOT, 'R', Blocks.REDSTONE_BLOCK, 'D', Items.DIAMOND)
        //素材系のレシピ
        GameRegistry.addRecipe(ItemStack(SolidXpRegistry.xpIron), " S ", "SIS", " S ", 'I', Items.IRON_INGOT, 'S', SolidXpRegistry.solidXps[2])
        GameRegistry.addRecipe(ItemStack(SolidXpRegistry.xpDiamond), " S ", "SDS", " S ", 'D', Items.DIAMOND, 'S', SolidXpRegistry.solidXps[4])
        //Solid Xpの変換レシピ
        for (i in 0..15) {
            if (i != 0) {
                GameRegistry.addShapelessRecipe(SolidXpRegistry.solidXps[i], SolidXpRegistry.solidXps[i - 1], SolidXpRegistry.solidXps[i - 1], SolidXpRegistry.solidXps[i - 1], SolidXpRegistry.solidXps[i - 1])
            }
            if (i != 15) {
                val itemStack = SolidXpRegistry.solidXps[i].copy()
                itemStack.count = 4
                GameRegistry.addShapelessRecipe(itemStack, SolidXpRegistry.solidXps[i + 1])
            }
        }
        //Toolのレシピ
        RecipeUtil.registerPickaxeRecipe(xpIronPickaxe, xpIron, Items.STICK)
        RecipeUtil.registerAxeRecipe(xpIronAxe, xpIron, Items.STICK)
        RecipeUtil.registerShovelRecipe(xpIronShovel, xpIron, Items.STICK)
        RecipeUtil.registerSwordRecipe(xpIronSword, xpIron, Items.STICK)
        RecipeUtil.registerHoeRecipe(xpIronHoe, xpIron, Items.STICK)
    }
}
