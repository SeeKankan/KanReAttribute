package io.seekankan.github.kanreattribute.extensions

import org.bukkit.Location
import org.bukkit.entity.LivingEntity

/**
 * 获取实体的垂直中心点（胸腔位置）。
 * 通过计算脚底坐标与眼睛坐标的中点得出。
 *
 * 返回的 Location 是一个全新的实例，修改它不会影响实体本身。
 */
fun LivingEntity.midpoint(): Location {
    val base = this.location
    val eye = this.eyeLocation
    return Location(
        base.world,
        (base.x + eye.x) * 0.5,
        (base.y + eye.y) * 0.5,
        (base.z + eye.z) * 0.5
    )
}