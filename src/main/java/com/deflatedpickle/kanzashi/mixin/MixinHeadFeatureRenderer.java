/* Copyright (c) 2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.kanzashi.mixin;

import net.minecraft.block.PlantBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("UnusedMixin")
@Mixin(HeadFeatureRenderer.class)
public class MixinHeadFeatureRenderer {
  @Redirect(
      method =
          "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
  public void renderItem(
      HeldItemRenderer instance,
      LivingEntity entity,
      ItemStack stack,
      ModelTransformation.Mode renderMode,
      boolean leftHanded,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light) {
    if (!(stack.getItem() instanceof BlockItem
        && ((BlockItem) stack.getItem()).getBlock() instanceof PlantBlock)) {
      MinecraftClient.getInstance()
          .getItemRenderer()
          .renderItem(
              entity,
              stack,
              ModelTransformation.Mode.HEAD,
              false,
              matrices,
              vertexConsumers,
              stack.getEntityHolder().getWorld(),
              light,
              0,
              0);
    }
  }
}
