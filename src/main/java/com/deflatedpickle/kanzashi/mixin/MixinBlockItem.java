/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.kanzashi.mixin;

import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("ALL")
@Mixin(BlockItem.class)
public abstract class MixinBlockItem extends Item implements Wearable {
  public MixinBlockItem(Settings settings) {
    super(settings);
  }

  @Override
  @Nullable
  public SoundEvent getEquipSound() {
    return SoundEvents.BLOCK_FLOWERING_AZALEA_PLACE;
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    ItemStack itemStack = user.getStackInHand(hand);
    var block = ((BlockItem) itemStack.getItem()).getBlock();

    if (block instanceof PlantBlock && !(block instanceof CropBlock)) {
      EquipmentSlot equipmentSlot = EquipmentSlot.HEAD;
      ItemStack headStack = user.getEquippedStack(equipmentSlot);

      if (headStack.isEmpty()) {
        user.equipStack(equipmentSlot, itemStack.copy());

        itemStack.decrement(1);
        return TypedActionResult.success(itemStack, world.isClient());
      } else if ((headStack.getItem() instanceof BlockItem
          && ((BlockItem) headStack.getItem()).getBlock() instanceof PlantBlock)) {
        user.giveItemStack(headStack);
        user.equipStack(equipmentSlot, itemStack.copy());

        itemStack.decrement(1);
        return TypedActionResult.success(itemStack, world.isClient());
      }
    }
    return super.use(world, user, hand);
  }
}
