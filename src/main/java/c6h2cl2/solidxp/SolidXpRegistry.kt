package c6h2cl2.solidxp

import c6h2cl2.solidxp.Block.BlockXpChest
import c6h2cl2.solidxp.Block.BlockXpGlass
import c6h2cl2.solidxp.Block.BlockXpGlassPane
import c6h2cl2.solidxp.Block.BlockXpInfuser
import c6h2cl2.solidxp.Block.SimpleBlock
import c6h2cl2.solidxp.Enchant.XpBoost
import c6h2cl2.solidxp.Event.XpBoostEventHandler
import c6h2cl2.solidxp.Item.ItemSolidXp
import c6h2cl2.solidxp.Item.ItemXpExtractor
import c6h2cl2.solidxp.Item.ItemXpIronAxe
import c6h2cl2.solidxp.Item.ItemXpIronHoe
import c6h2cl2.solidxp.Item.ItemXpIronPickaxe
import c6h2cl2.solidxp.Item.ItemXpIronShovel
import c6h2cl2.solidxp.Item.ItemXpIronSword
import c6h2cl2.solidxp.Render.RenderXpChest
import c6h2cl2.solidxp.TileEntity.TileXpChest
import c6h2cl2.solidxp.TileEntity.TileXpInfuser
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.enchantment.EnumEnchantmentType.*
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.stats.Achievement
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.AchievementPage
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.client.registry.ClientRegistry


/**
 * @author C6H2Cl2
 */

object SolidXpRegistry {
    //クリエタブ
    val tabSolidXp = object : CreativeTabs("SolidXp") {
        override fun getTabIconItem(): ItemStack {
            return ItemStack(solidXp, 1, 14)
        }
    }

    //ToolMaterial
    val materialXpIron = EnumHelper.addToolMaterial("xpIron", 2, 400, 7.2f, 2.5f, 16)

    //Items
    //中間素材
    val xpIron = Item()
            .setUnlocalizedName("xpIron")
            .setRegistryName(ResourceLocation(MOD_ID, "xpIron"))
            .setCreativeTab(tabSolidXp)!!
    val xpDiamond = Item()
            .setUnlocalizedName("xpDiamond")
            .setRegistryName(ResourceLocation(MOD_ID, "xpDiamond"))
            .setCreativeTab(tabSolidXp)!!
    //Main
    val solidXp = ItemSolidXp()
    val xpExtractor = ItemXpExtractor()
    val xpIronShovel = ItemXpIronShovel()
    val xpIronPickaxe = ItemXpIronPickaxe()
    val xpIronAxe = ItemXpIronAxe()
    val xpIronHoe = ItemXpIronHoe()
    val xpIronSword = ItemXpIronSword()

    //Blocks
    //Machines
    val xpInfuser = BlockXpInfuser()
    //Decorations
    val xpCobbleStone = SimpleBlock(Material.ROCK, "xpCobbleStone", "xp_cobblestone", 1.5f, 15f, 0.55f, "pickaxe", 0, SoundType.STONE)
    val xpStone = SimpleBlock(Material.ROCK, "xpStone", "xp_stone", 1.5f, 15f, 0.6f, "pickaxe", 0, SoundType.STONE)
    val xpGlass = BlockXpGlass()
    val xpGlassPane = BlockXpGlassPane()
    val xpWoodPlank = SimpleBlock(Material.WOOD, "xpWoodPlank", "xp_wood_plank", 2.0f, 5.0f, 0.6f, soundType = SoundType.WOOD)
    //val xpStoneBrick = BlockXpStoneBrick()
    //Misc
    val xpChest = BlockXpChest()
    //中間素材
    val xpMachineBasic = SimpleBlock(Material.ROCK, "xpMachineBase", "xp_machine_base", 1f, 10f, 0.4f, "pickaxe", 0, SoundType.METAL)

    //ItemBlocks
    val xpInfuserIB = xpInfuser.initItemBlock()

    //Enchantments
    val xpBoost = HashMap<EnumEnchantmentType, XpBoost>()

    //Achievements
    val achievementExtractXp = Achievement("$MOD_ID.extractXp", "$MOD_ID.extractXp", 0, 0, ItemStack(solidXp, 1, 1), null).registerStat()!!
    val achievementCraftXpIronPickaxe = Achievement("$MOD_ID.craftXpIronPickaxe", "$MOD_ID.craftXpIronPickaxe", 0, 2, xpIronPickaxe, achievementExtractXp).registerStat()!!
    val achievementCraftXpIronSword = Achievement("$MOD_ID.craftXpIronSword", "$MOD_ID.craftXpIronSword", 1, 2, xpIronSword, achievementExtractXp).registerStat()!!
    val achievementCraftXpIronHoe = Achievement("$MOD_ID.craftXpIronHoe", "$MOD_ID.craftXpIronHoe", -1, 2, xpIronHoe, achievementExtractXp).registerStat()!!
    val achievementPutXpInfuser = Achievement("$MOD_ID.putXpInfuser", "$MOD_ID.putXpInfuser", 2, 0, xpInfuserIB, achievementExtractXp).registerStat()!!

    //AchievementPage
    val achievementPageSolidXp = AchievementPage("SolidXp", achievementExtractXp,
            achievementCraftXpIronSword, achievementCraftXpIronPickaxe, achievementCraftXpIronHoe,
            achievementPutXpInfuser)

    init {
        xpBoost.set(WEAPON, XpBoost(WEAPON))
        xpBoost.set(DIGGER, XpBoost(DIGGER))
        xpCobbleStone.setHarvestLevel("pickaxe", 0)
    }

    fun handlePreInit(event: FMLPreInitializationEvent) {
        //Itemの登録
        GameRegistry.register(xpIron)
        GameRegistry.register(xpDiamond)
        GameRegistry.register(solidXp)
        GameRegistry.register(xpExtractor)
        GameRegistry.register(xpIronShovel)
        GameRegistry.register(xpIronPickaxe)
        GameRegistry.register(xpIronAxe)
        GameRegistry.register(xpIronHoe)
        GameRegistry.register(xpIronSword)
        //Blockの登録
        GameRegistry.register(xpInfuser)
        GameRegistry.register(xpCobbleStone)
        GameRegistry.register(xpStone)
        GameRegistry.register(xpMachineBasic)
        GameRegistry.register(xpGlass)
        GameRegistry.register(xpGlassPane)
        GameRegistry.register(xpWoodPlank)
        GameRegistry.register(xpChest)
        //GameRegistry.register(xpStoneBrick)
        //ItemBlockの登録
        GameRegistry.register(xpInfuserIB)
        GameRegistry.register(xpCobbleStone.initItemBlock())
        GameRegistry.register(xpStone.initItemBlock())
        GameRegistry.register(xpMachineBasic.initItemBlock())
        GameRegistry.register(xpGlass.initItemBlock())
        GameRegistry.register(xpGlassPane.initItemBlock())
        GameRegistry.register(xpWoodPlank.initItemBlock())
        GameRegistry.register(xpChest.initItemBlock())
        //GameRegistry.register(xpStoneBrick.initItemBlock())
        if (event.side.isClient) {
            //ItemのRender登録
            ModelLoader.setCustomModelResourceLocation(xpIron, 0, getModelResourceLocation(xpIron))
            ModelLoader.setCustomModelResourceLocation(xpDiamond, 0, getModelResourceLocation(xpDiamond))
            (0..15).forEach {
                ModelLoader.setCustomModelResourceLocation(solidXp, it, ModelResourceLocation(ResourceLocation(MOD_ID, "solidxp_$it"), "inventory"))
            }
            (0..15).forEach {
                ModelLoader.setCustomModelResourceLocation(xpExtractor, it, getModelResourceLocation(xpExtractor))
            }
            ModelLoader.setCustomModelResourceLocation(xpIronAxe, 0, getModelResourceLocation(xpIronAxe))
            ModelLoader.setCustomModelResourceLocation(xpIronPickaxe, 0, getModelResourceLocation(xpIronPickaxe))
            ModelLoader.setCustomModelResourceLocation(xpIronShovel, 0, getModelResourceLocation(xpIronShovel))
            ModelLoader.setCustomModelResourceLocation(xpIronHoe, 0, getModelResourceLocation(xpIronHoe))
            ModelLoader.setCustomModelResourceLocation(xpIronSword, 0, getModelResourceLocation(xpIronSword))
            ModelLoader.setCustomModelResourceLocation(xpInfuserIB, 0, getModelResourceLocation(xpInfuserIB))
            ModelLoader.setCustomModelResourceLocation(xpCobbleStone.getItemBlock(), 0, getModelResourceLocation(xpCobbleStone))
            ModelLoader.setCustomModelResourceLocation(xpStone.getItemBlock(), 0, getModelResourceLocation(xpStone))
            ModelLoader.setCustomModelResourceLocation(xpMachineBasic.getItemBlock(), 0, getModelResourceLocation(xpMachineBasic))
            ModelLoader.setCustomModelResourceLocation(xpGlass.getItemBlock(), 0, getModelResourceLocation(xpGlass))
            ModelLoader.setCustomModelResourceLocation(xpGlassPane.getItemBlock(), 0, getModelResourceLocation(xpGlassPane))
            ModelLoader.setCustomModelResourceLocation(xpWoodPlank.getItemBlock(), 0, getModelResourceLocation(xpWoodPlank))
            ModelLoader.setCustomModelResourceLocation(xpChest.getItemBlock(), 0, getModelResourceLocation(xpChest))
            /*(0..3).forEach {
                ModelLoader.setCustomModelResourceLocation(xpStoneBrick.getItemBlock(), it, ModelResourceLocation(ResourceLocation(MOD_ID, "xp_stone_brick_${BlockStoneBrick.EnumType.byMetadata(it).unlocalizedName}"), "inventory"))
            }*/
        }
    }

    fun handleInit(event: FMLInitializationEvent) {
        //レシピの登録
        GameRegistry.addRecipe(ItemStack(xpIron), " S ", "SIS", " S ", 'S', ItemStack(solidXp, 1, 2), 'I', Items.IRON_INGOT)
        (0 until 15).forEach {
            val itemStack = ItemStack(solidXp, 1, it)
            GameRegistry.addShapelessRecipe(ItemStack(solidXp, 1, it + 1), itemStack.copy(), itemStack.copy(), itemStack.copy(), itemStack.copy())
        }
        (1..15).forEach {
            GameRegistry.addShapelessRecipe(ItemStack(solidXp, 4, it - 1), ItemStack(solidXp, 1, it))
        }
        GameRegistry.addRecipe(xpIronShovel.getEnchanted(), " I ", " S ", " S ", 'I', xpIron, 'S', Items.STICK)
        GameRegistry.addRecipe(xpIronPickaxe.getEnchanted(), "III", " S ", " S ", 'I', xpIron, 'S', Items.STICK)
        GameRegistry.addRecipe(xpIronAxe.getEnchanted(), "II ", "IS ", " S ", 'I', xpIron, 'S', Items.STICK)
        GameRegistry.addRecipe(xpIronHoe.getEnchanted(), "II ", " S ", " S ", 'I', xpIron, 'S', Items.STICK)
        GameRegistry.addRecipe(xpIronSword.getEnchanted(), " I ", " I ", " S ", 'I', xpIron, 'S', Items.STICK)
        GameRegistry.addRecipe(ItemStack(xpMachineBasic), "III", "I I", "III", 'I', xpIron)
        GameRegistry.addRecipe(ItemStack(xpExtractor),"III","GRG","D D",'I', Items.IRON_INGOT,'G',Items.GOLD_INGOT,'R', Blocks.REDSTONE_BLOCK,'D',Items.DIAMOND)
        GameRegistry.addRecipe(ItemStack(xpInfuser), "III", "RMR", "EEE", 'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'M', xpMachineBasic, 'E', xpIron)
        GameRegistry.addSmelting(xpCobbleStone, ItemStack(xpStone), 0f)
        //Enchantの登録
        xpBoost.forEach {
            GameRegistry.register(it.value)
        }
        //TileEntityの登録
        GameRegistry.registerTileEntity(TileXpInfuser::class.java, ResourceLocation(MOD_ID, "xpInfuser").toString())
        GameRegistry.registerTileEntity(TileXpChest::class.java, ResourceLocation(MOD_ID, "xpChest").toString())
        //Eventの登録
        MinecraftForge.EVENT_BUS.register(XpBoostEventHandler())
        //実績の登録
        AchievementPage.registerAchievementPage(achievementPageSolidXp)
        //GUI Handlerの登録
        NetworkRegistry.INSTANCE.registerGuiHandler(SolidXpCore.INSTANCE, SolidXpGuiHandler())

        if (event.side.isClient){
            Minecraft.getMinecraft().renderItem.itemModelMesher.register(xpChest.getItemBlock(), 0, getModelResourceLocation(xpChest))
            ClientRegistry.bindTileEntitySpecialRenderer(TileXpChest::class.java, RenderXpChest())
        }
    }

    private fun getModelResourceLocation(item: Item): ModelResourceLocation {
        return ModelResourceLocation(item.registryName!!, "inventory")
    }

    private fun getModelResourceLocation(block: Block): ModelResourceLocation{
        return getModelResourceLocation(block.getItemBlock())
    }
}