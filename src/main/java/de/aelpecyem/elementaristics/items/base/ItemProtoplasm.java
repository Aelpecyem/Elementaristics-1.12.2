package de.aelpecyem.elementaristics.items.base;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemProtoplasm extends ItemBase {
    public static final String NBTKEY_WATER = "watertime";
    public static final String NBTKEY_NAME = "nametag";

    public ItemProtoplasm() {
        super("protoplasm");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTKEY_NAME)) {
            String name = stack.getTagCompound().getString(NBTKEY_NAME);
            if (!name.isEmpty()) {
                tooltip.add("\"" + I18n.format("tooltip.protoplasm_sample.smell") + " " + name + "\"");
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

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
                            if (entityItem.getItem().getTagCompound().hasKey(NBTKEY_NAME)) {
                                String name = entityItem.getItem().getTagCompound().getString(NBTKEY_NAME);
                                if (!name.isEmpty()) {
                                    protoplasm.setCustomNameTag(name);
                                }
                            }
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
