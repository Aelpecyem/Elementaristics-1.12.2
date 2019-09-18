package de.aelpecyem.elementaristics.items.base.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;

public class ItemWaterBody extends ItemBase implements IBauble {
    public ItemWaterBody() {
        super("body_water");
        maxStackSize = 1;
    }


    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BODY;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player.isInWater() && player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            float f = 0.5F;
            if (player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId() == SoulInit.soulWater.getId()) {
                f = 0.8F;
                player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 30, 0, false, false));
            }
            if (!player.isSneaking()) {
                float yaw = player.rotationYaw;
                float pitch = player.rotationPitch;

                player.motionX = (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
                player.motionZ = (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI) * f);
                player.motionY = (double) (-MathHelper.sin((pitch) / 180.0F * (float) Math.PI) * f);
            }
        }
    }
}
