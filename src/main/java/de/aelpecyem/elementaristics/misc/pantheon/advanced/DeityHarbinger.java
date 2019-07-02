package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.events.EventHandler;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeityBase;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

import java.util.ArrayList;
import java.util.List;

public class DeityHarbinger extends DeityBase {

    public DeityHarbinger() {
        super(TimeUtil.getTickTimeStartForHour(13), new ResourceLocation(Elementaristics.MODID, "deity_harbinger"), 664599);
    }//todo stuff

    @Override
    public void setUpTile(TileEntityDeityShrine te) {
        te.storage.setMaxStorage(5000);
        te.storage.setMaxReceive(200);
        te.storage.setMaxExtract(200);
        super.setUpTile(te);
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        super.symbolEffect(te);
        if (te.unusedInt > 0){
            te.unusedInt -= 1;
        }
        if (te.getWorld().rand.nextInt(20) == 1 && te.unusedInt == 0){
            if (te.storage.extractIfPossible(4990) && te.getWorld().getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(te.getPos().getX() - 10, te.getPos().getY() - 10, te.getPos().getZ() - 10 ,te.getPos().getX() + 11, te.getPos().getY() + 11, te.getPos().getZ() + 11 )).size() < 5){
                summonMobs(te);
                Elementaristics.proxy.generateGenericParticles(te.getWorld(), te.getPos().getX() + 0.5, te.getPos().getY() + 0.5, te.getPos().getZ() + 0.5, getColor(), 3 + (float) te.getWorld().rand.nextGaussian(), 100, 0, false, false);
                te.unusedInt = 200;
            }
        }
    }

    public void summonMobs(TileEntityDeityShrine te){
        int mobAmount = te.getWorld().rand.nextInt(4) + 1;
        List<EntityLiving> mobs = new ArrayList<>();
        for (int i = 0; i < mobAmount; i++){
            int number = te.getWorld().rand.nextInt(70);
            if (number < 20){
                mobs.add(new EntityZombie(te.getWorld()));
            }else if (number < 40){
                mobs.add(new EntitySkeleton(te.getWorld()));
            }else if (number < 50){
                mobs.add(new EntitySpider(te.getWorld()));
            }else if (number < 55){
                mobs.add(new EntityBat(te.getWorld()));
            }else if (number < 60){
                mobs.add(new EntityWitch(te.getWorld()));
            }else if (number < 65){
                mobs.add(new EntityWitherSkeleton(te.getWorld()));
            }
        }

        for (EntityLiving entity : mobs){
            entity.setPosition(te.getPos().getX() + 0.5F - entity.width / 2, te.getPos().getY() + 0.5- entity.height / 2, te.getPos().getZ() + 0.5F- entity.width / 2);
            entity.motionX=  (te.getWorld().rand.nextBoolean() ? -1 : 1) * (0.2 + Math.random()/ 3);
            entity.motionY = Math.random() / 2;
            entity.motionZ = (te.getWorld().rand.nextBoolean() ? -1 : 1) * (0.2 + Math.random()/ 3);
            if (!te.getWorld().isRemote) {
                te.getWorld().spawnEntity(entity);
            }
        }
    }
}


