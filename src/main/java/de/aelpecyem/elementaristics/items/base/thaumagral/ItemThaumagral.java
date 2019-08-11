package de.aelpecyem.elementaristics.items.base.thaumagral;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.ProtoplasmTaskInit;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs.ProtoplasmTask;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import de.aelpecyem.elementaristics.util.IHasModel;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.List;

public class ItemThaumagral extends ItemSword implements IHasModel {
    protected String name;
    float castingEfficiency;
    float cooldown;

    public static final String SAVEDTASK = "savedtask";
    public static final String MODE = "mode";
    public static final String WIPTASK = "wiptask";
    public static final String TUNED = "tuned";

    public ItemThaumagral(String name, ToolMaterial material, float castingEfficiency, float cooldownReduction) { //ThaumagralMaterial
        super(material);
        this.name = name;
        this.castingEfficiency = castingEfficiency;
        this.cooldown = cooldownReduction;
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);

        MinecraftForge.EVENT_BUS.register(this);
        ModItems.ITEMS.add(this);
    }

    public boolean isTuned(ItemStack stack){
        isReady(stack);
        return stack.getTagCompound().getBoolean(TUNED);
    }

    public void setTuned(ItemStack stack, boolean tuned){
        isReady(stack);
        stack.getTagCompound().setBoolean(TUNED, tuned);
    }

    public String getTaskString(ItemStack stack){
        isReady(stack);
        return stack.getTagCompound().getString(SAVEDTASK);
    }

    public void setTaskString(ItemStack stack, String task){
        isReady(stack);
        stack.getTagCompound().setString(SAVEDTASK, task);
    }

    public int getMode(ItemStack stack){
        isReady(stack);
        return stack.getTagCompound().getInteger(MODE);
    }

    public void setMode(ItemStack stack, int mode){
        isReady(stack);
        stack.getTagCompound().setInteger(MODE, mode);
    }

    public String getWIPTask(ItemStack stack){
        isReady(stack);
        return stack.getTagCompound().getString(WIPTASK);
    }

    public void setWIPTask(ItemStack stack, String task){
        isReady(stack);
        stack.getTagCompound().setString(WIPTASK, task);
    }


    public boolean appendTask(ItemStack stack, ProtoplasmTask task, EntityPlayer player){ //check this and send a message if it fails
        isReady(stack);
        if (ProtoplasmTaskInit.getTasksFromString(getTaskString(stack)).size() < 10) {
            stack.getTagCompound().setString(SAVEDTASK, stack.getTagCompound().getString(SAVEDTASK) + task.writeAsString());
            if (!player.world.isRemote){
                PacketHandler.sendTo(player, new PacketMessage("message.task_added"));
            }
            return true;

        }
        if (!player.world.isRemote){
            PacketHandler.sendTo(player, new PacketMessage("message.too_many_tasks"));
        }
        return false;
    }

    public boolean appendTask(ItemStack stack, String task, EntityPlayer player){
        isReady(stack);
        if (ProtoplasmTaskInit.getTasksFromString(getTaskString(stack)).size() < 10) {
            stack.getTagCompound().setString(SAVEDTASK, stack.getTagCompound().getString(SAVEDTASK) + task);
            return true;
        }
        return false;
    }

    public void cycleMode(ItemStack stack){
        isReady(stack);
        setMode(stack, getMode(stack) < ProtoplasmTaskInit.taskMap.size() - 1 ? getMode(stack) + 1 : 0);
    }

    public String getModeName(ItemStack stack){
        isReady(stack);
        return (String) ProtoplasmTaskInit.taskMap.keySet().toArray()[getMode(stack)];
    }

    public boolean isReady(ItemStack stack){
        if (stack.hasTagCompound()){
            if (!stack.getTagCompound().hasKey(SAVEDTASK)){
                stack.getTagCompound().setString(SAVEDTASK, "");
            }
            if (!stack.getTagCompound().hasKey(MODE)){
                stack.getTagCompound().setInteger(MODE, 0);
            }
            if (!stack.getTagCompound().hasKey(WIPTASK)){
                stack.getTagCompound().setString(WIPTASK, "");
            }
            if (!stack.getTagCompound().hasKey(TUNED)){
                stack.getTagCompound().setBoolean(TUNED, false);
            }
        }else{
            stack.setTagCompound(new NBTTagCompound());
            isReady(stack);
        }
        return true;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return false;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (isTuned(stack)) {
            if (!entityLiving.isSneaking()) {
                cycleMode(stack);
                return true;
            } else if (entityLiving instanceof EntityPlayer) {
                setTaskString(stack, "");
                setTuned(stack, false);
                if (!entityLiving.world.isRemote) {
                    PacketHandler.sendTo((EntityPlayer) entityLiving, new PacketMessage("message.reset_tasks"));
                }
                return true;
            }
        }

        return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (isTuned(stack)) {
            tooltip.add(I18n.format("hud.mode.name") + " " + I18n.format("hud." + getModeName(stack)));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntityProtoplasm && ((EntityProtoplasm) target).isOwner(playerIn)){ //also check soemthing else i forgot, probably if it's tamed lol
            if (isTuned(stack)) {
                if (playerIn.isSneaking()){
                    ((EntityProtoplasm) target).setTaskString("");
                    ((EntityProtoplasm) target).setTaskStage(0);
                    if (playerIn.world.isRemote) {
                        playerIn.sendStatusMessage(new TextComponentString(I18n.format("message.slime_task_reset")), false);
                    }
                }else {
                    if (playerIn.world.isRemote) {
                        playerIn.sendStatusMessage(new TextComponentString(I18n.format(getTaskString(stack).isEmpty() ? "message.slime_task_reset" : "message.slime_task_set")), false);
                    }
                    if (!((EntityProtoplasm) target).world.isRemote) {
                        ((EntityProtoplasm) target).setTaskString(getTaskString(stack));
                        ((EntityProtoplasm) target).setTaskStage(0);
                        ((EntityProtoplasm) target).getAISit().setSitting(false);
                        setTuned(stack, false);
                    }
                }
            }else{
                setTuned(stack, true);
            }
            return true;
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityProtoplasm && ((EntityProtoplasm) entity).isOwner(player)) {
            setTuned(stack, true);
            if (((EntityProtoplasm) entity).getTaskString() != "" && !((EntityProtoplasm) entity).getTaskString().equals(getTaskString(stack))){
                setTaskString(stack, ((EntityProtoplasm) entity).getTaskString());
                if (player.world.isRemote){
                   player.sendStatusMessage(new TextComponentString(ChatFormatting.LIGHT_PURPLE + I18n.format("message.slime_task_copied")), false);
                }
            }
            return true;
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (isTuned(stack)) {
            if (ProtoplasmTaskInit.getTaskByName(getModeName(stack)) != null) {
                ProtoplasmTaskInit.getTaskByName(getModeName(stack)).setTask(stack, this, player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isTuned(stack)) {
            if (ProtoplasmTaskInit.getTaskByName(getModeName(stack)) != null) {
                ProtoplasmTaskInit.getTaskByName(getModeName(stack)).setTask(stack, this, worldIn, playerIn, handIn);
            }
        }else {
            if (playerIn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                playerIn.setActiveHand(handIn);
                IPlayerCapabilities cap = playerIn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                SpellBase spellBase = cap.getCurrentSpell();

                if (spellBase != null && cap.getSoul().isSpellUsable(spellBase, cap)) {
                    if (cap.getMagan() >= (spellBase.getMaganCost() / SoulInit.getSoulFromId(cap.getSoulId()).getCastingEfficiency()) / castingEfficiency) {
                        MaganUtil.drainMaganFromPlayer(playerIn, spellBase.getMaganCost() / SoulInit.getSoulFromId(cap.getSoulId()).getCastingEfficiency() / castingEfficiency, spellBase.getStuntTime(), true);

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
                            if (!worldIn.isRemote)
                                worldIn.spawnEntity(spellProjectile);
                        }
                        if (spellBase.getType() == SpellBase.SpellType.SELF) {
                            spellBase.affect(PlayerUtil.rayTrace(playerIn, 30, 1), playerIn, worldIn, null);

                        }
                        if (spellBase.getType() == SpellBase.SpellType.WAVE) {
                            for (int i = -50; i < 50; i += 5) {
                                EntitySpellProjectile spellProjectile = new EntitySpellProjectile(worldIn, playerIn);
                                spellProjectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw + i, 0.0F, 0.5F * 3.0F, 1, spellBase);
                                spellProjectile.setSpell(spellBase);
                                if (!worldIn.isRemote)
                                    worldIn.spawnEntity(spellProjectile);
                            }
                        }

                        if (spellBase.getType() != SpellBase.SpellType.EDEMA)
                            playerIn.getCooldownTracker().setCooldown(this, Math.round(spellBase.getCooldownTicks() / cooldown));
                        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                    }
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }
}
