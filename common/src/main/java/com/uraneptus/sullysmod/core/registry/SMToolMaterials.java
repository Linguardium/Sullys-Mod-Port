package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.core.other.tags.SMItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public class SMToolMaterials {
    // 	TagKey<Block> incorrectBlocksForDrops,
    // 	int durability,
    // 	float speed,
    // 	float attackDamageBonus,
    // 	int enchantmentValue,
    // 	TagKey<Item> repairItems

    // TODO: custom material types and values?
    public static final ToolMaterial PRIMITIVE_KNIFE = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 20, -2.5F,5, 0, SMItemTags.UNREPAIRABLE);
    public static final ToolMaterial GLASS = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1, -1.2F,4, 0, SMItemTags.UNREPAIRABLE);
}
