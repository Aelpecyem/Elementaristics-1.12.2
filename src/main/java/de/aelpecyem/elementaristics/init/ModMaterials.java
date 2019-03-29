package de.aelpecyem.elementaristics.init;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Resource;

public class ModMaterials {
    public static final ItemArmor.ArmorMaterial MATERIAL_ROBES =  EnumHelper.addArmorMaterial("material_robes", new ResourceLocation(Elementaristics.MODID, "material_robes").toString(), 16, new int[]{2, 4, 3, 1}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
}
