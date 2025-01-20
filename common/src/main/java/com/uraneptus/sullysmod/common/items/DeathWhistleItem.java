package com.uraneptus.sullysmod.common.items;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.uraneptus.sullysmod.core.other.SMProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DeathWhistleItem extends Item {
    private final Supplier<List<SoundEvent>> DEATH_SOUNDS = Suppliers.memoize(()->ImmutableList.copyOf(
                    BuiltInRegistries.SOUND_EVENT.entrySet().stream()
                    .filter(soundEventRef -> soundEventRef.getKey().location().getPath().contains("death"))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList())
            ));

    public DeathWhistleItem() {
        super(SMProperties.Items.artifacts());
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        RandomSource random = player.getRandom();
        List<SoundEvent> soundEvents = DEATH_SOUNDS.get();
        SoundEvent soundevent = soundEvents.get(player.getRandom().nextInt(soundEvents.size()));
        level.playSound(player, player, soundevent, SoundSource.RECORDS, 16.0F, 1.0F);
        level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
        player.getCooldowns().addCooldown(itemstack, 60);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

}
