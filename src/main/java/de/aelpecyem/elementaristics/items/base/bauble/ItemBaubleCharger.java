package de.aelpecyem.elementaristics.items.base.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.items.base.ItemBase;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class ItemBaubleCharger extends ItemBase implements IBauble {
    protected final String X_KEY = "posX";
    protected final String Y_KEY = "posY";
    protected final String Z_KEY = "posZ";
    protected final String CHARGE_KEY = "charge";

    public ItemBaubleCharger() {
        super("charger_soul");
        maxStackSize = 1;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(CHARGE_KEY)) {
            tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.charge") + " " + stack.getTagCompound().getInteger(CHARGE_KEY));
            tooltip.add(" ");
        }
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(X_KEY)) {
            tooltip.add(I18n.format("tooltip.posX") + " " + stack.getTagCompound().getInteger(X_KEY));
            tooltip.add(I18n.format("tooltip.posY") + " " + stack.getTagCompound().getInteger(Y_KEY));
            tooltip.add(I18n.format("tooltip.posZ") + " " + stack.getTagCompound().getInteger(Z_KEY));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (player.isSneaking()) {
            if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, null)) {
                stack.getTagCompound().setInteger(X_KEY, pos.getX());
                stack.getTagCompound().setInteger(Y_KEY, pos.getY());
                stack.getTagCompound().setInteger(Z_KEY, pos.getZ());
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.CHARM;
    }


    public int drainTileEntity(World world, BlockPos pos) {
        if (world.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = world.getTileEntity(pos).getCapability(CapabilityEnergy.ENERGY, null);
            return storage.extractEnergy(50, false);
        }
        return 0;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (!player.world.isRemote) {
            if (itemstack.hasTagCompound()) {
                if (!itemstack.getTagCompound().hasKey(CHARGE_KEY)) {
                    itemstack.getTagCompound().setInteger(CHARGE_KEY, 0);
                }
                if (player.getPosition().getDistance(itemstack.getTagCompound().getInteger(X_KEY), itemstack.getTagCompound().getInteger(Y_KEY), itemstack.getTagCompound().getInteger(Z_KEY)) < 20) {
                    if (itemstack.getTagCompound().hasKey(CHARGE_KEY) && itemstack.getTagCompound().getInteger(CHARGE_KEY) < 10000) {
                        itemstack.getTagCompound().setInteger(CHARGE_KEY, itemstack.getTagCompound().getInteger(CHARGE_KEY) + drainTileEntity(player.world, new BlockPos(itemstack.getTagCompound().getInteger(X_KEY), itemstack.getTagCompound().getInteger(Y_KEY), itemstack.getTagCompound().getInteger(Z_KEY))));
                    } else {
                        itemstack.getTagCompound().setInteger(CHARGE_KEY, 10000);
                    }
                }
                if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                    IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                    if (cap.getTimeStunted() <= 0 && cap.getMagan() < cap.getMaxMagan()) {
                        if (itemstack.getTagCompound().getInteger(CHARGE_KEY) >= 5) {
                            itemstack.getTagCompound().setInteger(CHARGE_KEY, itemstack.getTagCompound().getInteger(CHARGE_KEY) - 50);
                            cap.fillMagan(MaganUtil.convertOccultEnergyToMagan(50));
                        }else{
                            cap.fillMagan(MaganUtil.convertOccultEnergyToMagan(itemstack.getTagCompound().getInteger(CHARGE_KEY)));
                            itemstack.getTagCompound().setInteger(CHARGE_KEY, 0);
                        }
                    }
                }
            }
        }
    }

}
