package de.aelpecyem.elementaristics.items.base.burnable;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.poisons.PoisonEffectBase;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemPoisonBase extends ItemBurnableAffectingBase {
    protected PoisonEffectBase poison;
    public static final String POISON_TAG = "poison_effect";

    public ItemPoisonBase(String name, PoisonEffectBase poison) {
        super(name);
        this.poison = poison;
    }

    public PoisonEffectBase getPoison() {
        return poison;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip." + name + ".name"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void affectPlayer(EntityItem item, EntityPlayer player) {
        poison.performEffect(player.world, player);
        super.affectPlayer(item, player);
    }

    @Override
    public void affect(EntityItem itemIn) {
        List<EntityItem> items = itemIn.world.getEntitiesWithinAABB(EntityItem.class, itemIn.getCollisionBoundingBox().expand(2, 2, 2));
        for (EntityItem entityItem : items) {
            if (!entityItem.isEntityEqual(itemIn)) {
                ItemStack stack = entityItem.getItem();
                if (stack.getItemUseAction() == EnumAction.DRINK || stack.getItemUseAction() == EnumAction.EAT) {
                    if (!stack.hasTagCompound()) {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    stack.getTagCompound().setInteger(POISON_TAG, poison.getId());
                    break;
                }
            }
        }

        if (itemIn.world.isRemote) {
            for (int i = 0; i < 20; i++)
                Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(itemIn.world, itemIn.posX, itemIn.posY, itemIn.posZ, itemIn.world.rand.nextGaussian() * 0.1F, Math.abs(itemIn.world.rand.nextGaussian()) * 0.1F, itemIn.world.rand.nextGaussian() * 0.1F, poison.getColor(), 3, 100, 0.1F, false, false, 0.99F, true));
        }
        super.affect(itemIn);
    }

}
