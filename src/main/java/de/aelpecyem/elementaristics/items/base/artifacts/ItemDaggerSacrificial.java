package de.aelpecyem.elementaristics.items.base.artifacts;


import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.base.crops.BlockMossBase;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.ModMaterials;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemDaggerSacrificial extends ItemSword implements IHasRiteUse, IHasModel {
    protected String name;

    public ItemDaggerSacrificial() {
        super(ModMaterials.MATERIAL_SWORD_ICE.setRepairItem(new ItemStack(ModItems.essence, 1, Aspects.ice.getId())));
        maxStackSize = 1;
        name = "dagger_sacrificial";

        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);

        ModItems.ITEMS.add(this);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.dagger_sacrificial.name"));

        tooltip.add(I18n.format("tooltip.aspect_tool.power") + " " + getPower());

        tooltip.add(I18n.format("tooltip.aspect_tool.aspects"));
        for (Aspect aspect : this.getAspects()) {
            tooltip.add("-" + aspect.getLocalizedName());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);

    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        target.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 2, false, false));
        if (target.getHealth() == 0) {
            for (int i = -3; i < 2; i++) {
                if (target.world.getBlockState(target.getPosition().add(0, i, 0)) == ModBlocks.mossBase.getDefaultState()) {
                    target.world.setBlockState(target.getPosition().add(0, i, 0), ModBlocks.mossEverchanging.getDefaultState());
                    target.attackEntityFrom(DamageSource.GENERIC, 1);
                    return super.hitEntity(stack, target, attacker);
                }
            }
            for (int t = 0; t < 20; t++) {
                for (int i = -3; i < 2; i++) {
                    int x = target.world.rand.nextInt(4) * (target.world.rand.nextBoolean() ? -1 : 1);
                    int z = target.world.rand.nextInt(4) * (target.world.rand.nextBoolean() ? -1 : 1);
                    if (target.world.getBlockState(target.getPosition().add(x, i, z)) == ModBlocks.mossBase.getDefaultState()) {
                        target.world.setBlockState(target.getPosition().add(x, i, z), ModBlocks.mossEverchanging.getDefaultState());
                        target.attackEntityFrom(DamageSource.GENERIC, 1);
                        return super.hitEntity(stack, target, attacker);
                    }
                }
            }

        }
        return super.hitEntity(stack, target, attacker);
    }

    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }



    @Override
    public List<Aspect> getAspects() {
        List<Aspect> aspects = new ArrayList<>();
        aspects.add(Aspects.ice);
        return aspects;
    }

    @Override
    public int getPower() {
        return 6;
    }

    @Override
    public boolean isConsumed() {
        return false;
    }
}
