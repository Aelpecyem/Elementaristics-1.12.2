package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.List;

public class DeityMother extends DeitySupplyEffectBase{
    public DeityMother() {
        super(TimeUtil.getTickTimeStartForHour(21), new ResourceLocation(Elementaristics.MODID, "deity_mother"), Aspects.life, 15481145);

    }
    public void addEffect(EntityAnimal target, TileEntityDeityShrine te) {
        if (target.getRNG().nextFloat() < 0.1 && te.storage.extractIfPossible(20)) {
            target.setInLove(null);
        }
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(5) && te.getWorld().rand.nextFloat() < 0.1) {
            List<EntityAnimal> targets = te.getWorld().getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(te.getPos().getX() - 12, te.getPos().getY() - 6, te.getPos().getZ() - 12, te.getPos().getX() + 12, te.getPos().getY() + 6, te.getPos().getZ() + 12), new Predicate<EntityAnimal>() {
                public boolean apply(@Nullable EntityAnimal input) {
                    return !input.isChild() && !input.isInLove();
                }
            });

            if (!targets.isEmpty() && targets.size() < 12) {
                for (EntityAnimal target : targets) {
                    addEffect(target, te);
                }
            }
            super.symbolEffect(te);
        }
    }


}
