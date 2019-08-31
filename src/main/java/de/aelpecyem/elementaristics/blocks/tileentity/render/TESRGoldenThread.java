package de.aelpecyem.elementaristics.blocks.tileentity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityGoldenThread;
import de.aelpecyem.elementaristics.entity.model.ModelCultist;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TESRGoldenThread extends TileEntitySpecialRenderer<TileEntityGoldenThread> {

    @Override
    public void render(TileEntityGoldenThread te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ModelBase model = new ModelCultist();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);

        GlStateManager.rotate(180, 0, 0, 1);
        GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * te.charge / 20, 0, 1, 0);
        bindTexture(new ResourceLocation(Elementaristics.MODID, "textures/entity/cultists/cultist_1.png"));
        model.render(null, 0, 0, 0, 0, 0, 0.0625F / 2);
        GlStateManager.popMatrix();
    }
}
