package c6h2cl2.solidxp

import c6h2cl2.YukariLib.Util.ItemUtil
import c6h2cl2.solidxp.Block.BlockXpChest
import c6h2cl2.solidxp.Block.BlockXpGlass
import c6h2cl2.solidxp.Block.BlockXpGlassPane
import c6h2cl2.solidxp.Block.BlockXpInfuser
import c6h2cl2.solidxp.Block.SimpleBlock
import c6h2cl2.solidxp.Enchant.XpBoost
import c6h2cl2.solidxp.Event.XpBoostEventHandler
import c6h2cl2.solidxp.Item.ItemSolidXp
import c6h2cl2.solidxp.Item.ItemXpExtractor
import c6h2cl2.solidxp.Item.Tools.ItemXpDiamondAxe
import c6h2cl2.solidxp.Item.Tools.ItemXpDiamondHoe
import c6h2cl2.solidxp.Item.Tools.ItemXpDiamondPickaxe
import c6h2cl2.solidxp.Item.Tools.ItemXpDiamondShovel
import c6h2cl2.solidxp.Item.Tools.ItemXpDiamondSword
import c6h2cl2.solidxp.Item.Tools.ItemXpIronAxe
import c6h2cl2.solidxp.Item.Tools.ItemXpIronHoe
import c6h2cl2.solidxp.Item.Tools.ItemXpIronPickaxe
import c6h2cl2.solidxp.Item.Tools.ItemXpIronShovel
import c6h2cl2.solidxp.Item.Tools.ItemXpIronSword
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
import net.minecraftforge.fml.common.IFuelHandler


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
    val materialXpDiamond = EnumHelper.addToolMaterial("xpDiamond", 3, 2000, 10.4f, 4.0f, 12)

    //Items
    //中間素材
    val xpIron = ItemUtil.CreateItem("xpIron", modID = MOD_ID, creativeTabs = tabSolidXp)
    val xpDiamond = ItemUtil.CreateItem("xpDiamond", modID = MOD_ID, creativeTabs = tabSolidXp)
    val xpCoal = ItemUtil.CreateItem("xpCoal", "xp_coal", MOD_ID, tabSolidXp)
    val xpFuel = ItemUtil.CreateItem("xpFuel", "xp_fuel", MOD_ID, tabSolidXp)
    val xpFuelAdv = ItemUtil.CreateItem("xpFuelAdv", "xp_fuel_adv", MOD_ID, tabSolidXp)

    //Main
    val solidXp = ItemSolidXp()
    val xpExtractor = ItemXpExtractor()
    //Tools
    //XpIron
    val xpIronShovel = ItemXpIronShovel()
    val xpIronPickaxe = ItemXpIronPickaxe()
    val xpIronAxe = ItemXpIronAxe()
    val xpIronHoe = ItemXpIronHoe()
    val xpIronSword = ItemXpIronSword()
    //XpDiamond
    val xpDiamondShovel = ItemXpDiamondShovel()
    val xpDiamondPickaxe = ItemXpDiamondPickaxe()
    val xpDiamondAxe = ItemXpDiamondAxe()
    val xpDiamondHoe = ItemXpDiamondHoe()
    val xpDiamondSword = ItemXpDiamondSword()

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
    val xpFuelBlock = SimpleBlock(Material.ROCK, "xpFuelBlock", "xp_fuel_block", 1f, 10f, 0.4f, "pickaxe", 0)
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

    val fuelHandler = IFuelHandler { fuel ->
        if (fuel == null || fuel.isEmpty)
            return@IFuelHandler 0
        when (fuel.item) {
            xpCoal -> 3200
            xpFuel -> 12800
            xpFuelAdv -> 102400
            xpFuelBlock.getItemBlock() -> 921600
            else -> 0
        }
    }

    init {
        xpBoost.set(WEAPON, XpBoost(WEAPON))
        xpBoost.set(DIGGER, XpBoost(DIGGER))
        xpCobbleStone.setHarvestLevel("pickaxe", 0)
    }

    fun handlePreInit(event: FMLPreInitializationEvent) {
        //Itemの登録
        GameRegistry.register(solidXp)
        GameRegistry.register(xpIron)
        GameRegistry.register(xpDiamond)
        GameRegistry.register(xpCoal)
        GameRegistry.register(xpFuel)
        GameRegistry.register(xpFuelAdv)
        GameRegistry.register(xpExtractor)
        GameRegistry.register(xpIronShovel)
        GameRegistry.register(xpIronPickaxe)
        GameRegistry.register(xpIronAxe)
        GameRegistry.register(xpIronHoe)
        GameRegistry.register(xpIronSword)
        GameRegistry.register(xpDiamondShovel)
        GameRegistry.register(xpDiamondPickaxe)
        GameRegistry.register(xpDiamondAxe)
        GameRegistry.register(xpDiamondHoe)
        GameRegistry.register(xpDiamondSword)
        //Blockの登録
        GameRegistry.register(xpInfuser)
        GameRegistry.register(xpChest)
        GameRegistry.register(xpCobbleStone)
        GameRegistry.register(xpStone)
        GameRegistry.register(xpMachineBasic)
        GameRegistry.register(xpGlass)
        GameRegistry.register(xpGlassPane)
        GameRegistry.register(xpWoodPlank)
        GameRegistry.register(xpFuelBlock)
        //GameRegistry.register(xpStoneBrick)
        //ItemBlockの登録
        GameRegistry.register(xpInfuserIB)
        GameRegistry.register(xpChest.initItemBlock())
        GameRegistry.register(xpCobbleStone.initItemBlock())
        GameRegistry.register(xpStone.initItemBlock())
        GameRegistry.register(xpMachineBasic.initItemBlock())
        GameRegistry.register(xpGlass.initItemBlock())
        GameRegistry.register(xpGlassPane.initItemBlock())
        GameRegistry.register(xpWoodPlank.initItemBlock())
        GameRegistry.register(xpFuelBlock.initItemBlock())
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
            ModelLoader.setCustomModelResourceLocation(xpDiamondShovel, 0, getModelResourceLocation(xpDiamondShovel))
            ModelLoader.setCustomModelResourceLocation(xpDiamondPickaxe, 0, getModelResourceLocation(xpDiamondPickaxe))
            ModelLoader.setCustomModelResourceLocation(xpDiamondAxe, 0, getModelResourceLocation(xpDiamondAxe))
            ModelLoader.setCustomModelResourceLocation(xpDiamondHoe, 0, getModelResourceLocation(xpDiamondHoe))
            ModelLoader.setCustomModelResourceLocation(xpDiamondSword, 0, getModelResourceLocation(xpDiamondSword))
            ModelLoader.setCustomModelResourceLocation(xpInfuserIB, 0, getModelResourceLocation(xpInfuserIB))
            ModelLoader.setCustomModelResourceLocation(xpCoal, 0, getModelResourceLocation(xpCoal))
            ModelLoader.setCustomModelResourceLocation(xpFuel, 0, getModelResourceLocation(xpFuel))
            ModelLoader.setCustomModelResourceLocation(xpFuelAdv, 0, getModelResourceLocation(xpFuelAdv))
            //ItemBlockのRender登録
            ModelLoader.setCustomModelResourceLocation(xpCobbleStone.getItemBlock(), 0, getModelResourceLocation(xpCobbleStone))
            ModelLoader.setCustomModelResourceLocation(xpStone.getItemBlock(), 0, getModelResourceLocation(xpStone))
            ModelLoader.setCustomModelResourceLocation(xpMachineBasic.getItemBlock(), 0, getModelResourceLocation(xpMachineBasic))
            ModelLoader.setCustomModelResourceLocation(xpGlass.getItemBlock(), 0, getModelResourceLocation(xpGlass))
            ModelLoader.setCustomModelResourceLocation(xpGlassPane.getItemBlock(), 0, getModelResourceLocation(xpGlassPane))
            ModelLoader.setCustomModelResourceLocation(xpWoodPlank.getItemBlock(), 0, getModelResourceLocation(xpWoodPlank))
            ModelLoader.setCustomModelResourceLocation(xpChest.getItemBlock(), 0, getModelResourceLocation(xpChest))
            ModelLoader.setCustomModelResourceLocation(xpFuelBlock.getItemBlock(), 0, getModelResourceLocation(xpFuelBlock))
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
        GameRegistry.addRecipe(xpDiamondShovel.getEnchanted(), " I ", " S ", " S ", 'I', xpDiamond, 'S', Items.STICK)
        GameRegistry.addRecipe(xpDiamondPickaxe.getEnchanted(), "III", " S ", " S ", 'I', xpDiamond, 'S', Items.STICK)
        GameRegistry.addRecipe(xpDiamondAxe.getEnchanted(), "II ", "IS ", " S ", 'I', xpDiamond, 'S', Items.STICK)
        GameRegistry.addRecipe(xpDiamondHoe.getEnchanted(), "II ", " S ", " S ", 'I', xpDiamond, 'S', Items.STICK)
        GameRegistry.addRecipe(xpDiamondSword.getEnchanted(), " I ", " I ", " S ", 'I', xpDiamond, 'S', Items.STICK)
        GameRegistry.addRecipe(ItemStack(xpMachineBasic), "III", "I I", "III", 'I', xpIron)
        GameRegistry.addRecipe(ItemStack(xpExtractor), "III", "GRG", "D D", 'I', Items.IRON_INGOT, 'G', Items.GOLD_INGOT, 'R', Blocks.REDSTONE_BLOCK, 'D', Items.DIAMOND)
        GameRegistry.addRecipe(ItemStack(xpInfuser), "III", "RMR", "EEE", 'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'M', xpMachineBasic, 'E', xpIron)
        GameRegistry.addRecipe(ItemStack(xpChest), "WWW", "W W", "WWW", 'W', xpWoodPlank)
        GameRegistry.addRecipe(ItemStack(xpGlassPane, 16), "GGG", "GGG", 'G', xpGlass)
        GameRegistry.addShapelessRecipe(ItemStack(xpFuel), xpCoal, xpCoal, xpCoal, xpCoal)
        GameRegistry.addShapelessRecipe(ItemStack(xpFuelAdv), xpFuel, xpFuel, xpFuel, xpFuel, xpFuel, xpFuel, xpFuel, xpFuel)
        GameRegistry.addShapelessRecipe(ItemStack(xpFuelBlock), xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv)
        //製錬レシピの登録
        GameRegistry.addSmelting(xpCobbleStone, ItemStack(xpStone), 0f)
        //FuelHandlerの登録
        GameRegistry.registerFuelHandler(fuelHandler)
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

        if (event.side.isClient) {
            Minecraft.getMinecraft().renderItem.itemModelMesher.register(xpChest.getItemBlock(), 0, getModelResourceLocation(xpChest))
            ClientRegistry.bindTileEntitySpecialRenderer(TileXpChest::class.java, RenderXpChest())
        }
    }

    private fun getModelResourceLocation(item: Item): ModelResourceLocation {
        return ModelResourceLocation(item.registryName!!, "inventory")
    }

    private fun getModelResourceLocation(block: Block): ModelResourceLocation {
        return getModelResourceLocation(block.getItemBlock())
    }
}