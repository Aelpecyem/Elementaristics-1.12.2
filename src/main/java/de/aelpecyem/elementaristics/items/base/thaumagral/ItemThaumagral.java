package de.aelpecyem.elementaristics.items.base.thaumagral;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import de.aelpecyem.elementaristics.util.IHasModel;
import de.aelpecyem.elementaristics.util.Keybinds;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ItemThaumagral extends ItemSword implements IHasModel {
    protected String name;
    float magicDmg, voidDmg;

    public ItemThaumagral(String name, ToolMaterial material, float magicDmg, float voidDmg) { //ThaumagralMaterial
        super(material);
        this.name = name;
        this.magicDmg = magicDmg;
        this.voidDmg = voidDmg;
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);
        MinecraftForge.EVENT_BUS.register(this);
        ModItems.ITEMS.add(this);
    }

    @SubscribeEvent
    public void changeSelectedSpell(TickEvent.PlayerTickEvent e) {
        // for (EnumHand hand = EnumHand.MAIN_HAND; hand == EnumHand.OFF_HAND; hand = EnumHand.OFF_HAND){
        if (e.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemThaumagral) {
            if (e.player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = e.player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                ItemStack stack = e.player.getHeldItem(EnumHand.MAIN_HAND);
                if (!e.player.world.isRemote) {
                    if (Keybinds.spellUp.isPressed()) {
                        if (getSpellSelected(createTagCompound(stack)) < SoulInit.getSoulFromId(cap.getSoulId()).getSpellList().size() - 1) {
                            setSpellSelected(createTagCompound(stack), getSpellSelected(createTagCompound(stack)) + 1);
                        } else {
                            setSpellSelected(createTagCompound(stack), 0);
                        }


                    } else if (Keybinds.spellDown.isPressed()) {
                        if (getSpellSelected(createTagCompound(stack)) > 0) {
                            setSpellSelected(createTagCompound(stack), getSpellSelected(createTagCompound(stack)) - 1);
                        } else {
                            setSpellSelected(createTagCompound(stack), SoulInit.getSoulFromId(cap.getSoulId()).getSpellList().size() - 1);
                        }

                    }
                }
            }
        }
        // }
    }

    public void setSpellSelected(NBTTagCompound compound, int spell) {
        compound.setInteger("currentSpell", spell);
    }

    public int getSpellSelected(NBTTagCompound compound) {
        if (!compound.hasKey("currentSpell")) {
            compound.setInteger("currentSpell", 0);
        }
        return compound.getInteger("currentSpell");
    }

    public NBTTagCompound createTagCompound(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    public SpellBase getCurrentSpell(ItemStack stack) {
        SpellBase spellBase = null;
        if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
            try {
                spellBase = SoulInit.getSoulFromId(Minecraft.getMinecraft().getRenderViewEntity().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId()).getSpellList().get(getSpellSelected(createTagCompound(stack)));
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return spellBase;
    }

    public SpellBase getSpellForSlot(ItemStack stack, int slot) {
        SpellBase spellBase = null;
        if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
            try {
                spellBase = SoulInit.getSoulFromId(Minecraft.getMinecraft().getRenderViewEntity().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getSoulId()).getSpellList().get(slot);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return spellBase;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (getCurrentSpell(stack) != null) {
            if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
                IPlayerCapabilities cap = Minecraft.getMinecraft().getRenderViewEntity().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                if (SoulInit.getSoulFromId(cap.getSoulId()).isSpellUsable(getCurrentSpell(stack), cap)) {
                    return super.getItemStackDisplayName(stack) + " (" + TextFormatting.GREEN + net.minecraft.util.text.translation.I18n.translateToLocal(getCurrentSpell(stack).getName().toString()) + ")";
                }
                return super.getItemStackDisplayName(stack) + " (" + TextFormatting.GRAY + net.minecraft.util.text.translation.I18n.translateToLocal("display.spell_locked") + ")";
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (getCurrentSpell(stack) != null)
            tooltip.add("Current Spell: " + I18n.format(getCurrentSpell(stack).getName().toString()));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            playerIn.setActiveHand(handIn);
            IPlayerCapabilities cap = playerIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            SpellBase spellBase = null;
            try {
                spellBase = SoulInit.getSoulFromId(cap.getSoulId()).getSpellList().get(getSpellSelected(createTagCompound(playerIn.getHeldItem(handIn))));
            } catch (IndexOutOfBoundsException e) {
            }
            if (spellBase != null && SoulInit.getSoulFromId(cap.getSoulId()).isSpellUsable(spellBase, cap)) {
                if (cap.getMagan() >= spellBase.getMaganCost() / SoulInit.getSoulFromId(cap.getSoulId()).getCastingEfficiency()) {
                    MaganUtil.drainMaganFromPlayer(playerIn, spellBase.getMaganCost() / SoulInit.getSoulFromId(cap.getSoulId()).getCastingEfficiency(), spellBase.getStuntTime(), true);
                    if (spellBase.getType() == SpellBase.SpellType.EDEMA) {
                        for (int i = 0; i < 10; i++) {
                            EntitySpellProjectile spellProjectile = new EntitySpellProjectile(worldIn, playerIn);
                            spellProjectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F * 3.0F, 17.0F, spellBase);
                            spellProjectile.setSpell(spellBase);
                            spellProjectile.setCaster(playerIn);

                            if (!worldIn.isRemote)
                                worldIn.spawnEntity(spellProjectile);
                        }
                    }
                    if (spellBase.getType() == SpellBase.SpellType.PROJECTILE) {
                        EntitySpellProjectile spellProjectile = new EntitySpellProjectile(worldIn, playerIn);
                        spellProjectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F * 3.0F, 1.0F);
                        spellProjectile.setSpell(spellBase);
                        spellProjectile.setCaster(playerIn);

                        if (!worldIn.isRemote)
                            worldIn.spawnEntity(spellProjectile);
                    }
                    if (spellBase.getType() == SpellBase.SpellType.SELF) {
                        spellBase.affect(playerIn.rayTrace(30, 1), playerIn, worldIn);

                    }
                    if (spellBase.getType() == SpellBase.SpellType.WAVE) {
                        for (int i = -50; i < 50; i += 5) {
                            EntitySpellProjectile spellProjectile = new EntitySpellProjectile(worldIn, playerIn);
                            spellProjectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw + i, 0.0F, 0.5F * 3.0F, 1, spellBase);
                            spellProjectile.setSpell(spellBase);
                            spellProjectile.setCaster(playerIn);

                            if (!worldIn.isRemote)
                                worldIn.spawnEntity(spellProjectile);
                        }
                    }

                    if (spellBase.getType() != SpellBase.SpellType.EDEMA)
                        playerIn.getCooldownTracker().setCooldown(this, spellBase.getCooldownTicks());
                    return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }
}
