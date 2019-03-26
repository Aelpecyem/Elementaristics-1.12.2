package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityForge;
import de.aelpecyem.elementaristics.recipe.ForgeRecipes;
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
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class TESRForge extends TileEntitySpecialRenderer<TileEntityForge> {

    @Override
    public void render(TileEntityForge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.color(1 + te.hitCount / 10, 1 + te.hitCount / 20, 1, 1 - (te.hitCount / 20));
            if (te.getBlockFacing() == EnumFacing.NORTH || te.getBlockFacing() == EnumFacing.UP || te.getBlockFacing() == EnumFacing.DOWN) {
                GlStateManager.translate(x + 0.60, y + 1.02, z + 0.40); //fine
                GlStateManager.rotate(90, 1, 0, 0);
                GlStateManager.rotate(45, 0, 0, 1);

            }
            if (te.getBlockFacing() == EnumFacing.SOUTH) { //fine
                GlStateManager.translate(x + 0.60, y + 1.02, z + 0.60);
                GlStateManager.rotate(270, 1, 0, 0);
                GlStateManager.rotate(45, 0, 0, 1);

            }
            if (te.getBlockFacing() == EnumFacing.WEST) {
                GlStateManager.translate(x + 0.40, y + 1.02, z + 0.40);
                GlStateManager.rotate(90, 1, 0, 0);
                GlStateManager.rotate(-45, 0, 0, 1);
            }
            if (te.getBlockFacing() == EnumFacing.EAST) {
                GlStateManager.translate(x + 0.60, y + 1.02, z + 0.60);
                GlStateManager.rotate(90, 1, 0, 0);
                GlStateManager.rotate(-45 - 180, 0, 0, 1);
            }

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }
}