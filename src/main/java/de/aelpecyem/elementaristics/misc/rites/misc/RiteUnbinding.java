package de.aelpecyem.elementaristics.misc.rites.misc;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemHeartStone;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RiteUnbinding extends RiteBase {

    public RiteUnbinding() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_unbinding"), 100, 0.8F, 4, Aspects.earth);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        if (player != null) {
            List<EntityLivingBase> entities = world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
                @Override
                public boolean apply(@Nullable EntityLivingBase input) {
                    return input.getEntityData().hasUniqueId("sharing_uuid") && input.getEntityData().getUniqueId("sharing_uuid").equals(player.getUniqueID());
                }
            });
            for (EntityLivingBase living : entities) {
                living.getEntityData().setUniqueId("sharing_uuid", living.getUniqueID());
            }
        }

    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
        Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, Aspects.earth.getColor(), 3, 60, 0, false, false);
        EntityPlayer player = world.getClosestPlayer(altarPos.getX(), altarPos.getY(), altarPos.getZ(), 20, false);
        Elementaristics.proxy.generateGenericParticles(player, Aspects.earth.getColor(), 3, 100, 0, false, true);
    }

}
