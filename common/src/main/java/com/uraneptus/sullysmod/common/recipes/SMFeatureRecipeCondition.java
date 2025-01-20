package com.uraneptus.sullysmod.common.recipes;

// TODO: Recipe Conditions
public class SMFeatureRecipeCondition {
//    private static final ResourceLocation ID = location("mod_features");
//    private final List<SMFeatures> condition;
//
//    public SMFeatureRecipeCondition(List<SMFeatures> condition) {
//        this.condition = condition;
//    }
//
//    @Override
//    public ResourceLocation getID() {
//        return ID;
//    }
//
//    @Override
//    public boolean test(IContext context) {
//        return condition.stream().allMatch(SMFeatures::isEnabled);
//    }
//
//    public static class Serializer implements IConditionSerializer<SMFeatureRecipeCondition> {
//        private final ResourceLocation location;
//
//        public Serializer() {
//            this.location = SullysMod.modPrefix("mod_features");
//        }
//
//        @Override
//        public void write(JsonObject json, SMFeatureRecipeCondition value) {
//            JsonArray values = new JsonArray();
//            for (SMFeatures feature : value.condition) {
//                values.add(feature.getSerializedName());
//            }
//            json.add("values", values);
//        }
//
//        @Override
//        public SMFeatureRecipeCondition read(JsonObject json) {
//            List<SMFeatures> features = new ArrayList<>();
//            for (JsonElement element : json.getAsJsonArray("values")) {
//                SMFeatures feature = SMFeatures.byName(element.getAsString());
//                features.add(feature);
//            }
//            return new SMFeatureRecipeCondition(features);
//        }
//
//        @Override
//        public ResourceLocation getID() {
//            return SMFeatureRecipeCondition.ID;
//        }
//    }
}
