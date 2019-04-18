package de.aelpecyem.elementaristics.blocks.base;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;

public class BlockSilverThread extends BlockBase {
    protected String name;

    public BlockSilverThread() {
        super(Material.IRON, "block_silver_thread", 100);
        setBlockUnbreakable();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = playerIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getPlayerAscensionStage() > 0 || playerIn.capabilities.isCreativeMode) {
                playerIn.changeDimension(playerIn.getSpawnDimension(), new ITeleporter() {
                    @Override
                    public void placeEntity(World world, Entity entity, float yaw) {
                        if (playerIn.getBedLocation() != null) {
                            System.out.println("TPing to bed");
                            entity.setPosition(playerIn.getBedLocation().getX(), playerIn.getBedLocation().getY(), playerIn.getBedLocation().getZ());
                        } else {
                            for (int i = 0; i < 250; i++) {
                                System.out.println("TPing to a location");
                                playerIn.attemptTeleport(playerIn.posX, 250 - i, playerIn.posZ);
                            }
                        }
                    }
                });
            } else if (worldIn.getEntitiesWithinAABB(EntitySilverThread.class, new AxisAlignedBB(pos.getX() - 200, pos.getY() - 50, pos.getZ() - 200, pos.getX() + 200, pos.getY() + 100, pos.getZ() + 200), new Predicate<EntitySilverThread>() {
                @Override
                public boolean apply(@Nullable EntitySilverThread input) {
                    return input.dimension == Config.mindDimensionId;
                }
            }).size() < 1) {

                EntitySilverThread boss = new EntitySilverThread(worldIn);
                boss.setPosition(pos.getX() + 20 * (worldIn.rand.nextBoolean() ? -1 : 1), pos.getY(), pos.getZ() + 20 * (worldIn.rand.nextBoolean() ? -1 : 1));
                if (!worldIn.isRemote) {
                    worldIn.spawnEntity(boss);
                }
            } else {
                if (worldIn.isRemote)
                    playerIn.sendStatusMessage(new TextComponentString(I18n.format("message.interact_silver_thread.error_fight")), true);
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

}
