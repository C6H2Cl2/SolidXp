package c6h2cl2.solidxp.tileentity

/**
 * @author C6H2Cl2
 */

const val INVENTORY = "tile.inventory"
const val PROGRESS = "tile.progress"
const val XP_STORAGE = "tile.xp_storage_value"
const val RECIPE_CACHE_ID = "tile.recipe_cache.id"
const val XP_TIER = "tile.xp_tier"

fun getXpValue(tier: Int): Long {
    return Math.pow(4.0, tier.toDouble()).toLong() * 10L
}