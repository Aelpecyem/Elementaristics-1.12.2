package de.aelpecyem.elementaristics.misc.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AscensionTrigger implements ICriterionTrigger<AscensionTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Elementaristics.MODID, "ascend");
    private final Map<PlayerAdvancements, Listeners> listeners = Maps.<PlayerAdvancements, AscensionTrigger.Listeners>newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<AscensionTrigger.Instance> listener) {
        AscensionTrigger.Listeners trigger = this.listeners.get(playerAdvancementsIn);

        if (trigger == null) {
            trigger = new AscensionTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, trigger);
        }

        trigger.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<AscensionTrigger.Instance> listener) {
        AscensionTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners != null) {
            consumeitemtrigger$listeners.remove(listener);

            if (consumeitemtrigger$listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public AscensionTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        int stage = -1;
        boolean know = false;
        if (json.has("stage")) {
            stage = JsonUtils.getInt(json, "stage");
        }
        if (json.has("know")) {
            know = JsonUtils.getBoolean(json, "know");
        }
        return new AscensionTrigger.Instance(stage, know);
    }

    public static class Instance extends AbstractCriterionInstance {
        private final int stage;
        private final boolean know;

        public Instance(int stage, boolean know) {
            super(AscensionTrigger.ID);
            this.stage = stage;
            this.know = know;

        }

        public boolean test(EntityPlayerMP player) {
            if (stage >= 0 && player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                return player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getPlayerAscensionStage() >= this.stage;
            }
            if (know && player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                return player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).knowsSoul();
            }
            return false;
        }
    }

    public void trigger(EntityPlayerMP player) {
        AscensionTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (enterblocktrigger$listeners != null) {
            enterblocktrigger$listeners.trigger(player);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<AscensionTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<AscensionTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<AscensionTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<AscensionTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player) {
            List<ICriterionTrigger.Listener<AscensionTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<AscensionTrigger.Instance> listener : this.listeners) {
                if (((AscensionTrigger.Instance) listener.getCriterionInstance()).test(player)) {
                    if (list == null) {
                        list = Lists.<ICriterionTrigger.Listener<AscensionTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<AscensionTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
