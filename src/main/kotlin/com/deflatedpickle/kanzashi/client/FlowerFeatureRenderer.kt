/* Copyright (c) 2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.kanzashi.client

import net.minecraft.block.PlantBlock
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayers
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.LivingEntityRenderer
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.BlockItem
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3f

class FlowerFeatureRenderer<T : LivingEntity, M : BipedEntityModel<T>>(
    context: EntityRendererFactory.Context,
    entityRenderer: LivingEntityRenderer<T, M>
) : FeatureRenderer<T, M>(entityRenderer) {
    override fun render(
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        tickDelta: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        val itemStack = (entity as LivingEntity).getEquippedStack(EquipmentSlot.HEAD)
        val item = itemStack.item

        matrices.push()

        if (item is BlockItem && item.block is PlantBlock) {
            contextModel.head.rotate(matrices)
            HeadFeatureRenderer.translate(matrices, false)

            matrices.translate(-0.6, -0.1, -0.4)
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(25f))

            MinecraftClient.getInstance().blockRenderManager.renderBlock(
                item.block.defaultState,
                BlockPos.ORIGIN,
                entity.world,
                matrices,
                vertexConsumers.getBuffer(RenderLayers.getBlockLayer(item.block.defaultState)),
                true,
                entity.random,
            )
        }

        matrices.pop()
    }
}
