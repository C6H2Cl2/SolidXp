package c6h2cl2.solidxp

import c6h2cl2.YukariLib.Util.ItemUtil
import c6h2cl2.solidxp.advancements.critereon.XpTrigger
import c6h2cl2.solidxp.block.BlockXpChest
import c6h2cl2.solidxp.block.BlockXpCollector
import c6h2cl2.solidxp.block.BlockXpGlass
import c6h2cl2.solidxp.block.BlockXpGlassPane
import c6h2cl2.solidxp.block.BlockXpInfuser
import c6h2cl2.solidxp.block.SimpleBlock
import c6h2cl2.solidxp.capability.IXpStorage
import c6h2cl2.solidxp.capability.XpStorage
import c6h2cl2.solidxp.enchant.XpBoost
import c6h2cl2.solidxp.event.XpBoostEventHandler
import c6h2cl2.solidxp.item.ItemSolidXp
import c6h2cl2.solidxp.item.ItemXpExtractor
import c6h2cl2.solidxp.item.Tools.ItemXpDiamondAxe
import c6h2cl2.solidxp.item.Tools.ItemXpDiamondHoe
import c6h2cl2.solidxp.item.Tools.ItemXpDiamondPickaxe
import c6h2cl2.solidxp.item.Tools.ItemXpDiamondShovel
import c6h2cl2.solidxp.item.Tools.ItemXpDiamondSword
import c6h2cl2.solidxp.item.Tools.ItemXpIronAxe
import c6h2cl2.solidxp.item.Tools.ItemXpIronHoe
import c6h2cl2.solidxp.item.Tools.ItemXpIronPickaxe
import c6h2cl2.solidxp.item.Tools.ItemXpIronShovel
import c6h2cl2.solidxp.item.Tools.ItemXpIronSword
import c6h2cl2.solidxp.item.ItemXpChecker
import c6h2cl2.solidxp.render.RenderXpChest
import c6h2cl2.solidxp.tileentity.TileXpChest
import c6h2cl2.solidxp.tileentity.TileXpCollector
import c6h2cl2.solidxp.tileentity.TileXpInfuser
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.DisplayInfo
import net.minecraft.advancements.FrameType.*
import net.minecraft.advancements.ICriterionTrigger
import net.minecraft.advancements.critereon.ImpossibleTrigger
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
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.client.Minecraft
import net.minecraft.command.FunctionObject
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.text.TextComponentTranslation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.Capability.IStorage
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager.INSTANCE
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.IFuelHandler
import net.minecraftforge.fml.common.asm.transformers.DeobfuscationTransformer
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.ReflectionHelper
import net.minecraftforge.fml.relauncher.Side.CLIENT
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.ShapedOreRecipe
import net.minecraftforge.oredict.ShapelessOreRecipe


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

    //Criteria
    //@JvmStatic
    val GET_XP = XpTrigger()

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
    val xpChecker = ItemXpChecker()
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
    val xpCollector = BlockXpCollector()
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
    /*val achievementExtractXp = Achievement("$MOD_ID.extractXp", "$MOD_ID.extractXp", 0, 0, ItemStack(solidXp, 1, 1), null).registerStat()!!
    val achievementCraftXpIronPickaxe = Achievement("$MOD_ID.craftXpIronPickaxe", "$MOD_ID.craftXpIronPickaxe", 0, 2, xpIronPickaxe, achievementExtractXp).registerStat()!!
    val achievementCraftXpIronSword = Achievement("$MOD_ID.craftXpIronSword", "$MOD_ID.craftXpIronSword", 1, 2, xpIronSword, achievementExtractXp).registerStat()!!
    val achievementCraftXpIronHoe = Achievement("$MOD_ID.craftXpIronHoe", "$MOD_ID.craftXpIronHoe", -1, 2, xpIronHoe, achievementExtractXp).registerStat()!!
    val achievementPutXpInfuser = Achievement("$MOD_ID.putXpInfuser", "$MOD_ID.putXpInfuser", 2, 0, xpInfuserIB, achievementExtractXp).registerStat()!!*/

    //Advancements
    val adcanvementExtractXp = Advancement(
            //AdvancementのID
            ResourceLocation(MOD_ID, "extractXp"),
            //親Advancement
            null,
            //ディスプレイへの表示の情報
            DisplayInfo(
                    //表示するItemStack
                    ItemStack(solidXp, 1, 1),
                    //ToolTipに表示するAdvancementsの名前
                    TextComponentTranslation("$ADVANCEMENT.extractXp"),
                    //ToolTipに表示するAdvancementsの説明
                    TextComponentTranslation("$ADVANCEMENT.extractXp.desc"),
                    //root.json用:ページ背景のテクスチャ
                    ResourceLocation(MOD_ID, "xp_machine_base"),
                    //Frameの形
                    TASK,
                    //トースト表示の可否
                    true,
                    //チャットに表示するか否か
                    true,
                    //解除するまで隠されているか
                    false
            ),
            //報酬
            AdvancementRewards(
                    //経験値
                    10,
                    //報酬のアイテムのLootTableのリソロケ
                    emptyArray(),
                    //報酬のレシピのリソロケ
                    emptyArray(),
                    //達成時に実行されるFunctionObject
                    FunctionObject.CacheableFunction(null as ResourceLocation?)
            ),
            //達成基準(Criterion)と名前(String)のMap<String, Criterion>
            mapOf(Pair("xp", Criterion(ImpossibleTrigger.Instance()))),
            //全達成基準名の列挙
            emptyArray()
    )

    //AchievementPage
    /*
    val achievementPageSolidXp = AchievementPage("SolidXp", achievementExtractXp,
            achievementCraftXpIronSword, achievementCraftXpIronPickaxe, achievementCraftXpIronHoe,
            achievementPutXpInfuser)*/

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

    //Capability
    @JvmStatic
    @CapabilityInject(IXpStorage::class)
    lateinit var XP_STORAGE_CAPABILITY: Capability<IXpStorage>

    init {
        xpBoost.set(WEAPON, XpBoost(WEAPON))
        xpBoost.set(DIGGER, XpBoost(DIGGER))
        xpCobbleStone.setHarvestLevel("pickaxe", 0)
    }

    @SubscribeEvent
    @Suppress("UNUSED")
    fun registerItems(event: RegistryEvent.Register<Item>) {
        val registry = event.registry
        //register items
        registry.registerAll(
                solidXp,
                xpIron, xpDiamond, xpCoal, xpFuel, xpFuelAdv,
                xpExtractor, xpChecker,
                xpIronShovel, xpIronPickaxe, xpIronAxe, xpIronHoe, xpIronSword,
                xpDiamondShovel, xpDiamondPickaxe, xpDiamondAxe, xpDiamondHoe, xpDiamondSword)
        //register item blocks
        registry.registerAll(
                xpInfuserIB,
                xpCollector.initItemBlock(), xpChest.initItemBlock(), xpCobbleStone.initItemBlock(), xpStone.initItemBlock(),
                xpMachineBasic.initItemBlock(), xpGlass.initItemBlock(), xpGlassPane.initItemBlock(), xpWoodPlank.initItemBlock(),
                xpFuelBlock.initItemBlock())
    }

    @SubscribeEvent
    @Suppress("UNUSED")
    fun registerBlocks(event: Register<Block>) {
        event.registry.registerAll(
                xpInfuser, xpCollector, xpChest,
                xpCobbleStone, xpStone, xpMachineBasic, xpGlass, xpGlassPane, xpWoodPlank, xpFuelBlock
        )
    }

    @SideOnly(CLIENT)
    @SubscribeEvent
    @Suppress("UNUSED")
    fun registerModels(event: ModelRegistryEvent) {
        //ItemのRender登録
        ModelLoader.setCustomModelResourceLocation(xpIron, 0, getModelResourceLocation(xpIron))
        ModelLoader.setCustomModelResourceLocation(xpDiamond, 0, getModelResourceLocation(xpDiamond))
        (0..15).forEach {
            ModelLoader.setCustomModelResourceLocation(solidXp, it, ModelResourceLocation(ResourceLocation(MOD_ID, "solidxp_$it"), "inventory"))
        }
        (0..15).forEach {
            ModelLoader.setCustomModelResourceLocation(xpExtractor, it, getModelResourceLocation(xpExtractor))
        }
        ModelLoader.setCustomModelResourceLocation(xpChecker, 0, getModelResourceLocation(xpChecker))
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
        ModelLoader.setCustomModelResourceLocation(xpCollector.getItemBlock(), 0, getModelResourceLocation(xpCollector))
    }

    @SubscribeEvent
    @Suppress("UNUSED")
    fun registerRecipes(event: Register<IRecipe>): Unit {
        val registry = event.registry
        (0 until 15).forEach {
            val itemStack = ItemStack(solidXp, 1, it)
            registry.register(ShapelessOreRecipe(solidXp.registryName, ItemStack(solidXp, 1, it + 1), itemStack.copy(), itemStack.copy(), itemStack.copy(), itemStack.copy()).setRegistryName("${solidXp.registryName.toString()}_$it"))
        }
        (1..15).forEach {
            val out = ItemStack(solidXp, 4, it - 1)
            val id = ResourceLocation("${out.item.registryName.toString()}_down_$it")
            registry.register(ShapelessOreRecipe(id, out, ItemStack(solidXp, 1, it)).setRegistryName(id))
        }
        registry.registerAll(
                getRecipe(ItemStack(xpIron), " S ", "SIS", " S ", 'S', ItemStack(solidXp, 1, 2), 'I', Items.IRON_INGOT),
                getRecipe(xpIronShovel.getEnchanted(), " I ", " S ", " S ", 'I', xpIron, 'S', Items.STICK),
                getRecipe(xpIronPickaxe.getEnchanted(), "III", " S ", " S ", 'I', xpIron, 'S', Items.STICK),
                getRecipe(xpIronAxe.getEnchanted(), "II ", "IS ", " S ", 'I', xpIron, 'S', Items.STICK),
                getRecipe(xpIronHoe.getEnchanted(), "II ", " S ", " S ", 'I', xpIron, 'S', Items.STICK),
                getRecipe(xpIronSword.getEnchanted(), " I ", " I ", " S ", 'I', xpIron, 'S', Items.STICK),
                getRecipe(xpDiamondShovel.getEnchanted(), " I ", " S ", " S ", 'I', xpDiamond, 'S', Items.STICK),
                getRecipe(xpDiamondPickaxe.getEnchanted(), "III", " S ", " S ", 'I', xpDiamond, 'S', Items.STICK),
                getRecipe(xpDiamondAxe.getEnchanted(), "II ", "IS ", " S ", 'I', xpDiamond, 'S', Items.STICK),
                getRecipe(xpDiamondHoe.getEnchanted(), "II ", " S ", " S ", 'I', xpDiamond, 'S', Items.STICK),
                getRecipe(xpDiamondSword.getEnchanted(), " I ", " I ", " S ", 'I', xpDiamond, 'S', Items.STICK),
                getRecipe(ItemStack(xpMachineBasic), "III", "I I", "III", 'I', xpIron),
                getRecipe(ItemStack(xpExtractor), "III", "GRG", "D D", 'I', Items.IRON_INGOT, 'G', Items.GOLD_INGOT, 'R', Blocks.REDSTONE_BLOCK, 'D', Items.DIAMOND),
                getRecipe(ItemStack(xpInfuser), "III", "RMR", "EEE", 'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'M', xpMachineBasic, 'E', xpIron),
                getRecipe(ItemStack(xpChest), "WWW", "W W", "WWW", 'W', xpWoodPlank),
                getRecipe(ItemStack(xpGlassPane, 16), "GGG", "GGG", 'G', xpGlass),
                getShapelessRecipe(ItemStack(xpFuel), xpCoal, xpCoal, xpCoal, xpCoal),
                getShapelessRecipe(ItemStack(xpFuelAdv), xpFuel, xpFuel, xpFuel, xpFuel, xpFuel, xpFuel, xpFuel, xpFuel),
                getShapelessRecipe(ItemStack(xpFuelBlock), xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv, xpFuelAdv)
        )
    }

    @SubscribeEvent
    @Suppress("UNUSED")
    fun registerEnchants(event: Register<Enchantment>): Unit {
        event.registry.registerAll(*xpBoost.values.toTypedArray())
    }

    fun handlePreInit(event: FMLPreInitializationEvent) {
        //Capabilityの登録
        INSTANCE.register(IXpStorage::class.java,
                object : IStorage<IXpStorage> {
                    override fun readNBT(capability: Capability<IXpStorage>, instance: IXpStorage, side: EnumFacing?, nbt: NBTBase?) {
                        val tag = nbt as? NBTTagCompound ?: return
                        instance.deserializeNBT(tag)
                    }

                    override fun writeNBT(capability: Capability<IXpStorage>, instance: IXpStorage, side: EnumFacing?): NBTBase? {
                        return instance.serializeNBT()
                    }
                }
        ) { XpStorage() }

        //レシピの登録

    }

    fun handleInit(event: FMLInitializationEvent) {
        //製錬レシピの登録
        GameRegistry.addSmelting(xpCobbleStone, ItemStack(xpStone), 0f)
        //FuelHandlerの登録
        GameRegistry.registerFuelHandler(fuelHandler)
        //TileEntityの登録
        GameRegistry.registerTileEntity(TileXpInfuser::class.java, ResourceLocation(MOD_ID, "xpInfuser").toString())
        GameRegistry.registerTileEntity(TileXpChest::class.java, ResourceLocation(MOD_ID, "xpChest").toString())
        GameRegistry.registerTileEntity(TileXpCollector::class.java, ResourceLocation(MOD_ID, "xpCollector").toString())
        //Eventの登録
        MinecraftForge.EVENT_BUS.register(XpBoostEventHandler())
        //実績の登録
        //AchievementPage.registerAchievementPage(achievementPageSolidXp)
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

    private fun getRecipe(output: ItemStack, vararg recipeComponents: Any): IRecipe {
        val recipe = ShapedOreRecipe(output.item.registryName, output, *recipeComponents).setRegistryName(output.item.registryName)
        return recipe
    }

    private fun getShapelessRecipe(output: ItemStack, vararg recipeComponents: Any): IRecipe {
        val recipe = ShapelessOreRecipe(output.item.registryName, output, *recipeComponents).setRegistryName(output.item.registryName)
        return recipe
    }
}