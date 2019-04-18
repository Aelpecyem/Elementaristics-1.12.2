package de.aelpecyem.elementaristics.items.base.armor;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.ModMaterials;
import de.aelpecyem.elementaristics.util.IHasModel;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class RobesCultist extends ItemArmor implements IHasModel {
    protected String name;
    public RobesCultist(String name, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(ModMaterials.MATERIAL_ROBES, renderIndexIn, equipmentSlotIn);
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementaristics.tab);
        ModItems.ITEMS.add(this);
    }


        @SideOnly(Side.CLIENT)
        public void registerItemModel() {
            Elementaristics.proxy.registerItemRenderer(this, 0, name);
        }



    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {

        super.onArmorTick(world, player, itemStack);
    }
}
