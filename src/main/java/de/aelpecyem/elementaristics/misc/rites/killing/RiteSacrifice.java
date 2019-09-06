package de.aelpecyem.elementaristics.misc.rites.killing;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RiteSacrifice extends RiteBase{//Soul's Conflagration
    Aspect aspect;
    Soul soul;
    DamageSource dmgSource;
    public RiteSacrifice(String name, Aspect aspectInvoked, Soul soulToAscend, DamageSource damageSource) {
        super(new ResourceLocation(Elementaristics.MODID, name), 100,0.89F, 8, soulToAscend, aspectInvoked); //set itempower higher, once the artifacts exist (also set maganPerTick higher)
        aspect = aspectInvoked;
        soul = soulToAscend;
        dmgSource = damageSource;
    }

    public RiteSacrifice(String name, Aspect aspectInvoked, DamageSource damageSource) {
        super(new ResourceLocation(Elementaristics.MODID, name), 100,0.89F, 8, aspectInvoked); //set itempower higher, once the artifacts exist (also set maganPerTick higher)
        aspect = aspectInvoked;
        dmgSource = damageSource;
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        if (!nexus.world.isRemote) {
            int killCount = 0;
            EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
            if (player != null && isSoulSpecific()) {
                if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                    IPlayerCapabilities caps = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                    if (caps.getPlayerAscensionStage() > 6) {
                        if (caps.getSoulId() == soul.getId()) {
                            //do final ascension
                            return;
                        }
                    }
                }
            }
            ItemStack stack = new ItemStack(ModItems.essence, 1, aspect.getId());
            EntityPlayer sacrifice = nexus.world.getClosestPlayerToEntity(nexus, 2);
            if (sacrifice != null){
                Elementaristics.proxy.generateGenericParticles(sacrifice, aspect.getColor(), 4, 100, 0, false, true);

                sacrifice.attackEntityFrom(dmgSource, 100);
                killCount++;
            }
            List<EntityLivingBase> entities = nexus.world.getEntitiesWithinAABB(EntityLivingBase.class, nexus.getEntityBoundingBox().grow(2), b -> b.isNonBoss() && !(b instanceof EntityPlayer) && !(b instanceof EntityCultist));
            for (int i = 0; i < 5; i++){
                if (i >= entities.size()){
                    break;
                }
                EntityLivingBase entity = entities.get(i);
                entity.attackEntityFrom(dmgSource, 50);
                if (nexus.world.rand.nextBoolean()) {
                    nexus.world.spawnEntity(new EntityItem(nexus.world, nexus.posX, nexus.posY, nexus.posZ, stack));
                }
            }
            nexus.world.spawnEntity(new EntityItem(nexus.world, nexus.posX, nexus.posY, nexus.posZ, new ItemStack(stack.getItem(), killCount, aspect.getId())));
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        List<Entity> entities = nexus.world.getEntitiesWithinAABB(EntityLivingBase.class, nexus.getEntityBoundingBox().grow(4));
        for (Entity entity : entities) {
            if (entity instanceof EntityLivingBase && entity.isNonBoss() & !(entity instanceof EntityPlayer) & !(entity instanceof EntityCultist)) {
                Elementaristics.proxy.generateGenericParticles(entity, aspect.getColor(), 2, 100, 0, false, true);
            }
        }
        Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, aspect.getColor(), 3, 60, 0, false, false);
    }
}
