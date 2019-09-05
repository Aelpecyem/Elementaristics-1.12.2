package de.aelpecyem.elementaristics.entity.render.protoplasm;

import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerProtoplasmGel implements LayerRenderer<EntityProtoplasm>{
    private final RenderProtoplasm slimeRenderer;
    private final ModelBase slimeModel = new ModelSlime(0);

    public LayerProtoplasmGel(RenderProtoplasm slimeRendererIn)
    {
        this.slimeRenderer = slimeRendererIn;
    }

    @Override
    public void doRenderLayer(EntityProtoplasm entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!entitylivingbaseIn.isInvisible()){
            GlStateManager.color((float) entitylivingbaseIn.getColorAWT().getRed() / 255F, (float) entitylivingbaseIn.getColorAWT().getGreen() / 255F, (float) entitylivingbaseIn.getColorAWT().getBlue() / 255F, (float) entitylivingbaseIn.getColorAWT().getAlpha() / 255F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }

    public boolean shouldCombineTextures()
        {
            return true;
        }
}
