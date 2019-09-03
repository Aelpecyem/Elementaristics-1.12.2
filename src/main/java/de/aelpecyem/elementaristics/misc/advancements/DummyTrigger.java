package de.aelpecyem.elementaristics.misc.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DummyTrigger implements ICriterionTrigger<DummyTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Elementaristics.MODID, "dummy");
    private final Map<PlayerAdvancements, Listeners> listeners = Maps.<PlayerAdvancements, DummyTrigger.Listeners>newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<DummyTrigger.Instance> listener) {
        DummyTrigger.Listeners trigger = this.listeners.get(playerAdvancementsIn);

        if (trigger == null) {
            trigger = new DummyTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, trigger);
        }

        trigger.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<DummyTrigger.Instance> listener) {
        DummyTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

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
    public DummyTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        String name = "";
        if (json.has("name")) {
            name = JsonUtils.getString(json, "name");
        }

        return new DummyTrigger.Instance(name);
    }

    public static class Instance extends AbstractCriterionInstance {
        private final String name;

        public Instance(String name) {
            super(DummyTrigger.ID);
            this.name = name;
        }

        public boolean test(String name) {
            if (this.name != null && this.name.equalsIgnoreCase(name)) {
                return true;
            }
            return false;
        }
    }

    public void trigger(EntityPlayerMP player, String name) {
        DummyTrigger.Listeners trigger = this.listeners.get(player.getAdvancements());

        if (trigger != null) {
            trigger.trigger(name);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<DummyTrigger.Instance>> listeners = Sets.<Listener<DummyTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<DummyTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<DummyTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(String name) {
            List<Listener<DummyTrigger.Instance>> list = null;

            for (Listener<DummyTrigger.Instance> listener : this.listeners) {
                if (((DummyTrigger.Instance) listener.getCriterionInstance()).test(name)) {
                    if (list == null) {
                        list = Lists.<Listener<DummyTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (Listener<DummyTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
