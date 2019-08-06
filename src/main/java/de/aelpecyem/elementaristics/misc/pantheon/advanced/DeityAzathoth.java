package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class DeityAzathoth extends DeitySupplyEffectBase {
    public DeityAzathoth() {
        super(TimeUtil.getTickTimeStartForHour(1), new ResourceLocation(Elementaristics.MODID, "deity_azathoth"), Aspects.vacuum, 5065806);
    }

    @Override
    public void activeStatueEffect(TileEntityDeityShrine te, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (PlayerUtil.hasSoul(playerIn, SoulInit.soulAncient)){
            playerIn.addPotionEffect(new PotionEffect(PotionInit.potionFatherBlessing, 8000, 0, true, true));
        }else{
            if (!PlayerUtil.hasEmotionActive(playerIn)) {
                playerIn.addPotionEffect(new PotionEffect(PotionInit.dread, 8000, 0, true, false));
            }
        }
        super.activeStatueEffect(te, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
