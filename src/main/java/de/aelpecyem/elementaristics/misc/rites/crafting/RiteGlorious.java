package de.aelpecyem.elementaristics.misc.rites.crafting;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPedestal;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon.BlockDeityShrineBase;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemChannelingTool;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.GloriousRecipes;
import de.aelpecyem.elementaristics.recipe.base.GloriousRecipe;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class RiteGlorious extends RiteBase {

    public RiteGlorious() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_glorious"), 260, 0.2F, 12, Aspects.light);
    }

    //on a pedestal
    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityItem> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(2), null);
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

        if (nexus.world.getBlockState(new BlockPos(coordsX, coordsY, coordsZ)).getBlock() == ModBlocks.stone_runed) {
            BlockPos posSelected = new BlockPos(coordsX, coordsY, coordsZ);
            if (posSelected.getDistance((int) nexus.posX, (int) nexus.posY, (int) nexus.posZ) < 20) {
                for (int x = -1; x < 2; x++) {
                    for (int z = -1; z < 2; z++) {
                        if (nexus.world.getBlockState(new BlockPos(coordsX + x, coordsY, coordsZ + z)).getBlock() instanceof BlockRedstoneWire) {
                            redstoneCount++;
                            nexus.world.setBlockState(new BlockPos(coordsX + x, coordsY, coordsZ + z), Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }

            if (redstoneCount >= 8) {
                if (nexus.world.getTileEntity(posSelected.up()) != null && nexus.world.getTileEntity(posSelected.up()) instanceof TileEntityPedestal) {
                    if (GloriousRecipes.getRecipeForInput(((TileEntityPedestal) nexus.world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)) != null) {
                        GloriousRecipe recipe = GloriousRecipes.getRecipeForInput(((TileEntityPedestal) nexus.world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0));
                        ((TileEntityPedestal) nexus.world.getTileEntity(posSelected.up())).inventory.setStackInSlot(0, ItemStack.EMPTY);
                        nexus.world.setBlockState(posSelected.up(), Blocks.AIR.getDefaultState());
                        if (nexus.world.isRemote) {
                            for (int x = 0; x < 8; x++) {
                                for (int y = 0; y < 8; y++) {
                                    for (int z = 0; z < 8; z++) {
                                        if (y < 2 || y > 5)
                                            Elementaristics.proxy.generateGenericParticles(nexus.world, posSelected.getX() + (float) (x + 0.5F) / 8, posSelected.getY() + 1 + (float) (y + 0.5F) / 8, posSelected.getZ() + (float) (z + 0.5F) / 8, 0, 0, 0, 3881787, 2F, 200, 0, true, true);
                                        else if ((x > 1 && x < 6) && (z > 1 && z < 6))
                                            Elementaristics.proxy.generateGenericParticles(nexus.world, posSelected.getX() + (float) (x + 0.5F) / 8, posSelected.getY() + 1 + (float) (y + 0.5F) / 8, posSelected.getZ() + (float) (z + 0.5F) / 8, 0, 0, 0, 3881787, 2F, 200, 0, true, true);

                                    }
                                }
                            }
                        }
                        nexus.world.setBlockState(posSelected, recipe.output.getDefaultState());
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
    public void onRitual(EntityDimensionalNexus nexus) {
        List<EntityItem> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(2));
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
            entity.motionX = (nexus.posX - entity.posX) / 20;
            entity.motionY = (nexus.posY - entity.posY) / 20;
            entity.motionZ = (nexus.posZ - entity.posZ) / 20;
        }

        if (nexus.world.getBlockState(new BlockPos(coordsX, coordsY, coordsZ)).getBlock() == ModBlocks.stone_runed) {
            BlockPos posSelected = new BlockPos(coordsX, coordsY, coordsZ);
            if (posSelected.getDistance((int) nexus.posX, (int) nexus.posY, (int) nexus.posZ) < 20) {
                for (int x = -1; x < 2; x++) {
                    for (int z = -1; z < 2; z++) {
                        if (nexus.world.getBlockState(new BlockPos(coordsX + x, coordsY, coordsZ + z)).getBlock() instanceof BlockRedstoneWire) {
                            redstoneCount++;
                            Elementaristics.proxy.generateGenericParticles(nexus.world, coordsX + x + nexus.world.rand.nextFloat(), coordsY + nexus.world.rand.nextFloat() / 8, coordsZ + z + nexus.world.rand.nextFloat(), 0, 0, 0, 14811136, 1.5F, 100, 0.001F, true, false);
                        }
                    }
                }
            }
            if (nexus.getRiteTicks() % 2 == 0) {
                if (redstoneCount >= 8) {
                    if (nexus.world.getTileEntity(posSelected.up()) != null && nexus.world.getTileEntity(posSelected.up()) instanceof TileEntityPedestal) {
                        if (GloriousRecipes.getRecipeForInput(((TileEntityPedestal) nexus.world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)) != null) {
                            if (nexus.world.isRemote) {
                                if (GloriousRecipes.getRecipeForInput(((TileEntityPedestal) nexus.world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)).output instanceof BlockDeityShrineBase) {
                                    Deity deity = ((BlockDeityShrineBase) GloriousRecipes.getRecipeForInput(((TileEntityPedestal) nexus.world.getTileEntity(posSelected.up())).inventory.getStackInSlot(0)).output).deity;
                                    Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(nexus.world, nexus.posX, nexus.posY, nexus.posZ, 0, 0, 0, deity.getColor(), 2, 300, 0,
                                            false, true, true, true, posSelected.getX() + nexus.world.rand.nextFloat(), posSelected.getY() + nexus.world.rand.nextFloat(), posSelected.getZ() + nexus.world.rand.nextFloat()));
                                    Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(nexus.world, posSelected.up().getX() + 0.5, posSelected.up().getY() + 0.9, posSelected.up().getZ() + 0.5, 0, 0, 0, Aspects.light.getColor(), 2, 300, 0,
                                            false, true, true, true, nexus.posX, nexus.posY, nexus.posZ));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
