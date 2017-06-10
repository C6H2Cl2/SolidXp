package c6h2cl2.solidxp.tileentity

/**
 * @author C6H2Cl2
 */

const val INVENTORY = "tile.inventory"
const val PROGRESS = "tile.progress"
const val XP_STORAGE = "tile.xp_storage"
const val RECIPE_CACHE_ID = "tile.recipe_cache.id"

fun getXpValue(tier: Int): Long {
    return Math.pow(4.0, tier.toDouble()).toLong() * 10L
}