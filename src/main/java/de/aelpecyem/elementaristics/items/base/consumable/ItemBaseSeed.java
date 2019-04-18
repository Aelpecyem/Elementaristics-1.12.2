package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class ItemBaseSeed extends ItemFoodBase {
    public ItemBaseSeed() {
        super("seed_dormant", 0, 0, false);
        setAlwaysEdible();
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (!worldIn.isRemote) {
            if (entityLiving.getActivePotionEffects().contains(entityLiving.getActivePotionEffect(PotionInit.ecstasy))) {
                if (entityLiving.getActivePotionEffect(PotionInit.ecstasy).getAmplifier() > 1) {
                    entityLiving.removePotionEffect(PotionInit.ecstasy);
                    worldIn.spawnEntity(new EntityItem(worldIn, entityLiving.posX + entityLiving.width / 2, entityLiving.posY + entityLiving.height / 2, entityLiving.posZ + entityLiving.width / 2,
                            new ItemStack(ModBlocks.flower_ecstasy)));
                }
            } else if (entityLiving.getActivePotionEffects().contains(entityLiving.getActivePotionEffect(PotionInit.contentment))) {
                if (!worldIn.isRaining()) {
                    entityLiving.removePotionEffect(PotionInit.contentment);
                    worldIn.spawnEntity(new EntityItem(worldIn, entityLiving.posX + entityLiving.width / 2, entityLiving.posY + entityLiving.height / 2, entityLiving.posZ + entityLiving.width / 2,
                            new ItemStack(ModBlocks.flower_contentment)));
                }
            } else if (entityLiving.getActivePotionEffects().contains(entityLiving.getActivePotionEffect(PotionInit.dread))) {
                if (!entityLiving.world.isDaytime() && entityLiving.dimension == DimensionType.OVERWORLD.getId() && worldIn.isRaining()) {
                    entityLiving.removePotionEffect(PotionInit.dread);
                    worldIn.spawnEntity(new EntityItem(worldIn, entityLiving.posX + entityLiving.width / 2, entityLiving.posY + entityLiving.height / 2, entityLiving.posZ + entityLiving.width / 2,
                            new ItemStack(ModBlocks.flower_dread)));
                }
            } else if (entityLiving.getActivePotionEffects().contains(entityLiving.getActivePotionEffect(PotionInit.fear))) {
                if (entityLiving.posY < 20 && entityLiving.dimension == DimensionType.NETHER.getId()) {
                    if (worldIn.getEntitiesWithinAABBExcludingEntity(entityLiving, new AxisAlignedBB(entityLiving.posX - 10, entityLiving.posY - 3, entityLiving.posZ - 10, entityLiving.posX + 10, entityLiving.posY + 10, entityLiving.posZ + 10)).size() > 1) {
                        entityLiving.removePotionEffect(PotionInit.fear);
                        worldIn.spawnEntity(new EntityItem(worldIn, entityLiving.posX + entityLiving.width / 2, entityLiving.posY + entityLiving.height / 2, entityLiving.posZ + entityLiving.width / 2,
                                new ItemStack(ModBlocks.flower_fear)));
                    }
                }
            } else if (entityLiving.getActivePotionEffects().contains(entityLiving.getActivePotionEffect(PotionInit.laughter))) {
                if (entityLiving.dimension == 0) {
                    entityLiving.removePotionEffect(PotionInit.laughter);
                    worldIn.spawnEntity(new EntityItem(worldIn, entityLiving.posX + entityLiving.width / 2, entityLiving.posY + entityLiving.height / 2, entityLiving.posZ + entityLiving.width / 2,
                            new ItemStack(ModBlocks.flower_laughter)));
                }
            } else if (entityLiving.getActivePotionEffects().contains(entityLiving.getActivePotionEffect(PotionInit.silence))) {
                if (entityLiving.posY > 120 && entityLiving.dimension == DimensionType.OVERWORLD.getId() && !worldIn.isDaytime()) {
                    if (worldIn.getEntitiesWithinAABBExcludingEntity(entityLiving, new AxisAlignedBB(entityLiving.posX - 10, entityLiving.posY - 3, entityLiving.posZ - 10, entityLiving.posX + 10, entityLiving.posY + 10, entityLiving.posZ + 10)).isEmpty()) {
                        entityLiving.removePotionEffect(PotionInit.silence);
                        worldIn.spawnEntity(new EntityItem(worldIn, entityLiving.posX + entityLiving.width / 2, entityLiving.posY + entityLiving.height / 2, entityLiving.posZ + entityLiving.width / 2,
                                new ItemStack(ModBlocks.flower_silence)));
                    }
                }
            }
        }
        return new ItemStack(stack.getItem(), stack.getCount() - 1);
    }
}
