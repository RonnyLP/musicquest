package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Pause: ImageVector
    get() {
        if (_Pause != null) {
            return _Pause!!
        }
        _Pause = ImageVector.Builder(
            name = "Pause",
            defaultWidth = 36.dp,
            defaultHeight = 36.dp,
            viewportWidth = 36f,
            viewportHeight = 36f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9f, 0f)
                arcTo(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.5f, 4.5f)
                verticalLineToRelative(27f)
                arcToRelative(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9f, 0f)
                verticalLineTo(4.5f)
                arcTo(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9f, 0f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(27f, 0f)
                arcToRelative(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.5f, 4.5f)
                verticalLineToRelative(27f)
                arcToRelative(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9f, 0f)
                verticalLineTo(4.5f)
                arcTo(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 27f, 0f)
                close()
            }
        }.build()

        return _Pause!!
    }

@Suppress("ObjectPropertyName")
private var _Pause: ImageVector? = null
