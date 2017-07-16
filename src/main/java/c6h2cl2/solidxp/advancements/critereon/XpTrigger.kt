package c6h2cl2.solidxp.advancements.critereon

import com.google.common.collect.Maps
import com.google.common.collect.Sets
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import net.minecraft.advancements.ICriterionTrigger
import net.minecraft.advancements.ICriterionTrigger.Listener
import net.minecraft.advancements.PlayerAdvancements
import net.minecraft.advancements.critereon.AbstractCriterionInstance
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class XpTrigger : ICriterionTrigger<XpTrigger.Instance> {
    companion object {
        val ID = ResourceLocation("solidxp", "")
    }

    val listeners = Maps.newHashMap<PlayerAdvancements, Listeners>()!!

    override fun getId(): ResourceLocation = ID

    override fun addListener(playerAdvancements: PlayerAdvancements, listener: Listener<Instance>) {
        val xpTriggerListeners = listeners[playerAdvancements]
                ?: kotlin.run {
            val _listener = Listeners(playerAdvancements)
            listeners.put(playerAdvancements, _listener)
            return@run _listener
        }
        xpTriggerListeners.add(listener)
    }

    override fun removeListener(playerAdvancements: PlayerAdvancements, listener: Listener<Instance>) {
        val xpTriggerListeners = listeners[playerAdvancements] ?: return
        xpTriggerListeners.remove(listener)
        if (xpTriggerListeners.isEmpty()) {
            listeners.remove(playerAdvancements)
        }
    }

    override fun removeAllListeners(playerAdvancements: PlayerAdvancements) {
        listeners.remove(playerAdvancements)
    }

    override fun deserializeInstance(json: JsonObject, context: JsonDeserializationContext): Instance {
        return Instance(json["xp"].asInt)
    }

    fun trigger(player: EntityPlayerMP) {
        listeners[player.advancements]?.trigger(player)
    }

    //Internal Classes

    class Instance(val requiredXp: Int) : AbstractCriterionInstance(ID)

    class Listeners(private val playerAdvancements: PlayerAdvancements) {
        private val listeners = Sets.newHashSet<Listener<XpTrigger.Instance>>()

        fun isEmpty(): Boolean = listeners.isEmpty()

        fun add(listener: Listener<XpTrigger.Instance>) {
            listeners.add(listener)
        }

        fun remove(listener: Listener<Instance>) {
            listeners.remove(listener)
        }

        fun trigger(player: EntityPlayerMP) {
            listeners.filter {
                player.experienceTotal >= it.criterionInstance.requiredXp
            }.forEach {
                it.grantCriterion(playerAdvancements)
            }
        }
    }
}