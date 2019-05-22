package de.aelpecyem.elementaristics.misc.rites.misc;

import akka.util.Reflect;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.command.CommandToggleDownfall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemNameTag;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

public class RiteWeather extends RiteBase {

    public RiteWeather() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_weather"), 220, 0.8F, 6, Aspects.water, Aspects.air);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        List<EntityItem> targets = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 2F, pos.getY() - 4F, pos.getZ() - 2F),
                new BlockPos(pos.getX() + 2F, pos.getY() + 4F, pos.getZ() + 2F)));

        for (EntityItem item : targets) {
            if (item.getItem().getItem() == ModItems.lightning_tangible) {
                item.getItem().shrink(1);
                for (int i = 0; i < 10; i++)
                    world.setLightFor(EnumSkyBlock.BLOCK, pos.add(i, 0, 0), 18);
                world.getWorldInfo().setThundering(true);
                world.getWorldInfo().setRaining(true);
                break;
            }
            if (item.getItem().getItem() == ModItems.sparks_brightest) {
                item.getItem().shrink(1);
                world.getWorldInfo().setRaining(false);
                break;
            }
            if (item.getItem().getItem() == ModItems.flesh_lamb) {
                item.getItem().shrink(1);
                world.getWorldInfo().setRaining(true);
                break;
            }
        }

    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
        List<Entity> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(altarPos.getX() - 4, altarPos.getY() - 2, altarPos.getZ() - 4, altarPos.getX() + 4, altarPos.getY() + 3, altarPos.getZ() + 4), null);
        for (Entity entity : entities) {
            entity.motionX = (altarPos.getX() + 0.5 - entity.posX) / 20;
            entity.motionY = (altarPos.getY() + 1.5 - entity.posY) / 20;
            entity.motionZ = (altarPos.getZ() + 0.5 - entity.posZ) / 20;
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.order.getColor(), 1, 10, 0, false, false);

        }
    }
}
