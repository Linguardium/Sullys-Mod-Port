package com.uraneptus.sullysmod.common.items;

import com.uraneptus.sullysmod.core.registry.SMBlocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class ArtifactHelmetItem extends ArmorItem {
    public ArtifactHelmetItem(ArmorMaterial material, Properties pProperties) {
        super(material, ArmorType.HELMET, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().getBlockState(pContext.getClickedPos()).is(SMBlocks.ITEM_STAND.get())) {
            return InteractionResult.PASS;
        }
        return super.useOn(pContext);
    }
// TODO: Helmet Overlay Rendering
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            @Override
//            public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
//                if (!stack.is(SMItems.SMALL_DENTED_HELMET.get())) return;
//
//                Minecraft minecraft = Minecraft.getInstance();
//                Gui gui = minecraft.gui;
//                GuiGraphics guiGraphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
//
//                gui.renderTextureOverlay(guiGraphics, location("textures/misc/tinyhelmetblur.png"), 1.0F);
//            }
//        });
//    }
}
