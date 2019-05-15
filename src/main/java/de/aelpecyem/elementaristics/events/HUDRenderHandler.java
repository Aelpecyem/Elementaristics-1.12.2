package de.aelpecyem.elementaristics.events;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import de.aelpecyem.elementaristics.misc.spell.damage.DamageSpellBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketPressSpellKey;
import de.aelpecyem.elementaristics.util.Keybinds;
import de.aelpecyem.elementaristics.util.MiscUtil;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HUDRenderHandler {
    public static final ResourceLocation TEXTURE = new ResourceLocation("elementaristics:textures/gui/hud_elements.png");


    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && !event.isCancelable()) {
            renderMaganBar(event);

        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && !event.isCancelable()) {
            renderSpellSelected(event);

        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT && !event.isCancelable()) {
            renderRiteHud(event);
            renderEnergyHud(event);

        }
    }

    public void renderSpellSelected(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!(heldItem.getItem() instanceof ItemThaumagral)) {
            heldItem = player.getHeldItem(EnumHand.OFF_HAND);

        }
        if (heldItem.getItem() instanceof ItemThaumagral) {
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                List<SpellBase> spells = cap.getSoul().getSpellList();
                for (int i = 0; i < spells.size(); i++) {
                    SpellBase spellIn = spells.get(i);
                    ResourceLocation TEX = new ResourceLocation(Elementaristics.MODID, "textures/spells/" + "spells" + ".png");

                    int posX = event.getResolution().getScaledWidth() / 2 - 88; // + 10;
                    int poxY = event.getResolution().getScaledHeight() - 19;
                    drawColoredTexturedModalRect(posX + (i * 20), poxY, 16 * spellIn.getIndexX(), 16 * spellIn.getIndexY(), 16, 16, i == cap.getSpellSlot() ? MiscUtil.convertIntToColor(spellIn.getColor()) : MiscUtil.blend(MiscUtil.convertIntToColor(spellIn.getColor()), Color.DARK_GRAY, 0.4, 0.6), i == cap.getSpellSlot() ? 0.8F : 0.4F, TEX);
                    //TODO add the nice fancy texture thingy yay
                }
                if (cap.getCurrentSpell() != null) {
                    int posX = 5;// ((int) Math.round(I18n.format(cap.getCurrentSpell().getName().toString()).length() * 1.5)) - 50;
                    int posY = event.getResolution().getScaledHeight() - 19;
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format(cap.getCurrentSpell().getName().toString()), posX, posY, cap.getCurrentSpell().getColor());
                }

            }
        }
        if (PlayerUtil.getBlockLookingAt(mc.player, 5) instanceof BlockAltar) {
            BlockAltar altar = (BlockAltar) PlayerUtil.getBlockLookingAt(mc.player, 5);
            TileEntityAltar tile = altar.getTileEntity(mc.player.world, PlayerUtil.getBlockPosLookingAt(5));
            if (RiteInit.getRiteForResLoc(tile.currentRite) != null) {
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite." + tile.currentRite + ".name"), 5, 5, 16777215);
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.item_power.name") + " " + tile.getItemPowerInArea() + " /  " + RiteInit.getRiteForResLoc(tile.currentRite).getItemPowerRequired(), 5, 15, 16777215);
                if (!(tile.tickCount * 100 / RiteInit.getRiteForResLoc(tile.currentRite).getTicksRequired() > 100)) {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress.name") + " " + tile.tickCount * 100 / RiteInit.getRiteForResLoc(tile.currentRite).getTicksRequired() + " %", 5, 25, 16777215);
                } else {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress_ready.name"), 5, 25, 16777215);
                }
                if (!tile.getAspectsInArea().isEmpty()) {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects.name"), 5, 35, 16777215);
                    List<Aspect> aspectList = new ArrayList<>();
                    aspectList.addAll(tile.getAspectsInArea());
                    for (int i = 0; i < aspectList.size(); i++) {
                        mc.ingameGUI.drawString(mc.fontRenderer, "-" + aspectList.get(i).getLocalizedName(), 5, 45 + i * 10, 16777215);
                    }
                }
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects_needed.name"), event.getResolution().getScaledWidth() - 100, 5, 16777215);
                List<Aspect> aspectList = new ArrayList<>();
                aspectList.addAll(RiteInit.getRiteForResLoc(tile.currentRite).getAspectsRequired());
                for (int i = 0; i < aspectList.size(); i++) {
                    mc.ingameGUI.drawString(mc.fontRenderer, "-" + aspectList.get(i).getLocalizedName(), event.getResolution().getScaledWidth() - 100, 15 + i * 10, 16777215);
                }
            }
        }
    }

    public void renderRiteHud(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (PlayerUtil.getBlockLookingAt(mc.player, 5) instanceof BlockAltar) {
            BlockAltar altar = (BlockAltar) PlayerUtil.getBlockLookingAt(mc.player, 5);
            TileEntityAltar tile = altar.getTileEntity(mc.player.world, PlayerUtil.getBlockPosLookingAt(5));
            if (RiteInit.getRiteForResLoc(tile.currentRite) != null) {
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite." + tile.currentRite + ".name"), 5, 5, 16777215);
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.item_power.name") + " " + tile.getItemPowerInArea() + " /  " + RiteInit.getRiteForResLoc(tile.currentRite).getItemPowerRequired(), 5, 15, 16777215);
                if (!(tile.tickCount * 100 / RiteInit.getRiteForResLoc(tile.currentRite).getTicksRequired() > 100)) {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress.name") + " " + tile.tickCount * 100 / RiteInit.getRiteForResLoc(tile.currentRite).getTicksRequired() + " %", 5, 25, 16777215);
                } else {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.progress_ready.name"), 5, 25, 16777215);
                }
                if (!tile.getAspectsInArea().isEmpty()) {
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects.name"), 5, 35, 16777215);
                    List<Aspect> aspectList = new ArrayList<>();
                    aspectList.addAll(tile.getAspectsInArea());
                    for (int i = 0; i < aspectList.size(); i++) {
                        mc.ingameGUI.drawString(mc.fontRenderer, "-" + aspectList.get(i).getLocalizedName(), 5, 45 + i * 10, 16777215);
                    }
                }
                mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("rite.aspects_needed.name"), event.getResolution().getScaledWidth() - 100, 5, 16777215);
                List<Aspect> aspectList = new ArrayList<>();
                aspectList.addAll(RiteInit.getRiteForResLoc(tile.currentRite).getAspectsRequired());
                for (int i = 0; i < aspectList.size(); i++) {
                    mc.ingameGUI.drawString(mc.fontRenderer, "-" + aspectList.get(i).getLocalizedName(), event.getResolution().getScaledWidth() - 100, 15 + i * 10, 16777215);
                }
            }
        }
    }

    public void renderEnergyHud(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) != null &&
                mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5)) instanceof TileEntityEnergy) {
            TileEntityEnergy tile = (TileEntityEnergy) mc.player.world.getTileEntity(PlayerUtil.getBlockPosLookingAt(5));
            //render whether it drains or gets drained etc.
            mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("hud.energy_current") + " " + tile.storage.getEnergyStored() + " / " + tile.storage.getMaxEnergyStored(), 5, 5, Aspects.electricity.getColor());
            if (tile.posBound != null)
                if (!(tile.posBound.getZ() == tile.getPos().getZ() && tile.posBound.getY() == tile.getPos().getY() && tile.posBound.getX() == tile.getPos().getX()))
                    mc.ingameGUI.drawString(mc.fontRenderer, (tile.receives ? I18n.format("hud.energy_from") : I18n.format("hud.energy_to")) + " X: " + tile.getPositionBoundTo().getX() + " Y: " + tile.getPositionBoundTo().getY() + " Z: " + tile.getPositionBoundTo().getZ() + " " + (tile.posBound.getDistance(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ()) < 16 ? " " : I18n.format("hud.too_far")), 5, 15, tile.posBound.getDistance(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ()) < 16 ? Aspects.electricity.getColor() : 13107200);
        }
    }

    public void renderMaganBar(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.player.capabilities.isCreativeMode && !mc.player.isSpectator()) {
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
        float zLevel = -90.0F;
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        //   bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x + 0), (double) (y + height), (double) zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + 0), (double) zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        bufferbuilder.pos((double) (x + 0), (double) (y + 0), (double) zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha).endVertex();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resTex);
        tessellator.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

}
