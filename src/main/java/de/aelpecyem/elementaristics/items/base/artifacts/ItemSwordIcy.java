package de.aelpecyem.elementaristics.items.base.artifacts;


import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.ModMaterials;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemSwordIcy extends ItemSword implements IHasRiteUse, IHasModel {
    protected String name;

    public ItemSwordIcy() {
        super(ModMaterials.MATERIAL_SWORD_ICE.setRepairItem(new ItemStack(ModItems.essence, 1, Aspects.ice.getId())));
        maxStackSize = 1;
        name = "sword_icy";

        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);

        ModItems.ITEMS.add(this);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.sword_icy.name"));

        tooltip.add(I18n.format("tooltip.aspect_tool.power") + " " + getPower());

        tooltip.add(I18n.format("tooltip.aspect_tool.aspects"));
        for (Aspect aspect : this.getAspects()) {
            tooltip.add("-" + aspect.getLocalizedName());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);

    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        target.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 2, false, false));
        return super.hitEntity(stack, target, attacker);
    }

    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public void registerItemModel(Item itemBlock) {

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        Entity entity = playerIn.rayTrace(10, 1).entityHit instanceof EntityLivingBase ? playerIn.rayTrace(10, 1).entityHit : null;
        BlockPos posHit = playerIn.rayTrace(10, 1).getBlockPos();
        EnumFacing sideHit = playerIn.rayTrace(10, 1).sideHit;

        if (playerIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = playerIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.knowsSoul()) {
                List<Entity> targets = new ArrayList<>();
                float x, y, z;
                if (entity != null) {
                    for (int i = 0; i < 10; i++)
                        Elementaristics.proxy.generateGenericParticles(entity, Aspects.ice.getColor(), 4, 1000, 0, true, true);
                    targets = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB((entity.posX + entity.width / 2) - 1.5, (entity.posY + entity.height / 2) - 1.5, (entity.posZ + entity.width / 2) - 1.5, (entity.posX + entity.width / 2) + 1.5, (entity.posY + entity.height / 2) + 1.5, (entity.posZ + entity.width / 2) + 1.5));
                } else if (posHit != null) {
                    x = posHit.getX() + 0.5F;
                    y = posHit.getY() + 0.5F;
                    z = posHit.getZ() + 0.5F;
                    if (sideHit != null && !(worldIn.getBlockState(posHit) == Blocks.AIR.getDefaultState())) {
                        switch (sideHit) {
                            case DOWN:
                                y--;
                                break;
                            case UP:
                                y++;
                                break;
                            case WEST:
                                x--;
                                break;
                            case EAST:
                                x++;
                                break;
                            case SOUTH:
                                z++;
                                break;
                            case NORTH:
                                z--;
                                break;
                        }
                    }
                    targets = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(x - 1.5, y - 1.5, y - 1.5, x + 1.5, y + 1.5, z + 1.5));
                    for (int i = 0; i < 10; i++)
                        Elementaristics.proxy.generateGenericParticles(worldIn, x + worldIn.rand.nextGaussian(), y + worldIn.rand.nextGaussian(), z + worldIn.rand.nextGaussian(), Aspects.ice.getColor(), 4, 1000, 0, true, true);
                }
                for (Entity target : targets) {
                    if (target instanceof EntityLivingBase) {
                        target.attackEntityFrom(DamageSource.GENERIC, 4);
                        ((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 2, true, true));
                    }
                }
                cap.drainMagan(10);
                cap.stuntMagan(28);
                playerIn.getCooldownTracker().setCooldown(this, 20);
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
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
        return false;
    }
}
