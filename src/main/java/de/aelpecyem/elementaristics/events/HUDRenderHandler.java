package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockAltar;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import scala.collection.parallel.ParIterableLike;

import java.util.ArrayList;
import java.util.List;

public class HUDRenderHandler {
    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && !event.isCancelable()){
            Minecraft mc = Minecraft.getMinecraft();
            if (!mc.player.capabilities.isCreativeMode && !mc.player.isSpectator()){
                if (mc.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                    IPlayerCapabilities caps = mc.player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                    if (caps.knowsSoul()) {
                        int posX = event.getResolution().getScaledWidth() / 2 - 93; // + 10;
                        int poxY = event.getResolution().getScaledHeight() - 31;

                        mc.renderEngine.bindTexture(new ResourceLocation("elementaristics:textures/gui/hud_elements.png"));
                        //  GL11.glEnable(GL11.GL_BLEND);
                        // GL11.glDisable(GL11.GL_DEPTH_TEST);
                        //GL11.glDepthMask(false);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                        if (caps.getTimeStunted() > 0) {
                            GL11.glColor4f(0.8F - (caps.getTimeStunted() / 100), 0.8F - (caps.getTimeStunted() / 100), 0.8F - (caps.getTimeStunted() / 100), 0.5F);
                        }
                        GL11.glDisable(GL11.GL_ALPHA_TEST);


                        //186, 9   186 * 1= 186      1 = current Magan : maxMagan
                        float mult = caps.getMagan() / caps.getMaxMagan();
                        mc.ingameGUI.drawTexturedModalRect(posX, poxY, 0, 0, Math.round(186 * mult), 9);

                        // GL11.glDisable(GL11.GL_BLEND);
                        //  GL11.glEnable(GL11.GL_DEPTH_TEST);
                        GL11.glEnable(GL11.GL_ALPHA_TEST);
                    }
                }
                }

        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT && !event.isCancelable()){
            Minecraft mc = Minecraft.getMinecraft();
            if (PlayerUtil.getBlockLookingAt(mc.player, 5) instanceof BlockAltar){
                BlockAltar altar = (BlockAltar) PlayerUtil.getBlockLookingAt(mc.player, 5);
                TileEntityAltar tile = altar.getTileEntity(mc.player.world, PlayerUtil.getBlockPosLookingAt(5));
                if (RiteInit.getRiteForResLoc(tile.currentRite) != null) {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite." + tile.currentRite + ".name"), 5, 5, 16777215);
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.item_power.name")+ " " + tile.getItemPowerInArea()+ " /  " +RiteInit.getRiteForResLoc(tile.currentRite).getItemPowerRequired(), 5, 15, 16777215);
                   if (!(tile.tickCount * 100 / RiteInit.getRiteForResLoc(tile.currentRite).getTicksRequired() > 100)) {
                       mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress.name") + " " + tile.tickCount * 100 / RiteInit.getRiteForResLoc(tile.currentRite).getTicksRequired() + " %", 5, 25, 16777215);
                   }else{
                       mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress_ready.name"), 5, 25, 16777215);
                   }
                   if (!tile.getAspectsInArea().isEmpty()) {
                       mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects.name"), 5, 35, 16777215);
                       List<Aspect> aspectList = new ArrayList<>();
                       aspectList.addAll(tile.getAspectsInArea());
                       for (int i = 0; i < aspectList.size(); i++) {
                           mc.ingameGUI.drawString(mc.fontRenderer,"-" + aspectList.get(i).getLocalizedName(), 5, 45 + i * 10, 16777215);
                       }
                   }
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects_needed.name"), event.getResolution().getScaledWidth() - 100, 5, 16777215);
                    List<Aspect> aspectList = new ArrayList<>();
                    aspectList.addAll(RiteInit.getRiteForResLoc(tile.currentRite).getAspectsRequired());
                    for (int i = 0; i < aspectList.size(); i++) {
                        mc.ingameGUI.drawString(mc.fontRenderer,"-" + aspectList.get(i).getLocalizedName(), event.getResolution().getScaledWidth() - 100, 15 + i * 10, 16777215);
                    }
                }
            }
        }
    }


}
