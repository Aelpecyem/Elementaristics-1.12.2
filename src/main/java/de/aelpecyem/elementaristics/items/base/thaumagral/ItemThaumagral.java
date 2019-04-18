package de.aelpecyem.elementaristics.items.base.thaumagral;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

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

        ModItems.ITEMS.add(this);
    }
    //gonna redo this with customization later on, though not for now
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        //deal void dmg with ancient soul? a bit of magic dmg with mana soul
            if (!attacker.isSwingInProgress) { //search for full-swing value
                target.attackEntityFrom(DamageSource.MAGIC, magicDmg);
                target.attackEntityFrom(DamageSource.OUT_OF_WORLD, voidDmg);
            }
        return super.hitEntity(stack, target, attacker);
    }




    public void registerItemModel() {
        Elementaristics.proxy.registerItemRenderer(this, 0, name);
    }

}
