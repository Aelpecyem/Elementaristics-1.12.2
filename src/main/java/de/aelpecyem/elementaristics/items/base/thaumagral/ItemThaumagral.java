package de.aelpecyem.elementaristics.items.base.thaumagral;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntitySpellProjectile;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;
import de.aelpecyem.elementaristics.util.IHasModel;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemThaumagral extends ItemSword implements IHasModel {
    protected String name;
    float castingEfficiency;
    float cooldown;

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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
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
                        spellBase.affect(PlayerUtil.rayTrace(playerIn, 30, 1), playerIn, worldIn);

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
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }
}
