package de.aelpecyem.elementaristics.misc.rites.crafting;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPedestal;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon.BlockDeityShrineBase;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemChannelingTool;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.EntropizerRecipes;
import de.aelpecyem.elementaristics.recipe.GloriousRecipes;
import de.aelpecyem.elementaristics.recipe.base.GloriousRecipe;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RiteGlorious extends RiteBase {

    public RiteGlorious() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_glorious"), 260, 0.2F, 12, Aspects.light);
    }

    //on a pedestal
    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player, TileEntityAltar tile) {
        List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
        int coordsX = 0, coordsY = 0, coordsZ = 0;
        int redstoneCount = 0;
        for (EntityItem entity : entities) {
            if (entity.getItem().getItem() instanceof ItemChannelingTool) {
                if (entity.getItem().hasTagCompound()) {
                    if (checkHasCoords(entity)) {
                        coordsX = entity.getItem().getTagCompound().getInteger(ItemChannelingTool.X_KEY);
                        coordsY = entity.getItem().getTagCompound().getInteger(ItemChannelingTool.Y_KEY);
                        coordsZ = entity.getItem().getTagCompound().getInteger(ItemChannelingTool.Z_KEY);
                        break;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        if (world.getBlockState(new BlockPos(coordsX, coordsY, coordsZ)).getBlock() == ModBlocks.stone_runed) {
            BlockPos posSelected = new BlockPos(coordsX, coordsY, coordsZ);
            if (posSelected.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 20) {
                for (int x = -1; x < 2; x++) {
                    for (int z = -1; z < 2; z++) {
                        if (world.getBlockState(new BlockPos(coordsX + x, coordsY, coordsZ + z)).getBlock() instanceof BlockRedstoneWire) {
                            redstoneCount++;
                            world.setBlockState(new BlockPos(coordsX + x, coordsY, coordsZ + z), Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }

            if (redstoneCount >= 8) {
                if (world.getTileEntity(posSelected.up()) != null && world.getTileEntity(posSelected.up()) instanceof TileEntityPedestal) {
                    if (GloriousRecipes.getRecipeForInput(((TileEntityPedestal) world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)) != null) {
                        GloriousRecipe recipe = GloriousRecipes.getRecipeForInput(((TileEntityPedestal) world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0));
                        ((TileEntityPedestal) world.getTileEntity(posSelected.up())).inventory.setStackInSlot(0, ItemStack.EMPTY);
                        world.setBlockState(posSelected.up(), Blocks.AIR.getDefaultState());
                        if (world.isRemote) {

                            for (int x = 0; x < 8; x++) {
                                for (int y = 0; y < 8; y++) {
                                    for (int z = 0; z < 8; z++) {
                                        if (y < 2 || y > 5)
                                            Elementaristics.proxy.generateGenericParticles(world, posSelected.getX() + (float) (x + 0.5F) / 8, posSelected.getY() + 1 + (float) (y + 0.5F) / 8, posSelected.getZ() + (float) (z + 0.5F) / 8, 0, 0, 0, 3881787, 2F, 200, 0, true, true);
                                        else if ((x > 1 && x < 6) && (z > 1 && z < 6))
                                            Elementaristics.proxy.generateGenericParticles(world, posSelected.getX() + (float) (x + 0.5F) / 8, posSelected.getY() + 1 + (float) (y + 0.5F) / 8, posSelected.getZ() + (float) (z + 0.5F) / 8, 0, 0, 0, 3881787, 2F, 200, 0, true, true);

                                    }
                                }
                            }
                        }
                        world.setBlockState(posSelected, recipe.output.getDefaultState());
                    }
                }
            }
        }

    }

    public boolean checkHasCoords(EntityItem item) {
        ItemStack stack = item.getItem();
        if (stack.hasTagCompound()) {
            if (stack.getTagCompound().hasKey(ItemChannelingTool.X_KEY) && stack.getTagCompound().hasKey(ItemChannelingTool.Y_KEY) && stack.getTagCompound().hasKey(ItemChannelingTool.Z_KEY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount, TileEntityAltar tile) {
        List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(altarPos.getX() - 2, altarPos.getY() - 1, altarPos.getZ() - 2, altarPos.getX() + 2, altarPos.getY() + 2, altarPos.getZ() + 2), null);
        int coordsX = 0, coordsY = 0, coordsZ = 0;
        int redstoneCount = 0;
        for (EntityItem entity : entities) {
            if (entity.getItem().getItem() instanceof ItemChannelingTool) {
                if (entity.getItem().hasTagCompound()) {
                    if (checkHasCoords(entity)) {
                        coordsX = entity.getItem().getTagCompound().getInteger(ItemChannelingTool.X_KEY);
                        coordsY = entity.getItem().getTagCompound().getInteger(ItemChannelingTool.Y_KEY);
                        coordsZ = entity.getItem().getTagCompound().getInteger(ItemChannelingTool.Z_KEY);
                    }
                }
            }
            entity.motionX = (altarPos.getX() + 0.5 - entity.posX) / 20;
            entity.motionY = (altarPos.getY() + 1.5 - entity.posY) / 20;
            entity.motionZ = (altarPos.getZ() + 0.5 - entity.posZ) / 20;
        }

        if (world.getBlockState(new BlockPos(coordsX, coordsY, coordsZ)).getBlock() == ModBlocks.stone_runed) {
            BlockPos posSelected = new BlockPos(coordsX, coordsY, coordsZ);
            if (posSelected.getDistance(altarPos.getX(), altarPos.getY(), altarPos.getZ()) < 20) {
                for (int x = -1; x < 2; x++) {
                    for (int z = -1; z < 2; z++) {
                        if (world.getBlockState(new BlockPos(coordsX + x, coordsY, coordsZ + z)).getBlock() instanceof BlockRedstoneWire) {
                            redstoneCount++;
                            Elementaristics.proxy.generateGenericParticles(world, coordsX + x + world.rand.nextFloat(), coordsY + world.rand.nextFloat() / 8, coordsZ + z + world.rand.nextFloat(), 0, 0, 0, 14811136, 1.5F, 100, 0.001F, true, false);
                        }
                    }
                }
            }
            if (tickCount % 2 == 0) {
                if (redstoneCount >= 8) {
                    if (world.getTileEntity(posSelected.up()) != null && world.getTileEntity(posSelected.up()) instanceof TileEntityPedestal) {
                        if (GloriousRecipes.getRecipeForInput(((TileEntityPedestal) world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)) != null) {
                            if (world.isRemote) {
                                if (GloriousRecipes.getRecipeForInput(((TileEntityPedestal) world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)).output instanceof BlockDeityShrineBase) {
                                    Deity deity = ((BlockDeityShrineBase) GloriousRecipes.getRecipeForInput(((TileEntityPedestal) world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)).output).deity;
                                    Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, altarPos.getX() + 0.5, altarPos.getY() + 0.9, altarPos.getZ() + 0.5, 0, 0, 0, deity.getColor(), 2, 300, 0,
                                            false, true, true, true, posSelected.getX() + world.rand.nextFloat(), posSelected.getY() + world.rand.nextFloat(), posSelected.getZ() + world.rand.nextFloat()));

                                    Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, posSelected.up().getX() + 0.5, posSelected.up().getY() + 0.9, posSelected.up().getZ() + 0.5, 0, 0, 0, Aspects.light.getColor(), 2, 300, 0,
                                            false, true, true, true, altarPos.getX() + world.rand.nextFloat(), altarPos.getY() + 0.8F + world.rand.nextFloat() / 15, altarPos.getZ() + world.rand.nextFloat()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
