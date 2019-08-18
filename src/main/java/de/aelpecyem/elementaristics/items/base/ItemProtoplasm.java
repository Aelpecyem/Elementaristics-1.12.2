package de.aelpecyem.elementaristics.items.base;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class ItemProtoplasm extends ItemBase {
    private static final String NBTKEY_WATER = "watertime";

    public ItemProtoplasm() {
        super("protoplasm");
    }

    //=======================================TESTING ZONE=======================================
    // todo remove that once the stuff is in game
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntityProtoplasm && playerIn.isCreative()){
            ((EntityProtoplasm) target).setSlimeSize(((EntityProtoplasm) target).getSize() + (playerIn.isSneaking() ? -1 : 1), true);
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
    //=======================================TESTING ZONE OVER=======================================

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (entityItem.isInWater()) {
            Elementaristics.proxy.generateGenericParticles(entityItem, 10813, 1, 100, 0, true, true);
            if (entityItem.getItem().hasTagCompound()) {
                if (entityItem.getItem().getTagCompound().hasKey(NBTKEY_WATER)) {
                    entityItem.getItem().getTagCompound().setInteger(NBTKEY_WATER, entityItem.getItem().getTagCompound().getInteger(NBTKEY_WATER) + 1);
                    if (entityItem.getItem().getTagCompound().getInteger(NBTKEY_WATER) > 100) {
                        if (!entityItem.world.isRemote){
                            EntityProtoplasm protoplasm = new EntityProtoplasm(entityItem.world);
                            protoplasm.setLocationAndAngles(entityItem.posX, entityItem.posY, entityItem.posZ, entityItem.rotationYaw, entityItem.rotationPitch);
                            protoplasm.onInitialSpawn(entityItem.world.getDifficultyForLocation(entityItem.getPosition()), null);
                            entityItem.world.spawnEntity(protoplasm);
                            entityItem.getItem().shrink(1);
                            entityItem.getItem().setTagCompound(new NBTTagCompound());
                        }
                    }
                } else {
                    entityItem.getItem().getTagCompound().setInteger(NBTKEY_WATER, 0);
                }
            } else {
                entityItem.getItem().setTagCompound(new NBTTagCompound());
                entityItem.getItem().getTagCompound().setInteger(NBTKEY_WATER, 0);
            }
        }
        return super.onEntityItemUpdate(entityItem);
    }
}
