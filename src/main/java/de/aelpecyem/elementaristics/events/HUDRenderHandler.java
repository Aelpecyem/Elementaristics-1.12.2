package de.aelpecyem.elementaristics.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.ProtoplasmTaskInit;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs.ProtoplasmGoToTask;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs.ProtoplasmTask;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemChannelingTool;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemEyeSplendor;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.MiscUtil;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HUDRenderHandler {
    public static final ResourceLocation TEXTURE = new ResourceLocation("elementaristics:textures/gui/hud_elements.png");
    private int progressPercentage;


    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && !event.isCancelable()) {
            renderMaganBar(event);
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && !event.isCancelable()) {
            renderSpellSelected(event);
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT && !event.isCancelable()) {
            renderSlimeTasks(event);
            renderRiteHud(event);
            renderEnergyHud(event);
            renderDeityHud(event);
        }

        if (event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE && !event.isCancelable()) {
            renderVision(event);
        }
    }


    public void renderVision(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = mc.player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.isVisionActive()) {
                ResourceLocation tex = new ResourceLocation(cap.getVision());
                int posX = event.getResolution().getScaledWidth() / 2 - 256 / 2;
                int poxY = event.getResolution().getScaledHeight() / 2 - 256 / 2;
                float alpha = (0.5F - Math.abs(0.5F - cap.getVisionProgression())) * 1.8F;
                drawColoredTexturedModalRect(posX, poxY, 0, 0, 256, 256, Color.WHITE, alpha, tex);
            }
        }
    }

    public void renderSlimeTasks(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
        if (heldItem.getItem() instanceof ItemThaumagral && ((ItemThaumagral) heldItem.getItem()).isTuned(heldItem)) {

            if (!mc.isGamePaused()) {
                particlesForSlimeTasks(player, heldItem, (ItemThaumagral) heldItem.getItem());
            }
            mc.ingameGUI.drawString(mc.fontRenderer, ChatFormatting.BOLD + I18n.format("hud.current_tasks.name") + ChatFormatting.RESET, 5, 20, 3045026);

            List<ProtoplasmTask> taskList = ProtoplasmTaskInit.getTasksFromString(((ItemThaumagral) heldItem.getItem()).getTaskString(heldItem));
            for (int i = 0; i < taskList.size(); i++) {
                mc.ingameGUI.drawString(mc.fontRenderer, taskList.get(i).getHudDescription(), 5, 30 + i * 10, 3045026);
            }

            mc.ingameGUI.drawString(mc.fontRenderer, ChatFormatting.BOLD + I18n.format("hud.mode.name") + ChatFormatting.RESET + " " + I18n.format("hud." + ((ItemThaumagral) heldItem.getItem()).getModeName(heldItem) + ""), 5, 5, 7304866);
            return;
        }

    }

    public void particlesForSlimeTasks(EntityPlayer player, ItemStack heldItem, ItemThaumagral item) {
        List<ProtoplasmTask> tasks = ProtoplasmTaskInit.getTasksFromString(item.getTaskString(heldItem));
        if (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {
                ProtoplasmTask task = tasks.get(i);
                ProtoplasmTask prevTask = null;
                ProtoplasmTask nextTask = null;
                if (i < tasks.size() - 1) {
                    nextTask = tasks.get(i + 1);
                } else if (tasks.size() > 1) {
                    nextTask = tasks.get(0);
                }
                if (i > 0) {
                    prevTask = tasks.get(i - 1);
                } else if (tasks.size() > 1) {
                    prevTask = tasks.get(tasks.size() - 1);
                }
                task.getParticles(i, tasks, prevTask, nextTask, player.world, player, heldItem, item);

                List<ProtoplasmTask> goToTasks = tasks.stream().filter(protoplasmTask -> protoplasmTask instanceof ProtoplasmGoToTask).collect(Collectors.toList());
                if (goToTasks.size() > 1) {
                    for (int j = 0; j < goToTasks.size(); j++) {
                        ProtoplasmGoToTask disPos = (ProtoplasmGoToTask) goToTasks.get(j);
                        ProtoplasmGoToTask nextPos = (ProtoplasmGoToTask) goToTasks.get(j < goToTasks.size() - 1 ? j + 1 : 0);
                        ParticleGeneric particles = new ParticleGeneric(player.world, disPos.getPosTo().getX() + 0.5 + (player.world.rand.nextGaussian() / 8), disPos.getPosTo().getY() + 0.5 + (player.world.rand.nextGaussian() / 8), disPos.getPosTo().getZ() + 0.5 + (player.world.rand.nextGaussian() / 8),
                                0, 0, 0, nextPos.getPosTo().getDistance(disPos.getPosTo().getX(), disPos.getPosTo().getY(), disPos.getPosTo().getZ()) > 32 ? 3093050 : 16740608, player.world.rand.nextFloat() + 0.1F, (int) (nextPos.getPosTo().getDistance(disPos.getPosTo().getX(), disPos.getPosTo().getY(), disPos.getPosTo().getZ()) * 30), 0, false, false, true, true,
                                nextPos.getPosTo().getX() + 0.5F + ((float) player.world.rand.nextGaussian() / 8F), nextPos.getPosTo().getY() + 0.5F + ((float) player.world.rand.nextGaussian() / 8F), nextPos.getPosTo().getZ() + 0.5F + ((float) player.world.rand.nextGaussian() / 8F));
                        Elementaristics.proxy.generateGenericParticles(particles);
                    }
                }
            }
        }


    }

    public void renderSpellSelected(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!(heldItem.getItem() instanceof ItemThaumagral)) {
            heldItem = player.getHeldItem(EnumHand.OFF_HAND);

        }
        if (heldItem.getItem() instanceof ItemThaumagral && !((ItemThaumagral) heldItem.getItem()).isTuned(heldItem)) {
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                List<SpellBase> spells = cap.getSoul().getSpellList();
                for (int i = 0; i < spells.size(); i++) {
                    SpellBase spellIn = spells.get(i);
                    ResourceLocation TEX = new ResourceLocation(Elementaristics.MODID, "textures/spells/" + "spells" + ".png");
                    int posX = event.getResolution().getScaledWidth() / 2 - 88; // + 10;
                    int poxY = event.getResolution().getScaledHeight() - 19;
                    if (cap.getSoul().isSpellUsable(spellIn, cap))
                        drawColoredTexturedModalRect(posX + (i * 20), poxY, 16 * spellIn.getIndexX(), 16 * spellIn.getIndexY(), 16, 16, i == cap.getSpellSlot() ? MiscUtil.convertIntToColor(spellIn.getColor()) : MiscUtil.blend(MiscUtil.convertIntToColor(spellIn.getColor()), Color.DARK_GRAY, 0.4, 0.6), i == cap.getSpellSlot() ? 0.8F : 0.4F, TEX);

                }
                if (cap.getCurrentSpell() != null) {
                    int posX = 5;// ((int) Math.round(I18n.format(cap.getCurrentSpell().getName().toString()).length() * 1.5)) - 50;
                    int posY = event.getResolution().getScaledHeight() - 19;
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format(cap.getCurrentSpell().getName().toString()), posX, posY, cap.getCurrentSpell().getColor());
                }

            }
        }
    }

    public void renderRiteHud(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (PlayerUtil.getEntityLookingAt() instanceof EntityDimensionalNexus) {
            EntityDimensionalNexus nexus = (EntityDimensionalNexus) PlayerUtil.getEntityLookingAt();
            if (nexus.getRite() != null) {
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite." + nexus.getRiteString() + ".name"), 5, 5, 16777215);
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.item_power.name") + " " + nexus.getItemPowerInArea(false) + " /  " + nexus.getRite().getItemPowerRequired(), 5, 15, 16777215);
                if (!((float) nexus.getRiteTicks() / nexus.getRite().getTicksRequired() > 1)) {
                    progressPercentage = Math.round((float) nexus.getRiteTicks() / nexus.getRite().getTicksRequired() * 100);
                    String progress = String.valueOf(progressPercentage);
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress.name") + " " + progress + " %", 5, 25, 16777215);
                } else {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress_ready.name"), 5, 25, 16777215);
                }
                if (!nexus.getAspectsInArea(false).isEmpty()) {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects.name"), 5, 35, 16777215);
                    List<Aspect> aspectList = new ArrayList<>();
                    aspectList.addAll(nexus.getAspectsInArea(false));
                    for (int i = 0; i < aspectList.size(); i++) {
                        mc.ingameGUI.drawString(mc.fontRenderer, "-" + aspectList.get(i).getLocalizedName(), 5, 45 + i * 10, 16777215);
                    }
                }
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects_needed.name"), event.getResolution().getScaledWidth() - 100, 5, 16777215);
                List<Aspect> aspectList = new ArrayList<>();
                aspectList.addAll(nexus.getRite().getAspectsRequired());
                for (int i = 0; i < aspectList.size(); i++) {
                    mc.ingameGUI.drawString(mc.fontRenderer, "-" + aspectList.get(i).getLocalizedName(), event.getResolution().getScaledWidth() - 100, 15 + i * 10, 16777215);
                }
                return;
            }
        }
    }

    public void renderEnergyHud(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemChannelingTool || mc.player.getHeldItemOffhand().getItem() instanceof ItemChannelingTool) {
            if (mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) != null &&
                    mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) instanceof TileEntityEnergy) {
                TileEntityEnergy tile = (TileEntityEnergy) mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5));
                //render whether it drains or gets drained etc.
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("hud.energy_current") + " " + tile.storage.getEnergyStored() + " / " + tile.storage.getMaxEnergyStored(), 5, 5, Aspects.electricity.getColor());
                if (tile.posBound != null)
                    if (!(tile.posBound.getZ() == tile.getPos().getZ() && tile.posBound.getY() == tile.getPos().getY() && tile.posBound.getX() == tile.getPos().getX()))
                        mc.ingameGUI.drawString(mc.fontRenderer, (tile.receives ? I18n.format("hud.energy_from") : I18n.format("hud.energy_to")) + " X: " + tile.getPositionBoundTo().getX() + " Y: " + tile.getPositionBoundTo().getY() + " Z: " + tile.getPositionBoundTo().getZ() + " " + (tile.posBound.getDistance(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ()) < 16 ? " " : I18n.format("hud.too_far")), 5, 40, tile.posBound.getDistance(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ()) < 16 ? Aspects.electricity.getColor() : 13107200);
            } else if (mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) != null &&
                    mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) instanceof IHasBoundPosition) {
                TileEntity tile = mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5));
                if (((IHasBoundPosition) tile).getPositionBoundTo() != null) {
                    if (!(((IHasBoundPosition) tile).getPositionBoundTo().getZ() == tile.getPos().getZ() && ((IHasBoundPosition) tile).getPositionBoundTo().getY() == tile.getPos().getY() && ((IHasBoundPosition) tile).getPositionBoundTo().getX() == tile.getPos().getX()))
                        mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("hud.pos_to") + " X: " + ((IHasBoundPosition) tile).getPositionBoundTo().getX() + " Y: " + ((IHasBoundPosition) tile).getPositionBoundTo().getY() + " Z: " + ((IHasBoundPosition) tile).getPositionBoundTo().getZ(), 5, 40, Aspects.magan.getColor());
                }
            }
        }
    }

    public void renderDeityHud(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEyeSplendor || mc.player.getHeldItemOffhand().getItem() instanceof ItemEyeSplendor) {
            if (mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) != null &&
                    mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) instanceof TileEntityDeityShrine) {
                TileEntityDeityShrine tile = (TileEntityDeityShrine) mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5));
                if (tile.deityBound != null || !tile.deityBound.equals(""))
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("hud.god") + " " + I18n.format(tile.deityBound), 5, 50, Aspects.light.getColor());
                if (tile.getNexusBound() != null)
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("hud.nexus_to") + " X: " + tile.getNexusBound().getPosition().getX() + " Y: " + tile.getNexusBound().getPosition().getY() + " Z: " + tile.getNexusBound().getPosition().getZ(), 5, 60, Aspects.light.getColor());
            }
        }
    }

    public void renderMaganBar(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (Config.client.showBar && !mc.player.capabilities.isCreativeMode && !mc.player.isSpectator()) {
            if (mc.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities caps = mc.player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                if (caps.knowsSoul()) {
                    int posX = event.getResolution().getScaledWidth() / 2 - 93; // + 10;
                    int poxY = event.getResolution().getScaledHeight() - 31;
                    float mult = caps.getMagan() / caps.getMaxMagan();
                    Color color = MiscUtil.convertIntToColor(SoulInit.getSoulFromId(caps.getSoulId()).getParticleColor());
                    if (caps.getTimeStunted() > 0) {
                        color = MiscUtil.blend(color, Color.gray, 1 - Math.min(0.1 * (float) caps.getTimeStunted() / 10F, 0.8), Math.min(0.1 * (float) caps.getTimeStunted() / 10F, 0.8));
                    }
                    //   mc.renderEngine.bindTexture(TEXTURE);
                    drawColoredTexturedModalRect(posX, poxY, 0, 0, Math.round(186 * mult), 9, color, 1, TEXTURE);
                }
            }
        }
    }

    public void drawColoredTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, Color color, float alpha, ResourceLocation resTex) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        float zLevel = -90.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos((double) x, (double) (y + height), (double) zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        bufferbuilder.pos((double) (x + width), (double) y, (double) zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        bufferbuilder.pos((double) x, (double) y, (double) zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resTex);
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        //Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }


}
