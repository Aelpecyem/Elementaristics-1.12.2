package de.aelpecyem.elementaristics.items.base.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketPressSpace;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemKeyWinged extends ItemBase implements IBauble {
    protected final String CHARGE_KEY = "charge";

    public ItemKeyWinged() {
        super("key_winged");
        maxStackSize = 1;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    //TODO drain magan on use
    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.TRINKET;
    }

    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntityLiving(), ModItems.key_winged) > -1) {
                event.setDamageMultiplier(0.5F);
            }
        }
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        player.fallDistance *= 0.8F;
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(CHARGE_KEY) && player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            if (itemstack.getTagCompound().getFloat(CHARGE_KEY) < 10 && player.onGround) {
                itemstack.getTagCompound().setFloat(CHARGE_KEY, 10);
            }
            if (itemstack.getTagCompound().getFloat(CHARGE_KEY) > 0.1F) {
                if (player.world.isRemote) {
                    if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) && Minecraft.getMinecraft().currentScreen == null) {
                        if (player instanceof EntityPlayer)
                            PacketHandler.sendToAll(new PacketPressSpace((EntityPlayer) player));
                        Elementaristics.proxy.generateGenericParticles(player, Aspects.air.getColor(), 1, 40, 0, true, true);
                    }
                }
            }
        } else {
            if (!itemstack.hasTagCompound())
                itemstack.setTagCompound(new NBTTagCompound());
            itemstack.getTagCompound().setInteger(CHARGE_KEY, 0);
        }
    }

}
