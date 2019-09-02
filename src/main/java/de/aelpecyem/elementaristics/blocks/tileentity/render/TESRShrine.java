package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TESRShrine extends TileEntitySpecialRenderer<TileEntityDeityShrine> {
    @Override
    public void render(TileEntityDeityShrine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te.getDeityBound() != null) {
            ModelBase model = te.getDeityBound().getModel();
            ResourceLocation loc = te.getDeityBound().getTexture();
            if (loc == null || model == null) return;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
            GlStateManager.rotate(180, 0, 0, 1);
            bindTexture(loc);
            EnumFacing face = te.getWorld().getBlockState(te.getPos()).getValue(BlockHorizontal.FACING);
            GlStateManager.rotate(face == EnumFacing.WEST ? 270 : face == EnumFacing.EAST ? 90 : face == EnumFacing.SOUTH ? 180 : 0, 0, 1, 0);
            model.render(null, 0, 0, 0, 0, 0, 0.0625f);
            GlStateManager.popMatrix();
        }
    }
}
