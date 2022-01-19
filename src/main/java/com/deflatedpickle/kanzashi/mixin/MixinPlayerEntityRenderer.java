/* Copyright (c) 2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.kanzashi.mixin;

import com.deflatedpickle.kanzashi.client.FlowerFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"UnusedMixin", "rawtypes", "unchecked"})
@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer extends LivingEntityRenderer {
  public MixinPlayerEntityRenderer(
      EntityRendererFactory.Context ctx, EntityModel model, float shadowRadius) {
    super(ctx, model, shadowRadius);
  }

  @Inject(method = "<init>", at = @At("TAIL"))
  public void addFlowerFeature(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
    addFeature(new FlowerFeatureRenderer(ctx, this));
  }
}
