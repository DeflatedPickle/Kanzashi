/* Copyright (c) 2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.kanzashi.mixin;

import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnusedMixin")
@Mixin(LivingEntity.class)
public class MixinLivingEntity {
  @Inject(method = "getPreferredEquipmentSlot", at = @At("TAIL"), cancellable = true)
  private static void flowerSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
    if (stack.getItem() instanceof BlockItem) {
      var block = ((BlockItem) stack.getItem()).getBlock();

      if (block instanceof PlantBlock && !(block instanceof CropBlock)) {
        cir.setReturnValue(EquipmentSlot.HEAD);
      }
    }
  }
}
