package de.aelpecyem.elementaristics.items.base.artifacts.rites.materials;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.consumable.ItemFoodBase;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemFleshLamb extends ItemFoodBase implements IHasRiteUse {
    public ItemFleshLamb() {
        super("flesh_lamb", 1, 0, false);
        setAlwaysEdible();
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntityTameable && !(target instanceof EntityProtoplasm)){
            if (((EntityTameable) target).isTamed() && target.isEntityAlive()){
                for (int i = 0; i < 40; i++)
                    Elementaristics.proxy.generateGenericParticles(target, 924705, 2 + (float) target.world.rand.nextGaussian(), 120 + target.world.rand.nextInt(40), 0.001F, false, false);
                if (!playerIn.world.isRemote && MaganUtil.drainMaganFromPlayer(playerIn, 80, 2000, true))
                    playerIn.world.spawnEntity(new EntityItem(playerIn.world, target.posX, target.posY, target.posZ, new ItemStack(ModItems.protoplasm)));
                target.attackEntityFrom(DamageSource.MAGIC, 10000);
                stack.shrink(1);
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityTameable){
            if (((EntityTameable) entity).isTamed()){
                for (int i = 0; i < 40; i++)
                    Elementaristics.proxy.generateGenericParticles(entity, 924705, 2 + (float) entity.world.rand.nextGaussian(), 120 + entity.world.rand.nextInt(40), 0.001F, false, false);
                if (!player.world.isRemote && MaganUtil.drainMaganFromPlayer(player, 80, 2000, true))
                    player.world.spawnEntity(new EntityItem(player.world, entity.posX, entity.posY, entity.posZ, new ItemStack(ModItems.protoplasm)));
                entity.attackEntityFrom(DamageSource.MAGIC, 10000);
                stack.shrink(1);
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        for (int i = 0; i < 40; i++)
            Elementaristics.proxy.generateGenericParticles(entityLiving, 924705, 2 + (float) worldIn.rand.nextGaussian(), 120 + worldIn.rand.nextInt(40), 0.001F, false, false);

        if (entityLiving instanceof EntityPlayer && !worldIn.isRemote && MaganUtil.drainMaganFromPlayer((EntityPlayer) entityLiving, 80, 2000, true))
            worldIn.spawnEntity(new EntityItem(worldIn, entityLiving.posX, entityLiving.posY, entityLiving.posZ, new ItemStack(ModItems.protoplasm)));
        entityLiving.attackEntityFrom(DamageSource.MAGIC, 10000);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.flesh_lamb.name"));
        tooltip.add(I18n.format("tooltip.aspect_tool.power") + " " + getPower());

        tooltip.add(I18n.format("tooltip.aspect_tool.aspects"));
        for (Aspect aspect : this.getAspects()) {
            tooltip.add("-" + aspect.getLocalizedName());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public List<Aspect> getAspects() {
        List<Aspect> aspects = new ArrayList<>();
        aspects.add(Aspects.ice);
        return aspects;
    }

    @Override
    public int getPower() {
        return 6;
    }

    @Override
    public boolean isConsumed() {
        return true;
    }
}
