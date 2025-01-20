package com.uraneptus.sullysmod.core.other;

import com.uraneptus.sullysmod.core.other.tags.SMItemTags;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.key;

public class SMArmorMaterials {
    public static final ResourceKey<EquipmentAsset> MINERS_HELMET_ASSETS = key(EquipmentAssets.ROOT_ID, "miners_helmet");
    public static final ResourceKey<EquipmentAsset> SMALL_DENTED_HELMET_ASSETS = key(EquipmentAssets.ROOT_ID, "miners_helmet");
    public static final ResourceKey<EquipmentAsset> LOST_CROWN_ASSETS = key(EquipmentAssets.ROOT_ID, "miners_helmet");

    public static final ArmorMaterial MINERS_HELMET = new ArmorMaterial(
            9,
            Map.of(ArmorType.BOOTS, 1),
        // original appears to add 1 armor to feet?
        //                new int[]{1, 0, 0, 0},
            0,
            SMSounds.EQUIP_MINERS_HELMET,
            0.0F,
            0.0F,
            SMItemTags.UNREPAIRABLE,
            MINERS_HELMET_ASSETS
    );
//    ArmorMaterial LEATHER = new ArmorMaterial(5, Util.make(new EnumMap(ArmorType.class), enumMap -> {
//        enumMap.put(ArmorType.BOOTS, 1);
//        enumMap.put(ArmorType.LEGGINGS, 2);
//        enumMap.put(ArmorType.CHESTPLATE, 3);
//        enumMap.put(ArmorType.HELMET, 1);
//        enumMap.put(ArmorType.BODY, 3);
//    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ItemTags.REPAIRS_LEATHER_ARMOR, EquipmentAssets.LEATHER);

    //public record ArmorMaterial(
    //	int durability,
    //	Map<ArmorType, Integer> defense,
    //	int enchantmentValue,
    //	Holder<SoundEvent> equipSound,
    //	float toughness,
    //	float knockbackResistance,
    //	TagKey<Item> repairIngredient,
    //	ResourceKey<EquipmentAsset> assetId
    //) {
    public static final ArmorMaterial SMALL_DENTED_HELMET = new ArmorMaterial(
            9,
            Map.of(ArmorType.BOOTS, 1),
//            new int[]{1, 0, 0, 0},
            0,
            SMSounds.EQUIP_SMALL_DENTED_HELMET,
            0.0F, 0.0F,
            //() -> Ingredient.of(ItemStack.EMPTY)
            SMItemTags.UNREPAIRABLE,
            SMALL_DENTED_HELMET_ASSETS
    );

    public static final ArmorMaterial LOST_CROWN = new ArmorMaterial(
            9,
            Map.of(ArmorType.BOOTS, 1),
//            new int[]{1, 0, 0, 0},
            0,
            SMSounds.EQUIP_LOST_CROWN,
            0.0F, 0.0F,
//            () -> Ingredient.of(ItemStack.EMPTY)
            SMItemTags.UNREPAIRABLE,
            LOST_CROWN_ASSETS
    );
}
