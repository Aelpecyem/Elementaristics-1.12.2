package de.aelpecyem.elementaristics.misc.poisons;

import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PoisonGlassblood extends PoisonEffectBase {
    public PoisonGlassblood() {
        super(PoisonInit.poisons.size(), Aspects.light.getColor());
    }

    @Override
    public void performEffect(World world, EntityLivingBase player) {
        player.attackEntityFrom(DamageSource.MAGIC, 14);
        player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1, false, false));
        if (player instanceof EntityPlayer)
            MaganUtil.drainMaganFromPlayer((EntityPlayer) player, 100, 2200, true);
        super.performEffect(world, player);
    }
}
