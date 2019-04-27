package de.aelpecyem.elementaristics.blocks.base;

import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.potions.effects.emotion.PotionEmotion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMushroomIntoxicating extends BlockFlowerBase {
    public BlockMushroomIntoxicating() {
        super("mushroom_intoxicating", 10, null);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(PotionInit.potionIntoxicated, 200, 0));
        }
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }

}
