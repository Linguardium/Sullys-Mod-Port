package com.uraneptus.sullysmod.common.items;

import com.uraneptus.sullysmod.core.other.SMArmorMaterials;

public class MinersHelmetItem extends ArtifactHelmetItem {

    public MinersHelmetItem(Properties pProperties) {
        super(SMArmorMaterials.MINERS_HELMET, pProperties);
    }
// TODO: Armor rendering?
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            HumanoidModel<?> model;
//
//            @Override
//            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> properties) {
//                if (slot == EquipmentSlot.HEAD) {
//                    model = MinersHelmetModel.INSTANCE;
//                    model.head.copyFrom(properties.head);
//                    return model;
//                }
//                return properties;
//            }
//        });
//    }
}
