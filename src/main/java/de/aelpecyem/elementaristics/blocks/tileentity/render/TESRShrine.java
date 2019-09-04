package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon.BlockDeityShrineBase;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class TESRShrine extends TileEntitySpecialRenderer<TileEntityDeityShrine> {
    @Override
    public void render(@Nullable TileEntityDeityShrine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te != null) {
            if (te.isStatue && te.getDeityBound() != null) { //get deity from block!
                ModelBase model = te.getDeityBound().getModel();
                ResourceLocation loc = te.getDeityBound().getTexture();
                if (loc == null || model == null) return;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
                GlStateManager.rotate(180, 0, 0, 1);
                bindTexture(loc);
                if (!te.unusedString.equals("item")) {
                    EnumFacing face = te.getWorld().getBlockState(te.getPos()).getValue(BlockHorizontal.FACING);
                    GlStateManager.rotate(face == EnumFacing.WEST ? 270 : face == EnumFacing.EAST ? 90 : face == EnumFacing.SOUTH ? 180 : 0, 0, 1, 0);
                }
                model.render(null, 0, 0, 0, 0, 0, 0.0625f);
                GlStateManager.popMatrix();
            }
        }
    }

    public static class ForwardingTEISR extends TileEntityItemStackRenderer {
        private TileEntityDeityShrine shrineRender = new TileEntityDeityShrine();
        private final TileEntityItemStackRenderer compose;

        public ForwardingTEISR(TileEntityItemStackRenderer compose) {
            this.compose = compose;
        }

        @Override
        public void renderByItem(ItemStack itemStack) {
            Block block = Block.getBlockFromItem(itemStack.getItem());
            if (block instanceof BlockDeityShrineBase) {
                shrineRender.isStatue = true;
                shrineRender.deityBound = ((BlockDeityShrineBase) block).deity.getName().toString();
                shrineRender.unusedString = "item";
                TileEntityRendererDispatcher.instance.render(this.shrineRender, 0.0D, 0.0D, 0.0D, 0.0F, 0, 0);
            } else {
                compose.renderByItem(itemStack);
            }
        }
    }
}
