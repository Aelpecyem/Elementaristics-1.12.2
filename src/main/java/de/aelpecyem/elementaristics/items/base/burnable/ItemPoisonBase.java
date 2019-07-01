package de.aelpecyem.elementaristics.items.base.burnable;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.poisons.PoisonEffectBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
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
        poison.drinkEffect(player.world, player);
        super.affectPlayer(item, player);
    }
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        List<EntityItem> items = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() + hitX + 0.5F, pos.getY() + hitY + 0.5F, pos.getZ() + hitZ  + 0.5F, pos.getX() + hitX - 0.5F, pos.getY() + hitY - 0.5F, pos.getZ() + hitZ - 0.5F));
        if (!items.isEmpty()) {
            for (EntityItem item : items) {
                ItemStack stack = item.getItem();
                if (stack.getItemUseAction() == EnumAction.DRINK || stack.getItemUseAction() == EnumAction.EAT) {
                    if (!stack.hasTagCompound()) {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    Elementaristics.proxy.generateGenericParticles(item, poison.getColor(), 2, 200, 0, true, true);
                    stack.getTagCompound().setInteger(POISON_TAG, poison.getId());
                    player.getHeldItem(hand).shrink(1);
                    Elementaristics.proxy.generateGenericParticles(worldIn,  pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ, poison.getColor(), 2, 160, 0, true, true);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void affect(EntityItem itemIn) {
        List<EntityItem> items = itemIn.world.getEntitiesWithinAABB(EntityItem.class, itemIn.getRenderBoundingBox().grow(2));
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
                Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(itemIn.world, itemIn.posX, itemIn.posY, itemIn.posZ, itemIn.world.rand.nextGaussian() * 0.1F, Math.abs(itemIn.world.rand.nextGaussian()) * 0.1F, itemIn.world.rand.nextGaussian() * 0.1F, poison.getColor(), 3, 60, 0.1F, true, false, 0.99F, true));
        }
        super.affect(itemIn);
    }

}