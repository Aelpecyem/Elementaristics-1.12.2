package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityReactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class TESRReactor extends TileEntitySpecialRenderer<TileEntityReactor> {

    @Override
    public void render(TileEntityReactor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();

            if (te.getBlockFacing() == EnumFacing.NORTH || te.getBlockFacing() == EnumFacing.UP || te.getBlockFacing() == EnumFacing.DOWN) {
                GlStateManager.translate(x + 0.75, y + +1.2, z + 0.5);
            }
            if (te.getBlockFacing() == EnumFacing.SOUTH) {
                GlStateManager.translate(x + 0.25, y + 1.2, z + 0.5);
            }
            if (te.getBlockFacing() == EnumFacing.WEST) {
                GlStateManager.translate(x + 0.5, y + 1.2, z + 0.25);
            }
            if (te.getBlockFacing() == EnumFacing.EAST) {
                GlStateManager.translate(x + 0.5, y + 1.2, z + 0.75);
            }
            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }

        ItemStack stack2 = te.inventory.getStackInSlot(1);
        if (!stack2.isEmpty()) {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();

            if (te.getBlockFacing() == EnumFacing.NORTH || te.getBlockFacing() == EnumFacing.UP || te.getBlockFacing() == EnumFacing.DOWN) {
                GlStateManager.translate(x + 0.25, y + 1.2, z + 0.5);
            }
            if (te.getBlockFacing() == EnumFacing.SOUTH) {
                GlStateManager.translate(x + 0.75, y + 1.2, z + 0.5);
            }
            if (te.getBlockFacing() == EnumFacing.WEST) {
                GlStateManager.translate(x + 0.5, y + 1.2, z + 0.75);
            }
            if (te.getBlockFacing() == EnumFacing.EAST) {
                GlStateManager.translate(x + 0.5, y + 1.2, z + 0.25);
            }

            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack2, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack2, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }
}