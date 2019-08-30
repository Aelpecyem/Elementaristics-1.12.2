package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.entity.projectile.EntityExplosionProjectile;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemGunpowderEnergized extends ItemAspects {

    public ItemGunpowderEnergized() {
        super("gunpowder_energized", 7, true, Aspects.fire, Aspects.electricity);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new IBehaviorDispenseItem() {
            @Override
            public ItemStack dispense(IBlockSource source, ItemStack stack) {
                EnumFacing enumfacing = source.getBlockState().getValue(BlockDispenser.FACING);
                Vec3d lookVect = new Vec3d(enumfacing.getDirectionVec());
                EntityExplosionProjectile projectile = new EntityExplosionProjectile(source.getWorld(), source.getBlockPos().getX() + (1.5 * lookVect.x) + 0.5, source.getBlockPos().getY() + 0.5d + lookVect.y, source.getBlockPos().getZ() + (1.5 * lookVect.z) + 0.5);
                projectile.shoot(projectile, 0, enumfacing.getHorizontalAngle(), 0, 1f, 0);
                source.getWorld().spawnEntity(projectile);
                stack.shrink(1);
                return stack;
            }
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        playerIn.setActiveHand(handIn);
        EntityExplosionProjectile spellProjectile = new EntityExplosionProjectile(worldIn, playerIn);
        spellProjectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F * 3.0F, 1.0F);
        spellProjectile.setCaster(playerIn);
        if (!worldIn.isRemote)
            worldIn.spawnEntity(spellProjectile);
        ItemStack stack = playerIn.getHeldItem(handIn);
        stack.shrink(1);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

}
