package de.aelpecyem.elementaristics.misc.potions.effects;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class PotionHealthShare extends PotionBase {
    public PotionHealthShare() {
        super("health_share", false, 15533410, 14);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public void hitEntity(LivingHealEvent event) {
        if (event.getEntityLiving().getActivePotionEffects().contains(event.getEntityLiving().getActivePotionEffect(this))) {
            if (event.getAmount() > 1) {
                System.out.println(event.getAmount());
                World world = event.getEntityLiving().world;
                EntityLivingBase attacked = event.getEntityLiving();
                List<EntityLivingBase> livingsShared = world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
                    @Override
                    public boolean apply(@Nullable EntityLivingBase input) {
                        return input.getEntityData().hasUniqueId("sharing_uuid") && input.getEntityData().getUniqueId("sharing_uuid").equals(attacked.getUniqueID());
                    }
                });
                if (!livingsShared.isEmpty()) {
                    livingsShared = livingsShared.subList(0, livingsShared.size() < 10 ? livingsShared.size() : 10);
                    float amountEach = event.getAmount() / (livingsShared.size() + 1);
                    System.out.println(livingsShared.size());
                    for (int i = 0; i < livingsShared.size(); i++) {
                        livingsShared.get(i).heal(amountEach);
                    }
                    event.setAmount(amountEach);
                }
            }
        }
    }

    @SubscribeEvent
    public void hitEntity(LivingHurtEvent event) {
        if (event.getSource() != Elementaristics.DAMAGE_PSYCHIC) {
            if (event.getEntityLiving().getActivePotionEffects().contains(event.getEntityLiving().getActivePotionEffect(this))) {
                if (event.getAmount() > 1) {
                    System.out.println(event.getAmount());
                    World world = event.getEntityLiving().world;
                    EntityLivingBase attacked = event.getEntityLiving();
                    List<EntityLivingBase> livingsShared = world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
                        @Override
                        public boolean apply(@Nullable EntityLivingBase input) {
                            return input.getEntityData().hasUniqueId("sharing_uuid") && input.getEntityData().getUniqueId("sharing_uuid").equals(attacked.getUniqueID());
                        }
                    });
                    if (!livingsShared.isEmpty()) {
                        livingsShared = livingsShared.subList(0, livingsShared.size() < 4 ? livingsShared.size() : 4);
                        float amountEach = event.getAmount() / (livingsShared.size() + 1);
                        System.out.println(livingsShared.size());
                        for (int i = 0; i < livingsShared.size(); i++) {
                            livingsShared.get(i).attackEntityFrom(event.getSource(), amountEach);
                        }
                        event.setAmount(amountEach);
                    }
                }
            }
        }
    }
}
