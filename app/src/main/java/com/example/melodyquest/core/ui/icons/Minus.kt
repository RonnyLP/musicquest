package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Minus: ImageVector
    get() {
        if (_Minus != null) {
            return _Minus!!
        }
        _Minus = ImageVector.Builder(
            name = "Minus",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(88.7f, 55.65f)
                horizontalLineToRelative(-77.4f)
                curveToRelative(-3.12f, 0f, -5.65f, -2.53f, -5.65f, -5.65f)
                reflectiveCurveToRelative(2.53f, -5.65f, 5.65f, -5.65f)
                horizontalLineToRelative(77.4f)
                curveToRelative(3.12f, 0f, 5.65f, 2.53f, 5.65f, 5.65f)
                reflectiveCurveToRelative(-2.53f, 5.65f, -5.65f, 5.65f)
                close()
            }
        }.build()

        return _Minus!!
    }

@Suppress("ObjectPropertyName")
private var _Minus: ImageVector? = null
