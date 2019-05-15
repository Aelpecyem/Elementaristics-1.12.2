package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Resource;

public class ModMaterials {
    public static final ItemArmor.ArmorMaterial MATERIAL_ROBES =  EnumHelper.addArmorMaterial("robes_cultist", new ResourceLocation(Elementaristics.MODID, "material_robes").toString(), 16, new int[]{2, 4, 3, 1}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static final Item.ToolMaterial MATERIAL_HAMMER = EnumHelper.addToolMaterial("hammer_heat", 2, 800, 3, 5, 20).setRepairItem(new ItemStack(ModItems.head_hammer));
    public static final Item.ToolMaterial MATERIAL_SWORD_ICE = EnumHelper.addToolMaterial("sword_ice", 2, 900, 3, 2, 20);
}
