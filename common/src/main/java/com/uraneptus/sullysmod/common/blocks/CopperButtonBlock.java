package com.uraneptus.sullysmod.common.blocks;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class CopperButtonBlock extends ButtonBlock {

    public CopperButtonBlock(Properties properties, BlockSetType pType, int pTicksToStayPressed) {
        super(pType, pTicksToStayPressed, properties);
    }
//    Replaced with platform specific access to waxed maps
//
//    @Override
//    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
//        //Scraping wax away list
//        HashMap<Block, Block> WAX_OFF_LIST = new HashMap<>();
//        WAX_OFF_LIST.put(SMBlocks.WAXED_COPPER_BUTTON.get(), SMBlocks.COPPER_BUTTON.get());
//        WAX_OFF_LIST.put(SMBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), SMBlocks.EXPOSED_COPPER_BUTTON.get());
//        WAX_OFF_LIST.put(SMBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), SMBlocks.WEATHERED_COPPER_BUTTON.get());
//        WAX_OFF_LIST.put(SMBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), SMBlocks.OXIDIZED_COPPER_BUTTON.get());
//        //Applying wax
//        HashMap<Block, Block> WAX_ON_LIST = new HashMap<>();
//        for (Map.Entry<Block, Block> entry : WAX_OFF_LIST.entrySet()) {
//            WAX_ON_LIST.put(entry.getValue(), entry.getKey());
//        }
//        WAX_ON_LIST.put(SMBlocks.COPPER_BUTTON.get(), SMBlocks.WAXED_COPPER_BUTTON.get());
//
//        //Stuff that makes this work
//        ItemStack stack = player.getItemInHand(hand);
//
//        InteractionResult returnResult = InteractionResult.PASS;
//
//        if (stack.getItem() instanceof AxeItem) {
//            if (WeatheringCopperButtonBlock.PREVIOUS_BY_BLOCK.get().containsKey(state.getBlock())) {
//                SMItemUtil.triggerItemUsedOnBlock(player, stack, pos);
//                level.setBlock(pos, WeatheringCopperButtonBlock.PREVIOUS_BY_BLOCK.get().get(state.getBlock()).withPropertiesOf(state), 11);
//                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
//                level.levelEvent(player, 3005, pos, 0);
//
//                SMItemUtil.damageItem(player, stack, hand);
//                returnResult = InteractionResult.sidedSuccess(true);
//            }
//            else if (WAX_OFF_LIST.containsKey(state.getBlock())) {
//                SMItemUtil.triggerItemUsedOnBlock(player, stack, pos);
//                level.setBlock(pos, WAX_OFF_LIST.get(state.getBlock()).withPropertiesOf(state), 11);
//                level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
//                level.levelEvent(player, 3004, pos, 0);
//
//                SMItemUtil.damageItem(player, stack, hand);
//                returnResult = InteractionResult.sidedSuccess(true);
//            }
//        }
//        else if (stack.getItem().equals(Items.HONEYCOMB)) {
//            if (WAX_ON_LIST.containsKey(state.getBlock())) {
//                SMItemUtil.triggerItemUsedOnBlock(player, stack, pos);
//                level.setBlock(pos, WAX_ON_LIST.get(state.getBlock()).withPropertiesOf(state), 11);
//                level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
//                level.levelEvent(player, 3003, pos, 0);
//
//                SMItemUtil.nonCreativeShrinkStack(player, stack);
//                returnResult = InteractionResult.sidedSuccess(true);
//            }
//        }
//        else {
//            returnResult = super.use(state, level, pos, player, hand, result);
//        }
//        return returnResult;
//    }
}