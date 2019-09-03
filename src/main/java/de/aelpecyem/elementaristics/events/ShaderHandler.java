package de.aelpecyem.elementaristics.events;

import com.google.gson.JsonSyntaxException;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.client.lib.events.RenderEventHandler;

import java.io.IOException;

public class ShaderHandler {
    public static final int SHADER_DRUGS = 0;
    public static final int SHADER_TRANCE = 1;
    public static final int SHADER_FOCUSED = 2;

    public static ResourceLocation[] shader_resources = new ResourceLocation[]{new ResourceLocation("shaders/post/intoxicated.json"), new ResourceLocation("shaders/post/trance.json"), new ResourceLocation("shaders/post/fxaa.json")};

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void doRender(TickEvent.PlayerTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.side == Side.CLIENT) {
            if (event.phase == TickEvent.Phase.START) {
                handleShaders(event, mc);
            }
        }
    }

    public void handleShaders(TickEvent.PlayerTickEvent event, Minecraft mc) {
        handleShader(event.player.isPotionActive(PotionInit.potionIntoxicated), SHADER_DRUGS);
        handleShader(event.player.isPotionActive(PotionInit.potionIntoxicated) && event.player.getActivePotionEffect(PotionInit.potionIntoxicated).getAmplifier() >= 1, SHADER_TRANCE);
        handleShader(event.player.isPotionActive(PotionInit.potionTrance), SHADER_TRANCE);
        handleShader(event.player.isPotionActive(PotionInit.potionFocused), SHADER_FOCUSED);
    }

    public static void handleShader(boolean condition, int shaderId) {
        if (condition) {
            if (!RenderEventHandler.shaderGroups.containsKey(shaderId)) {
                setShader(shader_resources[shaderId], shaderId);
            }
        } else if (RenderEventHandler.shaderGroups.containsKey(shaderId)) {
            deactivateShader(shaderId);
        }
    }

    public static void setShader(ResourceLocation shader, int shaderId) {
        try {
            ShaderGroup target = new ShaderGroup(Minecraft.getMinecraft().getTextureManager(), Minecraft.getMinecraft().getResourceManager(), Minecraft.getMinecraft().getFramebuffer(), shader_resources[shaderId]);
            if (OpenGlHelper.areShadersSupported()) {
                if (RenderEventHandler.shaderGroups.containsKey(shaderId)) {
                    ((ShaderGroup) RenderEventHandler.shaderGroups.get(shaderId)).deleteShaderGroup();//..func_148021_a();
                    RenderEventHandler.shaderGroups.remove(shaderId);
                }

                try {
                    if (target == null) {
                        deactivateShader(shaderId);
                    } else {
                        RenderEventHandler.resetShaders = true;
                        RenderEventHandler.shaderGroups.put(shaderId, target);
                    }
                } catch (Exception var4) {
                    RenderEventHandler.shaderGroups.remove(shaderId);
                }
            }
        } catch (JsonSyntaxException exception) {
            Elementaristics.LOGGER.fatal("An incorrect shader file syntax was detected!");
            exception.printStackTrace();
        } catch (IOException exception) {
            Elementaristics.LOGGER.fatal("Shader file was not found!");
            exception.printStackTrace();
        }
    }

    public static void deactivateShader(int shaderId) {
        if (RenderEventHandler.shaderGroups.containsKey(shaderId)) {
            ((ShaderGroup) RenderEventHandler.shaderGroups.get(shaderId)).deleteShaderGroup();//.func_148021_a();
        }

        RenderEventHandler.shaderGroups.remove(shaderId);
    }
}
