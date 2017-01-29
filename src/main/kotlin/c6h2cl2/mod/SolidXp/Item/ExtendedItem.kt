package c6h2cl2.mod.SolidXp.Item

import c6h2cl2.mod.SolidXp.SolidXpCore
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

abstract class ExtendedItem @JvmOverloads protected constructor(name: String, resourceName: String = name) : Item() {

    val modelResourceLocation: ModelResourceLocation

    init {
        creativeTab = SolidXpCore.tabSolidXp
        registryName = ResourceLocation("SolidXp:" + resourceName)
        unlocalizedName = name
        modelResourceLocation = ModelResourceLocation(registryName, "inventory")
    }
}
