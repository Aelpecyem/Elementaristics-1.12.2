package de.aelpecyem.elementaristics.items.base.consumable;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntityExplosionProjectile;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.init.SpellInit;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import de.aelpecyem.elementaristics.util.IHasModel;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

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
